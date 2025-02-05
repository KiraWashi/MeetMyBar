package org.meetmybar.meetmybarapi.repository;


import jakarta.annotation.Resource;
import jakarta.ws.rs.core.HttpHeaders;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Repository
public class PhotoRepository{

    private static final String SQL_GET_PHOTO_BY_ID =
            "SELECT id,description, image_data, main_photo FROM PHOTO WHERE id = :id";

    private static final String SQL_INSERT_PHOTO = "INSERT INTO PHOTO (description, image_data, main_photo) VALUES (:description, :image_data, :main_photo)";

    private static final String SQL_DOWNLOAD_PHOTO_BY_ID =
            "SELECT image_data FROM PHOTO WHERE id = :id";

    private static final String SQL_UPDATE_PHOTO =
            "UPDATE PHOTO SET description = :description, image_data = :imageData, main_photo = :mainPhoto " +
                    "WHERE id = :id";
    private static final String SQL_DELETE_PHOTO = "DELETE FROM PHOTO WHERE id = :id";
    @Autowired
    private NamedParameterJdbcTemplate photoTemplate;


    public Photo findById(int id) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            return photoTemplate.queryForObject(SQL_GET_PHOTO_BY_ID, map, (r, s) -> {

                return new Photo(
                        r.getInt("id"),
                        r.getString("description"),
                        r.getBytes("image_data"),
                        r.getBoolean("main_photo"));
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding Photo : " + e.getMessage(), e);
        }
    }

    public void save(Photo photo) {
        try {
            // Création de la map des paramètres
            Map<String, Object> params = new HashMap<>();
            params.put("description", photo.getDescription());
            params.put("image_data", photo.getImageData());
            params.put("main_photo", photo.isMainPhoto());

            photoTemplate.update(SQL_INSERT_PHOTO, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting photo: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<ByteArrayResource> downloadById(int id) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            byte[] imageData = photoTemplate.query(SQL_DOWNLOAD_PHOTO_BY_ID, map, (rs, rowNum) -> {
                return rs.getBytes("image_data");
            }).getFirst(); // Prend le premier résultat car on s'attend à une seule image
;
            //on décompresse l'image et on la renvoie
            byte[] image =ImageUtils.decompressImage(imageData);

            ByteArrayResource resource = new ByteArrayResource(image);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(imageData.length)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error downloading Photo: " + e.getMessage(), e);
        }
    }

    public Photo updatePhoto(Photo photo) {
        try {
            // Vérifie d'abord si la photo existe
            Photo existingPhoto = this.findById(photo.getId());
            if (existingPhoto == null) {
                throw new RuntimeException("Photo not found with id: " + photo.getId());
            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", photo.getId());
            params.put("description", photo.getDescription());
            params.put("image_data", photo.getImageData());
            params.put("main_photo", photo.isMainPhoto());

            int rowsAffected = photoTemplate.update(SQL_UPDATE_PHOTO, params);

            if (rowsAffected > 0) {
                return photo;
            } else {
                throw new RuntimeException("Failed to update photo with id: " + photo.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating photo : " + e.getMessage(), e);
        }
    }

    public Photo deletePhoto(int photoId) {
        try {
            // On récupère d'abord la boisson pour pouvoir la retourner après suppression
            Photo existingPhoto = findById(photoId);

            if (existingPhoto  == null) {
                throw new RuntimeException("Photo not found with id: " + photoId);
            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", photoId);

            int rowsAffected = photoTemplate.update(SQL_DELETE_PHOTO, params);

            if (rowsAffected > 0) {
                return existingPhoto;
            } else {
                throw new RuntimeException("Failed to delete Photo with id: " + photoId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting photo : " + e.getMessage(), e);
        }
    }
}