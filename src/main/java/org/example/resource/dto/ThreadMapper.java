package org.example.resource.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper
public interface ThreadMapper {
    ThreadMapper INSTANCE = Mappers.getMapper(ThreadMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    org.example.model.Thread toModel(ThreadPost threadPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    org.example.model.Thread toModel(ThreadPut threadPut);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    org.example.model.Thread update(@MappingTarget org.example.model.Thread thread, ThreadPut threadPut);

    @Named("stringToByteArray")
    default byte[] stringToByteArray(String image) {
        return image != null ? Base64.getDecoder().decode(image) : null;
    }
}
