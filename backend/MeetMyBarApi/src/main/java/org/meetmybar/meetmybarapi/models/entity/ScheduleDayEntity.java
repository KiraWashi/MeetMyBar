package org.meetmybar.meetmybarapi.models.entity;


public class ScheduleDayEntity {

  private java.sql.Time openning;
  private java.sql.Time closing;
  private String day;
  private String id;


  public java.sql.Time getOpenning() {
    return openning;
  }

  public void setOpenning(java.sql.Time openning) {
    this.openning = openning;
  }


  public java.sql.Time getClosing() {
    return closing;
  }

  public void setClosing(java.sql.Time closing) {
    this.closing = closing;
  }


  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
