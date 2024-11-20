package org.arpha.mapper;

import org.arpha.dto.product.request.CreateGenreRequest;
import org.arpha.dto.product.response.GenreResponse;
import org.arpha.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GenreMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping( target = "name", source = "name")
    Genre toGenre(CreateGenreRequest createGenreRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Genre toGenre(String name);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GenreResponse toGenreDto(Genre genre);

}
