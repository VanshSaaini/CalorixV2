package com.calorix.backend.controller;

import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.dto.progressphoto.ProgressPhotoResponse;
import com.calorix.backend.service.ProgressPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class ProgressPhotoController {

    private final ProgressPhotoService progressPhotoService;

    /**
     * Upload Progress Photo
     */
    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProgressPhotoResponse> savePhoto(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("recordDate") LocalDate recordDate) {

        ProgressPhotoResponse response =
                progressPhotoService.savePhoto(
                        userId,
                        file,
                        description,
                        recordDate
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Progress Photo
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProgressPhotoResponse> updatePhoto(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("recordDate") LocalDate recordDate) {

        return ResponseEntity.ok(
                progressPhotoService.updatePhoto(
                        id,
                        file,
                        description,
                        recordDate
                ));
    }

    /**
     * Get Progress Photo
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgressPhotoResponse> getPhoto(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                progressPhotoService.getPhoto(id));
    }

    /**
     * Get User Progress Photos
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressPhotoResponse>> getUserPhotos(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                progressPhotoService.getUserPhotos(userId));
    }

    /**
     * Get Latest Progress Photo
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<ProgressPhotoResponse> getLatestPhoto(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                progressPhotoService.getLatestPhoto(userId));
    }

    /**
     * Delete Progress Photo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePhoto(
            @PathVariable Long id) {

        progressPhotoService.deletePhoto(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Progress photo deleted successfully.",
                        null
                ));
    }
}