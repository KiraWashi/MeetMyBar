package org.meetmybar.meetmybarapi.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.meetmybar.meetmybarapi.business.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.exception.PhotoNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.models.modif.Bar;
import org.meetmybar.meetmybarapi.repository.BarRepository;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PhotoBusinessTest {

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private BarRepository barRepository;  // Ajout du mock pour BarRepository

    @InjectMocks
    private PhotoBusiness photoBusiness;

    private MultipartFile mockImageFile;
    private Photo mockPhoto;
    private Bar mockBar;  // Ajout d'un mock Bar

    @BeforeEach
    void setUp() {
        mockImageFile = mock(MultipartFile.class);
        mockPhoto = new Photo();
        mockPhoto.setId(1);
        mockPhoto.setDescription("Test Photo");
        mockPhoto.setImageData(new byte[]{1, 2, 3});
        mockPhoto.setMainPhoto(true);

        // Initialisation du mock Bar
        mockBar = new Bar();
        mockBar.setId(1);
        mockBar.setName("Test Bar");
    }

    @Test
    void savePhoto_Success() throws IOException {
        when(mockImageFile.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(photoRepository.save(any(Photo.class))).thenReturn(mockPhoto);

        Photo result = photoBusiness.savePhoto(mockImageFile, "Test Photo", true);

        assertNotNull(result);
        assertEquals(mockPhoto.isMainPhoto(), result.isMainPhoto());
        //assertEquals(mockPhoto.getImageData(), result.getImageData());
        assertEquals(mockPhoto.getDescription(), result.getDescription());
        verify(photoRepository, times(1)).save(any(Photo.class));
    }

    @Test
    void savePhoto_IOException() throws IOException {
        when(mockImageFile.getBytes()).thenThrow(new IOException("Failed to read file"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            photoBusiness.savePhoto(mockImageFile, "Test Photo", true);
        });

        assertEquals("Error saving photo: Failed to read file", exception.getMessage());
        verify(photoRepository, never()).save(any(Photo.class));
    }

    @Test
    void getPhotoById_Success() {
        when(photoRepository.findById(1)).thenReturn(mockPhoto);

        Photo result = photoBusiness.getPhotoById(1);

        assertNotNull(result);
        assertEquals(mockPhoto, result);
        verify(photoRepository, times(1)).findById(1);
    }

    @Test
    void getPhotoById_NotFound() {
        when(photoRepository.findById(1)).thenReturn(null);

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.getPhotoById(1);
        });

        verify(photoRepository, times(1)).findById(1);
    }

    @Test
    void downloadPhotoById_Success() {
        ByteArrayResource resource = new ByteArrayResource(new byte[]{1, 2, 3});
        when(photoRepository.findById(1)).thenReturn(mockPhoto);
        when(photoRepository.downloadById(1)).thenReturn(ResponseEntity.ok(resource));

        ResponseEntity<ByteArrayResource> response = photoBusiness.downloadPhotoById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(photoRepository, times(1)).findById(1);
        verify(photoRepository, times(1)).downloadById(1);
    }

    @Test
    void downloadPhotoById_NotFound() {
        when(photoRepository.findById(1)).thenReturn(null);

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.downloadPhotoById(1);
        });

        verify(photoRepository, times(1)).findById(1);
        verify(photoRepository, never()).downloadById(1);
    }

    @Test
    void updatePhoto_Success() {
        when(photoRepository.findById(1)).thenReturn(mockPhoto);
        when(photoRepository.updatePhoto(any(Photo.class))).thenReturn(mockPhoto);

        Photo updatedPhoto = new Photo();
        updatedPhoto.setId(1);
        updatedPhoto.setDescription("Updated Photo");

        Photo result = photoBusiness.updatePhoto(updatedPhoto);

        assertNotNull(result);
        assertEquals("Updated Photo", result.getDescription());
        verify(photoRepository, times(1)).findById(1);
        verify(photoRepository, times(1)).updatePhoto(updatedPhoto);
    }

    @Test
    void updatePhoto_NotFound() {
        when(photoRepository.findById(1)).thenReturn(null);

        Photo updatedPhoto = new Photo();
        updatedPhoto.setId(1);

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.updatePhoto(updatedPhoto);
        });

        verify(photoRepository, times(1)).findById(1);
        verify(photoRepository, never()).updatePhoto(any(Photo.class));
    }

    @Test
    void deletePhoto_Success() {
        when(photoRepository.findById(1)).thenReturn(mockPhoto);
        when(photoRepository.deletePhoto(1)).thenReturn(mockPhoto);

        Photo result = photoBusiness.deletePhoto(1);

        assertNotNull(result);
        assertEquals(mockPhoto, result);
        verify(photoRepository, times(1)).findById(1);
        verify(photoRepository, times(1)).deletePhoto(1);
    }

    @Test
    void deletePhoto_NotFound() {
        when(photoRepository.findById(1)).thenReturn(null);

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.deletePhoto(1);
        });

        verify(photoRepository, times(1)).findById(1);
        verify(photoRepository, never()).deletePhoto(1);
    }

    @Test
    void getPhotosByBar_Success() {
        when(barRepository.getBarById(1)).thenReturn(mockBar);
        List<Photo> mockPhotos = Arrays.asList(
                new Photo(1, "Photo 1", new byte[]{1, 2, 3}, true),
                new Photo(2, "Photo 2", new byte[]{4, 5, 6}, false)
        );

        when(photoRepository.findPhotosByBar(1)).thenReturn(mockPhotos);

        List<Photo> result = photoBusiness.getPhotoByIdBar(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockPhotos, result);
        verify(barRepository, times(1)).getBarById(1);
        verify(photoRepository, times(1)).findPhotosByBar(1);
    }

    @Test
    void getPhotosByBar_BarNotFound() {
        when(barRepository.getBarById(1)).thenReturn(null);

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.getPhotoByIdBar(1);
        });

        verify(barRepository, times(1)).getBarById(1);
        verify(photoRepository, never()).findPhotosByBar(anyInt());
    }

    @Test
    void downloadPhotosByBar_Success() {
        when(barRepository.getBarById(1)).thenReturn(mockBar);
        
        Map<String, Object> photo1 = new HashMap<>();
        photo1.put("id", 1);
        photo1.put("description", "Photo 1");
        photo1.put("image_data", "base64data1");
        photo1.put("main_photo", true);

        Map<String, Object> photo2 = new HashMap<>();
        photo2.put("id", 2); 
        photo2.put("description", "Photo 2");
        photo2.put("image_data", "base64data2");
        photo2.put("main_photo", false);

        List<Map<String, Object>> mockPhotos = Arrays.asList(photo1, photo2);
        ResponseEntity<List<Map<String, Object>>> mockResponse = ResponseEntity.ok(mockPhotos);
        
        when(photoRepository.downloadPhotosByBar(1)).thenReturn(mockResponse);

        ResponseEntity<List<Map<String, Object>>> result = photoBusiness.downloadPhotosByBar(1);

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        verify(barRepository, times(1)).getBarById(1);
        verify(photoRepository, times(1)).downloadPhotosByBar(1);
    }

    @Test
    void downloadPhotosByBar_BarNotFound() {
        when(barRepository.getBarById(1)).thenReturn(null);

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.downloadPhotosByBar(1);
        });

        verify(barRepository, times(1)).getBarById(1);
        verify(photoRepository, never()).downloadPhotosByBar(anyInt());
    }
}
