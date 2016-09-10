package nz.co.lolnet.api.mercury.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nz.co.lolnet.api.mercury.config.MercuryConfig;
import nz.co.lolnet.api.mercury.util.ConsoleOutput;

public class LolAuthDatabase {
	
    public static Connection getLolAuthConnection() throws SQLException {
    	
    	String host = MercuryConfig.config.getString("mysql.lolauth.host");
    	int port = MercuryConfig.config.getInt("mysql.lolauth.port");
    	String database = MercuryConfig.config.getString("mysql.lolauth.database");
    	String username = MercuryConfig.config.getString("mysql.lolauth.username");
    	String password = MercuryConfig.config.getString("mysql.lolauth.password");
    	
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        	ConsoleOutput.error("Encountered an error processing 'getLolAuthConnection' - Exception!");
        	ex.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
    }
}