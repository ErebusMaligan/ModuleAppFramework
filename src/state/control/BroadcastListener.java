package state.control;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 3, 2015, 10:33:17 PM 
 */
@FunctionalInterface
public interface BroadcastListener {
	public void broadcastReceived( BroadcastEvent e );
}