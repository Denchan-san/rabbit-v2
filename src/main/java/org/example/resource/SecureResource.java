package org.example.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SecureResource {

    @GET
    @Path("/user-data")
    @RolesAllowed("user") // Secures this endpoint for users with the 'user' role
    public String getUserData() {
        return "{\"message\": \"This is secure data only for authenticated users.\"}";
    }
}
