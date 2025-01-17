package org.meetmybar.meetmybarapi.business.impl;

import org.meetmybar.meetmybarapi.exception.*;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;

import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service

public class PhotoBusiness {

    private final PhotoRepository photoRepository;


    public PhotoBusiness(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;

    }

    public String savePhoto(MultipartFile imageFile, String description, boolean mainData) {
        try {
            // Compression de l'image
            byte[] compressedImage = ImageUtils.compressImage(imageFile.getBytes());
            Photo photo = new Photo();
            photo.setMainPhoto(mainData);
            photo.setImageData(compressedImage);
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


    public Photo getPhotoById(int id) {
        if(photoRepository.findById(id)==null){
            throw new PhotoNotFoundException(id);
        }
        return photoRepository.findById(id);
    }

    public ResponseEntity<ByteArrayResource> downloadPhotoById(int id) {
        if(photoRepository.findById(id)==null){
            throw new PhotoNotFoundException(id);
        }
        return photoRepository.downloadById(id);
    }


}
