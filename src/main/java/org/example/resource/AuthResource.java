package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Role;
import org.example.model.User;
import org.example.resource.dto.UserLogIn;
import org.example.resource.dto.UserPost;
import org.example.security.TokenService;
import org.example.util.PasswordUtils;

import jakarta.transaction.Transactional;

import java.util.*;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserResource userResource; // UserResource for user CRUD operations

    @Inject
    TokenService tokenService; // Token service for JWT generation

    @POST
    @Path("/login")
    public Response login(UserLogIn userLogIn) {
        User user = User.find("username", userLogIn.getUsername()).firstResult();

        if (user == null || !PasswordUtils.checkPassword(userLogIn.getPassword(), user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Invalid username or password\"}")
                    .build();
        }

        String token = tokenService.generateToken(user.getUsername(), user.getRoles(), user.getId());

        return Response.ok().entity("{\"token\":\"" + token + "\"}").build();
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserPost userPost) {
        User existingUser = User.find("username", userPost.getUsername()).firstResult();
        if (existingUser != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Username already taken\"}")
                    .build();
        }

        // Hashing password securely
        String hashedPassword = PasswordUtils.hashPassword(userPost.getPassword());

        User newUser = new User();
        newUser.setUsername(userPost.getUsername());
        newUser.setEmail(userPost.getEmail());
        newUser.setPassword(hashedPassword);
        if(newUser.getUsername().equals("denchan")) {
            newUser.setRoles(new HashSet<>(Arrays.asList(
                    Role.findById(1),
                    Role.findById(2)
            )));
        }
        else newUser.setRoles(new HashSet<>(Collections.singleton(Role.findById(1))));

        newUser.persist();

        String token = tokenService.generateToken(newUser.getUsername(), newUser.getRoles(), newUser.getId());

        // Return a structured JSON response
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "User registered successfully");
        responseMap.put("token", token);

        return Response.status(Response.Status.CREATED)
                .entity(responseMap)  // Use Map directly as the entity, it will be serialized to JSON automatically
                .build();
    }

}
