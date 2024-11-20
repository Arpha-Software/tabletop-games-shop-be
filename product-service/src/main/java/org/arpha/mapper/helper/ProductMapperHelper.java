package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.Category;
import org.arpha.entity.Genre;
import org.arpha.entity.Product;
import org.arpha.mapper.CategoryMapper;
import org.arpha.mapper.GenreMapper;
import org.arpha.repository.CategoryRepository;
import org.arpha.repository.GenreRepository;
import org.arpha.service.MediaService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapperHelper {

    private final CategoryMapper categoryMapper;
    private final GenreMapper genreMapper;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;
    private final MediaService mediaService;

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

    @Named("toSingleProductFileResponse")
    public FileResponse toSingleProductFileResponse(Product product) {
        List<FileResponse> fileResponse = mediaService.findFilesByTarget(product.getId(), TargetType.PRODUCT);
        return fileResponse.isEmpty() ? null : fileResponse.getFirst();
    }

    @Named("toProductFileResponses")
    public List<FileResponse> toProductFileResponses(Product product) {
        return mediaService.findFilesByTarget(product.getId(), TargetType.PRODUCT).stream()
                .toList();
    }

}
