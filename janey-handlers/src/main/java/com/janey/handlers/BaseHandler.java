package com.janey.handlers;


public abstract class BaseHandler implements Handler {
	private String action;
	
	public BaseHandler(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return this.action;
	}
}
