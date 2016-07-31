package nz.co.lolnet.api.Mercury;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zaxxer.hikari.HikariDataSource;
import nz.co.lolnet.api.Mercury.authentication.lolnet_account.*;
import org.bson.Document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by d_win on 19/07/2016.
 */
@Path("")
public class Main {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        MongoDatabase mongoDB = DatabaseConnection.getDatabase();
        MongoCollection coll = mongoDB.getCollection("test");
        Document dbObject = (Document) coll.find().first();

        try {
            Connection connection = nz.co.lolnet.api.Mercury.authentication.lolnet_account.DatabaseConnection.getDatabase();
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
    }

}
