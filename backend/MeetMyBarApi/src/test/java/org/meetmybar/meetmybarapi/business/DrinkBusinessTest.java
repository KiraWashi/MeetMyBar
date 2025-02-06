package org.meetmybar.meetmybarapi.business;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.impl.DrinkBusinessImpl;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.repository.DrinkRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class DrinkBusinessTest {

    @Mock
    private DrinkRepository drinkRepository;

    @InjectMocks
    private DrinkBusinessImpl drinkBusiness;

    @Test
    void Test_getDrinkById() {
        // Arrange
        Drink drink = getDrink();
        Mockito.when(drinkRepository.getDrinkById(Mockito.anyInt())).thenReturn(drink);

        // Act
        Drink drinkToAssert = this.drinkBusiness.getDrinkById(1);

        // Assert
        assertNotNull(drinkToAssert);
        Mockito.verify(this.drinkRepository, Mockito.times(1)).getDrinkById(Mockito.anyInt());
        Assertions.assertEquals("Lancelot", drinkToAssert.getBrand());
        Assertions.assertEquals(1, drinkToAssert.getId());
        Assertions.assertEquals("Duchesse", drinkToAssert.getName());
        Assertions.assertEquals(7.5, drinkToAssert.getAlcoholDegree());
        Assertions.assertEquals(Drink.TypeEnum.BIERE_BLONDE, drinkToAssert.getType());


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
