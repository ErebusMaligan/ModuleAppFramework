package ui.log;

import javax.swing.JComponent;

import gui.windowmanager.WindowDefinition;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 15, 2015, 11:15:36 PM 
 */
public class OutputLogDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		ApplicationProvider a = (ApplicationProvider)state;
		return new OutputLogPanel( a.getOutput(), a );
	}

	@Override
	public String getTitle() {
		return OutputLogConstants.WD_OUTPUT_LOG;
	}
}