package envJavaSpace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import tuplespace.Obligation;
import tuplespace.Points;
import tuplespace.Prohibition;
import tuplespace.Reading;
import tuplespace.TimeEntry;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;

public class AgentHandler extends UnicastRemoteObject implements RemoteEventListener {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	SpaceTest spaceTest;
	String agent;
	HashMap<String, Timestamp> timestamps;
	
	public AgentHandler(SpaceTest spaceTest, String agent) throws RemoteException {
		
		this.spaceTest = spaceTest;
		this.agent = agent;
		timestamps = new HashMap<String,Timestamp>();
    }

	public void notify(RemoteEvent anEvent) {
		//System.out.println("agent notification "+agent+" number "+anEvent.getSequenceNumber());
        try {
        	String type = anEvent.getRegistrationObject().get().toString();
        	Timestamp newTime = new Timestamp(System.currentTimeMillis());
        	ArrayList<TimeEntry> r = new ArrayList<TimeEntry>();
            //System.out.println("Got event: " + anEvent.getSource() + ", " +
            //                   anEvent.getID() + ", " +
            //                   anEvent.getSequenceNumber() + ", " + 
            //                   anEvent.getRegistrationObject().get());
            
            if (type.equals("reading")) {
            	System.out.println("agent position notification "+agent+" number "+anEvent.getSequenceNumber());
            	Reading temp = new Reading(agent);
            	r = spaceTest.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            else if (type.equals("obligation")) {
            	System.out.println("agent obligation notification "+agent+" number "+anEvent.getSequenceNumber());
            	Obligation temp = new Obligation(agent);
            	r = spaceTest.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            else if (type.equals("prohibition")) {
            	System.out.println("agent prohibition notification "+agent+" number "+anEvent.getSequenceNumber());
            	Prohibition temp = new Prohibition(agent);
            	r = spaceTest.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            else if (type.equals("points")) {
            	System.out.println("agent points notification "+agent+" number "+anEvent.getSequenceNumber());
            	Points temp = new Points(agent);
            	r = spaceTest.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            timestamps.put(type, newTime);
            spaceTest.notifyAgent(agent, r);
            
        } catch (Exception anE) {
           System.out.println("Error while processing notification");
            anE.printStackTrace(System.out);
        }
    }
}

