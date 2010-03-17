package com.janey.core.managers.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.janey.core.dao.BaseQuery;
import com.janey.core.managers.UserManager;
import com.janey.core.objects.User;

public class UserManagerImpl implements UserManager {
	private CreateUserQuery createUserQuery;
	private UpdateUserQuery updateUserQuery;
	private GetUserQuery getUserQuery;
	private GetAllQuery getAllQuery;
	
	public UserManagerImpl(Connection conn) throws SQLException {
		this.createUserQuery = new CreateUserQuery(conn);
		this.updateUserQuery = new UpdateUserQuery(conn);
		this.getUserQuery = new GetUserQuery(conn);
		this.getAllQuery = new GetAllQuery(conn);
	}
	
	public void create(User user) throws SQLException {
		this.createUserQuery.execute(user);
	}

	public void destroy() throws SQLException {
		this.createUserQuery.destroy();
		this.updateUserQuery.destroy();
		this.getUserQuery.destroy();
		this.getAllQuery.destroy();
	}

	public User get(String id) throws SQLException {
		return this.getUserQuery.execute(id);
	}

	public List<User> getAll() throws SQLException {
		return this.getAllQuery.execute();
	}

	public void update(User user) throws SQLException {
		this.updateUserQuery.execute(user);
	}

	protected class BaseUserQuery extends BaseQuery {
		protected String table = "janey_user";
		
		protected BaseUserQuery(Connection conn) {
			super(conn);
		}
		
		protected User create(ResultSet rs) throws SQLException {
			return new User(
					rs.getString("user_id"), 
					rs.getString("password"), 
					rs.getString("email"),
					rs.getBoolean("active")
			);
		}
	}
	
	protected class CreateUserQuery extends BaseUserQuery {
		protected CreateUserQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "insert into " + this.table + " (user_id, password, email, active) values(?,?,?,?)";
			this.compile(sql);
		}
		
		protected void execute(User user) throws SQLException {
			this.stmt.setString(1, user.getId());
			this.stmt.setString(2, user.getPassword());
			this.stmt.setString(3, user.getEmail());
			this.stmt.setInt(4, user.isActive() ? 1 : 0);
			this.stmt.executeUpdate();
		}
	}
	
	protected class UpdateUserQuery extends BaseUserQuery {
		protected UpdateUserQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "update " + this.table + " set password=?, email=?, active=? where user_id=?";
			this.compile(sql);
		}
		
		protected void execute(User user) throws SQLException {
			this.stmt.setString(1, user.getPassword());
			this.stmt.setString(2, user.getEmail());
			this.stmt.setInt(3, user.isActive() ? 1 : 0);
			this.stmt.setString(4, user.getId());
			this.stmt.executeUpdate();
		}
	}
	
	protected class GetUserQuery extends BaseUserQuery {
		protected GetUserQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select user_id, password, email, active from " + this.table + " where user_id=?";
			this.compile(sql);
		}
		
		protected User execute(String id) throws SQLException {
			User user = null;
			this.stmt.setString(1, id);
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				rs.next();
				user = create(rs);
				rs.close();
			}
			return user;
		}
	}
	
	protected class GetAllQuery extends BaseUserQuery {
		protected GetAllQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select user_id, password, email, active from " + this.table;
			this.compile(sql);
		}
		
		protected List<User> execute() throws SQLException {
			List<User> users = null;
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				users = new ArrayList<User>();
				while ( rs.next()) {
					users.add(create(rs));
				}
				rs.close();
			}
			return users;
		}
	}
}
