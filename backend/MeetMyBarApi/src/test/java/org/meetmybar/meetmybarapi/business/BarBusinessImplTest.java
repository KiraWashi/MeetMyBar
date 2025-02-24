package org.meetmybar.meetmybarapi.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.business.impl.BarBusinessImpl;
import org.meetmybar.meetmybarapi.exception.BarNotFoundException;
import org.meetmybar.meetmybarapi.models.modif.Bar;
import org.meetmybar.meetmybarapi.repository.BarRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarBusinessImplTest {

    @Mock
    private BarRepository barRepository;

    @InjectMocks
    private BarBusinessImpl barBusiness;

    private Bar testBar;

    @BeforeEach
    void setUp() {
        testBar = new Bar();
        testBar.setId(1);
        testBar.setName("Test Bar");
        testBar.setAddress("123 Test St");
    }

    /**
     * Teste la récupération de tous les bars
     */
    @Test
    void getBar_Success() {
        List<Bar> expectedBars = Arrays.asList(testBar);
        when(barRepository.getBar()).thenReturn(expectedBars);
        
        List<Bar> actualBars = barBusiness.getBar();
        
        assertEquals(expectedBars, actualBars);
        verify(barRepository).getBar();
    }

    /**
     * Teste la récupération d'un bar par son nom avec succès
     */
    @Test
    void getBarByName_Success() {
        when(barRepository.getBarByName("Test Bar")).thenReturn(testBar);
        
        Bar result = barBusiness.getBarByName("Test Bar");
        
        assertEquals(testBar, result);
    }

    /**
     * Teste la récupération d'un bar inexistant par son nom
     */
    @Test
    void getBarByName_NotFound() {
        when(barRepository.getBarByName("NonExistent")).thenReturn(null);
        
        assertThrows(BarNotFoundException.class, () -> 
            barBusiness.getBarByName("NonExistent")
        );
    }

    /**
     * Teste la modification d'un bar avec succès
     */
    @Test
    void modifyBar_Success() {
        when(barRepository.getBarById(1)).thenReturn(testBar);
        when(barRepository.modifyBar(testBar)).thenReturn(testBar);
        
        Bar result = barBusiness.modifyBar(testBar);
        
        assertEquals(testBar, result);
        verify(barRepository).modifyBar(testBar);
    }

    /**
     * Teste la modification d'un bar avec un ID null
     */
    @Test
    void modifyBar_NullId() {
        testBar.setId(null);
        
        assertThrows(IllegalArgumentException.class, () ->
            barBusiness.modifyBar(testBar)
        );
        
        verify(barRepository, never()).modifyBar(any());
    }

    /**
     * Teste la création d'un nouveau bar
     */
    @Test
    void createBar_Success() {
        when(barRepository.createBar(testBar)).thenReturn(testBar);
        
        Bar result = barBusiness.createBar(testBar);
        
        assertEquals(testBar, result);
        verify(barRepository).createBar(testBar);
    }

    /**
     * Teste la récupération d'un bar par son adresse avec succès
     */
    @Test
    void getBarByAddress_Success() {
        when(barRepository.getBarByAddress("123 Test St")).thenReturn(testBar);
        
        Bar result = barBusiness.getBarByAddress("123 Test St");
        
        assertEquals(testBar, result);
        verify(barRepository).getBarByAddress("123 Test St");
    }

    /**
     * Teste la récupération d'un bar inexistant par son adresse
     */
    @Test
    void getBarByAddress_NotFound() {
        when(barRepository.getBarByAddress("Adresse Inexistante")).thenReturn(null);
        
        assertThrows(BarNotFoundException.class, () -> 
            barBusiness.getBarByAddress("Adresse Inexistante")
        );
        verify(barRepository).getBarByAddress("Adresse Inexistante");
    }

    /**
     * Teste la récupération d'un bar par son ID avec succès
     */
    @Test
    void getBarById_Success() {
        when(barRepository.getBarById(1)).thenReturn(testBar);
        
        Bar result = barBusiness.getBarById(1);
        
        assertEquals(testBar, result);
        verify(barRepository).getBarById(1);
    }

    /**
     * Teste la récupération d'un bar inexistant par son ID
     */
    @Test
    void getBarById_NotFound() {
        when(barRepository.getBarById(999)).thenReturn(null);
        
        assertThrows(BarNotFoundException.class, () -> 
            barBusiness.getBarById(999)
        );
        verify(barRepository).getBarById(999);
    }

    /**
     * Teste la suppression d'un bar avec succès
     */
    @Test
    void deleteBar_Success() {
        when(barRepository.getBarById(1)).thenReturn(testBar);
        doNothing().when(barRepository).deleteBar(1);
        
        Bar result = barBusiness.deleteBar(1);
        
        assertEquals(testBar, result);
        verify(barRepository).deleteBar(1);
    }

    /**
     * Teste la suppression d'un bar avec ID null
     */
    @Test
    void deleteBar_NullId() {
        assertThrows(IllegalArgumentException.class, () ->
            barBusiness.deleteBar(null)
        );
        
        verify(barRepository, never()).deleteBar(any());
    }

    /**
     * Teste la suppression d'un bar non trouvé
     */
    @Test
    void deleteBar_NotFound() {
        when(barRepository.getBarById(999)).thenReturn(null);
        
        assertThrows(BarNotFoundException.class, () ->
            barBusiness.deleteBar(999)
        );
    }

    /**
     * Teste la modification d'un bar non trouvé
     */
    @Test
    void modifyBar_NotFound() {
        when(barRepository.getBarById(5)).thenReturn(null);
        
        assertThrows(BarNotFoundException.class, () ->
            barBusiness.modifyBar(testBar)
        );
        
        verify(barRepository, never()).modifyBar(any());
    }

    /**
     * Teste la modification d'un bar avec une erreur système
     */
    @Test
    void modifyBar_SystemError() {
        when(barRepository.getBarById(1)).thenReturn(testBar);
        when(barRepository.modifyBar(testBar)).thenThrow(new RuntimeException());
        
        assertThrows(RuntimeException.class, () ->
            barBusiness.modifyBar(testBar)
        );
    }

    /**
     * Teste la création d'un bar avec erreur système
     */
    @Test
    void createBar_SystemError() {
        when(barRepository.createBar(testBar)).thenThrow(new RuntimeException());
        
        assertThrows(RuntimeException.class, () ->
            barBusiness.createBar(testBar)
        );
    }

    /**
     * Teste la récupération de tous les bars avec erreur système
     */
    @Test
    void getBar_SystemError() {
        when(barRepository.getBar()).thenThrow(new RuntimeException());
        
        assertThrows(RuntimeException.class, () ->
            barBusiness.getBar()
        );
    }
}