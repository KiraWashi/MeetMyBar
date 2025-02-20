package org.meetmybar.meetmybarapi.business.impl;
import org.meetmybar.meetmybarapi.exception.*;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.repository.BarRepository;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;
import org.meetmybar.meetmybarapi.utils.ImageUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PhotoBusiness {

    private final PhotoRepository photoRepository;
    private final BarRepository barRepository;


    public PhotoBusiness(PhotoRepository photoRepository, BarRepository barRepository) {
        this.photoRepository = photoRepository;
        this.barRepository = barRepository;
    }

    public Photo savePhoto(MultipartFile imageFile, String description, boolean mainData) {
        try {
            Photo photo = new Photo();
            photo.setMainPhoto(mainData);
            photo.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
            if (description != null && !description.isEmpty()) {
                photo.setDescription(description);
            }

            // Sauvegarde dans le dépôt
            photoRepository.save(photo);
            return photo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving photo: " + e.getMessage(), e);
        }
    }


    public Photo getPhotoById(int id) {
        Photo photoRet=photoRepository.findById(id);
        if(photoRet==null){
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
        return photoRet;
    }

    public ResponseEntity<ByteArrayResource> downloadPhotoById(int id) {
        if(photoRepository.findById(id)==null){
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
        return photoRepository.downloadById(id);
    }


    public Photo updatePhoto(Photo patchPhoto) {
        System.out.println("Received update request for photo: " + patchPhoto);
        // Récupérer l'objet existant
        Photo existingPhoto = photoRepository.findById(patchPhoto.getId());
        if(existingPhoto==null){
            throw new PhotoNotFoundException("Photo not found for id :"+patchPhoto.getId());
        }

        existingPhoto.setDescription(patchPhoto.getDescription());
        existingPhoto.setImageData(patchPhoto.getImageData());
        existingPhoto.setMainPhoto(patchPhoto.isMainPhoto());
        // Appeler la méthode du repository qui exécute la requête dynamique
        return photoRepository.updatePhoto(existingPhoto);
    }


    public Photo deletePhoto(int id) {
        if(photoRepository.findById(id)==null){
            throw new PhotoNotFoundException("Photo not found for id :"+id);
        }
        return photoRepository.deletePhoto(id);
    }

    public List<Photo> getPhotoByIdBar(int id) {
        if(barRepository.getBarById(id)==null){
            throw new PhotoNotFoundException("Bar not found for id :"+id);
        }
        return photoRepository.findPhotosByBar(id);
    }

    public List<ResponseEntity<ByteArrayResource>> downloadPhotosByBar(int id) {
        if(barRepository.getBarById(id)==null){
            throw new PhotoNotFoundException("Bar not found for id :"+id);
        }
        return photoRepository.downloadPhotosByBar(id);
    }

    public Photo addPhotoBar(int idBar, int idPhoto) {
        // Vérification de l'existence du bar et de la photo
        if (barRepository.getBarById(idBar) == null) {
            throw new PhotoNotFoundException("Bar not found for id: " + idBar);
        }
        if (photoRepository.findById(idPhoto) == null) {
            throw new PhotoNotFoundException("Photo not found for id: " + idPhoto);
        }
        
        photoRepository.addPhotoBar(idBar, idPhoto);
        return photoRepository.findById(idPhoto);
    }
}
