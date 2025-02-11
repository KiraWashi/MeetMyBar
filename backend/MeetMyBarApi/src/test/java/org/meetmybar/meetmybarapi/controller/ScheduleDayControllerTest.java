package org.meetmybar.meetmybarapi.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.ScheduleDayBusiness;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ScheduleDayControllerTest {

    @MockBean
    private ScheduleDayBusiness scheduleDayBusinessMock;

    @LocalServerPort
    private int port;

    private ScheduleDay testScheduleDay;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        testScheduleDay = createTestScheduleDay();
    }

    @Test
    void testGetScheduleDays_Success() {
        // Arrange
        List<ScheduleDay> scheduleDays = Arrays.asList(testScheduleDay);
        when(scheduleDayBusinessMock.getScheduleDays()).thenReturn(scheduleDays);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .get("/scheduleday");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        verify(scheduleDayBusinessMock).getScheduleDays();
    }

    @Test
    void testGetScheduleDays_InternalServerError() {
        // Arrange
        when(scheduleDayBusinessMock.getScheduleDays()).thenThrow(new RuntimeException("Database error"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .get("/scheduleday");

        // Assert
        Assertions.assertEquals(500, response.getStatusCode());
    }

    @Test
    void testGetScheduleDayById_Success() {
        // Arrange
        when(scheduleDayBusinessMock.getScheduleDayById(1)).thenReturn(testScheduleDay);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("scheduledayId", 1)
                .get("/scheduleday/{scheduledayId}");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        verify(scheduleDayBusinessMock).getScheduleDayById(1);
    }

    @Test
    void testGetScheduleDayById_NotFound() {
        // Arrange
        when(scheduleDayBusinessMock.getScheduleDayById(anyInt()))
                .thenThrow(new RuntimeException("Schedule day not found"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("scheduledayId", 999)
                .get("/scheduleday/{scheduledayId}");

        // Assert
        Assertions.assertEquals(500, response.getStatusCode());
    }

    @Test
    void testCreateScheduleDay_Success() {
        // Arrange
        when(scheduleDayBusinessMock.createScheduleDay(any(ScheduleDay.class))).thenReturn(testScheduleDay);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testScheduleDay)
                .post("/scheduleday");

        // Assert
        Assertions.assertEquals(201, response.getStatusCode());
        verify(scheduleDayBusinessMock).createScheduleDay(any(ScheduleDay.class));
    }

    @Test
    void testCreateScheduleDay_BadRequest() {
        // Arrange
        when(scheduleDayBusinessMock.createScheduleDay(any(ScheduleDay.class)))
                .thenThrow(new IllegalArgumentException("Invalid scheduleDay data"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testScheduleDay)
                .post("/scheduleday");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
        verify(scheduleDayBusinessMock).createScheduleDay(any(ScheduleDay.class));
    }

    @Test
    void testUpdateScheduleDay_Success() {
        // Arrange
        when(scheduleDayBusinessMock.updateScheduleDay(any(ScheduleDay.class))).thenReturn(testScheduleDay);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testScheduleDay)
                .patch("/scheduleday");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        verify(scheduleDayBusinessMock).updateScheduleDay(any(ScheduleDay.class));
    }

    @Test
    void testUpdateScheduleDay_BadRequest() {
        // Arrange
        when(scheduleDayBusinessMock.updateScheduleDay(any(ScheduleDay.class)))
                .thenThrow(new IllegalArgumentException("Invalid scheduleDay data"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(testScheduleDay)
                .patch("/scheduleday");

        // Assert
        Assertions.assertEquals(400, response.getStatusCode());
        verify(scheduleDayBusinessMock).updateScheduleDay(any(ScheduleDay.class));
    }

    @Test
    void testDeleteScheduleDay_Success() {
        // Arrange
        when(scheduleDayBusinessMock.deleteScheduleDayById(1)).thenReturn(testScheduleDay);

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("scheduledayId", 1)
                .delete("/scheduleday/{scheduledayId}");

        // Assert
        Assertions.assertEquals(200, response.getStatusCode());
        verify(scheduleDayBusinessMock).deleteScheduleDayById(1);
    }

    @Test
    void testDeleteScheduleDay_BadRequest() {
        // Arrange
        when(scheduleDayBusinessMock.deleteScheduleDayById(anyInt()))
                .thenThrow(new RuntimeException("Invalid scheduleDay ID"));

        // Act
        Response response = RestAssured.given()
                .contentType("application/json")
                .pathParam("scheduledayId", -1)
                .delete("/scheduleday/{scheduledayId}");

        // Assert
        Assertions.assertEquals(500, response.getStatusCode());
        verify(scheduleDayBusinessMock).deleteScheduleDayById(-1);
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