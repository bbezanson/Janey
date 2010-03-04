package com.janey.lists;

import java.util.HashMap;

public class Platform {
	public static final int NA = 1;
	public static final int WINDOWS = 2;
	public static final int MAC = 3;
	public static final int LINUX = 4;
	public static final int OTHER = 5;
	
	private static final HashMap<Integer, String> platforms;
	
	static {
		platforms = new HashMap<Integer, String>();
		platforms.put(new Integer(NA), "N/A");
		platforms.put(new Integer(WINDOWS), "Windows");
		platforms.put(new Integer(MAC), "Mac");
		platforms.put(new Integer(LINUX), "Linux");
		platforms.put(new Integer(OTHER), "Other");
	}
	
	public static HashMap getPlatforms() {return platforms;}
	
	public static String getPlatform(String key) {
		if ( null != key ) {
			return (String)platforms.get(key);
		}
		return null;
	}

}
