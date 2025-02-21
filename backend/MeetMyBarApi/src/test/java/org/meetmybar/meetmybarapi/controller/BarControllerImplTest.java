package org.meetmybar.meetmybarapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.BarBusiness;
import org.meetmybar.meetmybarapi.controller.impl.BarControllerImpl;
import org.meetmybar.meetmybarapi.exception.BarNotFoundException;
import org.meetmybar.meetmybarapi.models.modif.Bar;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

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
}