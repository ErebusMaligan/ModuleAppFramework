package module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.spi.AppModuleSPI;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 1, 2016, 4:37:47 PM 
 */
public class AppModuleManager {
	
	private Map<String, AppModule> modules = new HashMap<>();
	
	public AppModuleManager( List<AppModule> loadMods ) {
		loadMods.forEach( m -> modules.put( m.getClass().getName(), m ) );
	}
	
	public List<AppModule> getAllModules() {
		return Collections.unmodifiableList( new ArrayList<AppModule>( modules.values() ) );
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AppModuleSPI> List<T> getModulesBySPIType( Class<T> spi ) {
		List<T> ret = new ArrayList<>();
		modules.values().forEach( m -> {
			if ( spi.isInstance( m ) ) {
				ret.add( (T)m );
			}
		} );
		return ret;//(List<T>)modules.values().stream().filter( m -> spi.isInstance( m ) ).collect( Collectors.toList() );
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AppModule> T getModuleByModuleClass( Class<T> module ) {
		return (T)modules.values().stream().filter( m -> module.isInstance( m ) ).findFirst().get();
	}
}