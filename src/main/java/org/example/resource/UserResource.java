package org.example.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.User;
import org.example.resource.dto.UserMapper;
import org.example.resource.dto.UserPost;
import org.example.resource.dto.UserPut;
import org.example.util.PasswordUtils;

import java.net.URI;

@ResourceProperties(path = "users")
public interface UserResource extends PanacheEntityResource<User, Long> {

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response add(UserPost userPost) {
        User existingUser = User.find("username", userPost.getUsername()).firstResult();
        if (existingUser != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Username already taken\"}")
                    .build();
        }

        // Hash the password before persisting
        String hashedPassword = PasswordUtils.hashPassword(userPost.getPassword());

        User user = UserMapper.INSTANCE.toModel(userPost);
        user.setPassword(hashedPassword); // Set the hashed password
        user.persist();

        return Response.created(URI.create("/users/" + user.getId())).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response update(@PathParam("id") Long id, UserPut userPut) {
        User user = User.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Initialize roles explicitly to avoid LazyInitializationException
        user.getRoles().size();

        // Update password only if provided
        if (userPut.getPassword() != null && !userPut.getPassword().isEmpty()) {
            String hashedPassword = PasswordUtils.hashPassword(userPut.getPassword());
            userPut.setPassword(hashedPassword);
        }
        else {
            userPut.setPassword(user.getPassword());
        }


        // Map DTO to the entity
        UserMapper.INSTANCE.update(user, userPut);

        // Persist updated user
        user.persist();
        return Response.ok(user).build();
    }


    @GET
    @Path("findByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    default Response findByUsername(@PathParam("username") String username) {
        // Use User's static method to find by username
        User user = User.find("username", username).firstResult();
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @Override
    @MethodProperties(exposed = false)
    User add(User entity);

    @Override
    @MethodProperties(exposed = false)
    User update(Long id, User entity);
}
