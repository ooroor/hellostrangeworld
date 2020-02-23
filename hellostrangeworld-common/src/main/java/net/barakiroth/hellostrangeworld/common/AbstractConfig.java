package net.barakiroth.hellostrangeworld.common;

import java.io.File;
import java.util.stream.Stream;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfig implements IConfig {

	  private static final Logger log = LoggerFactory.getLogger(AbstractConfig.class);
	  private static final Logger enteringMethodHeaderLogger =
	      LoggerFactory.getLogger("EnteringMethodHeader");
	  private static final Logger leavingMethodHeaderLogger  =
	      LoggerFactory.getLogger("LeavingMethodHeader");
	  
	  private final CompositeConfiguration compositeConfiguration;
	    
	  protected AbstractConfig() {
	    
	    enteringMethodHeaderLogger.debug(null);
	    
	    final CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
	    compositeConfiguration.addConfiguration(new SystemConfiguration());
	    compositeConfiguration.addConfiguration(new EnvironmentConfiguration());
	    log.info("Configurations loaded from system and environment variables");
	    
	    Stream
	        .of(
	            "application-test.properties",
	            "application.properties"
	        )
	        .forEach(propertyFileName -> {
	          final BaseConfiguration propertyFileConfiguration =
	              createPropertyFileConfiguration(propertyFileName);
	          if (propertyFileConfiguration != null) {
	            compositeConfiguration.addConfiguration(propertyFileConfiguration);
	          }
	        });
	    this.compositeConfiguration = compositeConfiguration;
	    log.info("Configurations loaded from property files");
	    log.info("All configurations loaded");
	    
	    leavingMethodHeaderLogger.debug(null);
	  }

	  @Override
	  public String getString(final String key, final String defaultValue) {
	    return this.compositeConfiguration.getString(key, defaultValue);
	  }

	  @Override
	  public String getRequiredString(final String key) {
	    checkRequired(key);
	    final String value = getString(key, null);
	    return value;
	  }

	  @Override
	  public int getInt(final String key) {
	    return getInt(key, 0);
	  }

	  @Override
	  public int getInt(final String key, final int defaultValue) {
	    return this.compositeConfiguration.getInt(key, defaultValue);
	  }

	  @Override
	  public int getRequiredInt(final String key) {
	    checkRequired(key);
	    final int value = getInt(key);
	    return value;
	  }

	  private void checkRequired(final String key) {
	    Validate.validState(
	        this.compositeConfiguration.containsKey(key),
	        "Couldn't find configuration value for the key %s",
	        key
	    );
	  }

	  private BaseConfiguration createPropertyFileConfiguration(final String path) {

	    enteringMethodHeaderLogger.debug(null);
	    
	    PropertiesConfiguration propertyFileConfiguration = null;
	    try {
	      propertyFileConfiguration = new Configurations().properties(new File(path));
	      log.info("Configuration loaded from {}", path);
	    } catch (ConfigurationException e) {
	      log.warn("Couldn't find {}", path);
	    }
	    
	    leavingMethodHeaderLogger.debug(null);
	    
	    return propertyFileConfiguration;
	  }
}