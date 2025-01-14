package org.meetmybar.meetmybarapi.buisness.impl;


import org.meetmybar.meetmybarapi.buisness.BarBuissness;
import org.meetmybar.meetmybarapi.models.dto.Bar;
import org.meetmybar.meetmybarapi.repository.BarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarBuissnessImpl implements BarBuissness {

    private BarRepository barRepository;

    public BarBuissnessImpl(BarRepository barRepository) {
        this.barRepository = barRepository;
    }


    @Override
    public List<Bar> getBar() {
        return this.barRepository.getBar();
    }
}
