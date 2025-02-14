package ui.theme;

import static ui.theme.ThemeConstants.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import props.utils.ColorUtils;
import statics.UIUtils;
import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:15:04 AM 
 */
public class BasicColorSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		ThemeConstants.BACKGROUND = ColorUtils.toColor( e.get( XBG ) );
		ThemeConstants.FOREGROUND = ColorUtils.toColor( e.get( XFG ) );
		UIUtils.FOREGROUND = ThemeConstants.FOREGROUND;
		UIUtils.BACKGROUND = ThemeConstants.BACKGROUND;
		ThemeConstants.FOREGROUND_DARKER = ThemeConstants.FOREGROUND.darker().darker().darker().darker();
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( XCOLORS, values );
		values.put( XBG, new String[] { ColorUtils.toHex( ThemeConstants.BACKGROUND ) } );
		values.put( XFG, new String[] { ColorUtils.toHex( ThemeConstants.FOREGROUND ) } );
		return ret;
	}
}