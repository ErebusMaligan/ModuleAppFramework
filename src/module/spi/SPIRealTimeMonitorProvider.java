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
 * Created: Apr 25, 2016, 1:41:08 PM 
 */
public interface SPIRealTimeMonitorProvider extends AppModuleSPI {
	
	public void initRTDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh );
	
	public List<AbstractMonitor> getRTDataMonitors();
}