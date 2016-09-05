package nz.co.lolnet.api.mercury;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private static MongoClient mongoClient;

    private DatabaseConnection() {
        init();
    }

    private void init() {
        ServerAddress serverAddress = new ServerAddress(Properties.getProperty("db_address"), Integer.parseInt(Properties.getProperty("db_port")));
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(Properties.getProperty("db_username"), Properties.getProperty("db_name"), Properties.getProperty("db_password").toCharArray());
        mongoClient = new MongoClient(serverAddress, Arrays.asList(mongoCredential));
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static MongoDatabase getDatabase() {
        return getInstance().getClientAndDatabase();
    }

    private MongoDatabase getClientAndDatabase() {
        return mongoClient.getDatabase(Properties.getProperty("db_name"));
    }

}
