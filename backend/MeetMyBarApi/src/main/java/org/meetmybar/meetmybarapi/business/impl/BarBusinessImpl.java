package org.meetmybar.meetmybarapi.business.impl;


import org.meetmybar.meetmybarapi.business.BarBusiness;
import org.meetmybar.meetmybarapi.models.dto.Bar;
import org.meetmybar.meetmybarapi.repository.BarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarBusinessImpl implements BarBusiness {

    private BarRepository barRepository;

    public BarBusinessImpl(BarRepository barRepository) {
        this.barRepository = barRepository;
    }


    @Override
    public List<Bar> getBar() {
        return this.barRepository.getBar();
    }
}
