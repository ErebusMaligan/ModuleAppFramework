package ssh;

import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;

public class SSHConstants {
	
	//ssh settings/command string parts
	public static String[] SSH_SESSION_IDS;

	public static String SSH_USER = "root";

	public static String SSH_IP = "192.168.10.199";

	public static String SSH_PORT = "22";
	
	public static String SSH_PW = "";
	
	public static String SSH_APP_PATH = "C:\\Program Files (x86)\\PuTTY";
	
	public static boolean SSH_USE_KEY = false;
	
	public static String SSH_KEY_PATH = "";
	
	public static final String SSH_APPLICATION = TerminalWindowManager.getInstance().OS == OSTerminalSettings.WINDOWS ? "plink" : "ssh";
	
	public static final String SSH_HOST_COMMAND = "-ssh";

	public static final String SSH_TTY_FIX = "-tt";
	
	public static final String SSH_PORT_COMMAND = TerminalWindowManager.getInstance().OS == OSTerminalSettings.WINDOWS ? "-P" : "-p";
	
	public static final String SSH_PW_COMMAND = "-pw";
	
	public static final String SSH_KEY_COMMAND = "-i";
	
	public static final String SSH_AT = "@";
	
	public static final String SSH_EXIT = "logout";
	
	public static final String ECHO_CMD = "echo";
	
	public static final String COMPLETE = "complete";
	
	//xml
	public static final String XSSH = "SSH";
	
	public static final String XSSHU = "USER";
	
	public static final String XSSHPW = "PASS";
	
	public static final String XSSHIP = "IP";
	
	public static final String XSSHPORT = "PORT";
	
	public static final String XSSHUSEKEY = "USE_KEY";
	
	public static final String XSSHKEYPATH = "KEY_PATH";
	
	public static final String XPLINK = "PLINK_PATH";
	
	//settings dialog
	public static final String SET_SSH_DIALOG = "SSH Settings";
	
	public static final String SET_SSH_IP = "IP: ";
	
	public static final String SET_SSH_USER = "User: ";
	
	public static final String SET_SSH_PASS = "Password: ";
	
	public static final String SET_SSH_PATH = "SSH Path (can be empty): ";
	
	public static final String SET_USE_SSH_KEY = "Use RSA-2 Key";
	
	public static final String SET_SSH_KEY_PATH = "Private Key Path: ";
	
	public static final String SET_SSH_PORT = "SSH Port: ";
	
	//Window definitions
	public static final String WD_SSH = "SSH Session Logs";
}
