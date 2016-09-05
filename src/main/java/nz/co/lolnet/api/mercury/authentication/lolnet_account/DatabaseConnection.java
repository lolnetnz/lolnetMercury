package nz.co.lolnet.api.mercury.authentication.lolnet_account;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import nz.co.lolnet.api.mercury.Properties;

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
