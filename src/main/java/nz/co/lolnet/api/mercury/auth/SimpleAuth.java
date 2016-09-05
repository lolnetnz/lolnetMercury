/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.api.mercury.auth;

import java.util.HashSet;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import nz.co.lolnet.api.mercury.Main;

/**
 *
 * @author James
 */
@Path("/auth")
public class SimpleAuth {
    public static HashSet<String> trustedIP = new HashSet<>();
    
    
    @Resource
    WebServiceContext webServiceContext;
    
    public boolean auth(String token,String IP)
    {
        if (Main.getConfig().getStringList("tokens").contains(token))
        {
            trustedIP.add(IP);
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
    public String loginWithToken(@PathParam("token") String token) {
        MessageContext messageContext = webServiceContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
        String callerIpAddress = request.getRemoteAddr();
        return "" + auth(token, callerIpAddress);
    }
    
    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
        String callerIpAddress = request.getRemoteAddr();
        return "" + trustedIP.remove(callerIpAddress);
    }
}
