package org.meetmybar.meetmybarapi.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bar")
public class BarEntity {

  @Column
  private String name;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column
  private long capacity;
  @Column
  private String address;
  @Column
  private String city;
  @Column(name = "postal_code")
  private long postalCode;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
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
