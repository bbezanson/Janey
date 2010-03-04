package com.janey.core.objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

public class Version {
	private long productId;
	private String version;
	
	public Version() {
	}
	
	public Version(JSONObject json) throws JSONException {
		this.productId = json.getLong("product_id");
		this.version = json.getString("version");
	}
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public void toJson(JSONWriter out) throws JSONException {
		out.object();
		out.key("product_id");out.value(this.productId);
		out.key("version");out.value(this.version);
		out.endObject();
	}
}
