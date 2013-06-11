package game;
import java.util.Date;
import java.util.Timer;

public class Start {
	

	private static Date startTime;
	private static int gamePace = 1000;
	
	private static Timer timer;
	
	private static JSpace jspace;
	private static Synchronization synchro;
	
	public static GameStep gs;
	
	public static void main(String[] args) {
		
		jspace = new JSpace(gs);
		
		if (jspace.error())
			return;
		
		synchro = new Synchronization(jspace);
		initiate();


	}

	public static void initiate() {
		
		startTime = new Date();
		
	    timer = new Timer();
	    gs = new GameStep(synchro);
	    timer.schedule(gs ,0, gamePace);
	    
	    Game.initiateGrid();
	    
	  }
}
