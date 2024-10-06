package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateGenreRequest;
import org.arpha.dto.product.response.GenreResponse;
import org.arpha.exception.CreateEntityException;
import org.arpha.exception.DeleteEntityException;
import org.arpha.mapper.GenreMapper;
import org.arpha.repository.GenreRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final ProductService productService;

    @Override
    public GenreResponse createGenre(CreateGenreRequest createGenreRequest) {
        return Boxed
                .of(createGenreRequest)
                .filter(genreDto1 -> !genreRepository.existsByName(createGenreRequest.getName()))
                .mapToBoxed(genreMapper::toGenre)
                .mapToBoxed(genreRepository::save)
                .mapToBoxed(genreMapper::toGenreDto)
                .orElseThrow(() -> new CreateEntityException("Unable to create genre, because it already exists!"));
    }

    @Override
    public void deleteGenre(long id) {
        Boxed
                .of(id)
                .filter(productService::containGenreAnyProduct)
                .ifPresentOrElseThrow(genreRepository::deleteById,
                        () -> new DeleteEntityException("Genre wasn't deleted because some product has it"));
    }

    @Override
    public Page<GenreResponse> getAllGenres(Predicate predicate, Pageable pageable) {
        return genreRepository.findAll(predicate, pageable).map(genreMapper::toGenreDto);
    }

}
