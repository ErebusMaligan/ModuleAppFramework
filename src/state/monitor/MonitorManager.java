package state.monitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 2:47:04 PM 
 */
public class MonitorManager {
	
	private Map<String, AbstractMonitor> monitors = new HashMap<>();

	private Map<String, MonitorData> dataMap = new HashMap<>();
	
	public void startAllMonitors() {
		monitors.values().forEach( m -> m.start() );
	}
	
	public void stopAllMonitors() {
		monitors.values().forEach( m -> m.stopRunning() );
	}
	
	public void foricblyStopAllMonitors() {
		monitors.values().forEach( m -> m.interrupt() );
	}
	
	public boolean monitorsHalted() {
		boolean ret = true;
		for ( AbstractMonitor m : monitors.values() ) { ret = ret && m.isHalted(); };
		return ret;
	}
	
	public MonitorData getDataByName( String name ) {
		return dataMap.get( name );
	}
	
	public AbstractMonitor getMonitorByName( String name ) {
		return monitors.get( name );
	}
	
	public Map<String, AbstractMonitor> getMonitors() {
		return monitors;
	}
	
	public Map<String, MonitorData> getData() {
		return dataMap;
	}
}