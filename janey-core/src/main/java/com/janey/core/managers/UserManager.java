package com.janey.core.managers;

import java.sql.SQLException;
import java.util.List;

import com.janey.core.objects.User;

public interface UserManager {
	public void create(User user) throws SQLException;
	public void update(User user) throws SQLException;
	public User get(String id) throws SQLException;
	public List<User> getAll() throws SQLException;
	public void destroy() throws SQLException;
}
