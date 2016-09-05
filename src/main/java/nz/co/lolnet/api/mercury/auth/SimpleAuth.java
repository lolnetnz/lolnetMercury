/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.api.mercury.auth;

import java.util.HashSet;
import java.util.prefs.Preferences;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import nz.co.lolnet.api.mercury.Main;

/**
 *
 * @author James
 */
@Path("/auth")
public class SimpleAuth {
    
    static Preferences userNodeForPackage = java.util.prefs.Preferences.userRoot();
    
    public boolean auth(String token,String IP)
    {
        if (Main.getConfig().getStringList("tokens").contains(token))
        {
            userNodeForPackage.put(IP, "true");
        }
        return true;
    }
    
    @GET
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login() {
        return "require token";
    }
    
    @GET
    @Path("login/{token}")
    @Produces(MediaType.TEXT_PLAIN)
    public String loginWithToken(@PathParam("token") String token, @Context HttpServletRequest requestContext,@Context SecurityContext context) {
        String yourIP = requestContext.getRemoteAddr();
        return "" + auth(token, yourIP);
    }
    
    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context HttpServletRequest requestContext,@Context SecurityContext context) {
        String yourIP = requestContext.getRemoteAddr();
        boolean isLoggedIn = isLoggedIn(yourIP);
        userNodeForPackage.remove(yourIP);
        return "" + isLoggedIn;
    }
    
    public static boolean isLoggedIn(String IP)
    {
        String get = userNodeForPackage.get(IP, "");
        return get.equals("true");
    }
}
