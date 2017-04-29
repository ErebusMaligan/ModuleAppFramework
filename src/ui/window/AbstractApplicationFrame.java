package ui.window;

import static ui.window.WindowConstants.FRAME_H;
import static ui.window.WindowConstants.FRAME_MAX_STATE;
import static ui.window.WindowConstants.FRAME_SCREEN;
import static ui.window.WindowConstants.FRAME_TITLE;
import static ui.window.WindowConstants.FRAME_W;
import static ui.window.WindowConstants.FRAME_X;
import static ui.window.WindowConstants.FRAME_Y;

import java.awt.GraphicsDevice;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import gui.dialog.BusyDialog;
import gui.progress.DigitalJProgressBar;
import gui.windowmanager.TabManager;
import ssh.SSHManager;
import state.monitor.MonitorManager;
import state.shutdown.ShutdownComponentManager;
import statics.UIUtils;
import xml.XMLSettingsWriter;

public abstract class AbstractApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public AbstractApplicationFrame( TabManager tab, SSHManager ssh, MonitorManager monitor, ShutdownComponentManager shutdown, GraphicsDevice d ) {
		super( d.getDefaultConfiguration() );
		setExtendedState( FRAME_MAX_STATE );
		setLocation( FRAME_X, FRAME_Y );
		setSize( FRAME_W, FRAME_H );
		setTitle( FRAME_TITLE );
		setIconImage( WindowConstants.APP_ICON.getImage() );
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				new BusyDialog( AbstractApplicationFrame.this, "Halting Monitor Threads" ) {
					private static final long serialVersionUID = 1L;
					@Override
					public void executeTask() {
						shutdown.shutdownComponents();
						monitor.stopAllMonitors();
						if ( !monitor.monitorsHalted() ) {
							try {
								Thread.sleep( 3500 );  //give 3.5 seconds to shutdown normally... then force shutdown
							} catch ( InterruptedException e1 ) {
								e1.printStackTrace();
							}
						}
						monitor.foricblyStopAllMonitors();
						while ( !monitor.monitorsHalted() ) {
							try {
								Thread.sleep( 10 );
							} catch ( InterruptedException e1 ) {
								e1.printStackTrace();
							}
						}
					}
					
					@Override
					public JProgressBar getProgressBar() {
						DigitalJProgressBar bar = new DigitalJProgressBar( 0, 1000 );
						bar.setSegments( 10 );
						UIUtils.setColors( bar );
						return bar;
					}
				};
				new BusyDialog( AbstractApplicationFrame.this, "Disconnecting SSH Sessions" ) {
					private static final long serialVersionUID = 1L;
					@Override
					public void executeTask() {
						ssh.shutdownSSHSessions();
					}
					
					@Override
					public JProgressBar getProgressBar() {
						DigitalJProgressBar bar = new DigitalJProgressBar( 0, 1000 );
						bar.setSegments( 10 );
						UIUtils.setColors( bar );
						return bar;
					}
				};
				tab.getAllWindows().forEach( w -> { 
					try {
						w.setClosed( true );
					} catch ( Exception e1 ) {
						e1.printStackTrace();
					}
				} );
				AbstractApplicationFrame.this.dispose();
			}
		} );

	}
	
	public void finishConstructor() {
		construct();
		finishInit();
	}
	
	
	public void saveWindowSettings( XMLSettingsWriter xml ) {
		FRAME_H = getHeight();
		FRAME_W = getWidth();
		FRAME_X = getLocation().x;
		FRAME_Y = getLocation().y;
		FRAME_MAX_STATE = getExtendedState();
		FRAME_SCREEN = getGraphicsConfiguration().getDevice().getIDstring();
		xml.writeSettings();
	}

	public abstract void construct();
	
	public abstract void finishInit();
	
}
