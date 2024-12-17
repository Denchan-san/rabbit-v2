package org.example.resource.dto;

import org.example.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id", ignore = true)
    Role toModel(RolePost rolePost);

    @Mapping(target = "id", ignore = true)
    Role toModel(RolePut rolePut);

    @Mapping(target = "id", ignore = true)
    Role update(@MappingTarget Role role, RolePut rolePut);
}
