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
    List<ScheduleDay> getScheduleDays();
}