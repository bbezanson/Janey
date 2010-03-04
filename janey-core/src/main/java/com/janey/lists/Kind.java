package com.janey.lists;

import java.util.HashMap;

public class Kind {
	public static final int MINOR = 1;
	public static final int MAJOR = 2;
	public static final int SEVERE = 3;
	public static final int CRASH = 4;
	public static final int REQUEST = 5;
	public static final int REQUIREMENT = 6;
	public static final int OTHER = 7;
	
	private static final HashMap<Integer, String> kinds;
	
	static{
		kinds = new HashMap<Integer, String>();
		kinds.put(new Integer(MINOR), "Minor");
		kinds.put(new Integer(MAJOR), "Major");
		kinds.put(new Integer(SEVERE), "Severe");
		kinds.put(new Integer(CRASH), "Crash");
		kinds.put(new Integer(REQUEST), "Enhancement Request");
		kinds.put(new Integer(REQUIREMENT), "Product Requirement");
		kinds.put(new Integer(OTHER), "Other");
	}
	
	public static HashMap getKinds() {return kinds;}
	
	public static String getKind(String key) {
		if ( null != key ) {
			return (String)kinds.get(key);
		}
		return null;
	}
}
