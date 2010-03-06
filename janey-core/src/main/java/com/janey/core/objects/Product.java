package com.janey.core.objects;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

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
		this.releaseDate = new Timestamp(json.getLong("release_date"));
		this.eosDate = new Timestamp(json.getLong("eosDate"));
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
	public void toJson(JSONWriter out) throws JSONException {
		out.object();
		out.key("product_id");out.value(this.productId);
		out.key("company_id");out.value(this.companyId);
		out.key("owner");out.value(this.owner);
		out.key("name");out.value(this.name);
		out.key("description");out.value(this.description);
		out.key("release_date");out.value(this.releaseDate);
		out.key("eos_date");out.value(this.eosDate);
		out.key("platforms");out.value(this.platforms);
		out.endObject();
	}
}
