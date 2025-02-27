package org.meetmybar.meetmybarapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.exception.PhotoNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
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

    private static final String SQL_DELETE_PHOTO = "DELETE FROM PHOTO WHERE id = :id";
    private static final String SQL_DELETE_PHOTO_LINKS = "DELETE FROM LINK_BAR_PHOTO WHERE id_photo = :id";
    private static final String SQL_DOWNLOAD_PHOTO_BY_ID = "SELECT image_data FROM PHOTO WHERE id = :id";

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
        byte[] compressedImageData = "test compressed image data".getBytes();
        byte[] decompressedImageData = "test decompressed image data".getBytes();
        
        // Mock le résultat de la requête SQL
        when(photoTemplate.query(eq(SQL_DOWNLOAD_PHOTO_BY_ID), anyMap(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(compressedImageData));
        
        // Mock la décompression de l'image
        try (MockedStatic<ImageUtils> imageUtils = mockStatic(ImageUtils.class)) {
            imageUtils.when(() -> ImageUtils.decompressImage(compressedImageData))
                    .thenReturn(decompressedImageData);

            // Act
            ResponseEntity<ByteArrayResource> result = photoRepository.downloadById(1);

            // Assert
            assertNotNull(result);
            assertTrue(result.getStatusCode().is2xxSuccessful());
            assertNotNull(result.getBody());
            assertEquals(MediaType.IMAGE_JPEG, result.getHeaders().getContentType());
            assertTrue(result.getHeaders().containsKey(HttpHeaders.CONTENT_DISPOSITION));
            assertEquals(decompressedImageData.length, result.getHeaders().getContentLength());
            
            verify(photoTemplate).query(eq(SQL_DOWNLOAD_PHOTO_BY_ID), anyMap(), any(RowMapper.class));
        }
    }

    @Test
    void downloadById_PhotoNotFound_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.query(eq(SQL_DOWNLOAD_PHOTO_BY_ID), anyMap(), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.downloadById(1));
        assertTrue(exception.getMessage().contains("Error downloading Photo"));
        verify(photoTemplate).query(eq(SQL_DOWNLOAD_PHOTO_BY_ID), anyMap(), any(RowMapper.class));
    }

    @Test
    void downloadById_DecompressionError_ThrowsRuntimeException() {
        // Arrange
        byte[] compressedImageData = "test compressed image data".getBytes();
        when(photoTemplate.query(eq(SQL_DOWNLOAD_PHOTO_BY_ID), anyMap(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(compressedImageData));

        // Mock la décompression de l'image pour qu'elle échoue
        try (MockedStatic<ImageUtils> imageUtils = mockStatic(ImageUtils.class)) {
            imageUtils.when(() -> ImageUtils.decompressImage(any()))
                    .thenThrow(new RuntimeException("Decompression failed"));

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> photoRepository.downloadById(1));
            assertTrue(exception.getMessage().contains("Error downloading Photo"));
            verify(photoTemplate).query(eq(SQL_DOWNLOAD_PHOTO_BY_ID), anyMap(), any(RowMapper.class));
        }
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
        verify(photoTemplate, times(2)).update(anyString(), anyMap());
        
        InOrder inOrder = inOrder(photoTemplate);
        inOrder.verify(photoTemplate).update(eq(SQL_DELETE_PHOTO_LINKS), anyMap());
        inOrder.verify(photoTemplate).update(eq(SQL_DELETE_PHOTO), anyMap());
    }

    @Test
    void deletePhoto_NonExistingPhoto_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(testPhoto);
        when(photoTemplate.update(anyString(), anyMap()))
                .thenReturn(1)
                .thenReturn(0);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> photoRepository.deletePhoto(999));
        verify(photoTemplate, times(2)).update(anyString(), anyMap());
        
        InOrder inOrder = inOrder(photoTemplate);
        inOrder.verify(photoTemplate).update(eq(SQL_DELETE_PHOTO_LINKS), anyMap());
        inOrder.verify(photoTemplate).update(eq(SQL_DELETE_PHOTO), anyMap());
    }

    @Test
    void deletePhoto_DatabaseErrorOnLinks_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(testPhoto);
        when(photoTemplate.update(eq(SQL_DELETE_PHOTO_LINKS), anyMap()))
                .thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.deletePhoto(1));
        assertTrue(exception.getMessage().contains("Error deleting photo"));
        
        verify(photoTemplate, never()).update(eq(SQL_DELETE_PHOTO), anyMap());
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
    void downloadPhotosByBar_ExistingBar_ReturnsResponseEntityList() {
        // Arrange
        List<Photo> mockPhotos = Arrays.asList(
            new Photo(1, "Photo 1", testImageData, true),
            new Photo(2, "Photo 2", testImageData, false)
        );
        when(photoTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(mockPhotos);

        // Act
        List<ResponseEntity<ByteArrayResource>> result = photoRepository.downloadPhotosByBar(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        result.forEach(response -> {
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertNotNull(response.getBody());
            assertTrue(response.getHeaders().containsKey("Content-Disposition"));
        });
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

    @Test
    void addPhotoBar_Success() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        // Act & Assert
        assertDoesNotThrow(() -> photoRepository.addPhotoBar(1, 1));
        verify(photoTemplate).update(anyString(), anyMap());
    }

    @Test
    void addPhotoBar_Failure_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(0);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.addPhotoBar(1, 1));
        assertTrue(exception.getMessage().contains("Échec de l'association de la photo au bar"));
    }

    @Test
    void addPhotoBar_DatabaseError_ThrowsRuntimeException() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap()))
            .thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.addPhotoBar(1, 1));
        assertTrue(exception.getMessage().contains("Erreur lors de l'association de la photo au bar"));
    }

    @Test
    void deletePhotoBarLink_Success() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        // Act & Assert
        assertDoesNotThrow(() -> photoRepository.deletePhotoBarLink(1, 1));
        verify(photoTemplate).update(anyString(), anyMap());
    }

    @Test
    void deletePhotoBarLink_NoAssociationFound() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(0);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.deletePhotoBarLink(1, 1));
        assertTrue(exception.getMessage().contains("Association non trouvée entre le bar 1 et la photo 1"));
        verify(photoTemplate).update(anyString(), anyMap());
    }

    @Test
    void deletePhotoBarLink_DatabaseError() {
        // Arrange
        when(photoTemplate.update(anyString(), anyMap()))
            .thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> photoRepository.deletePhotoBarLink(1, 1));
        assertTrue(exception.getMessage().contains("Erreur lors de la suppression du lien photo-bar"));
        verify(photoTemplate).update(anyString(), anyMap());
    }
}