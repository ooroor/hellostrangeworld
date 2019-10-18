package net.barakiroth.hellostrangeworld.farbackend;

import java.io.File;
import java.util.stream.Stream;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManagerConfig;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
    private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");
    
    private final CompositeConfiguration compositeConfiguration;

    @Getter(AccessLevel.PUBLIC)
    private final JettyManagerConfig     jettyManagerConfig;
    
    @Getter(AccessLevel.PUBLIC)
    private final JettyManager           jettyManager;
    
    public Config() {
    	
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
            .forEach((propertyFileName) -> {
                final BaseConfiguration propertyFileConfiguration =
                        createPropertyFileConfiguration(propertyFileName);
                if (propertyFileConfiguration != null) {
                    compositeConfiguration.addConfiguration(propertyFileConfiguration);
                }
            });
        this.compositeConfiguration = compositeConfiguration;
        log.info("Configurations loaded from property files");
        log.info("All configurations loaded");
    	
    	this.jettyManagerConfig = new JettyManagerConfig(this);
    	this.jettyManager = new JettyManager(this);
    	
    	leavingMethodHeaderLogger.debug(null);
    }

    public String getString(final String key, final String defaultValue) {
        return this.compositeConfiguration.getString(key, defaultValue);
    }

    public int getInteger(final String key) {
        return getInteger(key, 0);
    }

    public int getInteger(final String key, final int defaultValue) {
        return this.compositeConfiguration.getInt(key, defaultValue);
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