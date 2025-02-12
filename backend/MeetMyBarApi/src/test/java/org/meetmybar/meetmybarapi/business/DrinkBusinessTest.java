package org.meetmybar.meetmybarapi.business;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.impl.DrinkBusinessImpl;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.repository.DrinkRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DrinkBusinessTest {

    @Mock
    private DrinkRepository drinkRepository;

    @InjectMocks
    private DrinkBusinessImpl drinkBusiness;

    private Drink testDrink;

    @BeforeEach
    void setUp() {
        testDrink = new Drink();
        testDrink.setId(1);
        testDrink.setName("Duchesse");
        testDrink.setBrand("Lancelot");
        testDrink.setAlcoholDegree(7.5);
        testDrink.setType(Drink.TypeEnum.BIERE_BLONDE);
    }

    @Test
    void getDrinks_OK() {
        // Arrange
        List<Drink> expectedDrinks = Arrays.asList(testDrink);
        when(drinkRepository.getDrinks()).thenReturn(expectedDrinks);

        // Act
        List<Drink> actualDrinks = drinkBusiness.getDrinks();

        // Assert
        assertEquals(expectedDrinks, actualDrinks);
        verify(drinkRepository).getDrinks();
    }

    @Test
    void getDrinkByName_OK() {
        // Arrange
        when(drinkRepository.getDrinkByName("Duchesse")).thenReturn(testDrink);

        // Act
        Drink result = drinkBusiness.getDrinkByName("Duchesse");

        // Assert
        assertNotNull(result);
        assertEquals("Duchesse", result.getName());
        verify(drinkRepository).getDrinkByName("Duchesse");
    }

    @Test
    void getDrinkById_OK() {
        // Arrange
        when(drinkRepository.getDrinkById(1)).thenReturn(testDrink);

        // Act
        Drink result = drinkBusiness.getDrinkById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(drinkRepository).getDrinkById(1);
    }

    @Test
    void createDrink_OK() {
        // Arrange
        Drink newDrink = new Drink();
        newDrink.setName("NewDrink");
        newDrink.setBrand("NewBrand");
        newDrink.setAlcoholDegree(5.0);

        when(drinkRepository.getDrinkByName("NewDrink")).thenReturn(null);
        when(drinkRepository.createDrink(any(Drink.class))).thenReturn(newDrink);

        // Act
        Drink result = drinkBusiness.createDrink(newDrink);

        // Assert
        assertNotNull(result);
        assertEquals(newDrink.getName(), result.getName());
        verify(drinkRepository).createDrink(newDrink);
    }

    @Test
    void createDrink_KO_Drink_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> drinkBusiness.createDrink(null));
        verify(drinkRepository, never()).createDrink(any());
    }

    @Test
    void createDrink_KO_Drink_Already_Exist() {
        // Arrange
        Drink existingDrink = new Drink();
        existingDrink.setId(2);
        existingDrink.setName("Test");

        Drink newDrink = new Drink();
        newDrink.setName("Test");
        newDrink.setBrand("Brand");
        newDrink.setAlcoholDegree(5.0);

        when(drinkRepository.getDrinkByName("Test")).thenReturn(existingDrink);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                drinkBusiness.createDrink(newDrink)
        );
        assertEquals("A drink with this name already exists", exception.getMessage());
        verify(drinkRepository, never()).createDrink(any());
    }

    @Test
    void createDrink_OK_Type_Null() {
        // Arrange
        Drink drinkWithoutType = new Drink();
        drinkWithoutType.setName("Test");
        drinkWithoutType.setBrand("Test");
        drinkWithoutType.setAlcoholDegree(5.0);

        when(drinkRepository.getDrinkByName("Test")).thenReturn(null);
        when(drinkRepository.createDrink(any(Drink.class))).thenReturn(drinkWithoutType);

        // Act
        Drink result = drinkBusiness.createDrink(drinkWithoutType);

        // Assert
        assertEquals(Drink.TypeEnum.NON_DEFINI, result.getType());
    }

    @Test
    void updateDrink_OK() {
        // Arrange
        when(drinkRepository.updateDrink(any(Drink.class))).thenReturn(testDrink);
        when(drinkRepository.getDrinkByName(anyString())).thenReturn(testDrink);

        // Act
        Drink result = drinkBusiness.updateDrink(testDrink);

        // Assert
        assertNotNull(result);
        assertEquals(testDrink.getName(), result.getName());
        verify(drinkRepository).updateDrink(testDrink);
    }

    @Test
    void updateDrink_KO_drink_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> drinkBusiness.updateDrink(null));
        verify(drinkRepository, never()).updateDrink(any());
    }

    @Test
    void updateDrink_KO_Id_Null() {
        // Arrange
        Drink drinkWithoutId = new Drink();
        drinkWithoutId.setName("Test");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> drinkBusiness.updateDrink(drinkWithoutId));
        verify(drinkRepository, never()).updateDrink(any());
    }

    @Test
    void deleteDrink_OK() {
        // Arrange
        when(drinkRepository.deleteDrink(anyInt())).thenReturn(testDrink);

        // Act
        Drink result = drinkBusiness.deleteDrink(1);

        // Assert
        assertNotNull(result);
        assertEquals(testDrink.getId(), result.getId());
        verify(drinkRepository).deleteDrink(1);
    }


}
