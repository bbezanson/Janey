package com.janey.core.managers;

import java.sql.SQLException;
import java.util.List;

import com.janey.core.objects.Issue;

public interface IssueManager {
	public void create(Issue issue) throws SQLException;
	public void update(Issue issue) throws SQLException;
	public void delete(long id) throws SQLException;
	public Issue get(long id) throws SQLException;
	public List<Issue> getAll() throws SQLException;
	public List<Issue> search(/*TODO: criteria*/) throws SQLException;
	public void destroy() throws SQLException;
}
