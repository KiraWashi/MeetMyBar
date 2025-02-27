package org.meetmybar.meetmybarapi.business.impl;

import org.meetmybar.meetmybarapi.business.ScheduleDayBusiness;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.meetmybar.meetmybarapi.repository.ScheduleDayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleDayBusinessImpl implements ScheduleDayBusiness {

    private final ScheduleDayRepository scheduleDayRepository;

    public ScheduleDayBusinessImpl(ScheduleDayRepository scheduleDayRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    public List<ScheduleDay> getScheduleDays() {
        return this.scheduleDayRepository.getScheduleDays();
    }

    @Override
    public ScheduleDay getScheduleDayById(int id) {
        return this.scheduleDayRepository.getScheduleDayById(id);
    }

    @Override
    public ScheduleDay deleteScheduleDayById(int id) {
        return this.scheduleDayRepository.deleteScheduleDayById(id);
    }

    @Override
    public ScheduleDay createScheduleDay(ScheduleDay scheduleDay) {
        // Validation des données
        if (scheduleDay == null) {
            throw new IllegalArgumentException("scheduleDay cannot be null");
        }
        if (scheduleDay.getDay() == null || scheduleDay.getDay().trim().isEmpty()) {
            throw new IllegalArgumentException("ScheduleDay day cannot be null or empty");
        }
        if (scheduleDay.getClosing() == null || scheduleDay.getClosing().trim().isEmpty()) {
            throw new IllegalArgumentException("ScheduleDay closing cannot be null or empty");
        }
        if ((scheduleDay.getOpening() == null || scheduleDay.getOpening().trim().isEmpty())) {
            throw new IllegalArgumentException("ScheduleDay opening cannot be null or empty");
        }

        return scheduleDayRepository.createScheduleDay(scheduleDay);
    }

    @Override
    public ScheduleDay updateScheduleDay(ScheduleDay scheduleDay) {
        // Validation des données
        if (scheduleDay == null) {
            throw new IllegalArgumentException("scheduleDay cannot be null");
        }
        if (scheduleDay.getId() == null) {
            throw new IllegalArgumentException("scheduleDay ID cannot be null for update");
        }
        if (scheduleDay.getDay() == null || scheduleDay.getDay().trim().isEmpty()) {
            throw new IllegalArgumentException("ScheduleDay day cannot be null or empty");
        }
        if (scheduleDay.getOpening() == null || scheduleDay.getOpening().trim().isEmpty()) {
            throw new IllegalArgumentException("ScheduleDay opening cannot be null or empty");
        }
        if (scheduleDay.getClosing() == null || scheduleDay.getClosing().trim().isEmpty()) {
            throw new IllegalArgumentException("ScheduleDay closing cannot be null or empty");
        }

        return scheduleDayRepository.updateScheduleDay(scheduleDay);
    }
    
    @Override
    public ScheduleDay createBarScheduleDayLink(int barId, int scheduleDayId) {
        if (barId <= 0) {
            throw new IllegalArgumentException("Bar ID must be positive");
        }
        if (scheduleDayId <= 0) {
            throw new IllegalArgumentException("Schedule Day ID must be positive");
        }
        return scheduleDayRepository.createBarScheduleDayLink(barId, scheduleDayId);
    }

    @Override
    public ScheduleDay deleteBarScheduleDayLink(int barId, int scheduleDayId) {
        if (barId <= 0) {
            throw new IllegalArgumentException("Bar ID must be positive");
        }
        if (scheduleDayId <= 0) {
            throw new IllegalArgumentException("Schedule Day ID must be positive");
        }
        return scheduleDayRepository.deleteBarScheduleDayLink(barId, scheduleDayId);
    }

}