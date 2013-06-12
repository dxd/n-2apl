package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.javadocmd.simplelatlng.LatLng;

import dataJSon.Status;
import tuplespace.Cell;
import tuplespace.Position;
import tuplespace.Time;
import tuplespace.TimeEntry;
import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.lease.LeaseDeniedException;
import net.jini.core.lookup.ServiceMatches;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.ServiceDiscoveryManager;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.core.transaction.TransactionFactory;
import net.jini.core.transaction.server.TransactionManager;
import net.jini.space.JavaSpace;


public class JSpace {
	
	public static JavaSpace space = null;
	ServiceRegistrar sr = null;
	static ServiceDiscoveryManager sdm;
	private static TransactionManager transManager;
	//private static LeaseRenewalManager leaseRenewalManager;
	public static String[] agents = {"a1", "a2", "a3", "t1", "c1"};
	public Integer clock = 0;

	public JSpace(){
		init();
	}
	
	public void init() {
		
        System.setSecurityManager(new RMISecurityManager());
      
        try {
        	File file = new File("./log/"+ new Date(System.currentTimeMillis()) +".log");

            // Create file if it does not exist
            boolean success = file.createNewFile();
            if (success) {
                // File did not exist and was created
            } else {
                // File already exists
            }
            
            PrintStream printStream;
    		try {
    			printStream = new PrintStream(new FileOutputStream(file));
    			System.setOut(printStream);
    		} catch (FileNotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        } catch (IOException e) {
        	
        }
		
		LookupLocator ll = null;
		try {
			ll = new LookupLocator("jini://kafka.cs.nott.ac.uk:4160");
			//ll = new LookupLocator("jini://10.154.219.251");
			//ll = new LookupLocator("jini://10.154.154.26");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sr = ll.getRegistrar();
		} catch (IOException e) {
			

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println("Service Registrar: "+sr.getServiceID());


		ServiceTemplate template = new ServiceTemplate(null, new Class[] { JavaSpace.class }, null);

		ServiceMatches sms = null;
		try {
			sms = sr.lookup(template, 10);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		if(0 < sms.items.length) {
		    space = (JavaSpace) sms.items[0].service;
		    System.out.println("Java Space found.");
		   
		   
		} else {
		    System.out.println("No Java Space found.");
		}
		ServiceTemplate trans = new ServiceTemplate(null, new Class[] { TransactionManager.class }, null);

		ServiceMatches sms1 = null;
		try {
			sms1 = sr.lookup(trans, 10);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		if(0 < sms1.items.length) {
		    transManager = (TransactionManager) sms1.items[0].service;
		    System.out.println("TransactionManager found.");
		   
		   
		} else {
		    System.out.println("No TransactionManager found.");
		}
/*		try {
			sdm = new ServiceDiscoveryManager(null,null);
			leaseRenewalManager = sdm.getLeaseRenewalManager();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

	public boolean error() {
		
		return space == null;
	}

	public ArrayList<TimeEntry> readUpdate(TimeEntry te, Timestamp date, Timestamp newTime) {
	
		ArrayList<TimeEntry> t = (ArrayList<TimeEntry>) getAllFromDate(te, date,newTime);
		System.out.println(te.toString() + " "+date.toString()+" - " + newTime.toString());
		//System.out.println("result size is " + t.size());
		return t;
	}
	
	
	
	private ArrayList<TimeEntry> getAllFromDate(TimeEntry te, Timestamp date, Timestamp newTime) {
		TimeEntry entry;
		try {
			Transaction.Created trans = TransactionFactory.create(transManager, Lease.FOREVER);
			//leaseRenewalManager.renewUntil(trans.lease, Lease.FOREVER, null);
			Transaction txn = trans.transaction;
			try {
				ArrayList<TimeEntry> result = new ArrayList<TimeEntry>();
				while ((entry = (TimeEntry) space.take(te, txn, 200)) != null){
					//System.out.println(entry.toString());
					result.add(entry);
				}
				ArrayList<TimeEntry> e = getFromDate(result,date,newTime);
				//System.out.println(result.toString());
				txn.abort();
				//leaseRenewalManager.cancel(trans.lease);
				return e;
			} catch (UnusableEntryException e) {
				e.printStackTrace();
			} catch (TransactionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
		} catch (LeaseDeniedException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private ArrayList<TimeEntry> getFromDate(ArrayList<TimeEntry> result,
			Timestamp date, Timestamp newTime) {
		ArrayList<TimeEntry> results = new ArrayList<TimeEntry>();
		if (result.size() > 0) {
			for (TimeEntry te : result) {
				if (te.getTime().after(date) && te.getTime().before(newTime))
					results.add(te);
			}
			return results;
		}

		return null;
	}

	void write(TimeEntry e)
	{
		System.out.println(e.toString());
		try {
			if (e.getTime() == null)
				e.setTime();
			if (e.getClock() == null)
				e.setClock(clock);
			Lease l = space.write(e, null, Lease.FOREVER);

		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (TransactionException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	void writeTime(int clock) {
		this.clock = clock;
		Time time = new Time(clock);
		write(time);	
	}





/*			theManager.renewFor(myReg.getLease(), Lease.FOREVER,
                30000, new DebugListener());*/
	
}
