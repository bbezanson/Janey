package com.janey.core.helpers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.janey.core.objects.User;

public class JaneySession {
private static final String USER = "user";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, String> parameters;
	
	public JaneySession(HttpServletRequest req, HttpServletResponse resp) {
		this.request = req;
		this.response = resp;
		this.parameters = new HashMap<String, String>();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
		
	/**
	 * get the User from the session, the user object MUST
	 * be serialized in order for this to work properly
	 * @param req
	 * @return User object
	 */
	public User getUser() {
		User user = null;
		if ( this.request != null ) {
			HttpSession session = this.request.getSession();
			user = (User)session.getAttribute(USER);
		}
		return user;
	}
	
	/**
	 * set the User in the session, this user object MUST
	 * be serialized in order for this to work
	 * @param req
	 * @param user
	 */
	public void setUser(User user) {
		if ( this.request != null ) {
			HttpSession session = this.request.getSession();
			session.setAttribute(USER, user);
		}
	}
	
	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}
	
	public Map<String, String> getParameters() {
		return this.parameters;
	}
}
