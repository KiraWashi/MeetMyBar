package org.meetmybar.meetmybarapi.controller.impl;

import org.meetmybar.meetmybarapi.business.ScheduleDayBusiness;
import org.meetmybar.meetmybarapi.controller.api.ScheduleDayController;

import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleDayControllerImpl implements ScheduleDayController {

    private final ScheduleDayBusiness scheduleDayBusiness;

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



}