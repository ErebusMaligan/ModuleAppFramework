package ui.window;

import gui.windowmanager.ComponentWindow;
import gui.windowmanager.WindowDefinition;
import gui.windowmanager.WindowManager;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 1:51:26 AM 
 */
public class AppWindow extends ComponentWindow {
	
	private static final long serialVersionUID = 1L;
	
	public AppWindow( Object state, WindowManager wm, WindowDefinition def ) {
		super( state, wm, def );
	}

	@Override
	public void additionalConfiguration( Object state ) {
		if ( WindowConstants.APP_ICON != null ) {
			setFrameIcon( WindowConstants.APP_ICON );
		}
		UIUtils.setColors( this );
	}
}