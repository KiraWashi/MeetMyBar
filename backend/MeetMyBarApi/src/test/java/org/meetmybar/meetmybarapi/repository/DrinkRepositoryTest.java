package org.meetmybar.meetmybarapi.repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DrinkRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private DrinkRepository drinkRepository;

    private Drink testDrink;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testDrink = createTestDrink();
    }

    @Test
    void getDrinks_Success() {
        // Arrange
        List<Drink> expectedDrinks = Arrays.asList(testDrink);
        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(expectedDrinks);

        // Act
        List<Drink> actualDrinks = drinkRepository.getDrinks();

        // Assert
        assertNotNull(actualDrinks);
        assertEquals(expectedDrinks.size(), actualDrinks.size());
        assertEquals(expectedDrinks.get(0).getName(), actualDrinks.get(0).getName());
        verify(jdbcTemplate).query(eq("SELECT id, name, brand, degree, type FROM DRINK"),
                any(HashMap.class),
                any(RowMapper.class));
    }

    @Test
    void getDrinks_ThrowsException() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        assertThrows(RuntimeException.class, () -> drinkRepository.getDrinks());
    }

    @Test
    void getDrinkByName_Success() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testDrink);

        // Act
        Drink result = drinkRepository.getDrinkByName("Duchesse");

        // Assert
        assertNotNull(result);
        assertEquals("Duchesse", result.getName());
        verify(jdbcTemplate).queryForObject(eq("SELECT id, name, brand, degree, type FROM DRINK WHERE name = :name"),
                any(Map.class),
                any(RowMapper.class));
    }

    @Test
    void getDrinkByName_NotFound() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> drinkRepository.getDrinkByName("NonExistent"));
    }

    @Test
    void getDrinkById_Success() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testDrink);

        // Act
        Drink result = drinkRepository.getDrinkById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jdbcTemplate).queryForObject(eq("SELECT id, name, brand, degree, type FROM DRINK WHERE id = :id"),
                any(Map.class),
                any(RowMapper.class));
    }

    @Test
    void getDrinkById_NotFound() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> drinkRepository.getDrinkById(999));
    }

    @Test
    void createDrink_Success() {
        // Arrange
        doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(2);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", 1);
            ((KeyHolder) invocation.getArgument(2)).getKeyList().add(keyMap);
            return 1;
        }).when(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        // Act
        Drink result = drinkRepository.createDrink(testDrink);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(testDrink.getName(), result.getName());
        verify(jdbcTemplate).update(
                eq("INSERT INTO DRINK (name, brand, degree, type) VALUES (:name, :brand, :degree, :type)"),
                any(MapSqlParameterSource.class),
                any(KeyHolder.class),
                eq(new String[]{"id"})
        );
    }

    @Test
    void createDrink_ThrowsException() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        assertThrows(RuntimeException.class, () -> drinkRepository.createDrink(testDrink));
    }

    @Test
    void updateDrink_Success() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testDrink);

        // Act
        Drink result = drinkRepository.updateDrink(testDrink);

        // Assert
        assertNotNull(result);
        assertEquals(testDrink.getName(), result.getName());
        verify(jdbcTemplate).update(eq("UPDATE DRINK SET name = :name, brand = :brand, degree = :degree, type = :type WHERE id = :id"),
                any(Map.class));
    }

    @Test
    void updateDrink_NotFound() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(0);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> drinkRepository.updateDrink(testDrink));
    }

    @Test
    void deleteDrink_Success() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testDrink);
        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(1);

        // Act
        Drink result = drinkRepository.deleteDrink(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jdbcTemplate).update(eq("DELETE FROM DRINK WHERE id = :id"), any(Map.class));
    }

    @Test
    void deleteDrink_NotFound() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> drinkRepository.deleteDrink(999));
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