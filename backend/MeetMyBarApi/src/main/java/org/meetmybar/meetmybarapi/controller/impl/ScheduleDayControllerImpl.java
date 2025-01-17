package org.meetmybar.meetmybarapi.controller.impl;

import org.meetmybar.meetmybarapi.business.ScheduleDayBusiness;
import org.meetmybar.meetmybarapi.controller.api.ScheduleDayController;
import org.meetmybar.api.model.ScheduleDay;
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
            List<org.meetmybar.meetmybarapi.models.dto.ScheduleDay> scheduleDays = scheduleDayBusiness.getScheduleDays();
            return ResponseEntity.ok(scheduleDays.stream()
                    .map(this::convertToApiModel)
                    .toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ScheduleDay convertToApiModel(org.meetmybar.meetmybarapi.models.dto.ScheduleDay dto) {
        ScheduleDay apiModel = new ScheduleDay();
        apiModel.setId(dto.getId());
        apiModel.setOpening(dto.getOpening());
        apiModel.setClosing(dto.getClosing());
        apiModel.setDay(dto.getDay());
        return apiModel;
    }
}