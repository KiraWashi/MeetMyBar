package org.meetmybar.meetmybarapi.repository;

import jakarta.inject.Inject;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ScheduleDayRepository {

    private static final String SQL_GET_SCHEDULEDAYS =
            "SELECT id, openning, closing, day FROM SCHEDULE_DAY";

    @Inject
    private NamedParameterJdbcTemplate scheduleDayTemplate;

    public List<ScheduleDay> getScheduleDays() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            return scheduleDayTemplate.query(SQL_GET_SCHEDULEDAYS, map, (r, s) -> {
                var scheduleDay = new ScheduleDay();
                scheduleDay.setId(r.getInt("id"));
                scheduleDay.setOpening(r.getString("openning"));
                scheduleDay.setClosing(r.getString("closing"));
                scheduleDay.setDay(r.getString("day"));
                return scheduleDay;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching schedule days: " + e.getMessage(), e);
        }
    }
}