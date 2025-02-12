package org.meetmybar.meetmybarapi.models.modif;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bar
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-14T10:35:53.154932700+01:00[Europe/Paris]")
public class Bar {

  private Integer id;

  private String address;

  private String name;

  private Integer capacity;

  @Valid
  private List<@Valid Drink> drinks;

  @Valid
  private List<@Valid ScheduleDay> planning;

  private String city;

  private String postalCode;

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

  public Bar drinks(List<@Valid Drink> drinks) {
    this.drinks = drinks;
    return this;
  }

  public Bar addDrinksItem(Drink drinksItem) {
    if (this.drinks == null) {
      this.drinks = new ArrayList<>();
    }
    this.drinks.add(drinksItem);
    return this;
  }

  /**
   * Get drinks
   * @return drinks
  */
  @Valid 
  @Schema(name = "drinks", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("drinks")
  public List<@Valid Drink> getDrinks() {
    return drinks;
  }

  public void setDrinks(List<@Valid Drink> drinks) {
    this.drinks = drinks;
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

  public Bar city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
  */
  
  @Schema(name = "city", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Bar postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * @return postalCode
  */
  
  @Schema(name = "postal_code", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("postal_code")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
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
        Objects.equals(this.drinks, bar.drinks) &&
        Objects.equals(this.planning, bar.planning) &&
        Objects.equals(this.city, bar.city) &&
        Objects.equals(this.postalCode, bar.postalCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, name, capacity, drinks, planning, city, postalCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Bar {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
    sb.append("    drinks: ").append(toIndentedString(drinks)).append("\n");
    sb.append("    planning: ").append(toIndentedString(planning)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
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

