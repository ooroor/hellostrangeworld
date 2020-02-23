package net.barakiroth.hellostrangeworld.common;

public interface IConfig {
	  
	  int getInt(final String key);
	  
	  int getInt(final String key, final int defaultValue);
	  
	  int getRequiredInt(final String key);
	  
	  String getString(final String key, final String defaultValue);
	  
	  String getRequiredString(final String key);
}
