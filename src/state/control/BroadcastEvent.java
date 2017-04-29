package state.control;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 3, 2015, 10:25:44 PM 
 */
public class BroadcastEvent {

	private Broadcaster source;
	
	private Class<?> destination;
	
	private String eventType;
	
	private String eventSetting;
	
	private Object additional;

	public static final String LIGHT_STATUS = "LIGHT_STATUS";
	
	public static final String MONITOR_COMMAND = "MONITOR_COMMAND";
	
	public static final String MONITOR_STATE = "MONITOR_STATE";
	
	public static final String SSH_SESSION_COMMAND = "SSH_SESSION_COMMAND";
	
	public static final String SSH_SESSION_STATE = "SSH_SESSION_STATE";
	
	public static final String OFF = "OFF";
	
	public static final String ON = "ON";
	
	public static final String START = "START";
	
	public static final String STOP = "STOP";
	
	public static final String CONNECT = "CONNECT";
	
	public static final String DISCONNECT = "DISCONNECT";
	
	public BroadcastEvent( Broadcaster source, String eventType, String eventSetting ) {
		this( source, (Class<?>)null, eventType, eventSetting );
	}
	
	public BroadcastEvent( Broadcaster source, String eventType, String eventSetting, Object additional ) {
		this( source, null, eventType, eventSetting, additional );
	}
	
	public BroadcastEvent( Broadcaster source, Class<?> destination, String eventType, String eventSetting ) {
		this( source, destination, eventType, eventSetting, null );
	}
	
	public BroadcastEvent( Broadcaster source, Class<?> destination, String eventType, String eventSetting, Object additional ) {
		this.source = source;
		this.eventType = eventType;
		this.eventSetting = eventSetting;
		this.destination = destination;
		this.additional = additional;
	}

	public Class<?> getDestination() {
		return destination;
	}
	
	public Broadcaster getSource() {
		return source;
	}

	public String getEventType() {
		return eventType;
	}

	public String getEventSetting() {
		return eventSetting;
	}
	
	public Object getAdditional() {
		return additional;
	}
	
	@Override
	public String toString() {
		return "source: " + source.getClass().getName() + 
				" destinaton: " + ( destination == null ? "ALL" : destination.getName() ) + 
				" |    " + eventType.toString() + 
				" (" + eventSetting.toString() + ")" + 
				( additional == null ? "" : "    |    " + additional.toString() ) ; 
	}
}