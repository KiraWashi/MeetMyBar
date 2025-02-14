package org.meetmybar.meetmybarapi.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.impl.ScheduleDayBusinessImpl;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.meetmybar.meetmybarapi.repository.ScheduleDayRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleDayBusinessTest {

    @Mock
    private ScheduleDayRepository scheduleDayRepository;

    @InjectMocks
    private ScheduleDayBusinessImpl scheduleDayBusiness;

    private ScheduleDay testScheduleDay;

    @BeforeEach
    void setUp() {
        testScheduleDay = createTestScheduleDay();
    }

    @Test
    void getScheduleDays_ShouldReturnListOfScheduleDays() {
        // Arrange
        List<ScheduleDay> expectedScheduleDays = Arrays.asList(testScheduleDay);
        when(scheduleDayRepository.getScheduleDays()).thenReturn(expectedScheduleDays);

        // Act
        List<ScheduleDay> actualScheduleDays = scheduleDayBusiness.getScheduleDays();

        // Assert
        assertEquals(expectedScheduleDays, actualScheduleDays);
        verify(scheduleDayRepository).getScheduleDays();
    }

    @Test
    void getScheduleDayById_ShouldReturnScheduleDay() {
        // Arrange
        when(scheduleDayRepository.getScheduleDayById(1)).thenReturn(testScheduleDay);

        // Act
        ScheduleDay result = scheduleDayBusiness.getScheduleDayById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(scheduleDayRepository).getScheduleDayById(1);
    }

    @Test
    void deleteScheduleDayById_ShouldDeleteScheduleDay() {
        // Arrange
        when(scheduleDayRepository.deleteScheduleDayById(1)).thenReturn(testScheduleDay);

        // Act
        ScheduleDay result = scheduleDayBusiness.deleteScheduleDayById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(scheduleDayRepository).deleteScheduleDayById(1);
    }

    @Test
    void createScheduleDay_ShouldCreateNewScheduleDay() {
        // Arrange
        when(scheduleDayRepository.createScheduleDay(any(ScheduleDay.class))).thenReturn(testScheduleDay);

        // Act
        ScheduleDay result = scheduleDayBusiness.createScheduleDay(testScheduleDay);

        // Assert
        assertNotNull(result);
        assertEquals(testScheduleDay.getDay(), result.getDay());
        verify(scheduleDayRepository).createScheduleDay(testScheduleDay);
    }

    @Test
    void createScheduleDay_ShouldThrowException_WhenScheduleDayIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.createScheduleDay(null));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
    }

    @Test
    void createScheduleDay_ShouldThrowException_WhenDayIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setDay(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.createScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
    }

    @Test
    void createScheduleDay_ShouldThrowException_WhenDayIsEmpty() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setDay("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.createScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
    }

    @Test
    void createScheduleDay_ShouldThrowException_WhenOpeningIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setOpening(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.createScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
    }

    @Test
    void createScheduleDay_ShouldThrowException_WhenClosingIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setClosing(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.createScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
    }

    @Test
    void updateScheduleDay_ShouldUpdateExistingScheduleDay() {
        // Arrange
        when(scheduleDayRepository.updateScheduleDay(any(ScheduleDay.class))).thenReturn(testScheduleDay);

        // Act
        ScheduleDay result = scheduleDayBusiness.updateScheduleDay(testScheduleDay);

        // Assert
        assertNotNull(result);
        assertEquals(testScheduleDay.getDay(), result.getDay());
        verify(scheduleDayRepository).updateScheduleDay(testScheduleDay);
    }

    @Test
    void updateScheduleDay_ShouldThrowException_WhenScheduleDayIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.updateScheduleDay(null));
        verify(scheduleDayRepository, never()).updateScheduleDay(any());
    }

    @Test
    void updateScheduleDay_ShouldThrowException_WhenIdIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setId(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.updateScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).updateScheduleDay(any());
    }

    @Test
    void updateScheduleDay_ShouldThrowException_WhenDayIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setDay(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.updateScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).updateScheduleDay(any());
    }

    @Test
    void updateScheduleDay_ShouldThrowException_WhenOpeningIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setOpening(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.updateScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).updateScheduleDay(any());
    }

    @Test
    void updateScheduleDay_ShouldThrowException_WhenClosingIsNull() {
        // Arrange
        ScheduleDay scheduleDay = createTestScheduleDay();
        scheduleDay.setClosing(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> scheduleDayBusiness.updateScheduleDay(scheduleDay));
        verify(scheduleDayRepository, never()).updateScheduleDay(any());
    }

    private ScheduleDay createTestScheduleDay() {
        ScheduleDay scheduleDay = new ScheduleDay();
        scheduleDay.setId(1);
        scheduleDay.setDay("Lundi");
        scheduleDay.setOpening("10:00");
        scheduleDay.setClosing("22:00");
        return scheduleDay;
    }
}