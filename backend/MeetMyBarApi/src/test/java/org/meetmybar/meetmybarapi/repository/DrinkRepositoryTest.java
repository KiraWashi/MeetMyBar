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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DrinkRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;


    @Test
    void testGetDrinks() {

        // Assert
        this.drinkRepository.createDrink(getDrink());
        this.drinkRepository.createDrink(getDrinkV2());

        // Act
        List<Drink> drinks = this.drinkRepository.getDrinks();

        // Assert
        assertNotNull(drinks.getFirst());
        assertNotNull(drinks.getLast());
        Assertions.assertEquals("Lancelot", drinks.getFirst().getBrand());
        Assertions.assertEquals(1, drinks.getFirst().getId());
        Assertions.assertEquals("Duchesse", drinks.getFirst().getName());
        Assertions.assertEquals(7.5, drinks.getFirst().getAlcoholDegree());
        Assertions.assertEquals(Drink.TypeEnum.BIERE_BLONDE, drinks.getFirst().getType());

        Assertions.assertEquals(4, drinks.getLast().getId());
        Assertions.assertEquals("Chouffe", drinks.getLast().getName());
        Assertions.assertEquals(8, drinks.getLast().getAlcoholDegree());
        Assertions.assertEquals(Drink.TypeEnum.BIERE_ROUGE, drinks.getLast().getType());
    }

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

    @Test
    void testGetDrinkByName() {
        // Arrange
        this.drinkRepository.createDrink(getDrink());

        // Act
        Drink drink = this.drinkRepository.getDrinkByName("Duchesse");

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
        drink.setName("Duchesse");
        drink.setAlcoholDegree(7.5);
        drink.setType(Drink.TypeEnum.BIERE_BLONDE);
        return drink;
    }

    Drink getDrinkV2(){
        Drink drink = new Drink();
        drink.setBrand("");
        drink.setName("Chouffe");
        drink.setAlcoholDegree(8);
        drink.setType(Drink.TypeEnum.BIERE_ROUGE);
        return drink;
    }
}