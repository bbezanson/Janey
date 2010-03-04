package com.janey.core.managers.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.janey.core.dao.BaseQuery;
import com.janey.core.managers.IssueManager;
import com.janey.core.objects.Issue;

public class IssueManagerImpl implements IssueManager {

	private IssueCreateQuery issueCreateQuery;
	private IssueUpdateQuery issueUpdateQuery;
	private IssueDeleteQuery issueDeleteQuery;
	private IssueGetQuery issueGetQuery;
	private IssueGetAllQuery issueGetAllQuery;
	
	public IssueManagerImpl(Connection conn) throws SQLException {
		issueCreateQuery = new IssueCreateQuery(conn);
		issueUpdateQuery = new IssueUpdateQuery(conn);
		issueDeleteQuery = new IssueDeleteQuery(conn);
		issueGetQuery = new IssueGetQuery(conn);
		issueGetAllQuery = new IssueGetAllQuery(conn);
	}
	
	public void create(Issue issue) throws SQLException {
		this.issueCreateQuery.execute(issue);
	}

	public void delete(long id) throws SQLException {
		this.issueDeleteQuery.execute(id);
	}

	public Issue get(long id) throws SQLException {
		return this.issueGetQuery.execute(id);
	}

	public List<Issue> getAll() throws SQLException {
		return this.issueGetAllQuery.execute();
	}

	public List<Issue> search() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Issue issue) throws SQLException {
		this.issueUpdateQuery.execute(issue);
	}

	public void destroy() throws SQLException {
		this.issueCreateQuery.destroy();
		this.issueDeleteQuery.destroy();
		this.issueGetAllQuery.destroy();
		this.issueGetQuery.destroy();
		this.issueUpdateQuery.destroy();
	}
	
	protected class BaseIssueQuery extends BaseQuery {
		protected String table = "issue";
		
		protected BaseIssueQuery(Connection conn) {
			super(conn);
		}
		
		protected Issue create(ResultSet rs) throws SQLException {
			Issue i = new Issue();
			i.setId(rs.getLong("issue_id"));
			i.setProductId(rs.getLong("product_id"));
			i.setStatus(rs.getInt("status"));
			i.setType(rs.getInt("type"));
			i.setSeverity(rs.getInt("severity"));
			i.setPlatform(rs.getInt("platform"));
			i.setTitle(rs.getString("title"));
			i.setDescription(rs.getString("description"));
			i.setReportedBy(rs.getLong("reported_by"));
			i.setReportedDate(rs.getTimestamp("reported_date"));
			i.setReportedVersion(rs.getString("reported_version"));
			i.setAssignedTo(rs.getLong("assigned_to"));
			i.setResolvedBy(rs.getLong("resolved_by"));
			i.setResolvedDate(rs.getTimestamp("resolved_date"));
			i.setResolvedVersion(rs.getString("resolved_version"));
			return i;
		}
	}
	
	protected class IssueCreateQuery extends BaseIssueQuery {
		protected IssueCreateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "insert into " + this.table + " (issue_id, product_id, status, type, severity, platform, title, description, reported_by, reported_date, reported_version, assigned_to) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			this.compile(sql);
		}
		
		protected void execute(Issue issue) throws SQLException {
			this.stmt.setLong(1, issue.getId());
			this.stmt.setLong(2, issue.getProductId());
			this.stmt.setInt(3, issue.getStatus());
			this.stmt.setInt(4, issue.getType());
			this.stmt.setInt(5, issue.getSeverity());
			this.stmt.setInt(6, issue.getPlatform());
			this.stmt.setString(7, issue.getTitle());
			this.stmt.setString(8, issue.getDescription());
			this.stmt.setLong(9, issue.getReportedBy());
			this.stmt.setTimestamp(10, issue.getReportedDate());
			this.stmt.setString(11, issue.getReportedVersion());
			this.stmt.setLong(12, issue.getAssignedTo());
			this.stmt.executeUpdate();
		}
	}
	
	protected class IssueUpdateQuery extends BaseIssueQuery {
		protected IssueUpdateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "update " + this.table + " set product_id=?, status=?, type=?, severity=?, platform=?, title=?, description=?, reported_by=?, reported_date=?, reported_version=?, assigned_to=?, resolved_by=?, resolved_date=?, resolved_version=? where issue_id=?";
			this.compile(sql);
		}
		
		protected void execute(Issue issue) throws SQLException {
			this.stmt.setLong(1, issue.getProductId());
			this.stmt.setInt(2, issue.getStatus());
			this.stmt.setInt(3, issue.getType());
			this.stmt.setInt(4, issue.getSeverity());
			this.stmt.setInt(5, issue.getPlatform());
			this.stmt.setString(6, issue.getTitle());
			this.stmt.setString(7, issue.getDescription());
			this.stmt.setLong(8, issue.getReportedBy());
			this.stmt.setTimestamp(9, issue.getReportedDate());
			this.stmt.setString(10, issue.getReportedVersion());
			this.stmt.setLong(11, issue.getAssignedTo());
			this.stmt.setLong(12, issue.getResolvedBy());
			this.stmt.setTimestamp(13, issue.getResolvedDate());
			this.stmt.setString(14, issue.getResolvedVersion());
			this.stmt.setLong(15, issue.getId());
			this.stmt.executeUpdate();
		}
	}
	
	protected class IssueDeleteQuery extends BaseIssueQuery {
		protected IssueDeleteQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "delete from " + this.table + " where issue_id=?";
			this.compile(sql);
		}
		
		protected void execute(long id) throws SQLException {
			this.stmt.setLong(1, id);
			this.stmt.executeUpdate();
		}
	}
	
	protected class IssueGetQuery extends BaseIssueQuery {
		protected IssueGetQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select * from " + this.table + " where issue_id=?";
			this.compile(sql);
		}
		
		protected Issue execute(long id) throws SQLException {
			Issue issue = null;
			this.stmt.setLong(1, id);
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				rs.next();
				issue = create(rs);
				rs.close();
			}
			return issue;
		}
	}
	
	protected class IssueGetAllQuery extends BaseIssueQuery {
		protected IssueGetAllQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select * from " + this.table;
			this.compile(sql);
		}
		
		protected List<Issue> execute() throws SQLException {
			List<Issue> issues = null;
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				issues = new ArrayList<Issue>();
				while(rs.next()) {
					issues.add(create(rs));
				}
				rs.close();
			}
			return issues;
		}
	}
}
