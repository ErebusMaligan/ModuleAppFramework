package ui.ssh;

import javax.swing.JComponent;

import gui.windowmanager.WindowDefinition;
import ssh.SSHConstants;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 7:29:08 AM 
 */
public class SSHSessionDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		ApplicationProvider a = (ApplicationProvider)state;
		return new AllSSHSessionsPanel( a.getSSHManager(), a );
	}

	@Override
	public String getTitle() {
		return SSHConstants.WD_SSH;
	}
}