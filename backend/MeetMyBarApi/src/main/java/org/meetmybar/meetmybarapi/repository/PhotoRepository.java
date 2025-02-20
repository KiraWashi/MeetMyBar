package org.meetmybar.meetmybarapi.repository;


import jakarta.ws.rs.core.HttpHeaders;
import org.meetmybar.meetmybarapi.exception.PhotoNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class PhotoRepository{

    private static final String SQL_GET_PHOTO_BY_ID =
            "SELECT id,description, image_data, main_photo FROM PHOTO WHERE id = :id";

    private static final String SQL_INSERT_PHOTO = "INSERT INTO PHOTO (description, image_data, main_photo) VALUES (:description, :image_data, :main_photo)";

    private static final String SQL_DOWNLOAD_PHOTO_BY_ID =
            "SELECT image_data FROM PHOTO WHERE id = :id";

    private static final String SQL_UPDATE_PHOTO =
            "UPDATE PHOTO SET description = :description, image_data = :image_data, main_photo = :main_photo " +
                    "WHERE id = :id";
    private static final String SQL_DELETE_PHOTO = "DELETE FROM PHOTO WHERE id = :id";

    private static final String SQL_GET_PHOTOS_BY_BAR = """
    SELECT p.id, p.description, p.image_data, p.main_photo 
    FROM PHOTO p 
    INNER JOIN LINK_BAR_PHOTO lbp ON p.id = lbp.id_photo 
    WHERE lbp.id_bar = :id
    """;

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
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
    }

    public Photo save(Photo photo) {
        try {
            // Création de la map des paramètres
            Map<String, Object> params = new HashMap<>();
            params.put("description", photo.getDescription());
            params.put("image_data", photo.getImageData());
            params.put("main_photo", photo.isMainPhoto());

            photoTemplate.update(SQL_INSERT_PHOTO, params);
            return photo;
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
            byte[] image = ImageUtils.decompressImage(imageData);

            ByteArrayResource resource = new ByteArrayResource(image);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(image.length)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error downloading Photo: " + e.getMessage() + " for photo id: " + id, e);
        }
    }

    public Photo updatePhoto(Photo photo) {
        if (photo == null || photo.getId() <= 0) {
            throw new IllegalArgumentException("Photo invalide");
        }

        // Récupérer la photo existante
        Photo existingPhoto = findById(photo.getId());
        
        // Si pas de nouvelles données d'image, utiliser les données existantes
        if (photo.getImageData() == null) {
            photo.setImageData(existingPhoto.getImageData());
        } else {
            // Compresser la nouvelle image avant de la sauvegarder
            try {
                photo.setImageData(ImageUtils.compressImage(photo.getImageData()));
            } catch (IOException e) {
                throw new RuntimeException("Error compressing image: " + e.getMessage() + " for photo id: " + photo.getId(), e);
            }
        }
        
        Map<String, Object> params = new HashMap<>();
        params.put("id", photo.getId());
        params.put("description", photo.getDescription());
        params.put("image_data", photo.getImageData());
        params.put("main_photo", photo.isMainPhoto());

        System.out.println("Paramètres pour update: " + params);
        System.out.println("Image data finale: " + (photo.getImageData() != null ? photo.getImageData().length : "null"));

        try {
            int rowsAffected = photoTemplate.update(SQL_UPDATE_PHOTO, params);
            
            if (rowsAffected == 0) {
                throw new PhotoNotFoundException("Photo non trouvée avec l'id: " + photo.getId());
            }
            
            return photo;
            
        } catch (PhotoNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erreur détaillée: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de la photo: " + e.getMessage(), e);
        }
    }

    public Photo deletePhoto(int photoId) {
        try {
            // On récupère d'abord la boisson pour pouvoir la retourner après suppression
            Photo existingPhoto = findById(photoId);
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

    public List<Photo> findPhotosByBar(int id) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            return photoTemplate.query(SQL_GET_PHOTOS_BY_BAR, map, (r, s) ->
                    new Photo(
                            r.getInt("id"),
                            r.getString("description"),
                            r.getBytes("image_data"),
                            r.getBoolean("main_photo")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding Photos for bar id : " + id);
        }
    }

    public List<ResponseEntity<ByteArrayResource>> downloadPhotosByBar(int id) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            List<Photo> photos = photoTemplate.query(SQL_GET_PHOTOS_BY_BAR, map, 
                (rs, rowNum) -> new Photo(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getBytes("image_data"),
                    rs.getBoolean("main_photo")
                ));

            if (photos.isEmpty()) {
                return Collections.emptyList();
            }

            return photos.stream()
                    .map(photo -> {
                        try {
                            byte[] decompressedImage = ImageUtils.decompressImage(photo.getImageData());
                            ByteArrayResource resource = new ByteArrayResource(decompressedImage);
                            
                            return ResponseEntity.ok()
                                    .contentType(MediaType.IMAGE_JPEG)
                                    .contentLength(decompressedImage.length)
                                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"photo_" + photo.getId() + ".jpg\"")
                                    .body(resource);
                        } catch (Exception e) {
                            throw new RuntimeException("Erreur lors de la décompression de l'image: " + e.getMessage(), e);
                        }
                    }).toList();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du téléchargement des photos pour le bar id: " + id, e);
        }
    }
}