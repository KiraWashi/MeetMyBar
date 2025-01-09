package org.meetmybar.meetmybarapi.models.entity;


public class DrinkEntity {

  private String id;
  private String name;
  private String brand;
  private double degree;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }


  public double getDegree() {
    return degree;
  }

  public void setDegree(double degree) {
    this.degree = degree;
  }

}
