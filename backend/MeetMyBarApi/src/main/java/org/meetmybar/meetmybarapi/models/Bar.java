package org.meetmybar.meetmybarapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 */

@Schema(name = "Bar", description = "")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:34:47.595602900+01:00[Europe/Paris]")
public class Bar {

  private Integer id;

  private String address;

  private String name;

  private Integer capacity;

  @Valid
  private List<@Valid Beer> beers;

  @Valid
  private List<@Valid ScheduleDay> planning;

  public Bar() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Bar(Integer id) {
    this.id = id;
  }

  public Bar id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier for the given bar.
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "Unique identifier for the given bar.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Bar address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
  */
  
  @Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Bar name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Bar capacity(Integer capacity) {
    this.capacity = capacity;
    return this;
  }

  /**
   * Get capacity
   * @return capacity
  */
  
  @Schema(name = "capacity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("capacity")
  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public Bar beers(List<@Valid Beer> beers) {
    this.beers = beers;
    return this;
  }

  public Bar addBeersItem(Beer beersItem) {
    if (this.beers == null) {
      this.beers = new ArrayList<>();
    }
    this.beers.add(beersItem);
    return this;
  }

  /**
   * Get beers
   * @return beers
  */
  @Valid 
  @Schema(name = "beers", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("beers")
  public List<@Valid Beer> getBeers() {
    return beers;
  }

  public void setBeers(List<@Valid Beer> beers) {
    this.beers = beers;
  }

  public Bar planning(List<@Valid ScheduleDay> planning) {
    this.planning = planning;
    return this;
  }

  public Bar addPlanningItem(ScheduleDay planningItem) {
    if (this.planning == null) {
      this.planning = new ArrayList<>();
    }
    this.planning.add(planningItem);
    return this;
  }

  /**
   * Get planning
   * @return planning
  */
  @Valid 
  @Schema(name = "planning", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("planning")
  public List<@Valid ScheduleDay> getPlanning() {
    return planning;
  }

  public void setPlanning(List<@Valid ScheduleDay> planning) {
    this.planning = planning;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bar bar = (Bar) o;
    return Objects.equals(this.id, bar.id) &&
        Objects.equals(this.address, bar.address) &&
        Objects.equals(this.name, bar.name) &&
        Objects.equals(this.capacity, bar.capacity) &&
        Objects.equals(this.beers, bar.beers) &&
        Objects.equals(this.planning, bar.planning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, name, capacity, beers, planning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Bar {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
    sb.append("    beers: ").append(toIndentedString(beers)).append("\n");
    sb.append("    planning: ").append(toIndentedString(planning)).append("\n");
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

