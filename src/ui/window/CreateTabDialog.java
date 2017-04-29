package ui.window;

import static ui.window.WindowConstants.*;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.MouseInfo;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gui.dialog.OKCancelDialog;
import gui.entry.Entry;
import gui.props.variable.StringVariable;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 2:40:33 AM 
 */
public class CreateTabDialog extends OKCancelDialog {

	private static final long serialVersionUID = 1L;

	private StringVariable nm = new StringVariable( "" );
	
	public CreateTabDialog( Frame frame ) {
		super( frame, SET_TAB_NAME_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.setLayout( new BorderLayout() );
		
		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		c.add( new Entry( SET_TAB_NAME, nm ) );
		
		this.add( c, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		this.pack();
	}
	
	public String getTabName() {
		return nm.toString();
	}
}