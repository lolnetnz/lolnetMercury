/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.api.mercury.LolCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import nz.co.lolnet.api.mercury.auth.SimpleAuth;
import nz.co.lolnet.api.mercury.mysql.MysqlConnection;
import org.json.simple.JSONObject;

/**
 *
 * @author James
 */
@Path("/lolcoins/getplayerbalance")
public class GetPlayerBalance {


    @GET
    public String noInputHere() {
        return "Missing playerName";
    }

    @GET
    @Path("{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello(@PathParam("playerName") String playerName , @Context HttpServletRequest requestContext,@Context SecurityContext context) {

        String yourIP = requestContext.getRemoteAddr();
        if (!SimpleAuth.isLoggedIn(yourIP))
        {
            return "No Access, please login first";
        }

        Connection con = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = MysqlConnection.getLolConConnection();
            st = con.createStatement();
            ps = con.prepareStatement("SELECT `lolcoins` FROM `player` WHERE `playerName`=? LIMIT 0 , 1");
            ps.setString(1, playerName);
            rs = ps.executeQuery();
            int output = -1;
            while (rs.next()) {
                output = rs.getInt("lolcoins");
            }
            JSONObject outputJ = new JSONObject();
            outputJ.put("playerbalance", output);
            return outputJ.toJSONString();

        } catch (SQLException ex) {
            Logger.getLogger(GetPlayerBalance.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }
}
