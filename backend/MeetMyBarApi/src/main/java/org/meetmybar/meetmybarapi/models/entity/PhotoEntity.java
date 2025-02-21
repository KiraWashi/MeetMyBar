package org.meetmybar.meetmybarapi.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PHOTO")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "description", nullable = true)
    private String description;

    @NotNull
    @Column(name = "main_photo", nullable = false)
    private Boolean mainPhoto = false;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    public Integer getId() {
        return id;
    }

    public @NotNull Boolean getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(@NotNull Boolean mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public @Size(max = 255) String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 255) String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMainPhoto() {
        return this.mainPhoto;
    }

    @NotNull
    @JsonProperty("image_data")
    public byte[] getImageData() {
        return this.imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}