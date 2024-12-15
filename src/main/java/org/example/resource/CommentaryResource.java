package org.example.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Commentary;
import org.example.resource.dto.CommentaryMapper;
import org.example.resource.dto.CommentaryPost;
import org.example.resource.dto.CommentaryPut;

import java.net.URI;

@ResourceProperties(path = "commentaries")
public interface CommentaryResource extends PanacheEntityResource<Commentary, Long> {
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response add(CommentaryPost commentaryPost) {
        Commentary commentary = CommentaryMapper.INSTANCE.toModel(commentaryPost);
        commentary.persist();
        return Response.created(URI.create("/commentaries/" + commentary.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response update(@PathParam("id") Long id, CommentaryPut commentaryPut) {
        Commentary commentary = Commentary.findById(id);
        if (commentary == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        CommentaryMapper.INSTANCE.update(commentary, commentaryPut);
        commentary.persist();
        return Response.ok(commentary).build();
    }

    @Override
    @MethodProperties(exposed = false)
    Commentary add(Commentary entity);

    @Override
    @MethodProperties(exposed = false)
    Commentary update(Long id, Commentary entity);
}
