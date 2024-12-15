package org.example.resource.dto;

import org.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User toModel(UserPost userPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    User toModel(UserPut userPut);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "stringToByteArray")
    User update(@MappingTarget User user, UserPut userPut);

    @Named("stringToByteArray")
    default byte[] stringToByteArray(String image) {
        return image != null ? Base64.getDecoder().decode(image) : null;
    }
}
