package nz.co.lolnet.api.Mercury;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by d_win on 30/07/2016.
 */
public class Properties {

    private static Properties instance;
    private static java.util.Properties properties;
    private final String INPUT_STREAM = "config.properties";

    private Properties() {
        init();
    }

    public static String getProperty(String property) {
        return getInstance().properties.getProperty(property);
    }

    public static Properties getInstance() {
        if (instance == null) {
            instance = new Properties();
        }
        return instance;
    }

    private void init() {
        InputStream input  = null;
        try {
            properties = new java.util.Properties();
            input = new FileInputStream(INPUT_STREAM);
            properties.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
