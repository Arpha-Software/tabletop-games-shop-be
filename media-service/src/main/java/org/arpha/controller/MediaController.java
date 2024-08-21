package org.arpha.controller;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.service.MediaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public FileResponse uploadMedia(@RequestPart("file") MultipartFile file, @ModelAttribute FileUploadRequest fileUploadRequest) {
        return mediaService.upload(file, fileUploadRequest);
    }

    @GetMapping
    public Page<FileResponse> getFiles(@PageableDefault Pageable pageable) {
        return mediaService.getAll(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        mediaService.delete(id);
    }

    @GetMapping("/{id}")
    public FileResponse getFile(@PathVariable long id) {
        return mediaService.findById(id);
    }

}
