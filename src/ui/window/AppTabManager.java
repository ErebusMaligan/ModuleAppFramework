package ui.window;

import java.awt.Graphics;

import javax.swing.JDesktopPane;

import gui.windowmanager.ComponentWindowFactory;
import gui.windowmanager.TabManager;
import gui.windowmanager.WindowManager;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 3:02:49 AM 
 */
public class AppTabManager extends TabManager {

	public AppTabManager( Object state ) {
		super( state, ( s, wm, d ) -> { return new AppWindow( s, wm, d ); } );
	}

	@Override
	public WindowManager createWindowManager( String name ) {
		return new CustomWindowManager( name, state, factory );
	}
	
	@Override
	public void additionalConfiguration( Object state ) {
		UIUtils.setTabUI( tabPane );
		UIUtils.setColors( tabPane );
	}
	
	@Override
	public void loadPreviousConfig() {
		System.out.println( "Loadeding Previous Window Configuration..." );
		super.loadPreviousConfig();
		System.out.println( "Previous Window Configuration Loaded" );
	}
	
	private class CustomWindowManager extends WindowManager {
		
		public CustomWindowManager( String name, Object state, ComponentWindowFactory factory ) {
			super( name, state, factory );
		}
		
		@Override
		public JDesktopPane createDesktopPane() {
			return new JDesktopPane() {
				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent( Graphics g ) {
					super.paintComponent( g );
					g.setColor( ThemeConstants.BACKGROUND );
					g.fillRect( 0, 0, getWidth(), getHeight() );
				}
			};
		}
	}
}