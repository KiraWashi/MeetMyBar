package org.meetmybar.meetmybarapi.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

@Data
public class Photo {
    private int id;
    private String description;
    @JsonProperty("image_data")
    private byte[] imageData;
    @JsonProperty("main_photo")
    private boolean mainPhoto;

    /**
     * Constructor with only required parameters
     */
    public Photo(Integer id) {
        this.id = id;
    }
    /**
     * Constructor with only required parameters
     */
    public Photo(){
    }

    public Photo(int id, String description, byte[] image_data, boolean mainPhoto) {
        this.id = id;
        this.description = description;
        this.imageData = image_data;
        this.mainPhoto = mainPhoto;
    }

    public Photo id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     */
    @NotNull
    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        return Objects.equals(this.id, photo.id) &&
                Objects.equals(this.description, photo.description) &&
                Arrays.equals(this.imageData, photo.imageData) &&
                Objects.equals(this.mainPhoto, photo.mainPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, this.description, this.mainPhoto, Arrays.hashCode(this.imageData));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Photo {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    description: ").append(toIndentedString(this.description)).append("\n");
        sb.append("    mainPhoto: ").append(toIndentedString(this.mainPhoto)).append("\n");
        sb.append("    image: ").append(toIndentedString(this.imageData)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] image_data) {
        this.imageData = image_data;
    }

    public boolean isMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(boolean mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

}