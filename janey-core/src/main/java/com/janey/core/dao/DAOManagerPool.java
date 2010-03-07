package com.janey.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DAOManagerPool {
	final private static Logger log = Logger.getLogger(DAOManagerPool.class);
	
	private static final int POOLSIZE = 5;
	private static Object lock;
	
	List<DAOManager> pool;
	private int nextManager;
	
	public DAOManagerPool(Properties props) throws SQLException {		
		lock = new Object();
		
		// initialize the pool
		log.debug("creating pool of dao managers");
		this.pool = new ArrayList<DAOManager>();
		for ( int i = 0; i < POOLSIZE; i++ ) {
			this.pool.add(new DAOManager(props));
		}
		this.nextManager = 0;
	}
	
	/**
	 * destroy the pool of daoManagers
	 * @throws SQLException
	 */
	public void destroy() throws SQLException {
		for ( DAOManager daoManager : this.pool ) {
			daoManager.destroy();
		}
	}
	
	/**
	 * get the next available daoManager in the pool
	 * @return DAOManager
	 */
	public DAOManager next() {
		DAOManager daoManager = null;
		
		synchronized(lock) {
			if ( this.nextManager == POOLSIZE ) {
				this.nextManager = 0;
			}
			daoManager = this.pool.get(this.nextManager);
			++this.nextManager;
		}
		return daoManager;
	}
}
