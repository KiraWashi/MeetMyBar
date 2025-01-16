package org.meetmybar.meetmybarapi.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.meetmybar.meetmybarapi.business.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/photos")
@Validated
@Tag(name = "Photos", description = "API de gestion des photos")
@Component
public class PhotoController {
    private final PhotoBusiness photoBusiness;

    @Autowired
    public PhotoController(PhotoBusiness business) {

        this.photoBusiness = business;
    }
    @PostMapping
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "main_photo", required = false) Boolean mainPhoto) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(photoBusiness.savePhoto(file, description, mainPhoto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une photo par son ID")
    public ResponseEntity<Photo> getPhoto(@PathVariable int id) {
        return ResponseEntity.ok(photoBusiness.getPhotoById(id));
    }


    //@PatchMapping("/{id}")
    //@Operation(summary = "Mettre à jour une photo")
    //public ResponseEntity<Photo> updatePhoto(
    //       @PathVariable int id,
    //        @Valid @RequestBody Photo updateDto) {
        //   return ResponseEntity.ok(photoBusiness.updatePhoto(id, updateDto));
    // }

    //@DeleteMapping("/{id}")
    //@Operation(summary = "Supprimer une photo")
    //public ResponseEntity<Void> deletePhoto(@PathVariable int id) {
    //     photoBusiness.deletePhoto(id);
    //   return ResponseEntity.noContent().build();
    //}
}