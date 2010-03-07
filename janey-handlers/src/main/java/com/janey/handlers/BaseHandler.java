package com.janey.handlers;

import org.json.JSONException;
import org.json.JSONWriter;

public abstract class BaseHandler implements Handler {
	protected int STAT_SUCCESS = 0;
	protected int STAT_UNKNOWN = 1;
	protected int ERROR_CONFIG = 100;
	
	private String action;
	
	public BaseHandler(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public void returnStatus(JSONWriter out, int status) throws JSONException {
		out.object();
		out.key("status");out.value(status);
		out.endObject();
	}
}
