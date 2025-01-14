package org.meetmybar.meetmybarapi.repository;

import jakarta.inject.Inject;
import org.meetmybar.meetmybarapi.models.dto.Bar;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.meetmybar.meetmybarapi.models.entity.PhotoEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PhotoRepository {

    private static final String SQL_GET_PHOTO =
            "SELECT id, name, capacity, address, city, postal_code FROM BAR";

    @Inject
    private NamedParameterJdbcTemplate photoTemplate;

    public Photo findById(int id) {
        try {
            HashMap<String, Object> map = new HashMap<>();

            return photoTemplate.queryForObject(SQL_GET_PHOTO, map, (r, s) -> {

                return new Photo(
                        r.getInt("id"),
                        r.getString("description"),
                        r.getString("url_file"),
                        r.getBoolean("main_photo"));
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bars: " + e.getMessage(), e);
        }
    }



    public PhotoEntity save(PhotoEntity photo) {

    }


    public void delete(Photo photo) {
    }

    public boolean existsByMainPhotoTrue() {
    }

    public Iterable<Photo> findByMainPhotoTrue() {
    }
}