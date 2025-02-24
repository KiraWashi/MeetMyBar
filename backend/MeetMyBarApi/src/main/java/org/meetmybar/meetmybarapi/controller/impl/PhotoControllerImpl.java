package org.meetmybar.meetmybarapi.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;
import org.meetmybar.meetmybarapi.business.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.controller.api.PhotoController;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/photos")
@Validated
@Tag(name = "Photos", description = "API de gestion des photos")
@Component
public class PhotoControllerImpl implements PhotoController {
    private final PhotoBusiness photoBusiness;

    @Autowired
    public PhotoControllerImpl(PhotoBusiness business) {
        this.photoBusiness = business;
    }

    @Override
    public ResponseEntity<Photo> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "main_photo", required = false) Boolean mainPhoto) throws IOException {
        log.info("File : {} description {} main_photo {}", 
            file.getOriginalFilename(), 
            description, 
            mainPhoto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(photoBusiness.savePhoto(file, description, mainPhoto));
    }

    @Override
    public ResponseEntity<Photo> getPhoto(
            @PathVariable int id) {
        return ResponseEntity.ok(photoBusiness.getPhotoById(id));
    }


    @Override
    public ResponseEntity<List<Photo>> getPhotosByBar(@PathVariable int id) {
        return ResponseEntity.ok(photoBusiness.getPhotoByIdBar(id));
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadPhoto(
            @PathVariable int id) {
        return photoBusiness.downloadPhotoById(id);
    }

    @Override
    public ResponseEntity<Photo> updatePhoto(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "main_photo", required = false) Boolean mainPhoto) throws IOException {
        
        Photo photoToUpdate = new Photo();
        photoToUpdate.setId(id);
        photoToUpdate.setDescription(description);
        photoToUpdate.setMainPhoto(mainPhoto != null ? mainPhoto : false);
        
        if (file != null && !file.isEmpty()) {
            photoToUpdate.setImageData(file.getBytes());
        }
        
        Photo updatedPhoto = photoBusiness.updatePhoto(photoToUpdate);
        
        return ResponseEntity.ok(updatedPhoto);
    }

    @Override
    public ResponseEntity<Photo> deletePhoto(
            @PathVariable int id) {
        photoBusiness.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<ResponseEntity<ByteArrayResource>> downloadPhotosByBar(@PathVariable int id) {
        return photoBusiness.downloadPhotosByBar(id);
    }

    @Override
    public ResponseEntity<Photo> addPhotoBar(
            @RequestParam("idBar") int idBar,
            @RequestParam("idPhoto") int idPhoto) {
        log.info("Associating photo {} to bar {}", idPhoto, idBar);
        photoBusiness.addPhotoBar(idBar, idPhoto);
        return ResponseEntity.ok().build();
    }
}