package com.janey.core.managers.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.janey.core.managers.PrefsManager;

public class PrefsManagerImpl implements PrefsManager {
	public static final String PREFS_FILENAME = "janey.properties";
	public static final String DEFAULT_BASE_URL = "http://www.janey.com/";

	// the directory that properties should be read from and written to
	private String PREFS_DIR;
	
	// the actual properties
	private Properties props = new Properties();
	
	/**
	 * The constructor will read the preferences file from the default directory
	 * upon startup.
	 */
	public PrefsManagerImpl() {
		// determine which server we are running under
		String catalinaHome = System.getProperty("catalina.home");
		String jettyHome = System.getProperty("jetty.home");
				
		// set up the PREFS_DIR
		this.PREFS_DIR = ( null != catalinaHome ) ? catalinaHome + "/conf/" : jettyHome + "/etc/";
		
		readFile(this.PREFS_DIR + PREFS_FILENAME);
	}
	
	public void set(String key, String val) {
		if ( key != null && val != null ) {
			// add this to our properties
			this.props.put(key, val);
		}
	}
	
	/**
	 * get a value from the preferences
	 * @param key - the key of the value
	 * @return string - the value
	 */
	public String get(String key) {
		String val = null;
		if ( null != key ) {
			// first check the system properties in case the property
			// was overridden via -D on startup
			val = System.getProperty(key);
			// then read it from our properties
			if ( val == null ) {
				val = this.props.getProperty(key);
			}
		}
		return val;
	}

	/**
	 * get a value from the preferences but substitute with the
	 * default value if the value does not exist
	 * @param key - the key of the value
	 * @param defaultVal - substitute value if value does not exist
	 * @return string - the value
	 */
	public String get(String key, String defaultVal) {
		// try to get the pref value from our prefs
		String val = get(key);
		return ( val != null ? val : defaultVal );
	}
	
	private void readFile(String filename) {
		if ( null != filename ) {
			FileInputStream fis = null;
			try {
				File f = new File(filename);
				if ( f.exists() ) {
					fis = new FileInputStream(f);
					this.props.load(fis);
					fis.close();
					fis = null;
 				}
			} catch (IOException e) {
				//Logger.error("IOException while opening/reading prefs file: " + e.getMessage());
			} finally {
				try {
					if ( null != fis ) {
						fis.close();
					}
				} catch (IOException e) {
					//Logger.error("IOException while closing stream: " + e.getMessage());
				}
			}
		}	
	}
}
