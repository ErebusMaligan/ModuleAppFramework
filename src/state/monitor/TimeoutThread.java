package state.monitor;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 7, 2015, 5:58:09 AM 
 */
public class TimeoutThread extends Thread {

	private Runnable r;
	
	private boolean kill;
	
	public TimeoutThread( Runnable r ) {
		this.r = r;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep( 3000 );
			if ( !kill ) {
				r.run();
			}
		} catch ( InterruptedException e ) {
			//i don't care if these are interrupted they can just die if that is the case since it's probably during shutdown
		}
	}
	
	public void stopRunning() {
		kill = true;
	}
}