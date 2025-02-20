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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
public class PhotoRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate photoTemplate;

    @InjectMocks
    private PhotoRepository photoRepository;

    private Photo mockPhoto;
    private byte[] mockImageData;

    @BeforeEach
    void setUp() {
        mockImageData = new byte[]{1, 2, 3};
        mockPhoto = new Photo(1, "Test Photo", mockImageData, true);
    }

    @Test
    void findById_Success() {
        when(photoTemplate.queryForObject(
            eq("SELECT id,description, image_data, main_photo FROM PHOTO WHERE id = :id"),
            anyMap(),
            any(RowMapper.class)
        )).thenReturn(mockPhoto);

        Photo result = photoRepository.findById(1);

        assertNotNull(result);
        assertEquals(mockPhoto.getId(), result.getId());
        assertEquals(mockPhoto.getDescription(), result.getDescription());
        assertArrayEquals(mockPhoto.getImageData(), result.getImageData());
        assertEquals(mockPhoto.isMainPhoto(), result.isMainPhoto());
    }

    @Test
    void findById_NotFound() {
        when(photoTemplate.queryForObject(
            anyString(),
            anyMap(),
            any(RowMapper.class)
        )).thenThrow(new RuntimeException("Photo not found"));

        assertThrows(PhotoNotFoundException.class, () -> photoRepository.findById(1));
    }

    @Test
    void save_Success() {
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        Photo result = photoRepository.save(mockPhoto);

        assertNotNull(result);
        assertEquals(mockPhoto, result);
        verify(photoTemplate, times(1)).update(anyString(), anyMap());
    }

    @Test
    void downloadById_Success() throws Exception {
        byte[] compressedData = ImageUtils.compressImage(mockImageData);
        when(photoTemplate.query(
            eq("SELECT image_data FROM PHOTO WHERE id = :id"),
            anyMap(),
            any(RowMapper.class)
        )).thenReturn(Arrays.asList(compressedData));

        ResponseEntity<ByteArrayResource> response = photoRepository.downloadById(1);

        assertNotNull(response);
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().containsKey(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    void updatePhoto_Success() {
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        Photo result = photoRepository.updatePhoto(mockPhoto);

        assertNotNull(result);
        assertEquals(mockPhoto, result);
        verify(photoTemplate, times(1)).update(anyString(), anyMap());
    }

    @Test
    void updatePhoto_NotFound() {
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(0);

        assertThrows(PhotoNotFoundException.class, () -> photoRepository.updatePhoto(mockPhoto));
    }

    @Test
    void deletePhoto_Success() {
        when(photoTemplate.queryForObject(
            eq("SELECT id,description, image_data, main_photo FROM PHOTO WHERE id = :id"),
            anyMap(),
            any(RowMapper.class)
        )).thenReturn(mockPhoto);
        when(photoTemplate.update(anyString(), anyMap())).thenReturn(1);

        Photo result = photoRepository.deletePhoto(1);

        assertNotNull(result);
        assertEquals(mockPhoto, result);
        verify(photoTemplate, times(1)).update(anyString(), anyMap());
    }

    @Test
    void findPhotosByBar_Success() {
        List<Photo> mockPhotos = Arrays.asList(
            new Photo(1, "Photo 1", mockImageData, true),
            new Photo(2, "Photo 2", mockImageData, false)
        );

        when(photoTemplate.query(
            anyString(),
            anyMap(),
            any(RowMapper.class)
        )).thenReturn(mockPhotos);

        List<Photo> result = photoRepository.findPhotosByBar(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockPhotos, result);
    }

    @Test
    void downloadPhotosByBar_Success() throws Exception {
        List<Photo> mockPhotos = Arrays.asList(
            new Photo(1, "Photo 1", ImageUtils.compressImage(mockImageData), true),
            new Photo(2, "Photo 2", ImageUtils.compressImage(mockImageData), false)
        );

        when(photoTemplate.query(
            anyString(),
            anyMap(),
            any(RowMapper.class)
        )).thenReturn(mockPhotos);

        List<ResponseEntity<ByteArrayResource>> result = photoRepository.downloadPhotosByBar(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        result.forEach(response -> {
            assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
            assertNotNull(response.getBody());
            assertTrue(response.getHeaders().containsKey(HttpHeaders.CONTENT_DISPOSITION));
        });
    }

    @Test
    void downloadPhotosByBar_EmptyList() {
        when(photoTemplate.query(
            anyString(),
            anyMap(),
            any(RowMapper.class)
        )).thenReturn(Arrays.asList());

        List<ResponseEntity<ByteArrayResource>> result = photoRepository.downloadPhotosByBar(1);

        assertTrue(result.isEmpty());
    }
}