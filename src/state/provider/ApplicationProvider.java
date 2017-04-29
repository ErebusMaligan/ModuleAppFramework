package state.provider;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;

import gui.textarea.DefaultJTextAreaStreamManager;
import gui.windowmanager.TabManager;
import gui.windowmanager.WindowManager;
import module.AppModule;
import module.AppModuleManager;
import module.spi.SPIMonitorDataProvider;
import module.spi.SPIWindowDefinitionProvider;
import ssh.SSHManager;
import state.control.BasicBroadcastManager;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.control.BroadcastManager;
import state.monitor.MonitorManager;
import state.shutdown.BasicShutdownComponentManager;
import state.shutdown.ShutdownComponent;
import state.shutdown.ShutdownComponentManager;
import statics.LAFUtils;
import ui.log.OutputLogDefinition;
import ui.ssh.SSHSessionDefinition;
import ui.theme.ThemeConstants;
import ui.window.AbstractApplicationFrame;
import ui.window.AppTabManager;
import ui.window.WindowConstants;
import xml.SimpleFileXMLDocumentHandler;
import xml.XMLSettingsWriter;

public abstract class ApplicationProvider implements XMLSettingsWriter, BroadcastManager, ShutdownComponentManager {

	protected BasicShutdownComponentManager shutdown = new BasicShutdownComponentManager();
	
	protected BasicBroadcastManager broadcast = new BasicBroadcastManager();
	
	protected DefaultJTextAreaStreamManager output = new DefaultJTextAreaStreamManager();
	
	protected AppModuleManager manager;
	
	protected SSHManager sshm;
	
	protected SimpleFileXMLDocumentHandler doc;
	
	protected TabManager tab;
	
	protected AbstractApplicationFrame frame;

	protected MonitorManager mm = new MonitorManager();
	
	public ApplicationProvider() {
		init();
		loadSettings();
		initSSH();
		initModules();
		initWindows();
		initDataProviders();
		initOtherSPI();
		initFrame();
		loadTabs();
		handleAutoStart();
	}
	
		
	protected abstract List<AppModule> getModuleList();
	
	protected abstract void initFrame();

	//protected init stuff - for potential override
	protected void init() {
		tab = new AppTabManager( this );
	}
	
	protected void loadSettings() {
		doc.loadDoc();
		
		LAFUtils.setAllLAFFonts( new Font( "Tahoma", Font.PLAIN, 11 ) );
		
		//setup internal frames to use my colors that were just loaded
		LAFUtils.setAllLAFInternalFrames( ThemeConstants.FOREGROUND.darker(), ThemeConstants.BACKGROUND, 35, 21, 21 );  //ThemeConstants.FOREGROUND_DARKER is too dark for this	

	}
	
	/**
	 * create (but don't start) all ssh processes
	 */
	protected void initSSH() {
		sshm = new SSHManager( this );
	}
	
	protected void initModules() {
		manager = new AppModuleManager( getModuleList() );
		manager.getAllModules().forEach( m -> m.init() );
	}
	
	protected void initWindows() {
		//load all windowDefinitions
		manager.getModulesBySPIType( SPIWindowDefinitionProvider.class ).forEach( m -> m.loadWindowDefinitions() ); //same as above... colors aren't properly set if not loaded after loading doc
		
		WindowManager.addWindowDefinition( new SSHSessionDefinition() );
		WindowManager.addWindowDefinition( new OutputLogDefinition() );
	}
	
	protected void initDataProviders() {
		//add all data to the data map
		manager.getModulesBySPIType( SPIMonitorDataProvider.class ).forEach( m -> m.getMonitorData().forEach( d -> mm.getData().put( d.getClass().getName(), d ) ) );
	}
	
	protected void initOtherSPI() {
		
	}
	
	protected GraphicsDevice determineMonitor() {
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if ( WindowConstants.FRAME_SCREEN != null ) {
			GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			for ( GraphicsDevice d : screens ) {
				if ( d.getIDstring().equals( WindowConstants.FRAME_SCREEN ) ) {
					device = d;
					break;
				}
			}
		}
		return device;
	}
	
	protected void loadTabs() {
		tab.additionalConfiguration( this );  //if this isn't called here, the tabs don't get properly colored because it was called at construction before xml properties were loaded, so tabs have default colors
		tab.loadPreviousConfig();
	}
	
	protected void handleAutoStart() {
		//auto start stuff
		if ( ProviderConstants.AUTO ) {
			sshm.connectAllSessions();
			try {
				Thread.sleep( 1000 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
			mm.startAllMonitors();
		}
	}
	

	
	
	//accessors
	public SSHManager getSSHManager() {
		return sshm;
	}
	
	public DefaultJTextAreaStreamManager getOutput() {
		return output;
	}
	
	public TabManager getTabManager() {
		return tab;
	}
	
	public AbstractApplicationFrame getFrame() {
		return frame;
	}
	
	public AppModuleManager getModuleManager() {
		return manager;
	}

	public MonitorManager getMonitorManager() {
		return mm;
	}
	
	@Override
	public void writeSettings() {
		doc.createDoc();
	}

	@Override
	public void addBroadcastListener( BroadcastListener l ) {
		broadcast.addBroadcastListener( l );
		
	}

	@Override
	public void broadcast( BroadcastEvent e ) {
		broadcast.broadcast( e );
	}

	@Override
	public void registerShutdownComponent( ShutdownComponent c ) {
		shutdown.registerShutdownComponent( c );
		
	}

	@Override
	public void shutdownComponents() {
		shutdown.shutdownComponents();
	}
}