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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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

        String result = photoBusiness.savePhoto(mockImageFile, "Test Photo", true);

        assertEquals("Photo successfully inserted!", result);
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
        when(photoRepository.findById(1)).thenReturn(Optional.of(mockPhoto));

        Photo result = photoBusiness.getPhotoById(1)
                .orElseThrow(() -> new PhotoNotFoundException("Photo not found with id: 1"));

        assertEquals(mockPhoto, result);
        verify(photoRepository, times(1)).findById(1);
    }

    @Test
    void getPhotoById_NotFound() {
        when(photoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.getPhotoById(1)
                    .orElseThrow(() -> new PhotoNotFoundException("Photo not found with id: 1"));
        });

        verify(photoRepository, times(1)).findById(1);
    }

    @Test
    void updatePhoto_Success() {
        when(photoRepository.findById(1)).thenReturn(Optional.of(mockPhoto));
        when(photoRepository.updatePhoto(any(Photo.class))).thenReturn(mockPhoto);

        Photo result = photoBusiness.updatePhoto(1, mockPhoto);

        assertNotNull(result);
        assertEquals(mockPhoto, result);
        verify(photoRepository, times(1)).updatePhoto(any(Photo.class));
    }

    @Test
    void updatePhoto_NotFound() {
        when(photoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.updatePhoto(1, mockPhoto);
        });

        verify(photoRepository, never()).updatePhoto(any(Photo.class));
    }

    @Test
    void deletePhoto_Success() {
        // First, mock that the photo exists
        when(photoRepository.findById(1)).thenReturn(Optional.of(mockPhoto));

        // Then mock the delete operation
        when(photoRepository.deletePhoto(1)).thenReturn(Optional.of(mockPhoto));

        Optional<Photo> result = photoBusiness.deletePhoto(1);

        assertNotNull(result);
        assertEquals(Optional.of(mockPhoto), result);

        // Verify both methods were called
        verify(photoRepository).findById(1);
        verify(photoRepository).deletePhoto(1);
    }

    @Test
    void deletePhoto_NotFound() {
        when(photoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class, () -> {
            photoBusiness.deletePhoto(1);
        });

        verify(photoRepository, never()).deletePhoto(1);
    }
}