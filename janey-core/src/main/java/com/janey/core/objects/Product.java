package com.janey.core.objects;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
	private long productId;
	private long companyId;
	private long owner;
	private String name;
	private String description;
	private Timestamp releaseDate;
	private Timestamp eosDate;
	private String platforms;
	
	public Product() {
	}
	public Product(JSONObject json) throws JSONException {
		this.productId = json.getLong("product_id");
		this.companyId = json.getLong("company_id");
		this.owner = json.getLong("owner");
		this.name = json.getString("name");
		this.description = json.getString("description");
		//this.releaseDate = json.getLong("release_date");
		//this.eosDate = json.getLong("eosDate");
		this.platforms = json.getString("platforms");
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getEosDate() {
		return eosDate;
	}
	public void setEosDate(Timestamp eosDate) {
		this.eosDate = eosDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getPlatforms() {
		return platforms;
	}
	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public Timestamp getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

}
