package state.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;

import process.io.ProcessStreamSiphon;
import ssh.SSHConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 29, 2015, 10:26:24 PM 
 */
public abstract class MonitorData extends Observable implements ProcessStreamSiphon {
	
	protected CountDownLatch cdl;
	
	protected boolean winError = false;
	
	protected Map<String, LineSkimmer> skimmers = new HashMap<>();
	
	protected String skimmer;
	
	protected TimeoutThread timeout;
	
	public void giveLock( CountDownLatch cdl ) {
		this.cdl = cdl;
	}
	
	@Override
	public void skimMessage( String name, String line ) {
		if ( cdl != null ) {
			stopTimeout();
			if ( skimmer != null ) {
				if ( !winError( line ) && !checkDone( line ) && !exit( line ) ) {
					skimmers.get( skimmer ).skimLine( line );
					startTimeout();
				}
			}
		}
	}
	
	protected boolean exit( String line ) {
		boolean ret = false;
		if ( line.equals( "exit" ) ) {
			ret = true;
		}
		return ret;
	}
	
	protected boolean checkDone( String line ) {
		boolean ret = false;
		if ( line.equals( skimmerDone() ) ) {
			stopTimeout();
			cdl.countDown();
			ret = true;
		}
		return ret;
	}
	
	protected boolean winError( String line ) {
		winError = false;
		if ( line.contains( "The system cannot find the file specified" ) || line.contains( "operable program or batch file." ) ) {
			winError = true;
			stopTimeout();
			cdl.countDown();
		}
		return winError;
	}
	
	protected void deadSSH() {
		stopTimeout();
		cdl.countDown();
	}
	
	public boolean didWinError() {
		return winError;
	}
	
	public void resetWinError() {
		winError = false;
	}
	
	public void setActiveSkimmer( String skimmer ) {
		if ( skimmer != null ) {
			startTimeout();			
		}
		this.skimmer = skimmer;
	}
	
	protected void stopTimeout() {
		if ( timeout != null ) {
			timeout.stopRunning();
			timeout = null;
		}
	}
	
	protected void startTimeout() {
		if ( timeout == null ) {
			timeout = new TimeoutThread( () -> deadSSH() );
			timeout.start();
		}
	}
	
	protected String skimmerDone() {
		return ( skimmer.contains( "grep" ) ? "" : skimmer + " " ) + SSHConstants.COMPLETE;
	}
}