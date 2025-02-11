package org.meetmybar.meetmybarapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
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
public class ScheduleDayRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private ScheduleDayRepository scheduleDayRepository;

    private ScheduleDay testScheduleDay;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testScheduleDay = createTestScheduleDay();
    }

    @Test
    void getScheduleDays_Success() {
        // Arrange
        List<ScheduleDay> expectedScheduleDays = Arrays.asList(testScheduleDay);
        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(expectedScheduleDays);

        // Act
        List<ScheduleDay> actualScheduleDays = scheduleDayRepository.getScheduleDays();

        // Assert
        assertNotNull(actualScheduleDays);
        assertEquals(expectedScheduleDays.size(), actualScheduleDays.size());
        assertEquals(expectedScheduleDays.get(0).getDay(), actualScheduleDays.get(0).getDay());
        verify(jdbcTemplate).query(eq("SELECT id, opening, closing, day FROM SCHEDULE_DAY"),
                any(HashMap.class),
                any(RowMapper.class));
    }

    @Test
    void getScheduleDays_ThrowsException() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scheduleDayRepository.getScheduleDays());
    }

    @Test
    void getScheduleDayById_Success() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testScheduleDay);

        // Act
        ScheduleDay result = scheduleDayRepository.getScheduleDayById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jdbcTemplate).queryForObject(eq("SELECT id, opening, closing, day FROM SCHEDULE_DAY WHERE id = :id"),
                any(Map.class),
                any(RowMapper.class));
    }

    @Test
    void getScheduleDayById_NotFound() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scheduleDayRepository.getScheduleDayById(999));
    }

    @Test
    void createScheduleDay_Success() {
        // Arrange
        doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(2);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", 1);
            ((KeyHolder) invocation.getArgument(2)).getKeyList().add(keyMap);
            return 1;
        }).when(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        // Act
        ScheduleDay result = scheduleDayRepository.createScheduleDay(testScheduleDay);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(testScheduleDay.getDay(), result.getDay());
        verify(jdbcTemplate).update(
                eq("INSERT INTO SCHEDULE_DAY (day, closing, opening) VALUES (:day, :closing, :opening)"),
                any(MapSqlParameterSource.class),
                any(KeyHolder.class),
                eq(new String[]{"id"})
        );
    }

    @Test
    void createScheduleDay_ThrowsException() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scheduleDayRepository.createScheduleDay(testScheduleDay));
    }

    @Test
    void updateScheduleDay_Success() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testScheduleDay);

        // Act
        ScheduleDay result = scheduleDayRepository.updateScheduleDay(testScheduleDay);

        // Assert
        assertNotNull(result);
        assertEquals(testScheduleDay.getDay(), result.getDay());
        verify(jdbcTemplate).update(
                eq("UPDATE SCHEDULE_DAY SET day = :day, opening = :opening, closing = :closing WHERE id = :id"),
                any(Map.class)
        );
    }

    @Test
    void updateScheduleDay_NotFound() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scheduleDayRepository.updateScheduleDay(testScheduleDay));
    }

    @Test
    void deleteScheduleDay_Success() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(testScheduleDay);
        when(jdbcTemplate.update(anyString(), any(Map.class))).thenReturn(1);

        // Act
        ScheduleDay result = scheduleDayRepository.deleteScheduleDayById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jdbcTemplate).update(eq("DELETE FROM SCHEDULE_DAY WHERE id = :id"), any(Map.class));
    }

    @Test
    void deleteScheduleDay_NotFound() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scheduleDayRepository.deleteScheduleDayById(999));
    }

    private ScheduleDay createTestScheduleDay() {
        ScheduleDay scheduleDay = new ScheduleDay();
        scheduleDay.setId(1);
        scheduleDay.setDay("Lundi");
        scheduleDay.setOpening("18h");
        scheduleDay.setClosing("1h");
        return scheduleDay;
    }
}