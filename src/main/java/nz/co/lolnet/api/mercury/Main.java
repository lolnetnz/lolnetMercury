package nz.co.lolnet.api.mercury;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nz.co.lolnet.api.mercury.config.Configuration;

import nz.co.lolnet.api.mercury.config.MercuryConfig;

@Path("")
public class Main {

    private static Configuration config;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        
        return getConfig().getString("hello.player");
        /*
        MongoDatabase mongoDB = DatabaseConnection.getDatabase();
        MongoCollection coll = mongoDB.getCollection("test");
        Document dbObject = (Document) coll.find().first();

        try {
            Connection connection = nz.co.lolnet.api.mercury.authentication.lolnet_account.DatabaseConnection.getDatabase();
            Statement st = connection.createStatement();;
            ResultSet rs = st.executeQuery("SELECT * FROM test_table");
            String output = "";
            while (rs.next()) {
                output += " " + rs.getString("name");
            }
            return "Hello, world!" + output + " " + dbObject.toJson();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Hello, world!";
         */
    }
    
    public static Configuration getConfig() {
        if (config == null)
        {
            MercuryConfig mconfig = new MercuryConfig();
            mconfig.setupConfigFile();
            config = mconfig.config;
        }
        return config;
    }
}
