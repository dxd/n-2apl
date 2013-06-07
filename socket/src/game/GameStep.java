package game;
import java.util.TimerTask;

import com.javadocmd.simplelatlng.LatLng;


public class GameStep extends TimerTask
  {
		
		private Synchronization synchro;
		
		private int clock = 0;
		
		public int getClock() {
			return clock;
		}


		public GameStep (Synchronization request)
		{
			this.synchro = request;
		}
  
	    public void run()
	    {
	    	clock++;

			
	    	synchro.run(clock);
			System.out.println("Clock: " + clock);
	    }


		
  }