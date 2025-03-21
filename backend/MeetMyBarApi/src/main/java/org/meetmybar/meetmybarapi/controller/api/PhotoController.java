package org.meetmybar.meetmybarapi.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-17T14:26:25.502834400+01:00[Europe/Paris]")
@Validated
@RestController
@RequestMapping("/photos")
@Tag(name = "Photos", description = "API de gestion des photos")
public interface PhotoController {

    /**
     * POST /photos : Uploader une photo
     *
     * @param file        Fichier image à uploader (required)
     * @param description Description de la photo (optional)
     * @param mainPhoto   Indique si la photo est la photo principale (optional)
     * @return Photo uploadée avec succès (status code 200)
     */
    @Operation(
            operationId = "uploadImage",
            summary = "Uploader une photo",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo uploadée avec succès", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Photo.class))
                    })
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    default ResponseEntity<Photo> uploadImage(
            @Parameter(name = "image", description = "Fichier image à uploader", required = true) @RequestParam("image") MultipartFile file,
            @Parameter(name = "description", description = "Description de la photo") @RequestParam(value = "description", required = false) String description,
            @Parameter(name = "main_photo", description = "Indique si la photo est la photo principale") @RequestParam(value = "main_photo", required = false) Boolean mainPhoto
    ) throws IOException {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /photos/{id} : Récupérer une photo par son ID
     *
     * @param id ID de la photo (required)
     * @return Photo récupérée avec succès (status code 200)
     */
    @Operation(
            operationId = "getPhoto",
            summary = "Récupérer une photo par son ID",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo récupérée avec succès", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Photo.class))
                    })
            }
    )
    @GetMapping("/{id}")
    default ResponseEntity<Photo> getPhoto(
            @Parameter(name = "id", description = "ID de la photo", required = true, in = ParameterIn.PATH) @PathVariable("id") int id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /photos/bar/{id} : Récupérer les photos d'un bar par son id
     *
     * @param id ID du bar (required)
     * @return Liste des photos récupérées avec succès (status code 200)
     */
    @Operation(
            operationId = "getPhotosByBar",
            summary = "Récupérer les photos d'un bar par son ID",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photos récupérées avec succès", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Photo.class, type = "array"))
                    })
            }
    )
    @GetMapping("/bar/{id}")
    default ResponseEntity<List<Photo>> getPhotosByBar(
            @Parameter(name = "id", description = "ID du bar", required = true, in = ParameterIn.PATH) @PathVariable("id") int id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /photos/download/{id} : Télécharger une photo par son ID
     *
     * @param id ID de la photo (required)
     * @return Photo téléchargée avec succès (status code 200)
     */
    @Operation(
            operationId = "downloadPhoto",
            summary = "Télécharger une photo par son ID",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo téléchargée avec succès", content = {
                            @Content(mediaType = "application/octet-stream", schema = @Schema(implementation = ByteArrayResource.class))
                    })
            }
    )
    @GetMapping("/download/{id}")
    default ResponseEntity<ByteArrayResource> downloadPhoto(
            @Parameter(name = "id", description = "ID de la photo", required = true, in = ParameterIn.PATH) @PathVariable("id") int id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /photos/download/bar/{id} : Télécharger toutes les photos d'un bar par son ID
     *
     * @param id ID du bar required)
     * @return une liste de photos avec succès (status code 200)
     */
    @Operation(
            operationId = "downloadPhotosByBar",
            summary = "Télécharger une liste de photos par l'ID du bar",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photos téléchargées avec succès", content = {
                            @Content(mediaType = "image/jpeg", schema = @Schema(
                                type = "array",
                                description = "Liste des photos décompressées"
                            ))
                    }),
                    @ApiResponse(responseCode = "204", description = "Aucune photo trouvée pour ce bar"),
                    @ApiResponse(responseCode = "500", description = "Erreur lors du téléchargement des photos")
            }
    )
    @GetMapping("/download/bar/{id}")
    default List<ResponseEntity<ByteArrayResource>> downloadPhotosByBar(
            @Parameter(name = "id", description = "ID du bar", required = true, in = ParameterIn.PATH) @PathVariable("id") int id
    ) {
        return Collections.emptyList();
    }

    /**
     * PATCH /photos : Mettre à jour une photo
     *
     * @param updateDto DTO contenant les informations de mise à jour de la photo (required)
     * @return Photo mise à jour avec succès (status code 200)
     */
    @Operation(
            operationId = "updatePhoto",
            summary = "Mettre à jour une photo",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo mise à jour avec succès", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Photo.class))
                    })
            }
    )
    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    default ResponseEntity<Photo> updatePhoto(
            @Parameter(name = "id", description = "ID de la photo", required = true)
            @RequestParam(value = "id") int id,
            @Parameter(name = "image", description = "Fichier image à uploader")
            @RequestParam(value = "image", required = false) MultipartFile file,
            @Parameter(name = "description", description = "Description de la photo")
            @RequestParam(value = "description", required = false) String description,
            @Parameter(name = "main_photo", description = "Indique si la photo est la photo principale")
            @RequestParam(value = "main_photo", required = false) Boolean mainPhoto
    ) throws IOException {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * DELETE /photos/{id} : Supprimer une photo
     *
     * @param id ID de la photo (required)
     * @return Photo supprimée avec succès (status code 200)
     */
    @Operation(
            operationId = "deletePhoto",
            summary = "Supprimer une photo",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo supprimée avec succès", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Photo.class))
                    })
            }
    )
    @DeleteMapping("/{id}")
    default ResponseEntity<Photo> deletePhoto(
            @Parameter(name = "id", description = "ID de la photo", required = true, in = ParameterIn.PATH) @PathVariable("id") int id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * POST /photos/bar : Associer une photo à un bar
     *
     * @param idBar ID du bar (required)
     * @param idPhoto ID de la photo (required)
     * @return Association créée avec succès (status code 200)
     */
    @Operation(
            operationId = "addPhotoBar",
            summary = "Associer une photo à un bar",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Association créée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Bar ou photo non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    @PostMapping("/bar")
    default ResponseEntity<Photo> addPhotoBar(
            @Parameter(name = "idBar", description = "ID du bar", required = true) @RequestParam("idBar") int idBar,
            @Parameter(name = "idPhoto", description = "ID de la photo", required = true) @RequestParam("idPhoto") int idPhoto
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

     /**
     * DELETE /photos/bar : Supprime une photo d'un bar
     *
     * @param barId Identifiant du bar (required)
     * @param photoId Identifiant de la photo (required)
     * @return Suppression réussie (status code 204)
     */
    @Operation(
            operationId = "deletePhotoBarLink",
            summary = "Supprime une photo d'un bar",
            tags = { "Photos" },
            responses = {
                    @ApiResponse(responseCode = "204", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Association non trouvée"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    @DeleteMapping("/bar")
    default ResponseEntity<Photo> deletePhotoBarLink(
        @Parameter(name = "idBar", description = "ID du bar", required = true) @RequestParam("idBar") int idBar,
        @Parameter(name = "idPhoto", description = "ID de la photo", required = true) @RequestParam("idPhoto") int idPhoto
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}