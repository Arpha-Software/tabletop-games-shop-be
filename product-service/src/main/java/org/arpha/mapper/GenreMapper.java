package org.arpha.mapper;

import org.arpha.dto.product.GenreDto;
import org.arpha.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping( target = "name", source = "name")
    Genre toGenre(GenreDto genreDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Genre toGenre(String name);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GenreDto toGenreDto(Genre genre);

}
