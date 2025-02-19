package org.meetmybar.meetmybarapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.exception.PhotoNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate photoTemplate;

    @InjectMocks
    private PhotoRepository photoRepository;

    private Photo testPhoto;
    private byte[] testImageData;

    @BeforeEach
    void setUp() {
        testImageData = "test image data".getBytes();
        testPhoto = new Photo(1, "Test Photo", testImageData, true);
    }

    @Test
    void findById_ExistingPhoto_ReturnsPhoto() {
        // Arrange
        when(photoTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(testPhoto);

        // Act
        Photo result = photoRepository.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testPhoto.getId(), result.getId());
        assertEquals(testPhoto.getDescription(), result.getDescription());
        assertTrue(result.isMainPhoto());
        verify(photoTemplate).queryForObject(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void findById_NonExistingPhoto_ThrowsPhotoNotFoundException() {
        // Arrange
        when(photoTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(PhotoNotFoundException.class, () -> photoRepository.findById(999));
        verify(photoTemplate).queryForObject(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void save_ValidPhoto_ReturnsSavedPhoto() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        // Act
        Photo result = photoRepository.save(testPhoto);

        // Assert
        assertNotNull(result);
        assertEquals(testPhoto, result);
        verify(photoTemplate).update(anyString(), anyMap());
    }

    @Test
    void save_ErrorOccurs_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap()))
                .thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.save(testPhoto));
        assertTrue(exception.getMessage().contains("Error inserting photo"));
    }

    @Test
    void downloadById_ExistingPhoto_ReturnsResponseEntity() {
        // Arrange
        List<byte[]> mockResult = Arrays.asList(testImageData);
        when(photoTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(mockResult);

        // Act
        ResponseEntity<ByteArrayResource> result = photoRepository.downloadById(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        verify(photoTemplate).query(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void updatePhoto_ValidPhoto_ReturnsUpdatedPhoto() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        // Act
        Photo result = photoRepository.updatePhoto(testPhoto);

        // Assert
        assertNotNull(result);
        assertEquals(testPhoto.getId(), result.getId());
        assertEquals(testPhoto.getDescription(), result.getDescription());
        verify(photoTemplate).update(anyString(), anyMap());
    }

    @Test
    void updatePhoto_NonExistingPhoto_ThrowsPhotoNotFoundException() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(0);

        // Act & Assert
        assertThrows(PhotoNotFoundException.class, () -> photoRepository.updatePhoto(testPhoto));
    }

    @Test
    void updatePhoto_NullPhoto_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> photoRepository.updatePhoto(null));
    }

    @Test
    void deletePhoto_ExistingPhoto_ReturnsDeletedPhoto() {
        // Arrange
        when(photoTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(testPhoto);
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        // Act
        Photo result = photoRepository.deletePhoto(1);

        // Assert
        assertNotNull(result);
        assertEquals(testPhoto.getId(), result.getId());
        verify(photoTemplate).update(anyString(), anyMap());
    }

    @Test
    void deletePhoto_NonExistingPhoto_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(0);
        when(photoTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(testPhoto);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> photoRepository.deletePhoto(999));
    }

    @Test
    void findPhotosByBar_ExistingBar_ReturnsPhotoList() {
        // Arrange
        List<Photo> expectedPhotos = Arrays.asList(testPhoto);
        when(photoTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(expectedPhotos);

        // Act
        List<Photo> result = photoRepository.findPhotosByBar(1);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedPhotos.size(), result.size());
        assertEquals(expectedPhotos.get(0).getId(), result.get(0).getId());
        verify(photoTemplate).query(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void downloadPhotosByBar_ExistingBar_ReturnsResponseEntityWithResources() {
        // Arrange
        List<byte[]> mockImageData = Arrays.asList(testImageData);
        when(photoTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(mockImageData);

        // Act
        ResponseEntity<List<ByteArrayResource>> result = photoRepository.downloadPhotosByBar(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertFalse(result.getBody().isEmpty());
        verify(photoTemplate).query(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void downloadPhotosByBar_ErrorOccurs_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.downloadPhotosByBar(1));
        assertTrue(exception.getMessage().contains("Erreur lors du téléchargement des photos"));
    }
}