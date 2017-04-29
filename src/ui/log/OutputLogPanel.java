package ui.log;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gui.textarea.DefaultJTextAreaStreamManager;
import gui.textarea.JTextAreaLineLimitDocument;
import gui.windowmanager.WindowClosedHook;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.control.BroadcastManager;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 15, 2015, 11:06:24 PM 
 */
public class OutputLogPanel extends JPanel implements WindowClosedHook, BroadcastListener {

	private static final long serialVersionUID = 1L;

	private JTextArea area = new JTextArea( 24, 100 );

	private DefaultJTextAreaStreamManager output;

	public OutputLogPanel( DefaultJTextAreaStreamManager output, BroadcastManager broadcast ) {
		this.output = output;
		this.setLayout( new BorderLayout() );
		int b = 10;
		area.setBorder( BorderFactory.createEmptyBorder( b, b, b, b ) );
		this.add( UIUtils.setJScrollPane( new JScrollPane( area ) ), BorderLayout.CENTER );
		area.setBackground( ThemeConstants.BACKGROUND );
		area.setForeground( ThemeConstants.FOREGROUND );
		area.setDocument( new JTextAreaLineLimitDocument( area, 100 ) );
		output.registerArea( area );
		area.setEditable( false );
		broadcast.addBroadcastListener( this );
	}

	@Override
	public void closed() {
		output.removeArea( area );
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				UIUtils.setColorsRecursive( this );
			} else {
				UIUtils.setColorsRecursiveOff( this );
			}
		}
	}
}