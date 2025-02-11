package org.meetmybar.meetmybarapi.business.impl;

import jakarta.validation.Valid;
import org.meetmybar.meetmybarapi.exception.*;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;

import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service

public class PhotoBusiness {

    private final PhotoRepository photoRepository;


    public PhotoBusiness(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;

    }

    public String savePhoto(MultipartFile imageFile, String description, boolean mainData) {
        try {
            Photo photo = new Photo();
            photo.setMainPhoto(mainData);
            photo.setImageData(imageFile.getBytes());
            if (description != null && !description.isEmpty()) {
                photo.setDescription(description);
            }

            // Sauvegarde dans le dépôt
            photoRepository.save(photo);
            return "Photo successfully inserted!";
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving photo: " + e.getMessage(), e);
        }
    }


    public Optional<Photo> getPhotoById(int id) {
        return photoRepository.findById(id);
    }

    public ResponseEntity<ByteArrayResource> downloadPhotoById(int id) {
        if(photoRepository.findById(id).isEmpty()){
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
        return photoRepository.downloadById(id);
    }


    public Photo updatePhoto(int id, @Valid Photo updateDto) {
        if(photoRepository.findById(id).isEmpty()){
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
        return photoRepository.updatePhoto(updateDto);
    }

    public Optional<Photo> deletePhoto(int id) {
        if(photoRepository.findById(id).isEmpty()){
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
        return photoRepository.deletePhoto(id);
    }
}
