package org.meetmybar.meetmybarapi.models.entity;


public class LinkBarDrinkEntity {

  private String idBar;
  private String idDrink;
  private long volume;
  private double price;


  public String getIdBar() {
    return idBar;
  }

  public void setIdBar(String idBar) {
    this.idBar = idBar;
  }


  public String getIdDrink() {
    return idDrink;
  }

  public void setIdDrink(String idDrink) {
    this.idDrink = idDrink;
  }


  public long getVolume() {
    return volume;
  }

  public void setVolume(long volume) {
    this.volume = volume;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
