package org.example.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Thread;
import org.example.resource.dto.ThreadMapper;
import org.example.resource.dto.ThreadPost;
import org.example.resource.dto.ThreadPut;

import java.net.URI;

@ResourceProperties(path = "threads")
public interface ThreadResource extends PanacheEntityResource<Thread, Long> {
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response add(ThreadPost threadPost) {
        Thread thread = ThreadMapper.INSTANCE.toModel(threadPost);
        thread.persist();
        return Response.created(URI.create("/threads/" + thread.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response update(@PathParam("id") Long id, ThreadPut threadPut) {
        Thread thread = Thread.findById(id);
        if (thread == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ThreadMapper.INSTANCE.update(thread, threadPut);
        thread.persist();
        return Response.ok(thread).build();
    }


    @Override
    @MethodProperties(exposed = false)
    Thread add(Thread entity);

    @Override
    @MethodProperties(exposed = false)
    Thread update(Long id,Thread entity);
}
