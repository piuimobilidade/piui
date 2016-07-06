package co.piui.api.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException.Missing;
import com.typesafe.config.ConfigFactory;

import lombok.val;
import lombok.extern.java.Log;

@Log
public class Configuration {

	private Configuration() {}

	static final String CONF_REFERENCE = "conf/reference";

	/**
	 * Carrega a partir de um arquivo que está no CLASSPATH.
	 * 
	 * @return
	 */
	public static Config loadConfiguration() {
		val defaultConfiguration = ConfigFactory.load( "conf/application" );
		return defaultConfiguration.withFallback( ConfigFactory.load( CONF_REFERENCE ) );
	}

	/**
	 * Load default configuration from CLASS_PATH. The {@code rootName} argument
	 * defines which is the root of specific configuration in your code.
	 * 
	 * @param rootName
	 * @return
	 */
	public static Config loadConfiguration( String rootName ) {
		val defaultConfiguration = loadConfiguration();
		return loadConfiguration( rootName, defaultConfiguration );
	}

	/*
	 * Carrega valores a partir de um arquivo externo da aplicação
	 */
	public static Config loadExternalConfiguration() {
		String exdir = System.getenv( "home" );
		String configuration = exdir + "/piui";
		val defaultConfiguration = ConfigFactory.load( configuration );
		log.info( "Reading cobol configuration file: " + defaultConfiguration );
		return defaultConfiguration.withFallback( loadConfiguration() );
	}

	public static Config loadCobolConfiguration( String rootName ) {
		return loadConfiguration( rootName, loadExternalConfiguration() );
	}

	public static Config loadConfiguration( String rootName, Config defaultConfiguration ) {
		try {
			val config = defaultConfiguration.getConfig( rootName );
			return config.withFallback( defaultConfiguration );
		} catch ( Missing cause ) {
			log.warning( "Missing configuration for " + rootName + ". Using default configuration." );
			return defaultConfiguration;
		}
	}
}