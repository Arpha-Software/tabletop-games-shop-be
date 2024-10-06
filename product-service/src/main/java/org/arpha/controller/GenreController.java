package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateGenreRequest;
import org.arpha.dto.product.response.GenreResponse;
import org.arpha.entity.Genre;
import org.arpha.service.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
@SecurityRequirement(name = "Bearer Authentication")
public class GenreController {

    private final GenreService genreService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public GenreResponse create(@RequestBody CreateGenreRequest createGenreRequest) {
        return genreService.createGenre(createGenreRequest);
    }

    @GetMapping
    public Page<GenreResponse> getAllGenres(@QuerydslPredicate(root = Genre.class) Predicate predicate, Pageable pageable) {
        return genreService.getAllGenres(predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        genreService.deleteGenre(id);
    }

}
