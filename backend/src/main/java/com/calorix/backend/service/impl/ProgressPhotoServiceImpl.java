package com.calorix.backend.service.impl;

import com.calorix.backend.service.CloudinaryService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import com.calorix.backend.dto.progressphoto.ProgressPhotoRequest;
import com.calorix.backend.dto.progressphoto.ProgressPhotoResponse;
import com.calorix.backend.entity.ProgressPhoto;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.ProgressPhotoMapper;
import com.calorix.backend.repository.ProgressPhotoRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.ProgressPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressPhotoServiceImpl implements ProgressPhotoService {

    private final ProgressPhotoRepository progressPhotoRepository;
    private final UserRepository userRepository;
    private final ProgressPhotoMapper progressPhotoMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public ProgressPhotoResponse savePhoto(
            Long userId,
            MultipartFile file,
            String description,
            LocalDate recordDate) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        String imageUrl = cloudinaryService.uploadImage(file);

        ProgressPhoto progressPhoto = new ProgressPhoto();
        progressPhoto.setUser(user);
        progressPhoto.setImageUrl(imageUrl);
        progressPhoto.setDescription(description);
        progressPhoto.setRecordDate(recordDate);

        ProgressPhoto savedPhoto = progressPhotoRepository.save(progressPhoto);

        return progressPhotoMapper.toResponse(savedPhoto);
    }

    @Override
    public ProgressPhotoResponse updatePhoto(
            Long id,
            MultipartFile file,
            String description,
            LocalDate recordDate) {

        ProgressPhoto progressPhoto = progressPhotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress photo not found with id: " + id));

        if (file != null && !file.isEmpty()) {

            String imageUrl = cloudinaryService.uploadImage(file);

            progressPhoto.setImageUrl(imageUrl);
        }

        progressPhoto.setDescription(description);
        progressPhoto.setRecordDate(recordDate);

        ProgressPhoto updatedPhoto = progressPhotoRepository.save(progressPhoto);

        return progressPhotoMapper.toResponse(updatedPhoto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressPhotoResponse getPhoto(Long id) {

        ProgressPhoto progressPhoto = progressPhotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress photo not found with id: " + id));

        return progressPhotoMapper.toResponse(progressPhoto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressPhotoResponse> getUserPhotos(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return progressPhotoRepository.findByUserIdOrderByRecordDateDesc(userId)
                .stream()
                .map(progressPhotoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressPhotoResponse getLatestPhoto(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return progressPhotoRepository
                .findTopByUserIdOrderByRecordDateDesc(userId)
                .map(progressPhotoMapper::toResponse)
                .orElse(null);
    }

    @Override
    public void deletePhoto(Long id) {

        ProgressPhoto progressPhoto = progressPhotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress photo not found with id: " + id));

        progressPhotoRepository.delete(progressPhoto);
    }
}