package org.meetmybar.meetmybarapi.models.entity;


public class BarEntity {

  private String name;
  private String id;
  private long capacity;
  private String address;
  private String city;
  private long postalCode;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public long getCapacity() {
    return capacity;
  }

  public void setCapacity(long capacity) {
    this.capacity = capacity;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public long getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(long postalCode) {
    this.postalCode = postalCode;
  }

}
