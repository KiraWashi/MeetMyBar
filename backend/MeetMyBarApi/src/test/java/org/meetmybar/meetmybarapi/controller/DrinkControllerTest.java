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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class DrinkControllerTest {


    @MockBean
    private DrinkBusiness drinkBusinessMock;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetDrinkByDrinkId() {
        // Arrange
        Mockito.when(this.drinkBusinessMock.getDrinkById(1)).thenReturn(getDrink());
        // Act
        final Response responseToAssert = RestAssured.given()
                .contentType("application/json")
                .header("authentication", "JWT_VALID")
                .pathParam("drinkId", 1)
                .get("/drink/{drinkId}");

        // Assert
        Mockito.verify(this.drinkBusinessMock, Mockito.times(1))
                .getDrinkById(1);
        Assertions.assertEquals(200, responseToAssert.statusCode());

    }

    @Test
    void testGetDrinkByDrinkName() {
        // Arrange
        Mockito.when(this.drinkBusinessMock.getDrinkByName(Mockito.anyString())).thenReturn(getDrink());
        // Act
        final Response responseToAssert = RestAssured.given()
                .contentType("application/json")
                .header("authentication", "JWT_VALID")
                .pathParam("drinkName", "Duchesse")
                .get("/drink/name/{drinkName}");

        // Assert
        Mockito.verify(this.drinkBusinessMock, Mockito.times(1))
                .getDrinkByName("Duchesse");
        Assertions.assertEquals(200, responseToAssert.statusCode());

    }

    @Test
    void testGetDrinks() {
        // Arrange
        List<Drink> list = new ArrayList<>();
        list.add(getDrink());
        Mockito.when(this.drinkBusinessMock.getDrinks()).thenReturn(list);
        // Act
        final Response responseToAssert = RestAssured.given()
                .contentType("application/json")
                .header("authentication", "JWT_VALID")
                .get("/drink");

        // Assert
        Mockito.verify(this.drinkBusinessMock, Mockito.times(1))
                .getDrinks();
        Assertions.assertEquals(200, responseToAssert.statusCode());

    }

    Drink getDrink(){
        Drink drink = new Drink();
        drink.setBrand("Lancelot");
        drink.setId(1);
        drink.setName("Duchesse");
        drink.setAlcoholDegree(7.5);
        drink.setType(Drink.TypeEnum.BIERE_BLONDE);
        return drink;
    }

}
