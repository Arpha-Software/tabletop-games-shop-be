package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.GenreDto;
import org.arpha.exception.CreateGenreException;
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
    public GenreDto createGenre(GenreDto genreDto) {
        return Boxed
                .of(genreDto)
                .mapToBoxed(genreMapper::toGenre)
                .mapToBoxed(genreRepository::save)
                .mapToBoxed(genreMapper::toGenreDto)
                .orElseThrow(() -> new CreateGenreException("Error happened during genre creating"));
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
    public Page<GenreDto> getAllGenres(Predicate predicate, Pageable pageable) {
        return genreRepository.findAll(predicate, pageable).map(genreMapper::toGenreDto);
    }

}
