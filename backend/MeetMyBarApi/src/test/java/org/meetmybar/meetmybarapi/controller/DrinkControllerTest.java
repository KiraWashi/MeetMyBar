package org.meetmybar.meetmybarapi.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.DrinkBusiness;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class DrinkControllerTest {


    @MockBean
    private DrinkBusiness drinkBusinessMock;

    @LocalServerPort
    private int port;

    private Drink testDrink;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        testDrink = createTestDrink();
    }

    @Test
    void testGetDrinks_Success() {
        // Arrange
        List<Drink> drinks = new ArrayList<>();
        drinks.add(testDrink);
        when(drinkBusinessMock.getDrinks()).thenReturn(drinks);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .get("/drink");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).getDrinks();

    }

    @Test
    void testGetDrinks_InternalServerError() {
        // Arrange
        when(drinkBusinessMock.getDrinks()).thenThrow(new RuntimeException("Database error"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .get("/drink");

        // Assert
        Assertions.assertEquals(500, response.getStatusCode());
    }

    @Test
    void testGetDrinkById_Success() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("drinkId", 1)
                .get("/drink/{drinkId}");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).getDrinkById(1);
    }

    @Test
    void testGetDrinkById_NotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(anyInt())).thenReturn(null);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("drinkId", 999)
                .get("/drink/{drinkId}");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testGetDrinkByName_Success() {
        // Arrange
        when(drinkBusinessMock.getDrinkByName("Duchesse")).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("drinkName", "Duchesse")
                .get("/drink/name/{drinkName}");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).getDrinkByName("Duchesse");
    }

    @Test
    void testGetDrinkByName_InternalServerError() {
        // Arrange
        when(drinkBusinessMock.getDrinkByName(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("drinkName", "NonExistent")
                .get("/drink/name/{drinkName}");

        // Assert
        Assertions.assertEquals(500, response.getStatusCode());
    }

    @Test
    void testCreateDrink_Success() {
        // Arrange
        when(drinkBusinessMock.createDrink(any(Drink.class))).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testDrink)
                .post("/drink");

        // Assert
        Assertions.assertEquals(201, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).createDrink(any(Drink.class));
    }

    @Test
    void testCreateDrink_BadRequest() {
        // Arrange
        when(drinkBusinessMock.createDrink(any(Drink.class)))
                .thenThrow(new IllegalArgumentException("Invalid drink data"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testDrink)
                .post("/drink");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void testUpdateDrink_Success() {
        // Arrange
        when(drinkBusinessMock.updateDrink(any(Drink.class))).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testDrink)
                .patch("/drink");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).updateDrink(any(Drink.class));
    }

    @Test
    void testUpdateDrink_BadRequest() {
        // Arrange
        when(drinkBusinessMock.updateDrink(any(Drink.class)))
                .thenThrow(new IllegalArgumentException("Invalid drink data"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testDrink)
                .patch("/drink");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void testDeleteDrink_Success() {
        // Arrange
        when(drinkBusinessMock.deleteDrink(1)).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("drinkId", 1)
                .delete("/drink/{drinkId}");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).deleteDrink(1);
    }

    @Test
    void testDeleteDrink_BadRequest() {
        // Arrange
        when(drinkBusinessMock.deleteDrink(anyInt()))
                .thenThrow(new IllegalArgumentException("Invalid drink ID"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("drinkId", -1)
                .delete("/drink/{drinkId}");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void testAddDrinkToBar_Success() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.addDrinkToBar(1, 1, 0.33, 5.0)).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .queryParam("price", 5.0)
            .post("/drink/bar");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).addDrinkToBar(1, 1, 0.33, 5.0);
    }

    @Test
    void testAddDrinkToBar_DrinkNotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(999)).thenReturn(null);

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 999)
            .queryParam("volume", 0.33)
            .queryParam("price", 5.0)
            .post("/drink/bar");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testAddDrinkToBar_InvalidData() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.addDrinkToBar(1, 1, -0.33, 5.0))
            .thenThrow(new IllegalArgumentException("Le volume doit être supérieur à 0"));

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", -0.33)
            .queryParam("price", 5.0)
            .post("/drink/bar");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void testAddDrinkToBar_BarNotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.addDrinkToBar(999, 1, 0.33, 5.0))
            .thenThrow(new RuntimeException("foreign key constraint fails"));

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 999)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .queryParam("price", 5.0)
            .post("/drink/bar");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testUpdateDrinkBar_Success() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.updateDrinkBar(1, 1, 0.33, 6.0)).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .queryParam("newPrice", 6.0)
            .patch("/drink/bar");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).updateDrinkBar(1, 1, 0.33, 6.0);
    }

    @Test
    void testUpdateDrinkBar_AssociationNotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.updateDrinkBar(1, 1, 0.33, 6.0))
            .thenThrow(new RuntimeException("Association bar-boisson non trouvée"));

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .queryParam("newPrice", 6.0)
            .patch("/drink/bar");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testDeleteDrinkBar_Success() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.deleteDrinkBar(1, 1, 0.33)).thenReturn(testDrink);

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .delete("/drink/bar");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        Mockito.verify(drinkBusinessMock).deleteDrinkBar(1, 1, 0.33);
    }

    @Test
    void testDeleteDrinkBar_AssociationNotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.deleteDrinkBar(1, 1, 0.33))
            .thenThrow(new RuntimeException("Association bar-boisson non trouvée"));

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .delete("/drink/bar");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testUpdateDrinkBar_DrinkNotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(999)).thenReturn(null);

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 999)
            .queryParam("volume", 0.33)
            .queryParam("newPrice", 6.0)
            .patch("/drink/bar");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testUpdateDrinkBar_InvalidPrice() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.updateDrinkBar(1, 1, 0.33, -1.0))
            .thenThrow(new IllegalArgumentException("Le prix ne peut pas être négatif"));

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", 0.33)
            .queryParam("newPrice", -1.0)
            .patch("/drink/bar");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void testDeleteDrinkBar_DrinkNotFound() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(999)).thenReturn(null);

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 999)
            .queryParam("volume", 0.33)
            .delete("/drink/bar");

        // Assert
        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    void testDeleteDrinkBar_InvalidVolume() {
        // Arrange
        when(drinkBusinessMock.getDrinkById(1)).thenReturn(testDrink);
        when(drinkBusinessMock.deleteDrinkBar(1, 1, -0.33))
            .thenThrow(new IllegalArgumentException("Le volume doit être supérieur à 0"));

        // Act
        Response response = RestAssured.given()
            .contentType("application/json")
            .queryParam("idBar", 1)
            .queryParam("idDrink", 1)
            .queryParam("volume", -0.33)
            .delete("/drink/bar");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
    }

    private Drink createTestDrink() {
        Drink drink = new Drink();
        drink.setBrand("Lancelot");
        drink.setId(1);
        drink.setName("Duchesse");
        drink.setAlcoholDegree(7.5);
        drink.setType(Drink.TypeEnum.BIERE_BLONDE);
        return drink;
    }

}
