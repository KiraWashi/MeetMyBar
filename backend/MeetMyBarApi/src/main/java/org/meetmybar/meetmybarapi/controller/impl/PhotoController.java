package org.meetmybar.meetmybarapi.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.meetmybar.meetmybarapi.buisness.BarBuissness;
import org.meetmybar.meetmybarapi.buisness.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    //@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@Operation(summary = "Uploader une nouvelle photo")
    //public ResponseEntity<Photo> uploadPhoto(
    //        @RequestParam("file") MultipartFile file,
    //       @Valid Photo createDto) {
    //    return ResponseEntity.ok(photoBusiness.savePhoto(file, createDto));
    // }

    //@GetMapping
    //@Operation(summary = "Récupérer toutes les photos")
    //public ResponseEntity<List<Photo>> getAllPhotos() {
    //    return ResponseEntity.ok(photoBusiness.getAllPhotos());
    //}

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une photo par son ID")
    public ResponseEntity<Photo> getPhoto(@PathVariable int id) {
        return ResponseEntity.ok(photoBusiness.getPhotoById(id));
    }

    @GetMapping("/{id}/file")
    @Operation(summary = "Récupérer le fichier d'une photo")
    public ResponseEntity<Resource> getPhotoFile(@PathVariable int id) {
        Resource resource = photoBusiness.getPhotoFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
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