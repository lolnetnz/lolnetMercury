package nz.co.lolnet.api.mercury;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import nz.co.lolnet.api.mercury.config.MercuryConfig;

public class Mercury extends ServletContainer {
	
	private static final long serialVersionUID = 1L;
	public static final String version = "0.0.1-SNAPSHOT";
	
	public Mercury() {
		//This will get called by Jetty when loading the war file - due to the web.xml pointing at this class.
		//As this is only called once we can use it to setup our configuration files and establish MySQL connections.
		MercuryConfig mercuryConfig = new MercuryConfig();
		mercuryConfig.loadConfig();
	}
}