/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.api.mercury.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class MercuryConfig {

    private File getConfigFolder() {
        return new File(folderLocation);
    }

    final String folderLocation = "/var/lib/jetty9/config/";
    public Configuration config;

    public void setupConfigFile() {
        try {
            if (!getConfigFolder().exists()) {
                getConfigFolder().mkdir();
            }
            File configFile = new File(getConfigFolder(), "config.yml");
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getConfigFolder(), "config.yml"));
        } catch (IOException ex) {
            Logger.getLogger(MercuryConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
