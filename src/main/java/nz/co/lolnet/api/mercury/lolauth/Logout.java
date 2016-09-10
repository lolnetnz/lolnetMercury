package nz.co.lolnet.api.mercury.lolauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import nz.co.lolnet.api.mercury.mysql.LolAuthDatabase;
import nz.co.lolnet.api.mercury.util.ConsoleOutput;
import nz.co.lolnet.api.mercury.util.ResponseReference;

@Path("/auth/logout")
public class Logout {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String logout() {
		return ResponseReference.METHOD_NOT_ALLOWED;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String logout(@Context HttpServletRequest requestContext) {
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(requestContext.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			line = null;
			bufferedReader.close();
			
			JsonObject request = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
			stringBuilder = null;
			
			if (request.size() != 2 || !request.has("developerId") || !request.has("developerSecret")) {
				request = null;
				return ResponseReference.BAD_REQUEST;
			}
			
			Connection connection = LolAuthDatabase.getLolAuthConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT `developerSecret` FROM `accounts` WHERE `developerId`=? LIMIT 0 , 1");
			preparedStatement.setString(1, request.get("developerId").getAsString());
			ResultSet result = preparedStatement.executeQuery();
			preparedStatement = null;
			result.next();
			
			if (result.getRow() == 0) {
				result = null;
				request = null;
				return ResponseReference.NOT_FOUND;
			}
			
			if (!result.getString("developersecret").equals(DigestUtils.sha512Hex(request.get("developerSecret").getAsString()))) {
				result = null;
				request = null;
				return ResponseReference.FORBIDDEN;
			}
			
			result = null;
			
			preparedStatement = connection.prepareStatement("DELETE FROM `clients` WHERE `developerId`=?");
			preparedStatement.setString(1, request.get("developerId").getAsString());
			preparedStatement.executeUpdate();
			preparedStatement = null;
			request = null;
			
			return "";
		} catch (IOException | IllegalStateException | JsonParseException | SQLException ex) {
        	ConsoleOutput.error("Encountered an error processing 'getLolAuthConnection' - Exception!");
        	ex.printStackTrace();
		}
		return ResponseReference.INTERNAL_SERVER_ERROR;
	}
}