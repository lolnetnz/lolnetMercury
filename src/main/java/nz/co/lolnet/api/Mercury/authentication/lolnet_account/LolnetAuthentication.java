package nz.co.lolnet.api.Mercury.authentication.lolnet_account;

import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by d_win on 31/07/2016.
 */
@Path("login")
public class LolnetAuthentication {

    private static final String AUTHENTICATION_USERNAME = "username";
    private static final String AUTHENTICATION_PASSWORD = "password";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String SCOPE = "scope";
    private static final String TOKEN_TYPE = "token_type";
    private static final String USER_ID = "user_id";
    private static final String EXPIRES = "token_timestamp";
    public static final int ACCESS_TOKEN_EXPIRARY_TIME = 3600000; //1 hour

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject authenticate(JSONObject authenticationObject) throws NoSuchAlgorithmException {
        String username = (String) authenticationObject.get(AUTHENTICATION_USERNAME);
        String password = (String) authenticationObject.get(AUTHENTICATION_PASSWORD);
        JSONObject output = new JSONObject();
        if(authenticateUser(username, password))
        {
            output.put(ACCESS_TOKEN, TokenHandler.generateAccessToken(username.toLowerCase()));
            output.put(EXPIRES, ACCESS_TOKEN_EXPIRARY_TIME);
            output.put(REFRESH_TOKEN, "REFRESHTOKENGOESHERE");
            output.put(SCOPE, "");
            output.put(TOKEN_TYPE, "Bearer");
            output.put(USER_ID, username.toLowerCase());
        }
        else
        {
            Response.status(401);
            output.put("success", false);
        }
        return output;
    }

    private static boolean authenticateUser(String username, String password)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getDatabase().prepareStatement("SELECT `user_password` FROM `phpbb_users` WHERE `username_clean`=? LIMIT 1;");
            ps.setString(1, username.toLowerCase());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                /*
                * Requires the replacement of 2y with 2a as PHP BCrypt does something weird with BCrypt hashes...
                 */
                String user_password = rs.getString("user_password").replaceFirst("2y","2a");
                if(BCrypt.checkpw(password, user_password))
                {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
