package com.janey.core.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.janey.core.helpers.DBConnectionHelper;
import com.janey.core.managers.CommentManager;
import com.janey.core.managers.IssueManager;
import com.janey.core.managers.PrefsManager;
import com.janey.core.managers.ProductsManager;
import com.janey.core.managers.VersionManager;
import com.janey.core.managers.impl.CommentManagerImpl;
import com.janey.core.managers.impl.IssueManagerImpl;
import com.janey.core.managers.impl.ProductsManagerImpl;
import com.janey.core.managers.impl.VersionManagerImpl;

public class DAOManager {
	private Connection conn;
	private CommentManager commentManager;
	private IssueManager issueManager;
	private PrefsManager prefsManager;
	private ProductsManager productsManager;
	private VersionManager versionManager;
	
	public DAOManager(PrefsManager pm) throws SQLException {
		// TODO: Create the managers
		this.prefsManager = pm;
		DBConnectionHelper dbhelper = new DBConnectionHelper(this.prefsManager);
		this.conn = dbhelper.getConnection();
		
		this.commentManager = new CommentManagerImpl(this.conn);
		this.issueManager = new IssueManagerImpl(this.conn);
		this.productsManager = new ProductsManagerImpl(this.conn);
		this.versionManager = new VersionManagerImpl(this.conn);
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
		return prefsManager;
	}

	public ProductsManager getProductsManager() {
		return productsManager;
	}

	public VersionManager getVersionManager() {
		return versionManager;
	}
}
