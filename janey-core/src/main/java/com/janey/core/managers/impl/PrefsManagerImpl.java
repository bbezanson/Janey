package com.janey.core.managers.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.janey.core.managers.PrefsManager;

public class PrefsManagerImpl implements PrefsManager {
	final private static Logger log = Logger.getLogger(PrefsManagerImpl.class);
	private static final String PREFS_FILENAME = "janey.properties";
	
	// the directory that properties should be read from and written to
	private String PROPS_FILE;
	
	// create a lock for saving the preferences because this class is
	// not a singleton within the pool of dao managers
	private static Object lock;
	static {
		lock = new Object();
	}
	
	/**
	 * The constructor will read the preferences file from the default directory
	 * upon startup.
	 */
	public PrefsManagerImpl() {
		// TODO: this needs to change to put the file in the webapp's config dir
		// determine which server we are running under
		String catalinaHome = System.getProperty("catalina.home");
		String jettyHome = System.getProperty("jetty.home");
		
		// set up the PROPS_FILE
		this.PROPS_FILE = ( null != catalinaHome ? catalinaHome + "/conf/" : jettyHome + "/etc/" ) + PREFS_FILENAME;
	}
	
	public void save(Properties props) {
		synchronized(lock) {
			this.writeFile(this.PROPS_FILE, props);
		}
	}
	
	public Properties restore() {
		return this.readFile(this.PROPS_FILE);
	}
	
	private Properties readFile(String filename) {
		Properties props = new Properties();
		if ( null != filename ) {
			FileInputStream fis = null;
			try {
				File f = new File(filename);
				if ( f.exists() ) {
					fis = new FileInputStream(f);
					props.load(fis);
					fis.close();
					fis = null;
 				}
			} catch (IOException e) {
				log.error("IOException while opening/reading prefs file: " + e.getMessage());
			} finally {
				try {
					if ( null != fis ) {
						fis.close();
					}
				} catch (IOException e) {
					log.error("IOException while closing stream: " + e.getMessage());
				}
			}
		}	
		return props;
	}
	
	private void writeFile(String filename, Properties props) {
		if ( filename != null ) {
			FileOutputStream fos = null;
			try {
				File f = new File(filename);
				if ( f.exists() ) {
					f.delete();
				}
				f.createNewFile();
				fos = new FileOutputStream(f);
				props.store(fos, "Generated Properties File");
			} catch (IOException e) {
				log.error("IOException while creating/writing prefs file: " + e.getMessage());
			} finally {
				if ( fos != null ) {
					try {
						fos.close();
					} catch (IOException e) {
						log.error("IOException while closing stream: " + e.getMessage());
					}
				}
			}
		}
	}
}
