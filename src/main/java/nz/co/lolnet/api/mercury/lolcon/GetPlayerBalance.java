package nz.co.lolnet.api.mercury.lolcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;

import nz.co.lolnet.api.mercury.mysql.LolConDatabase;
import nz.co.lolnet.api.mercury.util.ConsoleOutput;
import nz.co.lolnet.api.mercury.util.ResponseReference;

@Path("/lolcoins/getplayerbalance")
public class GetPlayerBalance {
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayerBalance() {
        return ResponseReference.BAD_REQUEST;
    }

    @GET
    @Path("{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayerBalance(@PathParam("playerName") String playerName, @Context HttpServletRequest requestContext) {
    	
    	/*
        if (!SimpleAuth.isLoggedIn(requestContext.getRemoteAddr())) {
            return ResponseReference.FORBIDDEN;
        }
        */
        
        try {
            Connection connection = LolConDatabase.getLolConConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `lolcoins` FROM `player` WHERE `playerName`=? LIMIT 0 , 1");
            preparedStatement.setString(1, playerName);
            ResultSet result = preparedStatement.executeQuery();
            
            result.next();
            
            JsonObject response = new JsonObject();
            
            if (result.getRow() == 0) {
            	response.addProperty("playerBalance", result.getInt("lolcoins"));
            } else {
            	response.addProperty("playerBalance", -1);
            }
            
            return response.getAsJsonObject().toString();
        } catch (SQLException ex) {
        	ConsoleOutput.error("Encountered an error processing 'getPlayerBalance " + playerName + "' for '" + requestContext.getRemoteAddr() + "' - SQLException!");
        	ex.printStackTrace();
        }
        return ResponseReference.INTERNAL_SERVER_ERROR;
    }
}