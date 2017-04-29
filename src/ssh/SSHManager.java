package ssh;

import static ssh.SSHConstants.SSH_APP_PATH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import process.TerminalProcess;
import state.control.BroadcastManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:27:28 PM 
 */
public class SSHManager {
	
	private Map<String, SSHSession> sessions;
	
	public SSHManager( BroadcastManager broadcast ) {
		sessions = new HashMap<>();
		for ( String s : SSHConstants.SSH_SESSION_IDS ) {
			SSHSession sess = new SSHSession( broadcast, s );
			broadcast.addBroadcastListener( sess );
			sessions.put( s, sess );
			TerminalProcess p = new TerminalProcess( s );
			p.sendCommand( TerminalProcess.getOSSettings().issueCDCommand( SSH_APP_PATH ) );
		}
	}
	
	public void connectAllSessions() {
		sessions.values().forEach( s -> s.connect() );
	}
	
	public void shutdownSSHSessions() {
		for ( SSHSession ssh : getSSHSessions() ) {
			if ( ssh.isConnected() ) {
				ssh.disconnect();
				while ( ssh.isConnected() ) {
					try {
						Thread.sleep( 10 );
					} catch ( InterruptedException e1 ) {
						e1.printStackTrace();
					}
				}
			} else {
				ssh.shutdownProcess();
			}
		}
	}
	
	public SSHSession getSSHSession( String processName ) {
		return sessions.get( processName );
	}
	
	public List<SSHSession> getSSHSessions() {
		return new ArrayList<SSHSession>( sessions.values() );
	}
}