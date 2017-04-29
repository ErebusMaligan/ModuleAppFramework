package module.spi;

import java.util.List;

import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 25, 2016, 1:27:57 PM 
 */
public interface SPIDataMonitorProvider extends AppModuleSPI {
	
	public void initDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh );
	
	public List<AbstractMonitor> getDataMonitors();
}