package org.example.resource.dto;

import org.example.model.Commentary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface CommentaryMapper {
    CommentaryMapper INSTANCE = getMapper(CommentaryMapper.class);

    @Mapping(target = "id", ignore = true)
    Commentary toModel(CommentaryPost commentaryPost);

    @Mapping(target = "id", ignore = true)
    Commentary toModel(CommentaryPut commentaryPut);

    @Mapping(target = "id", ignore = true)
    Commentary update(@MappingTarget Commentary commentary, CommentaryPut commentaryPut);
}
