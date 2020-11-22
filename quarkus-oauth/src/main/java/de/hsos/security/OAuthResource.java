package de.hsos.security;

import java.security.Principal;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/oauth")
public class OAuthResource {

    @GET
    @Path("public")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String hello() {
        return "Hello from PUBLIC RESOURCE in OAUTH2";
    }

    @GET
    @Path("admin")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAdmin(@Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, role: admin", name, ctx.isSecure(),
                ctx.getAuthenticationScheme());
        return helloReply;
    }

    @GET
    @Path("user")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesUser(@Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, role: user", name, ctx.isSecure(),
                ctx.getAuthenticationScheme());
        return helloReply;
    }
}