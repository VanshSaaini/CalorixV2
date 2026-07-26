package com.calorix.backend.service;
import com.calorix.backend.dto.progressphoto.ProgressPhotoResponse;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProgressPhotoService {

    ProgressPhotoResponse savePhoto(
            Long userId,
            MultipartFile file,
            String description,
            LocalDate recordDate);

    ProgressPhotoResponse updatePhoto(
            Long id,
            MultipartFile file,
            String description,
            LocalDate recordDate);

    ProgressPhotoResponse getPhoto(Long id);

    List<ProgressPhotoResponse> getUserPhotos(Long userId);

    void deletePhoto(Long id);

    ProgressPhotoResponse getLatestPhoto(Long userId);

}