package org.example.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Post;
import org.example.resource.dto.PostMapper;
import org.example.resource.dto.PostPost;
import org.example.resource.dto.PostPut;

import java.net.URI;

@ResourceProperties(path = "posts")
public interface PostResource extends PanacheEntityResource<Post, Long> {
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response add(PostPost postPost) {
        Post post = PostMapper.INSTANCE.toModel(postPost);
        post.persist();
        return Response.created(URI.create("/posts/" + post.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    default Response update(@PathParam("id") Long id, PostPut postPut) {
        Post post = Post.findById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        PostMapper.INSTANCE.update(post,postPut);
        post.persist();
        return Response.ok(post).build();
    }

    @Override
    @MethodProperties(exposed = false)
    Post add(Post entity);

    @Override
    @MethodProperties(exposed = false)
    Post update(Long id, Post entity);
}
