package org.meetmybar.meetmybarapi.business;

import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import java.util.List;

public interface ScheduleDayBusiness {

    /**
     * getScheduleDays
     * Récupère tous les horaires d'ouverture en base de données
     *
     * @return List of ScheduleDay
     */
    public List<ScheduleDay> getScheduleDays();


    /**
     * getScheduleDayById
     * Récupère un horaire d'ouverture en base de données
     *
     * @param id Identifiant du scheduleDay
     * @return ScheduleDay
     */
    public ScheduleDay getScheduleDayById(int id);


    /**
     * deleteScheduleDayById
     * Supprime un horaire d'ouverture en base de données
     *
     * @param id Identifiant du scheduleDay
     * @return le ScheduleDay supprimé
     */
    public ScheduleDay deleteScheduleDayById(int id);

    /**
     * createScheduleDayById
     * Créer un horaire d'ouverture en base de données
     *
     * @param scheduleDay scheduleDay à insérer dans la base de données
     * @return le ScheduleDay créé
     */
    public ScheduleDay createScheduleDay(ScheduleDay scheduleDay);

    /**
     * updateScheduleDayById
     * Modifie un horaire d'ouverture en base de données
     *
     * @param scheduleDay scheduleDay à modifier dans la base de données
     * @return le ScheduleDay modifié
     */
    public ScheduleDay updateScheduleDay(ScheduleDay scheduleDay);
    /**
     * Crée une association entre un bar et un horaire
     * @param barId Identifiant du bar
     * @param scheduleDayId Identifiant de l'horaire
     */
    public ScheduleDay createBarScheduleDayLink(int barId, int scheduleDayId);

    /**
     * Supprime une association entre un bar et un horaire
     * @param barId Identifiant du bar
     * @param scheduleDayId Identifiant de l'horaire
     */
    public ScheduleDay deleteBarScheduleDayLink(int barId, int scheduleDayId);

}