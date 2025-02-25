package org.meetmybar.meetmybarapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.controller.impl.PhotoControllerImpl;
import org.meetmybar.meetmybarapi.exception.PhotoNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoControllerImplTest {

    @Mock
    private PhotoBusiness photoBusiness;

    @InjectMocks
    private PhotoControllerImpl photoController;

    private Photo mockPhoto;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockPhoto = new Photo();
        mockPhoto.setId(1);
        mockPhoto.setDescription("Test photo");
        mockPhoto.setMainPhoto(false);

        mockFile = new MockMultipartFile(
            "image",
            "test.jpg",
            "image/jpeg",
            "test image content".getBytes()
        );
    }

    /**
     * Test l'upload d'une image avec tous les paramètres
     */
    @Test
    void uploadImage_WithAllParameters_ShouldReturnPhoto() throws IOException {
        when(photoBusiness.savePhoto(any(), anyString(), anyBoolean()))
            .thenReturn(mockPhoto);

        ResponseEntity<Photo> response = photoController.uploadImage(
            mockFile,
            "Test description",
            true
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(photoBusiness).savePhoto(mockFile, "Test description", true);
    }

    /**
     * Test la récupération d'une photo par son ID
     */
    @Test
    void getPhoto_WithValidId_ShouldReturnPhoto() {
        when(photoBusiness.getPhotoById(1)).thenReturn(mockPhoto);

        ResponseEntity<Photo> response = photoController.getPhoto(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPhoto, response.getBody());
    }

    /**
     * Test la récupération des photos d'un bar
     */
    @Test
    void getPhotosByBar_WithValidBarId_ShouldReturnPhotosList() {
        List<Photo> photos = Arrays.asList(mockPhoto);
        when(photoBusiness.getPhotoByIdBar(1)).thenReturn(photos);

        ResponseEntity<List<Photo>> response = photoController.getPhotosByBar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(photos, response.getBody());
    }

    /**
     * Test le téléchargement d'une photo
     */
    @Test
    void downloadPhoto_WithValidId_ShouldReturnByteArrayResource() {
        ByteArrayResource resource = new ByteArrayResource("test".getBytes());
        ResponseEntity<ByteArrayResource> expectedResponse = ResponseEntity.ok(resource);
        
        when(photoBusiness.downloadPhotoById(1)).thenReturn(expectedResponse);

        ResponseEntity<ByteArrayResource> response = photoController.downloadPhoto(1);

        assertEquals(expectedResponse, response);
    }

    /**
     * Test la mise à jour d'une photo avec tous les paramètres
     */
    @Test
    void updatePhoto_WithAllParameters_ShouldReturnUpdatedPhoto() throws IOException {
        when(photoBusiness.updatePhoto(any())).thenReturn(mockPhoto);

        ResponseEntity<Photo> response = photoController.updatePhoto(
            1,
            mockFile,
            "Updated description",
            true
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test la suppression d'une photo
     */
    @Test
    void deletePhoto_WithValidId_ShouldReturnNoContent() {
        when(photoBusiness.deletePhoto(1)).thenReturn(mockPhoto);

        ResponseEntity<Photo> response = photoController.deletePhoto(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Test le téléchargement des photos d'un bar
     */
    @Test
    void downloadPhotosByBar_WithValidBarId_ShouldReturnPhotosList() {
        ByteArrayResource resource = new ByteArrayResource("test".getBytes());
        List<ResponseEntity<ByteArrayResource>> expectedResponses = 
            Arrays.asList(ResponseEntity.ok(resource));
        
        when(photoBusiness.downloadPhotosByBar(1)).thenReturn(expectedResponses);

        List<ResponseEntity<ByteArrayResource>> responses = photoController.downloadPhotosByBar(1);

        assertEquals(expectedResponses, responses);
    }

    /**
     * Test l'association d'une photo à un bar
     */
    @Test
    void addPhotoBar_WithValidIds_ShouldReturnOk() {
        when(photoBusiness.addPhotoBar(1, 1)).thenReturn(mockPhoto);

        ResponseEntity<Photo> response = photoController.addPhotoBar(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPhoto, response.getBody());
    }

    /**
     * Test la suppression du lien entre une photo et un bar - cas succès
     */
    @Test
    void deletePhotoBarLink_WithValidIds_ShouldReturnOk() {
        when(photoBusiness.deletePhotoBarLink(1, 1)).thenReturn(mockPhoto);

        ResponseEntity<Photo> response = photoController.deletePhotoBarLink(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPhoto, response.getBody());
    }

    /**
     * Test la suppression du lien entre une photo et un bar - cas photo non trouvée
     */
    @Test
    void deletePhotoBarLink_WithPhotoNotFound_ShouldReturnNotFound() {
        when(photoBusiness.deletePhotoBarLink(1, 1))
            .thenThrow(new PhotoNotFoundException("Photo not found"));

        ResponseEntity<Photo> response = photoController.deletePhotoBarLink(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test la suppression du lien entre une photo et un bar - cas erreur serveur
     */
    @Test
    void deletePhotoBarLink_WithServerError_ShouldReturnInternalServerError() {
        when(photoBusiness.deletePhotoBarLink(1, 1))
            .thenThrow(new RuntimeException("Server error"));

        ResponseEntity<Photo> response = photoController.deletePhotoBarLink(1, 1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}