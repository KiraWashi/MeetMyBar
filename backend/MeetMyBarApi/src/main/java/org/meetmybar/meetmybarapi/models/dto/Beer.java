package org.meetmybar.meetmybarapi.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Beer
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:34:47.595602900+01:00[Europe/Paris]")
public class Beer {

  private Integer id;

  private BigDecimal alcoholDegree;

  private String name;

  private String brand;

  public Beer() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Beer(Integer id) {
    this.id = id;
  }

  public Beer id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Beer alcoholDegree(BigDecimal alcoholDegree) {
    this.alcoholDegree = alcoholDegree;
    return this;
  }

  /**
   * Get alcoholDegree
   * @return alcoholDegree
  */
  @Valid 
  @Schema(name = "alcohol_degree", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("alcohol_degree")
  public BigDecimal getAlcoholDegree() {
    return alcoholDegree;
  }

  public void setAlcoholDegree(BigDecimal alcoholDegree) {
    this.alcoholDegree = alcoholDegree;
  }

  public Beer name(String name) {
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

  public Beer brand(String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   * @return brand
  */
  
  @Schema(name = "brand", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("brand")
  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Beer beer = (Beer) o;
    return Objects.equals(this.id, beer.id) &&
        Objects.equals(this.alcoholDegree, beer.alcoholDegree) &&
        Objects.equals(this.name, beer.name) &&
        Objects.equals(this.brand, beer.brand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, alcoholDegree, name, brand);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Beer {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    alcoholDegree: ").append(toIndentedString(alcoholDegree)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
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

