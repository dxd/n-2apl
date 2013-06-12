package game;
import java.util.Date;
import java.util.Timer;

public class Start {
	

	//private static Date startTime;
	private static int gamePace = 1000;
	
	private static Timer timer;
	
	private static JSpace jspace;
	private static Synchronization synchro;
	
	public static GameStep gs;
	
	public static void main(String[] args) {
		
		
		jspace = new JSpace();
		
		if (jspace.error())
			return;
		initiate();
		


	}

	public static void initiate() {
		
		Game.initiateGrid();
		//startTime = new Date();
		
	    timer = new Timer();
	    synchro = new Synchronization(jspace);
		
	    gs = new GameStep(synchro);
	    
	    
	    
	    timer.schedule(gs ,0, gamePace);
	  }
}
