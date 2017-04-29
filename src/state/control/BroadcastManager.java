package state.control;

public interface BroadcastManager {
	
	public void addBroadcastListener( BroadcastListener l );
	
	public void broadcast( BroadcastEvent e );
}