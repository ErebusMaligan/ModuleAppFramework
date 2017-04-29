package ui.ssh;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ssh.SSHManager;
import state.shutdown.ShutdownComponentManager;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 7:34:52 AM 
 */
public class AllSSHSessionsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public AllSSHSessionsPanel( SSHManager ssh, ShutdownComponentManager shutdown ) {
		this.setLayout( new BorderLayout() );
		JTabbedPane tab = new JTabbedPane();
		UIUtils.setColors( this );
		UIUtils.setTabUI( tab );
		UIUtils.setColors( tab );
		ssh.getSSHSessions().forEach( s -> tab.add( s.getProcessName(), new SSHSessionPanel( shutdown, s.getProcessName() ) ) );
		this.add( tab, BorderLayout.CENTER );
	}
}