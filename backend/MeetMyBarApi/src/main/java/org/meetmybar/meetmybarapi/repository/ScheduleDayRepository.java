package org.meetmybar.meetmybarapi.repository;

import jakarta.inject.Inject;

import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ScheduleDayRepository {

    private static final String SQL_GET_SCHEDULEDAYS =
            "SELECT id, opening, closing, day FROM SCHEDULE_DAY";

    private static final String SQL_GET_SCHEDULEDAY_BY_ID =
            "SELECT id, opening, closing, day FROM SCHEDULE_DAY WHERE id = :id";

    private static final String SQL_DELETE_SCHEDULEDAY =
            "DELETE FROM SCHEDULE_DAY WHERE id = :id";

    private static final String SQL_INSERT_SCHEDULEDAY =
            "INSERT INTO SCHEDULE_DAY (day, closing, opening) VALUES (:day, :closing, :opening)";

    private static final String SQL_UPDATE_SCHEDULEDAY =
            "UPDATE SCHEDULE_DAY SET day = :day, opening = :opening, closing = :closing WHERE id = :id";


    @Inject
    private NamedParameterJdbcTemplate scheduleDayTemplate;

    public List<ScheduleDay> getScheduleDays() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            return scheduleDayTemplate.query(SQL_GET_SCHEDULEDAYS, map, (r, s) -> {
                var scheduleDay = new ScheduleDay();
                scheduleDay.setId(r.getInt("id"));
                scheduleDay.setOpening(r.getString("opening"));
                scheduleDay.setClosing(r.getString("closing"));
                scheduleDay.setDay(r.getString("day"));
                return scheduleDay;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching schedule days: " + e.getMessage(), e);
        }
    }

    public ScheduleDay getScheduleDayById(int id){
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);

            return scheduleDayTemplate.queryForObject(
                    SQL_GET_SCHEDULEDAY_BY_ID,
                    params,
                    (r, rowNum) -> {
                        var scheduleDay = new ScheduleDay();
                        scheduleDay.setId(r.getInt("id"));
                        scheduleDay.setOpening(r.getString("opening"));
                        scheduleDay.setClosing(r.getString("closing"));
                        scheduleDay.setDay(r.getString("day"));
                        return scheduleDay;
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching scheduleday with id " + id + ": " + e.getMessage(), e);
        }
    }

    public ScheduleDay deleteScheduleDayById(int id){
        try {
            // On récupère d'abord l'horraire pour pouvoir la retourner après suppression
            ScheduleDay existingScheduleDay = getScheduleDayById(id);

            if (existingScheduleDay == null) {
                throw new RuntimeException("ScheduleDay not found with id: " + id);
            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);

            int rowsAffected = scheduleDayTemplate.update(SQL_DELETE_SCHEDULEDAY, params);

            if (rowsAffected > 0) {
                return existingScheduleDay;
            } else {
                throw new RuntimeException("Failed to delete ScheduleDay with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting ScheduleDay: " + e.getMessage(), e);
        }
    }

    public ScheduleDay createScheduleDay(ScheduleDay scheduleDay) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("day", scheduleDay.getDay());
            params.put("closing", scheduleDay.getClosing());
            params.put("opening", scheduleDay.getOpening());


            KeyHolder keyHolder = new GeneratedKeyHolder();
            scheduleDayTemplate.update(SQL_INSERT_SCHEDULEDAY, new MapSqlParameterSource(params), keyHolder, new String[]{"id"});

            Number newId = keyHolder.getKey();
            if (newId != null) {
                scheduleDay.setId(newId.intValue());
                return scheduleDay;
            } else {
                throw new RuntimeException("Failed to create scheduleDay - no ID returned");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating scheduleDay: " + e.getMessage(), e);
        }
    }

    public ScheduleDay updateScheduleDay(ScheduleDay scheduleDay) {
        try {
            // Vérifie d'abord si la boisson existe
            ScheduleDay existingDrink = getScheduleDayById(scheduleDay.getId());
            if (existingDrink == null) {
                throw new RuntimeException("ScheduleDay not found with id: " + scheduleDay.getId());
            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", scheduleDay.getId());
            params.put("day", scheduleDay.getDay());
            params.put("opening", scheduleDay.getOpening());
            params.put("closing", scheduleDay.getClosing());


            int rowsAffected = scheduleDayTemplate.update(SQL_UPDATE_SCHEDULEDAY, params);

            if (rowsAffected > 0) {
                return scheduleDay;
            } else {
                throw new RuntimeException("Failed to update scheduleDay with id: " + scheduleDay.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating ScheduleDay: " + e.getMessage(), e);
        }
    }

}