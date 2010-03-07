package com.janey.core.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.janey.core.helpers.DBConnectionHelper;
import com.janey.core.managers.CommentManager;
import com.janey.core.managers.IssueManager;
import com.janey.core.managers.PrefsManager;
import com.janey.core.managers.ProductsManager;
import com.janey.core.managers.VersionManager;
import com.janey.core.managers.impl.CommentManagerImpl;
import com.janey.core.managers.impl.IssueManagerImpl;
import com.janey.core.managers.impl.PrefsManagerImpl;
import com.janey.core.managers.impl.ProductsManagerImpl;
import com.janey.core.managers.impl.VersionManagerImpl;

public class DAOManager {
	private Connection conn = null;
	private CommentManager commentManager;
	private IssueManager issueManager;
	private PrefsManager prefsManager;
	private Properties properties;
	private ProductsManager productsManager;
	private VersionManager versionManager;
	
	public DAOManager(Properties props) throws SQLException {
		this.properties = props;
		this.prefsManager = new PrefsManagerImpl();
		this.init(props);
	}
	
	public void init(Properties props) throws SQLException {
		if ( !props.isEmpty() ) {
			this.properties = props;
			// if this is an update, close the db connection
			if ( this.conn != null ) {
				destroy();
				DBConnectionHelper.closeConnection(this.conn);
			}
			DBConnectionHelper dbhelper = new DBConnectionHelper(props);
			this.conn = dbhelper.getConnection();

			this.commentManager = new CommentManagerImpl(this.conn);
			this.issueManager = new IssueManagerImpl(this.conn);
			this.productsManager = new ProductsManagerImpl(this.conn);
			this.versionManager = new VersionManagerImpl(this.conn);
		}
	}
	
	public void destroy() throws SQLException {
		this.commentManager.destroy();
		this.issueManager.destroy();
		this.productsManager.destroy();
		this.versionManager.destroy();
	}
	
	public void sync() throws SQLException {
		this.conn.commit();
	}
	
	public void reset() throws SQLException {
		this.conn.rollback();
	}
	
	public CommentManager getCommentManager() {
		return commentManager;
	}

	public IssueManager getIssueManager() {
		return issueManager;
	}
	
	public PrefsManager getPrefsManager() {
		return this.prefsManager;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public ProductsManager getProductsManager() {
		return productsManager;
	}

	public VersionManager getVersionManager() {
		return versionManager;
	}
}
