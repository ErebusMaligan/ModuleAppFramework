package ui.ssh;

import static ssh.SSHConstants.*;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.MouseInfo;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.dialog.OKCancelDialog;
import gui.entry.CheckEntry;
import gui.entry.DirectoryEntry;
import gui.entry.Entry;
import gui.entry.FileEntry;
import gui.entry.PasswordEntry;
import gui.props.variable.BooleanVariable;
import gui.props.variable.StringVariable;
import statics.GU;
import statics.UIUtils;
import xml.XMLSettingsWriter;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 10, 2015, 12:58:50 AM 
 */
public class SSHDialog extends OKCancelDialog {
	
	private static final long serialVersionUID = 1L;

	private StringVariable ip = new StringVariable( SSH_IP);
	private StringVariable user = new StringVariable( SSH_USER );
	private StringVariable pw = new StringVariable( SSH_PW );
	private StringVariable path = new StringVariable( SSH_APP_PATH );
	private BooleanVariable useKey = new BooleanVariable( SSH_USE_KEY );
	private StringVariable keyPath = new StringVariable( SSH_KEY_PATH );
	private StringVariable port = new StringVariable( SSH_PORT );
	
	private XMLSettingsWriter writer;
	
	public SSHDialog( Frame frame, XMLSettingsWriter writer ) {
		super( frame, SET_SSH_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.writer = writer;
		this.setLayout( new BorderLayout() );
		
		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		GU.spacer( c );
		c.add( new Entry( SET_SSH_IP, ip, GU.SHORT ) );
		GU.spacer( c );
		c.add( new Entry( SET_SSH_PORT, port, GU.SHORT ) );
		GU.spacer( c );
		c.add( new Entry( SET_SSH_USER, user, GU.SHORT ) );
		GU.spacer( c );
		c.add( new PasswordEntry( SET_SSH_PASS, pw, GU.SHORT ) );
		GU.spacer( c );
		c.add( new DirectoryEntry( SET_SSH_PATH, path ) );
		GU.spacer( c );
		c.add( new CheckEntry( SET_USE_SSH_KEY, useKey ) );
		GU.spacer( c );
		c.add( new FileEntry( SET_SSH_KEY_PATH, keyPath, new FileNameExtensionFilter( "private keys", "ppk" ) ) );
		this.add( c, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		UIUtils.setColorsRecursive( this );
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		this.pack();
	}
	
	public void ok() {
		SSH_IP = ip.toString();
		SSH_USER = user.toString();
		SSH_PW = pw.toString();
		SSH_APP_PATH = path.toString();
		SSH_USE_KEY = Boolean.parseBoolean( useKey.toString() );
		SSH_KEY_PATH = keyPath.toString();
		SSH_PORT = port.toString();
		writer.writeSettings();
		super.ok();
	}
}