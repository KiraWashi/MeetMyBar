package org.meetmybar.meetmybarapi.business.impl;

import org.meetmybar.meetmybarapi.business.ScheduleDayBusiness;
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
}