package org.meetmybar.meetmybarapi.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @NotNull
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;

    public boolean isMainPhoto() {
        return this.mainPhoto;
    }
}