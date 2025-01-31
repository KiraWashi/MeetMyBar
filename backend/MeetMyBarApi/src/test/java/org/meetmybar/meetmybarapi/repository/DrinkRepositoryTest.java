package org.meetmybar.meetmybarapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DrinkRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;

    @Test
    void testGetDrinkById() {
        // Arrange
        this.drinkRepository.createDrink(getDrink());

        // Act
        Drink drink = this.drinkRepository.getDrinkById(1);

        // Assert
        assertNotNull(drink);
        Assertions.assertEquals("Lancelot", drink.getBrand());
        Assertions.assertEquals(1, drink.getId());
        Assertions.assertEquals("Duchesse", drink.getName());
        Assertions.assertEquals(7.5, drink.getAlcoholDegree());
        Assertions.assertEquals(Drink.TypeEnum.BIERE_BLONDE, drink.getType());
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