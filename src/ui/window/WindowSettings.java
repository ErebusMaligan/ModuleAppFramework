package ui.window;

import static ui.window.WindowConstants.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:15:04 AM 
 */
public class WindowSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		FRAME_H = Integer.parseInt( e.get( XWH ) );
		FRAME_W = Integer.parseInt( e.get( XWW ) );
		FRAME_X = Integer.parseInt( e.get( XWX ) );
		FRAME_Y = Integer.parseInt( e.get( XWY ) );
		FRAME_MAX_STATE = Integer.parseInt( e.get( XWM ) );
		FRAME_SCREEN = e.get( XWS );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( XWINDOW, values );
		values.put( XWH, new String[] { String.valueOf( FRAME_H ) } );
		values.put( XWW, new String[] { String.valueOf( FRAME_W ) } );
		values.put( XWX, new String[] { String.valueOf( FRAME_X ) } );
		values.put( XWY, new String[] { String.valueOf( FRAME_Y ) } );
		values.put( XWM, new String[] { String.valueOf( FRAME_MAX_STATE ) } );
		values.put( XWS, new String[] { FRAME_SCREEN } );
		return ret;
	}
}