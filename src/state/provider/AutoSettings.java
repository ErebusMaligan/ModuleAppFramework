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
 * Created: Oct 12, 2015, 12:58:51 AM 
 */
public class AutoSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		AUTO = Boolean.parseBoolean( e.get( XAUTO ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( XAUTO, values );
		values.put( XAUTO, new String[] { String.valueOf( AUTO ) } );
		return ret;
	}
}