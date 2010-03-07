package com.janey.core.managers;

import java.util.Properties;

public interface PrefsManager {
	public void save(Properties props);
	public Properties restore();
}
