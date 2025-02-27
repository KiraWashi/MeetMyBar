package org.meetmybar.meetmybarapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meetmybar.meetmybarapi.exception.BarNotFoundException;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.meetmybar.meetmybarapi.models.modif.Bar;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate barTemplate;

    @Mock
    private ScheduleDayRepository scheduleDayRepository;

    @InjectMocks
    private BarRepository barRepository;

    private Bar testBar;
    private List<ScheduleDay> testScheduleDays;
    
    // Compteur pour générer des IDs uniques pour les ScheduleDay dans les tests
    private int scheduleDayIdCounter = 1;

    @BeforeEach
    void setUp() {
        // Réinitialiser le compteur avant chaque test
        scheduleDayIdCounter = 1;
        
        // Initialisation du bar de test
        testBar = new Bar();
        testBar.setName("Test Bar");
        testBar.setCapacity(100);
        testBar.setAddress("123 Test St");
        testBar.setCity("Test City");
        testBar.setPostalCode("12345");
        
        // Initialisation des jours de planning de test
        testScheduleDays = new ArrayList<>();
        
        ScheduleDay day1 = new ScheduleDay();
        day1.setDay("LUNDI");
        day1.setOpening("09:00");
        day1.setClosing("18:00");
        
        ScheduleDay day2 = new ScheduleDay();
        day2.setDay("MARDI");
        day2.setOpening("10:00");
        day2.setClosing("19:00");
        
        testScheduleDays.add(day1);
        testScheduleDays.add(day2);
    }

    /**
     * Teste la création d'un bar sans planning
     */
    @Test
    void createBar_WithoutPlanning_Success() {
        // Configuration du mock pour simuler l'insertion en base de données
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        // Exécution de la méthode à tester
        Bar result = barRepository.createBar(testBar);
        
        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
        verify(scheduleDayRepository, never()).createBarScheduleDayLink(anyInt(), anyInt());
    }

    /**
     * Teste la création d'un bar avec planning
     */
    @Test
    void createBar_WithPlanning_Success() {
        // Configuration du bar avec planning
        testBar.setPlanning(testScheduleDays);
        
        // Configuration des mocks
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        // Configuration des mocks pour la création des ScheduleDay
        ScheduleDay createdScheduleDay1 = new ScheduleDay();
        createdScheduleDay1.setId(10);
        createdScheduleDay1.setDay("LUNDI");
        createdScheduleDay1.setOpening("09:00");
        createdScheduleDay1.setClosing("18:00");
        
        ScheduleDay createdScheduleDay2 = new ScheduleDay();
        createdScheduleDay2.setId(11);
        createdScheduleDay2.setDay("MARDI");
        createdScheduleDay2.setOpening("10:00");
        createdScheduleDay2.setClosing("19:00");
        
        when(scheduleDayRepository.createScheduleDay(testScheduleDays.get(0))).thenReturn(createdScheduleDay1);
        when(scheduleDayRepository.createScheduleDay(testScheduleDays.get(1))).thenReturn(createdScheduleDay2);
        
        when(scheduleDayRepository.createBarScheduleDayLink(1, 10)).thenReturn(createdScheduleDay1);
        when(scheduleDayRepository.createBarScheduleDayLink(1, 11)).thenReturn(createdScheduleDay2);
        
        // Exécution de la méthode à tester
        Bar result = barRepository.createBar(testBar);
        
        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getPlanning());
        assertEquals(2, result.getPlanning().size());
        assertEquals(10, result.getPlanning().get(0).getId());
        assertEquals(11, result.getPlanning().get(1).getId());
        
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository, times(2)).createScheduleDay(any(ScheduleDay.class));
        verify(scheduleDayRepository).createBarScheduleDayLink(1, 10);
        verify(scheduleDayRepository).createBarScheduleDayLink(1, 11);
    }

    /**
     * Teste la création d'un bar avec erreur lors de l'insertion
     */
    @Test
    void createBar_InsertionError() {
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenThrow(new RuntimeException("DB error"));
        
        assertThrows(RuntimeException.class, () -> barRepository.createBar(testBar));
    }

    /**
     * Teste la création d'un bar avec erreur lors de la création d'un ScheduleDay
     */
    @Test
    void createBar_ScheduleDayCreationError() {
        // Configuration du bar avec planning
        testBar.setPlanning(testScheduleDays);
        
        // Configuration des mocks
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        when(scheduleDayRepository.createScheduleDay(any())).thenThrow(new RuntimeException("ScheduleDay creation error"));
        
        assertThrows(RuntimeException.class, () -> barRepository.createBar(testBar));
    }

    /**
     * Teste la création d'un bar avec planning vide
     */
    @Test
    void createBar_WithEmptyPlanning_Success() {
        // Configuration du bar avec planning vide
        testBar.setPlanning(new ArrayList<>());
        
        // Configuration des mocks
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        // Exécution de la méthode à tester
        Bar result = barRepository.createBar(testBar);
        
        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getPlanning());
        assertTrue(result.getPlanning().isEmpty());
        
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
        verify(scheduleDayRepository, never()).createBarScheduleDayLink(anyInt(), anyInt());
    }

    /**
     * Teste la création d'un bar avec plusieurs jours de planning
     */
    @Test
    void createBar_WithMultipleScheduleDays_Success() {
        // Configuration du bar avec plusieurs jours de planning
        testBar.setPlanning(new ArrayList<>());
        
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
        
        testBar.getPlanning().add(monday);
        testBar.getPlanning().add(tuesday);
        testBar.getPlanning().add(friday);
        
        // Configuration des mocks
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        // Configuration des mocks pour les ScheduleDay
        when(scheduleDayRepository.createScheduleDay(any(ScheduleDay.class))).thenAnswer(invocation -> {
            ScheduleDay sd = invocation.getArgument(0);
            ScheduleDay created = new ScheduleDay();
            created.setId(scheduleDayIdCounter++);
            created.setDay(sd.getDay());
            created.setOpening(sd.getOpening());
            created.setClosing(sd.getClosing());
            return created;
        });
        
        when(scheduleDayRepository.createBarScheduleDayLink(anyInt(), anyInt())).thenAnswer(invocation -> {
            Integer barId = invocation.getArgument(0);
            Integer scheduleDayId = invocation.getArgument(1);
            
            // Simuler la récupération du ScheduleDay après création du lien
            ScheduleDay linkedScheduleDay = new ScheduleDay();
            linkedScheduleDay.setId(scheduleDayId);
            
            // Trouver le jour correspondant dans le planning original
            for (ScheduleDay day : testBar.getPlanning()) {
                if (day.getId() != null && day.getId().equals(scheduleDayId)) {
                    linkedScheduleDay.setDay(day.getDay());
                    linkedScheduleDay.setOpening(day.getOpening());
                    linkedScheduleDay.setClosing(day.getClosing());
                    break;
                }
            }
            
            return linkedScheduleDay;
        });
        
        // Exécution de la méthode à tester
        Bar result = barRepository.createBar(testBar);
        
        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getPlanning());
        assertEquals(3, result.getPlanning().size());
        
        // Vérifier que chaque jour a été créé et lié
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository, times(3)).createScheduleDay(any(ScheduleDay.class));
        verify(scheduleDayRepository, times(3)).createBarScheduleDayLink(eq(1), anyInt());
    }

    /**
     * Teste la création d'un bar avec planning null
     */
    @Test
    void createBar_WithNullPlanning_Success() {
        // Configuration du bar avec planning null
        testBar.setPlanning(null);
        
        // Configuration des mocks
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        // Exécution de la méthode à tester
        Bar result = barRepository.createBar(testBar);
        
        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNull(result.getPlanning());
        
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
        verify(scheduleDayRepository, never()).createBarScheduleDayLink(anyInt(), anyInt());
    }

    /**
     * Teste la création d'un bar avec erreur lors de la création du lien bar-scheduleday
     */
    @Test
    void createBar_BarScheduleDayLinkCreationError() {
        // Configuration du bar avec planning
        testBar.setPlanning(testScheduleDays);
        
        // Configuration des mocks
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);
        
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            kh.getKeyList().add(keyMap);
            return 1;
        });
        
        when(scheduleDayRepository.createScheduleDay(any())).thenAnswer(invocation -> {
            ScheduleDay sd = invocation.getArgument(0);
            ScheduleDay created = new ScheduleDay();
            created.setId(10);
            created.setDay(sd.getDay());
            created.setOpening(sd.getOpening());
            created.setClosing(sd.getClosing());
            return created;
        });
        
        when(scheduleDayRepository.createBarScheduleDayLink(anyInt(), anyInt()))
            .thenThrow(new RuntimeException("Link creation error"));
        
        // Vérification que l'exception est bien propagée
        assertThrows(RuntimeException.class, () -> barRepository.createBar(testBar));
        
        // Vérifier que le bar a été créé mais que l'erreur s'est produite lors de la création du lien
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository).createScheduleDay(any());
        verify(scheduleDayRepository).createBarScheduleDayLink(eq(1), eq(10));
    }

    /**
     * Teste la création d'un bar avec erreur lors de la récupération de l'ID généré
     */
    @Test
    void createBar_NoGeneratedIdError() {
        // Configuration des mocks pour simuler l'absence d'ID généré
        when(barTemplate.update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class))).thenReturn(1);
        
        // Vérification que l'exception est bien propagée
        assertThrows(RuntimeException.class, () -> barRepository.createBar(testBar));
        
        verify(barTemplate).update(eq(BarRepository.SQL_INSERT_BAR), any(MapSqlParameterSource.class), 
                any(KeyHolder.class), any(String[].class));
        verify(scheduleDayRepository, never()).createScheduleDay(any());
        verify(scheduleDayRepository, never()).createBarScheduleDayLink(anyInt(), anyInt());
    }
} 