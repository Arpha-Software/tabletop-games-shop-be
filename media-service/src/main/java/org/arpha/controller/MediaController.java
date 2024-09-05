package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.File;
import org.arpha.service.MediaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public FileResponse uploadMedia(@RequestBody @Valid FileUploadRequest fileUploadRequest) {
        return mediaService.upload(fileUploadRequest);
    }

    @GetMapping
    public Page<FileResponse> getFiles(@QuerydslPredicate(root = File.class) Predicate predicate, @PageableDefault Pageable pageable) {
        return mediaService.getAll(predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        mediaService.deleteFileById(id);
    }

    @GetMapping("/{id}")
    public FileResponse getFile(@PathVariable long id) {
        return mediaService.findFileById(id);
    }

}
