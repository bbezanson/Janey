package com.janey.core.managers.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.janey.core.dao.BaseQuery;
import com.janey.core.managers.CommentManager;
import com.janey.core.objects.Comment;

public class CommentManagerImpl implements CommentManager {

	private CommentCreateQuery commentCreateQuery;
	private CommentUpdateQuery commentUpdateQuery;
	private CommentDeleteQuery commentDeleteQuery;
	private CommentGetAllQuery commentGetAllQuery;
	
	public CommentManagerImpl(Connection conn) throws SQLException {
		commentCreateQuery = new CommentCreateQuery(conn);
		commentUpdateQuery = new CommentUpdateQuery(conn);
		commentDeleteQuery = new CommentDeleteQuery(conn);
		commentGetAllQuery = new CommentGetAllQuery(conn);
	}
	
	public void create(Comment comment) throws SQLException {
		this.commentCreateQuery.execute(comment);
	}

	public void delete(Comment comment) throws SQLException {
		this.commentDeleteQuery.execute(comment.getId());
	}

	public List<Comment> getAll(long issueId) throws SQLException {
		return this.commentGetAllQuery.execute(issueId);
	}

	public void update(Comment comment) throws SQLException {
		this.commentUpdateQuery.execute(comment);
	}
	
	public void destroy() throws SQLException {
		this.commentCreateQuery.destroy();
		this.commentDeleteQuery.destroy();
		this.commentGetAllQuery.destroy();
		this.commentUpdateQuery.destroy();
	}
	
	protected class BaseCommentQuery extends BaseQuery {
		protected String table = "comment";
		
		protected BaseCommentQuery(Connection conn) {
			super(conn);
		}
		
		protected Comment create(ResultSet rs) throws SQLException {
			Comment c = new Comment();
			c.setId(rs.getLong("id"));
			c.setIssueId(rs.getLong("issue_id"));
			c.setType(rs.getInt("type"));
			c.setDate(rs.getTimestamp("comment_date"));
			c.setComment(rs.getString("comment"));
			return c;
		}
	}
	
	protected class CommentCreateQuery extends BaseCommentQuery {
		protected CommentCreateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "insert into " + this.table + " (id, issue_id, type, comment_date, comment) values (?,?,?,CURRENT_TIMESTAMP,?)";
			this.compile(sql);
		}
		
		protected void execute(Comment comment) throws SQLException {
			this.stmt.setLong(1, comment.getId());
			this.stmt.setLong(2, comment.getIssueId());
			this.stmt.setInt(3, comment.getType());
			this.stmt.setTimestamp(4, comment.getDate());
			this.stmt.setString(5, comment.getComment());
			this.stmt.executeUpdate();
		}
	}
	
	protected class CommentUpdateQuery extends BaseCommentQuery {
		protected CommentUpdateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "update " + this.table + " set type=?, comment=? where id=?";
			this.compile(sql);
		}
		
		protected void execute(Comment comment) throws SQLException {
			this.stmt.setInt(1, comment.getType());
			this.stmt.setString(2, comment.getComment());
			this.stmt.setLong(3, comment.getId());
			this.stmt.executeUpdate();
		}
	}
	
	protected class CommentDeleteQuery extends BaseCommentQuery {
		protected CommentDeleteQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "delete from " + this.table + " where id=?";
			this.compile(sql);
		}
		
		protected void execute(long id) throws SQLException {
			this.stmt.setLong(1, id);
			this.stmt.executeUpdate();
		}
	}
	
	protected class CommentGetAllQuery extends BaseCommentQuery {
		protected CommentGetAllQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select id, issue_id, type, comment_date, comment from " + this.table + " where issue_id=";
			this.compile(sql);
		}
		
		protected List<Comment> execute(long id) throws SQLException {
			List<Comment> comments = null;
			this.stmt.setLong(1, id);
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				comments = new ArrayList<Comment>();
				while(rs.next()) {
					comments.add(create(rs));
				}
				rs.close();
			}
			return comments;
		}
	}
}
