package state.shutdown;

import java.util.ArrayList;
import java.util.List;

public class BasicShutdownComponentManager implements ShutdownComponentManager {
	
	protected List<ShutdownComponent> shutdown = new ArrayList<>();
	
	public void registerShutdownComponent( ShutdownComponent c ) {
		shutdown.add( c );
	}
	
	public void shutdownComponents() {
		shutdown.forEach( c -> c.shutdown() );
	}

}