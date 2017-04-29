package state.control;

import java.lang.ref.WeakReference;
import java.util.Vector;

public class BasicBroadcastManager implements BroadcastManager {

	protected Vector<WeakReference<BroadcastListener>> broadcastListeners = new Vector<>();
	
	@Override
	public void addBroadcastListener( BroadcastListener l ) {
		broadcastListeners.add( new WeakReference<BroadcastListener>( l ) );
	}
	
	@Override
	public void broadcast( BroadcastEvent e ) {
		System.out.println( "Broadcasting: " + e );
		broadcastListeners.forEach( l -> l.get().broadcastReceived( e ) );
	}
}