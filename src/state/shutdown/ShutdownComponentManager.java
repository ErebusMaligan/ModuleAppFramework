package state.shutdown;

public interface ShutdownComponentManager {

	public void registerShutdownComponent( ShutdownComponent c );
	
	public void shutdownComponents();
	
}