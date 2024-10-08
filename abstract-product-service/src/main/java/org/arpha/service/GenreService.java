package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.request.CreateGenreRequest;
import org.arpha.dto.product.response.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    GenreResponse createGenre(CreateGenreRequest createGenreRequest);
    void deleteGenre(long id);
    Page<GenreResponse> getAllGenres(Predicate predicate, Pageable pageable);

}
