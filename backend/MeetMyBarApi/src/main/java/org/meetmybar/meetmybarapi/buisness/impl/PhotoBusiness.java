package org.meetmybar.meetmybarapi.buisness.impl;

import org.meetmybar.meetmybarapi.exception.*;
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.models.entity.PhotoEntity;
import org.meetmybar.meetmybarapi.repository.PhotoRepository;
import org.meetmybar.meetmybarapi.util.ImageCompressor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service

public class PhotoBusiness {
    private static final String uploadPath="./Upload/";
    private final PhotoRepository photoRepository;


    public PhotoBusiness(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;

    }


    //public Photo savePhoto(MultipartFile file, Photo photoDTO) {
    //    validateImageFile(file);

        //if (photoDTO.isMainPhoto()) {
        //    updateExistingMainPhotos();
        //}

    //    String fileName = photoDTO.getId() + generateFileName(file);
    //    BufferedImage compressedImage = compressImage(file);
    //    saveImageToFileSystem(compressedImage, fileName);

    //    PhotoEntity photo = createPhotoEntity(photoDTO, fileName);
    //    return photoMapper.toDto(photoRepository.save(photo));
    // }

    private String generateFileName(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return "." + (extension != null ? extension : "jpg");
    }

    //public List<Photo> getAllPhotos() {
      //  return photoRepository.findAll().stream()
        //        .map(photoMapper::toDto)
          //      .collect(Collectors.toList());
    //}

    public Photo getPhotoById(int id) {
        if(photoRepository.findById(id)==null){
            throw new PhotoNotFoundException(id);
        }
        return photoRepository.findById(id);
    }

    public Resource getPhotoFile(int id) {
        Photo photo = photoRepository.findById(id);
        if(photo==null){
            throw new PhotoNotFoundException(id);
        }
        return loadPhotoResource(photo.getUrlFile());
    }

    // public Photo updatePhoto(int id, Photo updateDto) {
    //   Photo photo = photoRepository.findById(id);
    //   if(photo==null){
    //       throw new PhotoNotFoundException(id);
    //   }
    //    updatePhotoEntity(photo, updateDto);
    //    return photoMapper.toDto(photoRepository.save(photoMapper.toEntity(photo)));
    // }

    //public void deletePhoto(int id) {
    //     Photo photo = photoRepository.findById(id);
    //     if(photo==null){
    //        throw new PhotoNotFoundException(id);
    //    }

    //   deletePhotoFile(photo.getUrlFile());
    //   photoRepository.delete(photo);
    //}

    // Méthodes privées utilitaires
    private void validateImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidImageFileException();
        }
    }

    //private void updateExistingMainPhotos() {
    //   if (photoRepository.existsByMainPhotoTrue()) {
    //       photoRepository.findByMainPhotoTrue()
                    //               .forEach(p -> {
    //                   p.setMainPhoto(false);
    //                   photoRepository.save(photoMapper.toEntity(p));
    //                });
    //   }
    // }
    
    private BufferedImage compressImage(MultipartFile file) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            return ImageCompressor.compress(originalImage, 1024);
        } catch (IOException e) {
            throw new ImageProcessingException("Erreur lors de la compression de l'image", e);
        }
    }

    private void saveImageToFileSystem(BufferedImage image, String fileName) {
        try {
            Path filePath = Paths.get(uploadPath, fileName);
            Files.createDirectories(filePath.getParent());
            ImageIO.write(image, "jpg", filePath.toFile());
        } catch (IOException e) {
            throw new ImageStorageException("Erreur lors de la sauvegarde de l'image", e);
        }
    }

    private PhotoEntity createPhotoEntity(Photo createDto, String fileName) {
        /**PhotoEntity photo = new PhotoEntity();
        photo.setDescription(createDto.getDescription());
        photo.setUrlFile("/photos/" + fileName);
        photo.setMainPhoto(createDto.isMainPhoto());
        return photo;**/
        return null;
    }

    private void updatePhotoEntity(Photo photo, Photo updateDto) {
        if (updateDto.getDescription() != null) {
            photo.setDescription(updateDto.getDescription());
        }

        if (updateDto.isMainPhoto() && !photo.isMainPhoto()) {
            //updateExistingMainPhotos();
            photo.setMainPhoto(true);
        } else {
            photo.setMainPhoto(updateDto.isMainPhoto());
        }
    }

    private Resource loadPhotoResource(String urlFile) {
        try {
            Path path = Paths.get(uploadPath, urlFile.substring("/photos/".length()));
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            }
            throw new PhotoFileNotFoundException(urlFile);
        } catch (IOException e) {
            throw new ImageStorageException("Erreur lors du chargement de l'image", e);
        }
    }

    private void deletePhotoFile(String urlFile) {
        try {
            Path path = Paths.get(uploadPath, urlFile.substring("/photos/".length()));
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression du fichier: {"+ urlFile+ " , "+ e+"}");
            // On ne relance pas l'exception pour permettre la suppression de l'entité même si le fichier est introuvable
        }
    }
}
