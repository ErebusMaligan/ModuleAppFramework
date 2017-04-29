package ui.theme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.button.CustomButtonUI;
import gui.dialog.OKCancelDialog;
import state.provider.ApplicationProvider;
import statics.GU;
import statics.UIUtils;
import static ui.theme.ThemeConstants.*;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 11:36:30 PM 
 */
public class BasicColorDialog extends OKCancelDialog {

	private static final long serialVersionUID = 1L;

	protected ApplicationProvider state;
	
	protected JButton bg = new JButton();
	
	protected JButton fg = new JButton();
	
	public BasicColorDialog( ApplicationProvider state ) {
		super( state.getFrame(), SET_COLOR_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.state = state;
		this.setLayout( new BorderLayout() );		

		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		initButtons( c );
		
		this.add( c, BorderLayout.CENTER );
		JPanel bp = getButtonPanel();
		this.add( bp, BorderLayout.SOUTH );
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		UIUtils.setColors( this, c, bp );
		this.pack();
	}
	
	protected void initButtons( JPanel c ) {
		initButton( bg, BACKGROUND, SET_COLOR_BG, c );
		initButton( fg, FOREGROUND, SET_COLOR_FG, c );
	}
	
	protected void initButton( JButton b, Color back, String label, JPanel panel ) {
//		b.setContentAreaFilled( false );
		b.setUI( new CustomButtonUI() );
		b.setOpaque( true );
		b.addActionListener( e -> {
			JButton button = ( (JButton)e.getSource() );
			Color c = JColorChooser.showDialog( BasicColorDialog.this, "Select New Color", button.getBackground() );
			if ( c != null ) {
				button.setBackground( c );
			}
		} );
		b.setBackground( back );
		b.setBorder( BorderFactory.createLineBorder( Color.DARK_GRAY ) );
		JLabel l = new JLabel( label );
		UIUtils.setColors( l );
		UIUtils.setColors( GU.hp( panel, l, b ) );
		GU.spacer( panel );
	}
	
	protected void saveParams() {
		BACKGROUND = bg.getBackground();
		FOREGROUND = fg.getBackground();
	}
	
	public void ok() {
		saveParams();
		state.writeSettings();
		JOptionPane.showMessageDialog( this, "Color Options Require Application Restart To Take Effect" );
		super.ok();
	}
}