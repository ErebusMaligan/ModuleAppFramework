package state.provider;

import static state.provider.ProviderConstants.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:08:24 AM 
 */
public class DebugSettings implements XMLValues {
	
	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		DEBUG = Boolean.parseBoolean( e.get( XDEBUG ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( XDEBUG, values );
		values.put( XDEBUG, new String[] { String.valueOf( DEBUG ) } );
		return ret;
	}
}