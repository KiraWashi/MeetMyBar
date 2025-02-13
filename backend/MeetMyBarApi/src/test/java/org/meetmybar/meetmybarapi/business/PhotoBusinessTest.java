package org.meetmybar.meetmybarapi.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.impl.PhotoBusiness;
import org.meetmybar.meetmybarapi.exception.PhotoNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhotoBusinessTest {

    @Mock
    private PhotoRepository photoRepository;

    @InjectMocks
    private PhotoBusiness photoBusiness;

    private MultipartFile mockImageFile;
    private Photo mockPhoto;

    @BeforeEach
    void setUp() {
        mockImageFile = mock(MultipartFile.class);
        mockPhoto = new Photo();
        mockPhoto.setId(1);
        mockPhoto.setDescription("Test Photo");
        mockPhoto.setImageData(new byte[]{1, 2, 3});
        mockPhoto.setMainPhoto(true);
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
        assertEquals(200, response.getStatusCodeValue());
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
}
