package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    GenreDto createGenre(GenreDto genreDto);
    void deleteGenre(long id);
    Page<GenreDto> getAllGenres(Predicate predicate, Pageable pageable);

}
