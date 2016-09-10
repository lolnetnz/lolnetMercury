package nz.co.lolnet.api.mercury.config;

import java.io.File;
import java.io.IOException;

import nz.co.lolnet.api.mercury.util.ConsoleOutput;

public class MercuryConfig {
	
	public static Configuration config;
	private static File configFile;
	private final File configDirectory = new File("/var/lib/jetty9/config/");
	
    private File getConfigFolder() {
        return configDirectory;
    }
    
    public void loadConfig() {
    	if (!getConfigFolder().exists()) {
    		getConfigFolder().mkdir();
    		ConsoleOutput.info("Successfully created configuration directory.");
    	}
    	
        try {
        	configFile = new File(getConfigFolder(), "config.yml");
        	
            if (!configFile.exists()) {
                configFile.createNewFile();
                ConsoleOutput.info("Successfully created configuration file.");
            }
            
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
            ConsoleOutput.info("Successfully loaded configuration file.");
        } catch (IOException ex) {
        	ConsoleOutput.error("Exception loading configuration file!");
        	ex.printStackTrace();
        }
    }
}