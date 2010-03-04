package com.janey.core.managers;

import java.sql.SQLException;
import java.util.List;

import com.janey.core.objects.Product;

public interface ProductsManager {
	public void create(Product product) throws SQLException;
	public void update(Product product) throws SQLException;
	public void delete(long id) throws SQLException;
	public Product get(long id) throws SQLException;
	public List<Product> getAll() throws SQLException;
	public void destroy() throws SQLException;
}
