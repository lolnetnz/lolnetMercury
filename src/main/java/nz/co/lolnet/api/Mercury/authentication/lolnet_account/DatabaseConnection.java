package nz.co.lolnet.api.Mercury.authentication.lolnet_account;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import nz.co.lolnet.api.Mercury.Properties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by d_win on 30/07/2016.
 */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private static HikariDataSource hikaridataSource;

    private DatabaseConnection() {
        init();
    }

    private void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Properties.getProperty("authentication_jdbcurl"));
        config.setUsername(Properties.getProperty("authentication_username"));
        config.setPassword(Properties.getProperty("authentication_password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikaridataSource = new HikariDataSource(config);
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection getDatabase() throws SQLException {
        return getInstance().getClientAndDatabase();
    }

    private Connection getClientAndDatabase() throws SQLException { return hikaridataSource.getConnection(); }

}
