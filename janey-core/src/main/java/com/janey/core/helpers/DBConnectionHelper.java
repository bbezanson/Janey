package com.janey.core.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBConnectionHelper {
	final private static Logger log = Logger.getLogger(DBConnectionHelper.class);
		
	private String dbDriver = null;
	private String dbURL = null;
	private String dbName = null;
	private String dbUser = null;
	private String dbPass = null;
	
	/**
	 * private Constructor - static class, don't construct
	 *
	 */
	public DBConnectionHelper(Properties props) {
		loadValuesFromPrefs(props);
	}
	
	/**
	 * get a connection to the database using the values from preferences
	 * @return
	 */
	public Connection getConnection() {
		return getConnection(dbDriver, dbURL, dbName, dbUser, dbPass);
	}
	
	/**
	 * get a connection to a different database on the same server
	 * @param database
	 * @param user
	 * @param password
	 */
	public Connection getConnection(String database, String user, String password) {
		return getConnection(dbDriver, dbURL, database, user, password);
	}
		
	/**
	 * get a connection to a different database on a different server
	 * @return boolean
	 */
	public Connection getConnection(String driver, String url, String database, String user, String password) {
		Connection newConnection = null;
		
		try {
			// get an instance of the driver
			Class.forName(driver).newInstance();
			// create the connection
			newConnection = DriverManager.getConnection(url + database, user, password);
			// turn off auto commit
			// newConnection.setAutoCommit(false);
		} catch ( SQLException e ) {
			log.error("SQLException while getting db connection " + e.getMessage());
		} catch ( Exception e ) {
			log.error("Exception while getting db connection " + e.getMessage());
		}
		
		return newConnection;
	}
	
	/**
	 * close an open database connection
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if ( null != conn ) {
			try {
				conn.close();
				conn = null;
			} catch ( SQLException e ) {
				log.error("SQLException while closing db connection " + e.getMessage());
			} catch ( Exception e ) {
				log.error("Exception while closing db connection " + e.getMessage());
			}
		}
	}

	/**
	 * get the values from the preferences file for the database settings
	 *
	 */
	private void loadValuesFromPrefs(Properties props) {
		// get the settings or use the defaults
		dbDriver 	= props.getProperty("com.janey.db.driver");
		dbURL 		= props.getProperty("com.janey.db.url");
		dbName 		= props.getProperty("com.janey.db");
		dbUser 		= props.getProperty("com.janey.username");
		dbPass 		= props.getProperty("com.janey.password");
	}
}
