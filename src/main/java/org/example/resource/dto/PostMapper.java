package org.example.resource.dto;

import org.example.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    Post toModel(PostPost postPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    Post toModel(PostPut postPut);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    Post update(@MappingTarget Post post, PostPut postPut);

    @Named("stringToByteArray")
    default byte[] stringToByteArray(String image) {
        return image != null ? Base64.getDecoder().decode(image) : null;
    }
}
