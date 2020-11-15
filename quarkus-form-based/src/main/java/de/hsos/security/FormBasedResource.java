package de.hsos.security;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/form")
public class FormBasedResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    @Path("public")
    public String publicResource() {
        return "Hello from PUBLIC RESOURCE in FORM";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("admin")
    @Path("admin")
    public String admin() {
        return "top secret message only admins can read";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"user", "admin"})
    @Path("user")
    public String me(@Context SecurityContext securityContext) {
        return "Hello " + securityContext.getUserPrincipal().getName() + "! You are logged in.";
    }
}