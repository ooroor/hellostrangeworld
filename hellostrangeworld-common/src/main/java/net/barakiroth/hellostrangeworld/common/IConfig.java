package net.barakiroth.hellostrangeworld.common;

import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;

public interface IConfig {
	  int getInt(final String key);
	  int getInt(final String key, final int defaultValue);
	  int getRequiredInt(final String key);
	  String getString(final String key, final String defaultValue);
	  String getRequiredString(final String key);
	  IJettyManagerConfig getJettyManagerConfig();
	  JettyManager getJettyManager();
}