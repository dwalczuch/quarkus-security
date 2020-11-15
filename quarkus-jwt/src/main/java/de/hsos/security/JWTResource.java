package de.hsos.security;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.HashSet;

@Path("/jwt")
public class JWTResource {

    @Inject
    JsonWebToken jwt;
    @Inject
    @Claim(standard = Claims.birthdate)
    String birthdate;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    @Path("public")
    public String publicResource() {
        return "Hello from PUBLIC RESOURCE in JWT";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("admin")
    @Path("admin")
    public String hello() {
        return "top secret message only admins can read";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"user", "admin"})
    @Path("user")
    public String me(@Context SecurityContext securityContext) {
        return "Hello "
                + securityContext.getUserPrincipal().getName()
                + "! You are logged in and your birthdate is "
                + birthdate;    //or jwt.getClaim("birthdate").toString();
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    @Path("getJWT")
    public String getJWT() {
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn("jdoe@quarkus.io")
                        .groups(new HashSet<>(Arrays.asList("user", "admin")))
                        .claim(Claims.birthdate.name(), "2001-07-13")
                        .sign();
        System.out.println(token);
        return token;
    }
}