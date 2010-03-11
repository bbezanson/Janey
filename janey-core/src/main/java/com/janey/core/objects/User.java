package com.janey.core.objects;

import org.json.JSONException;
import org.json.JSONWriter;

public class User {
	private String id;
	private String password;
	private String email;
	
	public User(String id, String password, String email) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
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
	public void toJson(JSONWriter out) throws JSONException {
		out.object();
		out.key("id");out.value(this.id);
		out.key("email");out.value(this.email);
		out.endObject();
	}
}
