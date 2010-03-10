package com.janey.core.objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

public class Product {
	private long productId;
	private int owner;
	private String name;
	private String description;
	
	public Product() {
	}
	public Product(JSONObject json) throws JSONException {
		this.productId = json.getLong("product_id");
		this.owner = json.getInt("owner");
		this.name = json.getString("name");
		this.description = json.getString("description");
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public void toJson(JSONWriter out) throws JSONException {
		out.object();
		out.key("product_id");out.value(this.productId);
		out.key("owner");out.value(this.owner);
		out.key("name");out.value(this.name);
		out.key("description");out.value(this.description);
		out.endObject();
	}
}
