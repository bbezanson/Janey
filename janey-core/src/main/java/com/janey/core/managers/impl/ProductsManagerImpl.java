package com.janey.core.managers.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.janey.core.dao.BaseQuery;
import com.janey.core.managers.ProductsManager;
import com.janey.core.objects.Product;

public class ProductsManagerImpl implements ProductsManager {

	private ProductCreateQuery productCreateQuery;
	private ProductUpdateQuery productUpdateQuery;
	private ProductDeleteQuery productDeleteQuery;
	private ProductGetQuery productGetQuery;
	private ProductGetAllQuery productGetAllQuery;
	
	public ProductsManagerImpl(Connection conn) throws SQLException {
		productCreateQuery = new ProductCreateQuery(conn);
		productUpdateQuery = new ProductUpdateQuery(conn);
		productDeleteQuery = new ProductDeleteQuery(conn);
		productGetQuery = new ProductGetQuery(conn);
		productGetAllQuery = new ProductGetAllQuery(conn);
	}
	
	public void create(Product product) throws SQLException {
		productCreateQuery.execute(product);
	}

	public void delete(long id) throws SQLException {
		productDeleteQuery.execute(id);
	}

	public Product get(long id) throws SQLException {
		return productGetQuery.execute(id);
	}

	public List<Product> getAll() throws SQLException {
		return productGetAllQuery.execute();
	}

	public void update(Product product) throws SQLException {
		productUpdateQuery.execute(product);
	}

	public void destroy() throws SQLException {
		this.productCreateQuery.destroy();
		this.productDeleteQuery.destroy();
		this.productGetAllQuery.destroy();
		this.productGetQuery.destroy();
		this.productUpdateQuery.destroy();
	}
	
	protected class BaseProductQuery extends BaseQuery {
		protected String table = "product";
		
		protected BaseProductQuery(Connection conn) {
			super(conn);
		}
		
		protected Product create(ResultSet rs) throws SQLException {
			Product p = new Product();
			p.setProductId(rs.getLong("product_id"));
			p.setCompanyId(rs.getLong("company_id"));
			p.setOwner(rs.getInt("owner"));
			p.setName(rs.getString("name"));
			p.setDescription(rs.getString("description"));
			p.setReleaseDate(rs.getTimestamp("release_date"));
			p.setEosDate(rs.getTimestamp("eos_date"));
			p.setPlatforms(rs.getString("platforms"));
			return p;
		}
	}
	
	protected class ProductCreateQuery extends BaseProductQuery {
		protected ProductCreateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "insert into " +this.table+ " (product_id, company_id, owner, name, description, release_date, eos_date, platforms) values (?,?,?,?,?,?,?,?)";
			this.compile(sql);
		}
		
		protected void execute(Product product) throws SQLException {
			this.stmt.setLong(1, product.getProductId());
			this.stmt.setLong(2, product.getCompanyId());
			this.stmt.setInt(3, product.getOwner());
			this.stmt.setString(4, product.getName());
			this.stmt.setString(5, product.getDescription());
			this.stmt.setTimestamp(6, product.getReleaseDate());
			this.stmt.setTimestamp(7, product.getEosDate());
			this.stmt.setString(8, product.getPlatforms());
			this.stmt.executeUpdate();
		}
	}
	
	protected class ProductUpdateQuery extends BaseProductQuery {
		protected ProductUpdateQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "update " +this.table+ " set company_id=?, owner=?, name=?, description=?, release_date=?, eos_date=?, platforms=? where product_id=?";
			this.compile(sql);
		}
		
		protected void execute(Product product) throws SQLException {
			this.stmt.setLong(1, product.getCompanyId());
			this.stmt.setInt(2, product.getOwner());
			this.stmt.setString(3, product.getName());
			this.stmt.setString(4, product.getDescription());
			this.stmt.setTimestamp(5, product.getReleaseDate());
			this.stmt.setTimestamp(6, product.getEosDate());
			this.stmt.setString(7, product.getPlatforms());
			this.stmt.setLong(8, product.getProductId());
			this.stmt.executeUpdate();
		}
	}
	
	protected class ProductDeleteQuery extends BaseProductQuery {
		protected ProductDeleteQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "delete from " + this.table + " where product_id=?";
			this.compile(sql);
		}
		
		protected void execute(long id) throws SQLException {
			this.stmt.setLong(1, id);
			this.stmt.executeUpdate();
		}
	}
	
	protected class ProductGetQuery extends BaseProductQuery {
		protected ProductGetQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select company_id, owner, name, description, release_date, eos_date, platforms from " + this.table + " where product_id=?";
			this.compile(sql);
		}
		
		protected Product execute(long id) throws SQLException {
			Product p = null;
			
			this.stmt.setLong(1, id);
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				rs.next();
				p = create(rs);
				rs.close();
			}
			return p;
		}
	}
	
	protected class ProductGetAllQuery extends BaseProductQuery {
		protected ProductGetAllQuery(Connection conn) throws SQLException {
			super(conn);
			String sql = "select product_id, company_id, owner, name, description, release_date, eos_date, platforms from " + this.table;
			this.compile(sql);
		}
		
		protected List<Product> execute() throws SQLException {
			List<Product> products = null;
			
			ResultSet rs = this.stmt.executeQuery();
			if ( rs != null ) {
				products = new ArrayList<Product>();
				while(rs.next()) {
					products.add(create(rs));
				}
				rs.close();
			}
			return products;
		}
	}
}
