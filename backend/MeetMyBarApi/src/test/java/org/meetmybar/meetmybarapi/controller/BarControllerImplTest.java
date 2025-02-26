package org.meetmybar.meetmybarapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.BarBusiness;
import org.meetmybar.meetmybarapi.controller.impl.BarControllerImpl;
import org.meetmybar.meetmybarapi.exception.BarNotFoundException;
import org.meetmybar.meetmybarapi.models.modif.Bar;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarControllerImplTest {

    @Mock
    private BarBusiness barBusiness;

    @InjectMocks
    private BarControllerImpl barController;

    private Bar testBar;

    @BeforeEach
    void setUp() {
        testBar = new Bar();
        testBar.setId(1);
        testBar.setName("Test Bar");
        testBar.setAddress("123 Test St");
        testBar.setCapacity(100);
        testBar.setCity("Test City");
        testBar.setPostalCode("12345");
    }

    /**
     * Teste la récupération d'un bar par son nom avec succès
     */
    @Test
    void getBarByName_Success() {
        when(barBusiness.getBarByName("Test Bar")).thenReturn(testBar);
        
        ResponseEntity<Bar> response = barController.getBarByName("Test Bar");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBar, response.getBody());
        verify(barBusiness).getBarByName("Test Bar");
    }

    /**
     * Teste la récupération d'un bar inexistant par son nom
     */
    @Test
    void getBarByName_NotFound() {
        when(barBusiness.getBarByName("NonExistent")).thenThrow(new BarNotFoundException("NonExistent"));
        
        ResponseEntity<Bar> response = barController.getBarByName("NonExistent");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Teste la récupération de tous les bars avec succès
     */
    @Test
    void getBar_Success() {
        List<Bar> bars = Arrays.asList(testBar);
        when(barBusiness.getBar()).thenReturn(bars);
        
        ResponseEntity<List<Bar>> response = barController.getBar();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bars, response.getBody());
    }

    /**
     * Teste la mise à jour d'un bar avec succès
     */
    @Test
    void updateBar_Success() {
        when(barBusiness.modifyBar(testBar)).thenReturn(testBar);
        
        ResponseEntity<Bar> response = barController.updateBar(testBar);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBar, response.getBody());
    }

    /**
     * Teste la mise à jour d'un bar avec un ID null
     */
    @Test
    void updateBar_NullId() {
        testBar.setId(null);
        
        ResponseEntity<Bar> response = barController.updateBar(testBar);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(barBusiness, never()).modifyBar(any());
    }

    /**
     * Teste la création d'un nouveau bar avec succès
     */
    @Test
    void addBar_Success() {
        when(barBusiness.createBar(testBar)).thenReturn(testBar);
        
        ResponseEntity<Bar> response = barController.addBar(testBar);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testBar, response.getBody());
    }

    /**
     * Teste la création d'un bar avec une erreur de validation
     */
    @Test
    void addBar_ValidationError() {
        when(barBusiness.createBar(testBar)).thenThrow(new IllegalArgumentException());
        
        ResponseEntity<Bar> response = barController.addBar(testBar);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Teste la récupération d'un bar par son adresse avec succès
     */
    @Test
    void getBarByAddress_Success() {
        when(barBusiness.getBarByAddress("123 Test St")).thenReturn(testBar);
        
        ResponseEntity<Bar> response = barController.getBarByAddress("123 Test St");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBar, response.getBody());
        verify(barBusiness).getBarByAddress("123 Test St");
    }

    /**
     * Teste la récupération d'un bar par son adresse avec une erreur système
     */
    @Test
    void getBarByAddress_SystemError() {
        when(barBusiness.getBarByAddress(anyString())).thenThrow(new RuntimeException());
        
        ResponseEntity<Bar> response = barController.getBarByAddress("123 Test St");
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Teste la récupération d'un bar par ID avec succès
     */
    @Test
    void getBarById_Success() {
        when(barBusiness.getBarById(1)).thenReturn(testBar);
        
        ResponseEntity<Bar> response = barController.getBarById(1);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBar, response.getBody());
    }

    /**
     * Teste la récupération d'un bar par ID non trouvé
     */
    @Test
    void getBarById_NotFound() {
        when(barBusiness.getBarById(999)).thenThrow(new BarNotFoundException("999"));
        
        ResponseEntity<Bar> response = barController.getBarById(999);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Teste la suppression d'un bar avec succès
     */
    @Test
    void deleteBarBarId_Success() {
        when(barBusiness.deleteBar(1)).thenReturn(testBar);
        
        ResponseEntity<Bar> response = barController.deleteBarBarId(1);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBar, response.getBody());
    }

    /**
     * Teste la suppression d'un bar avec ID null
     */
    @Test
    void deleteBarBarId_NullId() {
        ResponseEntity<Bar> response = barController.deleteBarBarId(null);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(barBusiness, never()).deleteBar(any());
    }

    /**
     * Teste la suppression d'un bar non trouvé
     */
    @Test
    void deleteBarBarId_NotFound() {
        when(barBusiness.deleteBar(999)).thenThrow(new BarNotFoundException("999"));
        
        ResponseEntity<Bar> response = barController.deleteBarBarId(999);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Teste la récupération de tous les bars avec erreur système
     */
    @Test
    void getBar_SystemError() {
        when(barBusiness.getBar()).thenThrow(new RuntimeException());
        
        ResponseEntity<List<Bar>> response = barController.getBar();
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Teste la mise à jour d'un bar non trouvé
     */
    @Test
    void updateBar_NotFound() {
        when(barBusiness.modifyBar(any())).thenThrow(new BarNotFoundException("1"));
        
        ResponseEntity<Bar> response = barController.updateBar(testBar);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Teste la mise à jour d'un bar avec erreur système
     */
    @Test
    void updateBar_SystemError() {
        when(barBusiness.modifyBar(any())).thenThrow(new RuntimeException());
        
        ResponseEntity<Bar> response = barController.updateBar(testBar);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Teste la création d'un bar avec planning
     */
    @Test
    void addBar_WithPlanning_Success() {
        // Création d'un bar avec planning
        Bar barWithPlanning = new Bar();
        barWithPlanning.setName("Bar avec planning");
        barWithPlanning.setAddress("123 Test St");
        barWithPlanning.setCapacity(100);
        barWithPlanning.setCity("Test City");
        barWithPlanning.setPostalCode("12345");
        
        List<ScheduleDay> planning = new ArrayList<>();
        ScheduleDay scheduleDay = new ScheduleDay();
        scheduleDay.setDay("LUNDI");
        scheduleDay.setOpening("09:00");
        scheduleDay.setClosing("18:00");
        planning.add(scheduleDay);
        
        barWithPlanning.setPlanning(planning);
        
        // Configuration du mock
        Bar createdBar = new Bar();
        createdBar.setId(2);
        createdBar.setName("Bar avec planning");
        createdBar.setAddress("123 Test St");
        createdBar.setCapacity(100);
        createdBar.setCity("Test City");
        createdBar.setPostalCode("12345");
        
        List<ScheduleDay> createdPlanning = new ArrayList<>();
        ScheduleDay createdScheduleDay = new ScheduleDay();
        createdScheduleDay.setId(5);
        createdScheduleDay.setDay("LUNDI");
        createdScheduleDay.setOpening("09:00");
        createdScheduleDay.setClosing("18:00");
        createdPlanning.add(createdScheduleDay);
        
        createdBar.setPlanning(createdPlanning);
        
        when(barBusiness.createBar(barWithPlanning)).thenReturn(createdBar);
        
        // Exécution
        ResponseEntity<Bar> response = barController.addBar(barWithPlanning);
        
        // Vérifications
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getId());
        assertNotNull(response.getBody().getPlanning());
        assertEquals(1, response.getBody().getPlanning().size());
        assertEquals(5, response.getBody().getPlanning().get(0).getId());
        assertEquals("LUNDI", response.getBody().getPlanning().get(0).getDay());
        
        verify(barBusiness).createBar(barWithPlanning);
    }

    /**
     * Teste la création d'un bar avec planning vide
     */
    @Test
    void addBar_WithEmptyPlanning_Success() {
        // Création d'un bar avec planning vide
        Bar barWithEmptyPlanning = new Bar();
        barWithEmptyPlanning.setName("Bar avec planning vide");
        barWithEmptyPlanning.setAddress("123 Test St");
        barWithEmptyPlanning.setCapacity(100);
        barWithEmptyPlanning.setCity("Test City");
        barWithEmptyPlanning.setPostalCode("12345");
        barWithEmptyPlanning.setPlanning(new ArrayList<>());
        
        // Configuration du mock
        Bar createdBar = new Bar();
        createdBar.setId(3);
        createdBar.setName("Bar avec planning vide");
        createdBar.setAddress("123 Test St");
        createdBar.setCapacity(100);
        createdBar.setCity("Test City");
        createdBar.setPostalCode("12345");
        createdBar.setPlanning(new ArrayList<>());
        
        when(barBusiness.createBar(barWithEmptyPlanning)).thenReturn(createdBar);
        
        // Exécution
        ResponseEntity<Bar> response = barController.addBar(barWithEmptyPlanning);
        
        // Vérifications
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getId());
        assertNotNull(response.getBody().getPlanning());
        assertTrue(response.getBody().getPlanning().isEmpty());
        
        verify(barBusiness).createBar(barWithEmptyPlanning);
    }

    /**
     * Teste la création d'un bar avec planning null
     */
    @Test
    void addBar_WithNullPlanning_Success() {
        // Création d'un bar avec planning null
        Bar barWithNullPlanning = new Bar();
        barWithNullPlanning.setName("Bar sans planning");
        barWithNullPlanning.setAddress("123 Test St");
        barWithNullPlanning.setCapacity(100);
        barWithNullPlanning.setCity("Test City");
        barWithNullPlanning.setPostalCode("12345");
        barWithNullPlanning.setPlanning(null);
        
        // Configuration du mock
        Bar createdBar = new Bar();
        createdBar.setId(4);
        createdBar.setName("Bar sans planning");
        createdBar.setAddress("123 Test St");
        createdBar.setCapacity(100);
        createdBar.setCity("Test City");
        createdBar.setPostalCode("12345");
        createdBar.setPlanning(null);
        
        when(barBusiness.createBar(barWithNullPlanning)).thenReturn(createdBar);
        
        // Exécution
        ResponseEntity<Bar> response = barController.addBar(barWithNullPlanning);
        
        // Vérifications
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().getId());
        assertNull(response.getBody().getPlanning());
        
        verify(barBusiness).createBar(barWithNullPlanning);
    }

    /**
     * Teste la création d'un bar avec plusieurs jours de planning
     */
    @Test
    void addBar_WithMultipleDaysPlanning_Success() {
        // Création d'un bar avec plusieurs jours de planning
        Bar barWithMultipleDays = new Bar();
        barWithMultipleDays.setName("Bar avec plusieurs jours");
        barWithMultipleDays.setAddress("123 Test St");
        barWithMultipleDays.setCapacity(100);
        barWithMultipleDays.setCity("Test City");
        barWithMultipleDays.setPostalCode("12345");
        
        List<ScheduleDay> planning = new ArrayList<>();
        
        ScheduleDay monday = new ScheduleDay();
        monday.setDay("LUNDI");
        monday.setOpening("09:00");
        monday.setClosing("18:00");
        
        ScheduleDay tuesday = new ScheduleDay();
        tuesday.setDay("MARDI");
        tuesday.setOpening("10:00");
        tuesday.setClosing("20:00");
        
        ScheduleDay friday = new ScheduleDay();
        friday.setDay("VENDREDI");
        friday.setOpening("14:00");
        friday.setClosing("02:00");
        
        planning.add(monday);
        planning.add(tuesday);
        planning.add(friday);
        
        barWithMultipleDays.setPlanning(planning);
        
        // Configuration du mock
        Bar createdBar = new Bar();
        createdBar.setId(5);
        createdBar.setName("Bar avec plusieurs jours");
        createdBar.setAddress("123 Test St");
        createdBar.setCapacity(100);
        createdBar.setCity("Test City");
        createdBar.setPostalCode("12345");
        
        List<ScheduleDay> createdPlanning = new ArrayList<>();
        
        ScheduleDay createdMonday = new ScheduleDay();
        createdMonday.setId(10);
        createdMonday.setDay("LUNDI");
        createdMonday.setOpening("09:00");
        createdMonday.setClosing("18:00");
        
        ScheduleDay createdTuesday = new ScheduleDay();
        createdTuesday.setId(11);
        createdTuesday.setDay("MARDI");
        createdTuesday.setOpening("10:00");
        createdTuesday.setClosing("20:00");
        
        ScheduleDay createdFriday = new ScheduleDay();
        createdFriday.setId(12);
        createdFriday.setDay("VENDREDI");
        createdFriday.setOpening("14:00");
        createdFriday.setClosing("02:00");
        
        createdPlanning.add(createdMonday);
        createdPlanning.add(createdTuesday);
        createdPlanning.add(createdFriday);
        
        createdBar.setPlanning(createdPlanning);
        
        when(barBusiness.createBar(barWithMultipleDays)).thenReturn(createdBar);
        
        // Exécution
        ResponseEntity<Bar> response = barController.addBar(barWithMultipleDays);
        
        // Vérifications
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getId());
        assertNotNull(response.getBody().getPlanning());
        assertEquals(3, response.getBody().getPlanning().size());
        
        // Vérifier que chaque jour a été créé avec son ID
        assertEquals(10, response.getBody().getPlanning().get(0).getId());
        assertEquals("LUNDI", response.getBody().getPlanning().get(0).getDay());
        
        assertEquals(11, response.getBody().getPlanning().get(1).getId());
        assertEquals("MARDI", response.getBody().getPlanning().get(1).getDay());
        
        assertEquals(12, response.getBody().getPlanning().get(2).getId());
        assertEquals("VENDREDI", response.getBody().getPlanning().get(2).getDay());
        
        verify(barBusiness).createBar(barWithMultipleDays);
    }

    /**
     * Teste la création d'un bar avec erreur système lors de la création du planning
     */
    @Test
    void addBar_WithPlanningSystemError() {
        // Création d'un bar avec planning
        Bar barWithPlanning = new Bar();
        barWithPlanning.setName("Bar avec planning");
        barWithPlanning.setAddress("123 Test St");
        barWithPlanning.setCapacity(100);
        barWithPlanning.setCity("Test City");
        barWithPlanning.setPostalCode("12345");
        
        List<ScheduleDay> planning = new ArrayList<>();
        ScheduleDay scheduleDay = new ScheduleDay();
        scheduleDay.setDay("LUNDI");
        scheduleDay.setOpening("09:00");
        scheduleDay.setClosing("18:00");
        planning.add(scheduleDay);
        
        barWithPlanning.setPlanning(planning);
        
        // Configuration du mock pour simuler une erreur système
        when(barBusiness.createBar(barWithPlanning)).thenThrow(new RuntimeException("Erreur lors de la création du planning"));
        
        // Exécution
        ResponseEntity<Bar> response = barController.addBar(barWithPlanning);
        
        // Vérifications
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(barBusiness).createBar(barWithPlanning);
    }
}