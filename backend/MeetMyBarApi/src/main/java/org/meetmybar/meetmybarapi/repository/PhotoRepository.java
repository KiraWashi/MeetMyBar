package org.meetmybar.meetmybarapi.repository;


import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PhotoRepository{

    private static final String SQL_GET_PHOTO_BY_ID =
            "SELECT id,description, image_data, main_photo FROM PHOTO WHERE id = :id";

    private static final String SQL_INSERT_PHOTO = "INSERT INTO PHOTO (description, image_data, main_photo) VALUES (:description, :image_data, :main_photo)";

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
            throw new RuntimeException("Error fetching bars: " + e.getMessage(), e);
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
}