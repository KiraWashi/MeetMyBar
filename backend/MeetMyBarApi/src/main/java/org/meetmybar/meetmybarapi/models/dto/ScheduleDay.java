package org.meetmybar.meetmybarapi.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * ScheduleDay
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:34:47.595602900+01:00[Europe/Paris]")
public class ScheduleDay {

  private Integer id;

  private String opening;

  private String closing;

  private String day;

  public ScheduleDay() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ScheduleDay(Integer id) {
    this.id = id;
  }

  public ScheduleDay id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ScheduleDay opening(String opening) {
    this.opening = opening;
    return this;
  }

  /**
   * Get opening
   * @return opening
  */
  
  @Schema(name = "opening", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("opening")
  public String getOpening() {
    return opening;
  }

  public void setOpening(String opening) {
    this.opening = opening;
  }

  public ScheduleDay closing(String closing) {
    this.closing = closing;
    return this;
  }

  /**
   * Get closing
   * @return closing
  */
  
  @Schema(name = "closing", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("closing")
  public String getClosing() {
    return closing;
  }

  public void setClosing(String closing) {
    this.closing = closing;
  }

  public ScheduleDay day(String day) {
    this.day = day;
    return this;
  }

  /**
   * Get day
   * @return day
  */
  
  @Schema(name = "day", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("day")
  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScheduleDay scheduleDay = (ScheduleDay) o;
    return Objects.equals(this.id, scheduleDay.id) &&
        Objects.equals(this.opening, scheduleDay.opening) &&
        Objects.equals(this.closing, scheduleDay.closing) &&
        Objects.equals(this.day, scheduleDay.day);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, opening, closing, day);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScheduleDay {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    opening: ").append(toIndentedString(opening)).append("\n");
    sb.append("    closing: ").append(toIndentedString(closing)).append("\n");
    sb.append("    day: ").append(toIndentedString(day)).append("\n");
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
}

