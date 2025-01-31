package org.meetmybar.meetmybarapi.controller.impl;

import org.meetmybar.meetmybarapi.business.ScheduleDayBusiness;
import org.meetmybar.meetmybarapi.controller.api.ScheduleDayController;

import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleDayControllerImpl implements ScheduleDayController {

    private final ScheduleDayBusiness scheduleDayBusiness;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleDayControllerImpl.class);

    @Autowired
    public ScheduleDayControllerImpl(ScheduleDayBusiness scheduleDayBusiness) {
        this.scheduleDayBusiness = scheduleDayBusiness;
    }

    @Override
    public ResponseEntity<List<ScheduleDay>> getScheduleday() {
        try {
            List<ScheduleDay> scheduleDays = scheduleDayBusiness.getScheduleDays();
            return ResponseEntity.ok(scheduleDays);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ScheduleDay> getScheduledayScheduledayId(int scheduleDayId) {
        try {
            return ResponseEntity.ok(scheduleDayBusiness.getScheduleDayById(scheduleDayId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ScheduleDay> deleteScheduledayScheduledayId(int scheduleDayId){
        try {
            return ResponseEntity.ok(scheduleDayBusiness.deleteScheduleDayById(scheduleDayId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ScheduleDay> postScheduleday(ScheduleDay scheduleDay){
        try {
            ScheduleDay createdScheduleDay = scheduleDayBusiness.createScheduleDay(scheduleDay);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdScheduleDay);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid ScheduleDay data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error creating ScheduleDay", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ScheduleDay> patchScheduleday(ScheduleDay scheduleDay){
        try {
            ScheduleDay updatedScheduleDay = scheduleDayBusiness.updateScheduleDay(scheduleDay);
            return ResponseEntity.ok(updatedScheduleDay);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid ScheduleDay data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error updating ScheduleDay", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}