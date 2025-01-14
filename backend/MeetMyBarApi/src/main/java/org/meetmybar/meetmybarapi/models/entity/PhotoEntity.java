package org.meetmybar.meetmybarapi.models.entity;


public class PhotoEntity {

  private int id;
  private String description;
  private String urlFile;
  private boolean mainPhoto;

  public PhotoEntity(int id, String description, String urlFile, boolean mainPhoto) {
    this.id = id;
    this.description = description;
    this.urlFile = urlFile;
    this.mainPhoto = mainPhoto;
  }

    public PhotoEntity() {

    }

    public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getUrlFile() {
    return urlFile;
  }

  public void setUrlFile(String urlFile) {
    this.urlFile = urlFile;
  }

  public boolean isMainPhoto() {
    return this.mainPhoto;
  }

  public void setMainPhoto(boolean mainPhoto) {
    this.mainPhoto = mainPhoto;
  }
}
