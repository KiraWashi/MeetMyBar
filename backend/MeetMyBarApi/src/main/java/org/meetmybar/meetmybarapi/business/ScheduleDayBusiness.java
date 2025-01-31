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
     * @return ScheduleDay
     */
    public ScheduleDay deleteScheduleDayById(int id);
}