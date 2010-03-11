package com.janey.core.managers.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.janey.core.dao.BaseQuery;
import com.janey.core.managers.VersionManager;
import com.janey.core.objects.Version;

public class VersionManagerImpl implements VersionManager {

	private VersionCreateQuery versionCreateQuery;
	private VersionUpdateQuery versionUpdateQuery;
	private VersionDeleteQuery versionDeleteQuery;
	private VersionGetAllQuery versionGetAllQuery;
	
	public VersionManagerImpl(Connection conn) throws SQLException {
		versionCreateQuery = new VersionCreateQuery(conn);
		versionUpdateQuery = new VersionUpdateQuery(conn);
		versionDeleteQuery = new VersionDeleteQuery(conn);
		versionGetAllQuery = new VersionGetAllQuery(conn);
	}
	
	public void create(Version version)
			throws SQLException {
		this.versionCreateQuery.execute(version);
	}

	public void delete(Version version)
			throws SQLException {
		this.versionDeleteQuery.execute(version);
	}

	public List<Version> getAll(long productId)
			throws SQLException {
		return this.versionGetAllQuery.execute(productId);
	}

	public void update(Version version)
			throws SQLException {
		// TODO pass in a string for a new version
		this.versionUpdateQuery.execute(version, "newVersion");
	}
	
	public void destroy() throws SQLException{
		this.versionCreateQuery.destroy();
		this.versionDeleteQuery.destroy();
		this.versionGetAllQuery.destroy();
		this.versionUpdateQuery.destroy();
	}
	
	protected class BaseVersionQuery extends BaseQuery {
		protected String table = "version";
		
		protected BaseVersionQuery(Connection conn) {
			super(conn);
		}
		
		protected Version create(ResultSet rs) throws SQLException {
			Version v = new Version();
			v.setProductId(rs.getLong("product_id"));
			v.setVersion(rs.getString("version"));
			return v;
		}
	}
	
	protected class VersionCreateQuery extends BaseVersionQuery {
		protected VersionCreateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "insert into " + this.table + " (product_id, version) values (?,?)";
			this.compile(sql);
		}
		
		protected void execute(Version version) throws SQLException {
			this.stmt.setLong(1, version.getProductId());
			this.stmt.setString(2, version.getVersion());
			this.stmt.executeUpdate();
		}
	}
	
	protected class VersionUpdateQuery extends BaseVersionQuery {
		protected VersionUpdateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "update " + this.table + " set version=? where product_id=? and version=?";
			this.compile(sql);
		}
		
		protected void execute(Version version, String newVersion) throws SQLException {
			this.stmt.setString(1, newVersion);
			this.stmt.setLong(2, version.getProductId());
			this.stmt.setString(3, version.getVersion());
			this.stmt.executeUpdate();
		}
	}
	
	protected class VersionDeleteQuery extends BaseVersionQuery {
		protected VersionDeleteQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "delete from " + this.table + " where product_id=? and version=?";
			this.compile(sql);
		}
		
		protected void execute(Version version) throws SQLException {
			this.stmt.setLong(1, version.getProductId());
			this.stmt.setString(2, version.getVersion());
			this.stmt.executeUpdate();
		}
	}
	
	protected class VersionGetAllQuery extends BaseVersionQuery {
		protected VersionGetAllQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select product_id, version from " + this.table + " where product_id=?";
			this.compile(sql);
		}
		
		protected List<Version> execute(long id) throws SQLException {
			List<Version> versions = null;
			this.stmt.setLong(1, id);
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				versions = new ArrayList<Version>();
				while(rs.next()) {
					versions.add(create(rs));
				}
				rs.close();
			}
			return versions;
		}
	}
}
