package envJavaSpace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuplespace.Obligation;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.space.JavaSpace;

public class OrgHandler extends UnicastRemoteObject implements RemoteEventListener {

	SpaceTest spaceTest;


	public OrgHandler(SpaceTest spaceTest) throws RemoteException{ 
		
		this.spaceTest = spaceTest;
		
	}

	public synchronized void notify(RemoteEvent anEvent) {

        try {
        	String[] type = (String[]) anEvent.getRegistrationObject().get();
           // System.out.println("Got event: " + anEvent.getSource() + ", " +
             //                  anEvent.getID() + ", " +
               //                anEvent.getSequenceNumber() + ", " + 
                 //              anEvent.getRegistrationObject().get());
            
            if (type[0].equals("position")) {
            	System.out.println("org position notification");
            	spaceTest.notifyOrg();
            }
            else if (type[0].equals("reading")) {
            	System.out.println("org reading notification");
            	spaceTest.notifyOrg();
            }
            else if (type[0].equals("points")) {
            	System.out.println("org points notification");
            	spaceTest.notifyOrg();
            }
            else if (type[0].equals("cargo")) {
            	System.out.println("org cargo notification");
            	spaceTest.notifyOrg();
            }
            else if (type[0].equals("coin")) {
            	System.out.println("org coin notification");
            	spaceTest.notifyOrg();
            }

            

        } catch (Exception anE) {
            System.out.println("Error while procession organizaiton notification");
            anE.printStackTrace(System.out);
        }
    }
}


