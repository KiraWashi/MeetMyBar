package org.meetmybar.meetmybarapi.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.meetmybar.meetmybarapi.business.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.controller.api.PhotoController;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        return ResponseEntity.status(HttpStatus.OK).body(photoBusiness.savePhoto(file, description, mainPhoto));
    }

    @Override
    public ResponseEntity<Photo> getPhoto(
            @PathVariable int id) {
        return ResponseEntity.ok(photoBusiness.getPhotoById(id));
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadPhoto(
            @PathVariable int id) {
        return photoBusiness.downloadPhotoById(id);
    }

    @Override
    public ResponseEntity<Photo> updatePhoto(
            @Valid @RequestBody Photo updateDto) {
        System.out.println("Received update request for photo: " + updateDto);
        return ResponseEntity.ok(photoBusiness.updatePhoto(updateDto));
    }

    @Override
    public ResponseEntity<Photo> deletePhoto(
            @PathVariable int id) {
        photoBusiness.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }
}