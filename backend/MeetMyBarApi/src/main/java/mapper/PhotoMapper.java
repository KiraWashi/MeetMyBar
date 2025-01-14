package mapper;


import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.models.entity.PhotoEntity;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {
    public Photo toDto(PhotoEntity photo) {
        return new Photo(
                photo.getId(),
                photo.getDescription(),
                photo.getUrlFile(),
                photo.isMainPhoto()
        );
    }

    public PhotoEntity toEntity(Photo photo) {
        return new PhotoEntity(
                photo.getId(),
                photo.getDescription(),
                photo.getUrlFile(),
                photo.isMainPhoto()
        );
    }
}