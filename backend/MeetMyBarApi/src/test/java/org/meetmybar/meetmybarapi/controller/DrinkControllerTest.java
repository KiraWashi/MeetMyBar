package org.meetmybar.meetmybarapi.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.meetmybar.meetmybarapi.business.DrinkBusiness;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DrinkControllerTest {


    private DrinkBusiness drinkBusinessMock = mock(DrinkBusiness.class);

    //@Test
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
