package com.janey.core.managers;

import java.sql.SQLException;
import java.util.List;

import com.janey.core.objects.Version;

public interface VersionManager {
	public void create(Version version) throws SQLException;
	public void update(Version version) throws SQLException;
	public void delete(Version version) throws SQLException;
	public List<Version> getAll(long productId) throws SQLException;
	public void destroy() throws SQLException;
}
