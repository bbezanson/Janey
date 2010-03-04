package com.janey.core.managers;

import java.sql.SQLException;
import java.util.List;

import com.janey.core.objects.Comment;

public interface CommentManager {
	public void create(Comment comment) throws SQLException;
	public void update(Comment comment) throws SQLException;
	public void delete(Comment comment) throws SQLException;
	public List<Comment> getAll(long issueId) throws SQLException;
	public void destroy() throws SQLException;
}
