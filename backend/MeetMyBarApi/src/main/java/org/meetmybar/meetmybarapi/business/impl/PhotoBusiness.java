package org.meetmybar.meetmybarapi.business.impl;

import org.meetmybar.meetmybarapi.exception.*;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;

import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service

public class PhotoBusiness {

    private final PhotoRepository photoRepository;


    public PhotoBusiness(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;

    }

    public String savePhoto(MultipartFile imageFile, Photo photo) {
        try {
            // Compression de l'image
            byte[] compressedImage = ImageUtils.compressImage(imageFile.getBytes());
            photo.setImageData(compressedImage);

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
}
