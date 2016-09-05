/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.api.mercury.auth;

import java.io.File;
import java.io.IOException;
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
import nz.co.lolnet.api.mercury.Main;

/**
 *
 * @author James
 */
@Path("/auth")
public class SimpleAuth {

    static final String folderLocation = "/var/lib/jetty9/config/auth";

    public boolean auth(String token, String IP) {
        if (Main.getConfig().getStringList("tokens").contains(token)) {
            File file = new File(getConfigFolder(), IP);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SimpleAuth.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    @GET
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login() {
        if (!getConfigFolder().exists()) {
            getConfigFolder().mkdir();
        }
        return "require token";
    }

    @GET
    @Path("login/{token}")
    @Produces(MediaType.TEXT_PLAIN)
    public String loginWithToken(@PathParam("token") String token, @Context HttpServletRequest requestContext, @Context SecurityContext context) {
        String yourIP = requestContext.getRemoteAddr();
        return "" + auth(token, yourIP);
    }

    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context HttpServletRequest requestContext, @Context SecurityContext context) {
        String yourIP = requestContext.getRemoteAddr();
        boolean isLoggedIn = isLoggedIn(yourIP);
        File file = new File(getConfigFolder(), yourIP);
        file.delete();
        return "" + isLoggedIn;
    }

    public static boolean isLoggedIn(String IP) {
        File file = new File(getConfigFolder(), IP);
        return file.exists();
    }

    private static File getConfigFolder() {
        return new File(folderLocation);
    }
}
