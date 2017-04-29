package ui.ssh;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

import ssh.SSHConstants;
import state.shutdown.ShutdownComponent;
import state.shutdown.ShutdownComponentManager;
import statics.UIUtils;
import ui.terminal.panel.TerminalPanel;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 8:07:53 PM 
 */
public class SSHSessionPanel extends JPanel implements ShutdownComponent {
	
	private static final long serialVersionUID = 1L;
	
	private TerminalPanel term;
	
	public SSHSessionPanel( ShutdownComponentManager shutdown, String processName ) {
		this.setLayout( new BorderLayout() );
		term = new TerminalPanel( processName, SSHConstants.SSH_APP_PATH );
		term.getWindowSettings().setBackgroundColor( ThemeConstants.BACKGROUND );
		term.getWindowSettings().setForegroundColor( ThemeConstants.FOREGROUND );
		term.getWindowSettings().setLineLimit( 4000 );
		term.getArea().setFont( term.getArea().getFont().deriveFont( 12.0f ).deriveFont( Font.BOLD ) );
		term.setColors();
		term.setLineLimit();
		UIUtils.setJScrollPane( term.getScrollPane() );
		term.getWindowSettings().setForegroundColor( ThemeConstants.FOREGROUND );
		term.getWindowSettings().setBackgroundColor( ThemeConstants.BACKGROUND );
		this.add( term, BorderLayout.CENTER );
		shutdown.registerShutdownComponent( this );
	}
	
	public void sendCommand( String command ) {
		term.sendCommand( command );
	}

	@Override
	public void shutdown() {
		term.shutDown();
	}
}