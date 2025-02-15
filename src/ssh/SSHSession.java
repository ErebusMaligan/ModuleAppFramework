package ssh;

import static ssh.SSHConstants.SSH_APPLICATION;
import static ssh.SSHConstants.SSH_APP_PATH;
import static ssh.SSHConstants.SSH_AT;
import static ssh.SSHConstants.SSH_EXIT;
import static ssh.SSHConstants.SSH_HOST_COMMAND;
import static ssh.SSHConstants.SSH_IP;
import static ssh.SSHConstants.SSH_KEY_COMMAND;
import static ssh.SSHConstants.SSH_KEY_PATH;
import static ssh.SSHConstants.SSH_PORT;
import static ssh.SSHConstants.SSH_PORT_COMMAND;
import static ssh.SSHConstants.SSH_PW;
import static ssh.SSHConstants.SSH_PW_COMMAND;
import static ssh.SSHConstants.SSH_TTY_FIX;
import static ssh.SSHConstants.SSH_USER;
import static ssh.SSHConstants.SSH_USE_KEY;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import listeners.BasicObservable;
import process.ProcessManager;
import process.TerminalProcess;
import process.io.ProcessStreamSiphon;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.control.BroadcastManager;
import state.control.Broadcaster;
import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 9:56:31 PM 
 */
public class SSHSession extends BasicObservable implements ProcessStreamSiphon, BroadcastListener, Broadcaster {
	
	boolean connected = false;
	
	private String processName;
	
	private final Semaphore lock = new Semaphore( 1 );
	
	private final Semaphore monitorLock = new Semaphore( 1 );
	
	private boolean connecting = false;
	
	private boolean disconnecting = false;
	
	private Object startLock = new Object();
	
	private boolean started = false;
	
	private BroadcastManager broadcast;
	
	private int logoutCount = 0;
	
	public SSHSession( BroadcastManager broadcast,String processName ) {
		this.broadcast = broadcast;
		this.processName = processName;
		ProcessManager.getInstance().registerSiphon( processName, this );
	}
	
	private void restartProcess() {
		if ( ProcessManager.getInstance().getProcessByName( processName ) == null ) {
			started = false;
			new TerminalProcess( processName );
		} else {
			started = true;
		}
	}
	
	public String getProcessName() {
		return processName;
	}
	
	public CountDownLatch getLock() throws InterruptedException {
		lock.acquire();
		return new CountDownLatch( 1 );
	}
	
	public void returnLock() {
		lock.release();
	}
	
	public void getMonitorLock() throws InterruptedException {
		monitorLock.acquire();
	}
	
	public void returnMonitorLock() {
		monitorLock.release();
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public boolean isConnecting() {
		return connecting;
	}
	
	public boolean sendCommand( String command ) {
		boolean ret = false;
		try {
			ProcessManager.getInstance().getProcessByName( processName ).sendCommand( command );
			ret = true;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void disconnect() {
		if ( connected && !disconnecting ) {
			disconnecting = true;
			sendCommand( SSH_EXIT );
			sendCommand( SSH_EXIT );  //send logout twice; once to log out of root, back to normal user, then once to actually logout completely
		}
	}
	
	public void connect() {
		if ( !connected && !connecting && !disconnecting ) {
			System.out.println( "SSH Session " + processName + " Attempting Connection..." );
			connecting = true;
			restartProcess();
			synchronized( startLock ) {
				if ( started ) {
					if ( ( TerminalWindowManager.getInstance().OS == OSTerminalSettings.WINDOWS ) ) {
						ProcessManager.getInstance().getProcessByName( processName ).sendCommand( TerminalProcess.getOSSettings().issueCDCommand( SSH_APP_PATH ) );
						sendCommand( SSH_APPLICATION + " " + SSH_HOST_COMMAND + " " + SSH_USER + SSH_AT + SSH_IP + " " + SSH_PORT_COMMAND + " " + SSH_PORT + " " 
					+ ( SSH_USE_KEY ? SSH_KEY_COMMAND + " " + SSH_KEY_PATH : SSH_PW_COMMAND + " " + SSH_PW ) );
						started = false;
					} else {
						sendCommand( SSH_APPLICATION + " " + SSH_USER + SSH_AT + SSH_IP + " " + SSH_TTY_FIX + " " + SSH_PORT_COMMAND + " " + SSH_PORT + " " 
								+ ( SSH_USE_KEY ? SSH_KEY_COMMAND + " " + SSH_KEY_PATH : "" ) ); //can't provide password in linux ssh
					}
				}
			}
		}
	}
	
	@Override
	public void skimMessage( String name, String line ) {
		if ( line.contains( "Access granted. Press Return to begin session." ) && !connected ) {
			sendCommand( "" );
		}
		if ( line.trim().startsWith( "Welcome" ) && !connected ) {	
			try {
				Thread.sleep( 2000 );
			} catch ( InterruptedException e1 ) {
				e1.printStackTrace();
			}
			sendCommand( "sudo -iS" );
			sendCommand( SSH_PW );
			try {
				Thread.sleep( 2000 );
			} catch ( InterruptedException e1 ) {
				e1.printStackTrace();
			}
			connected = true;
			connecting = false;
			System.out.println( "SSH Session " + processName + " Connected" );
			changed();
		} else if ( line.contains( "logout" ) && connected ) {
			logoutCount++;
			if ( logoutCount == 2 ) {
				logoutCount = 0;
				System.out.println( "SSH Session " + processName + " Disconnected" );
				shutdownProcess();
				connected = false;
				disconnecting = false;
				changed();
			}
		}
	}
	
	public void shutdownProcess() {
		try {
			TerminalProcess t = ( (TerminalProcess)ProcessManager.getInstance().getProcessByName( processName ) );
			if ( t != null ) {  //will be null if the SSH session has been turned off (manually or otherwise) and never restarted
				t.closeResources();
				while ( !t.isTerminated() ) {
					try {
						Thread.sleep( 100 );
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void changed() {
		notifyObservers( connected );
		broadcast.broadcast( new BroadcastEvent( this, BroadcastEvent.SSH_SESSION_STATE, connected ? BroadcastEvent.ON : BroadcastEvent.OFF, processName ) );
	}
	
	@Override
	public void notifyProcessEnded( String name ) {
		started = false;
		connected = false;
		disconnecting = false;
		changed();
	}

	@Override
	public void notifyProcessStarted( String name ) {
		synchronized( startLock ) {
			started = true;
			if ( connecting ) {
				ProcessManager.getInstance().getProcessByName( processName ).sendCommand( TerminalProcess.getOSSettings().issueCDCommand( SSH_APP_PATH ) );
				sendCommand( SSH_APPLICATION + " " + SSH_HOST_COMMAND + " " + SSH_USER + SSH_AT + SSH_IP + " " + SSH_PORT_COMMAND + " " + SSH_PORT + " " 
			+ ( SSH_USE_KEY ? SSH_KEY_COMMAND + " " + SSH_KEY_PATH : SSH_PW_COMMAND + " " + SSH_PW ) );
				started = false;
			}
		}
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getDestination() != null ) {
			if ( e.getDestination().getName().equals( this.getClass().getName() ) ) {
				if ( e.getEventType().equals( BroadcastEvent.SSH_SESSION_COMMAND ) ) {
					if ( e.getAdditional() != null ) {
						if ( e.getAdditional().equals( processName ) ) {
							if ( e.getEventSetting().equals( BroadcastEvent.CONNECT ) ) {
								this.connect();
							} else if ( e.getEventSetting().equals( BroadcastEvent.DISCONNECT ) ) {
								this.disconnect();
							}
						}
					}
				}
			}
		}
	}	
}