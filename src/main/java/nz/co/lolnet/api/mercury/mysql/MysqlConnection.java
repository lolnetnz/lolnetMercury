/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.api.mercury.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.lolnet.api.mercury.Main;

/**
 *
 * @author James
 */
public class MysqlConnection {
    
    
    public static Connection getLolConConnection() throws SQLException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MysqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MysqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MysqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URL = "jdbc:mysql://"+ Main.getConfig().getString("mysql.lolcon.host") + ":" 
                + Main.getConfig().getString("mysql.lolcon.port")+"/" 
                + Main.getConfig().getString("mysql.lolcon.database");
        String USERNAME = Main.getConfig().getString("mysql.lolcon.username");
        String PASSWORD = Main.getConfig().getString("mysql.lolcon.password");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
