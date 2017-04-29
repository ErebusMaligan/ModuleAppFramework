package ssh;

import static ssh.SSHConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:15:04 AM 
 */
public class SSHSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		SSH_USER = e.get( XSSHU );
		SSH_PW = e.get( XSSHPW );
		SSH_IP = e.get( XSSHIP );
		SSH_APP_PATH = e.get( XPLINK );
		if ( e.get( XSSHKEYPATH ) != null ) {
			SSH_KEY_PATH = e.get( XSSHKEYPATH );
		}
		if ( e.get( XSSHUSEKEY ) != null ) {
			SSH_USE_KEY = Boolean.parseBoolean( e.get( XSSHUSEKEY ) );
		}
		if ( e.get( XSSHPORT ) != null ) {
			SSH_PORT = e.get( XSSHPORT );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( XSSH, values );
		values.put( XSSHU, new String[] { SSH_USER } );
		values.put( XSSHPW, new String[] { SSH_PW } );
		values.put( XSSHIP, new String[] { SSH_IP } );
		values.put( XPLINK, new String[] { SSH_APP_PATH } );
		values.put( XSSHUSEKEY, new String[] { String.valueOf( SSH_USE_KEY ) } );
		values.put( XSSHKEYPATH, new String[] { SSH_KEY_PATH } );
		values.put( XSSHPORT, new String[] { SSH_PORT } );
		return ret;
	}
}