package com.janey.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseQuery {
	protected PreparedStatement stmt;
	protected Connection conn;
	
	protected BaseQuery(Connection conn) {
		this.conn = conn;
	}
	
	protected void compile(String sql) throws SQLException {
		this.stmt = this.conn.prepareStatement(sql);
	}
	
	protected void createSearcher() throws SQLException {
		//this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public void destroy() throws SQLException {
		if ( this.stmt != null ) {
			this.stmt.close();
		}
	}
}
