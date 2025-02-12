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
