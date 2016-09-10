package nz.co.lolnet.api.mercury.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nz.co.lolnet.api.mercury.config.MercuryConfig;
import nz.co.lolnet.api.mercury.util.ConsoleOutput;

public class LolConDatabase {
	
    public static Connection getLolConConnection() throws SQLException {
    	
    	String host = MercuryConfig.config.getString("mysql.lolcon.host");
    	int port = MercuryConfig.config.getInt("mysql.lolcon.port");
    	String database = MercuryConfig.config.getString("mysql.lolcon.database");
    	String username = MercuryConfig.config.getString("mysql.lolcon.username");
    	String password = MercuryConfig.config.getString("mysql.lolcon.password");
    	
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        	ConsoleOutput.error("Encountered an error processing 'getLolConConnection' - Exception!");
        	ex.printStackTrace();
        }
        
        return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
    }
}