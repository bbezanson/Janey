package com.janey.core.managers;

public interface PrefsManager {
	public void set(String key, String val);
	public String get(String key);
	public String get(String key, String defaultVal);
}
