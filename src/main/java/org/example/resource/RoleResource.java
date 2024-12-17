package org.example.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Role;
import org.example.model.Thread;
import org.example.resource.dto.RoleMapper;
import org.example.resource.dto.RolePost;
import org.example.resource.dto.RolePut;

import java.net.URI;

@ResourceProperties(path = "roles")
public interface RoleResource extends PanacheEntityResource<Role, Long> {
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response add(RolePost rolePost) {
        Role role = RoleMapper.INSTANCE.toModel(rolePost);
        role.persist();
        return Response.created(URI.create("/roles/" + role.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response update(@PathParam("id") Long id, RolePut rolePut) {
        Role role = Role.findById(id);
        if (role == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        RoleMapper.INSTANCE.update(role, rolePut);
        role.persist();
        return Response.ok(role).build();
    }

    @Override
    @MethodProperties(exposed = false)
    Role add(Role entity);

    @Override
    @MethodProperties(exposed = false)
    Role update(Long id, Role entity);
}
