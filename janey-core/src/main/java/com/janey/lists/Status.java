package com.janey.lists;

import java.util.HashMap;

public class Status {
	public static final int NEW = 1;
	public static final int OPEN = 2;
	public static final int CLOSED = 3;
	public static final int DEFERRED = 4;
	public static final int DUPLICATE = 5;
	
	private static final HashMap<Integer, String> statuses;
	
	static {
		statuses = new HashMap<Integer, String>();
		statuses.put(new Integer(NEW), "New");
		statuses.put(new Integer(OPEN), "Open");
		statuses.put(new Integer(CLOSED), "Closed");
		statuses.put(new Integer(DEFERRED), "Deferred");
		statuses.put(new Integer(DUPLICATE), "Duplicate");
	}
	
	public static HashMap getStatuses() {return statuses;}
	
	public static String getStatus(String key) {
		if ( null != key ) {
			return (String)statuses.get(key);
		}
		return null;
	}
}
