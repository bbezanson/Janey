package com.janey.core.objects;

import org.json.JSONException;
import org.json.JSONWriter;

public class User {
	private String id;
	private String password;
	private String email;
	private boolean active;
	
	public User(String id, String password, String email, boolean active) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.active = active;
	}
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return this.email;
	}
	public boolean isActive() {
		return this.active;
	}
	public void toJson(JSONWriter out) throws JSONException {
		out.object();
		out.key("id");out.value(this.id);
		out.key("email");out.value(this.email);
		out.key("active");out.value(this.active);
		out.endObject();
	}
}
