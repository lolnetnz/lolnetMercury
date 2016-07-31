package nz.co.lolnet.api.Mercury.authentication.lolnet_account;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nz.co.lolnet.api.Mercury.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by d_win on 31/07/2016.
 */
public class TokenHandler {

    private static final String TOKEN_TIMESTAMP = "token_timestamp";
    private static final String USER_ID = "user_id";
    private static final String TOKEN = "token";
    private static final String EXPIRES = "token_timestamp";
    public static final int ACCESS_TOKEN_EXPIRARY_TIME = 3600000; //1 hour

    public static String generateAccessToken(String username) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(String.valueOf(secureRandom.nextLong()).getBytes());
        byte[] rawHash = md.digest();
        String token = hexToString(rawHash);
        try {
            saveAccessToken(username, token);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return token;
    }

    private static String hexToString(byte[] input) {
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<input.length;i++) {
            hexString.append(Integer.toHexString(0xFF & input[i]));
        }
        return hexString.toString();
    }

    private static void saveAccessToken(String username, String token) throws ServletException {
        MongoDatabase mongoDB = nz.co.lolnet.api.Mercury.DatabaseConnection.getDatabase();
        MongoCollection coll = mongoDB.getCollection("access_tokens");
        JSONObject data = new JSONObject();
        data.put(TOKEN_TIMESTAMP, System.currentTimeMillis());
        data.put(USER_ID, username);
        data.put(TOKEN, token);
        data.put(EXPIRES, System.currentTimeMillis() + ACCESS_TOKEN_EXPIRARY_TIME);
        Document dbObject = new Document(data);
        coll.insertOne(dbObject);
    }

}
