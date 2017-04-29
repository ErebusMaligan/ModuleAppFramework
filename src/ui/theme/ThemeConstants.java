package ui.theme;

import java.awt.Color;

public class ThemeConstants {

	public static Color BACKGROUND = Color.BLACK;
	
	public static Color FOREGROUND = Color.RED;
	
	public static Color FOREGROUND_DARKER = FOREGROUND.darker().darker().darker().darker();
	
	public static Color TRANSPARENT_BG = new Color( BACKGROUND.getRed(), BACKGROUND.getGreen(), BACKGROUND.getBlue(), 96 );
	
	
	public static final String SET_COLOR_DIALOG = "Application Color Settings";
	
	public static final String SET_COLOR_FG = "Foreground: ";
	
	public static final String SET_COLOR_BG = "Background: ";
	
	
	public static final String XFG = "FOREGROUND";
	
	public static final String XBG = "BACKGROUND";
	
	public static final String XCOLORS = "COLORS";
}
