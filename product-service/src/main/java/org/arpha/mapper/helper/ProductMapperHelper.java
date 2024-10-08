package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.entity.Category;
import org.arpha.entity.Genre;
import org.arpha.mapper.CategoryMapper;
import org.arpha.mapper.GenreMapper;
import org.arpha.repository.CategoryRepository;
import org.arpha.repository.GenreRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapperHelper {

    private final CategoryMapper categoryMapper;
    private final GenreMapper genreMapper;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;

    @Named("toStringGenres")
    public Set<String> toStringGenres(Set<Genre> genres) {
        return genres.stream().map(Genre::getName).collect(Collectors.toSet());
    }

    @Named("toStringCategories")
    public Set<String> toStringCategories(Set<Category> categories) {
        return categories.stream().map(Category::getName).collect(Collectors.toSet());
    }

    @Named("toGenres")
    public Set<Genre> toGenres(Set<String> genres) {
        Set<Genre> genreSet = new HashSet<>();
        for (String genreName : genres) {
            if(genreRepository.existsByName(genreName)) {
                genreSet.add(genreRepository.findByName(genreName));
            } else {
                genreSet.add(genreRepository.save(genreMapper.toGenre(genreName)));
            }
        }

        return genreSet;
    }

    @Named("toCategories")
    public Set<Category> toCategories(Set<String> categories) {
        Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            if(categoryRepository.existsByName(categoryName)) {
                categorySet.add(categoryRepository.findByName(categoryName));
            } else {
                categorySet.add(categoryRepository.save(categoryMapper.toCategory(categoryName)));
            }
        }

        return categorySet;
    }

}
