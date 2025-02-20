package org.meetmybar.meetmybarapi.business.impl;


import org.meetmybar.meetmybarapi.business.BarBusiness;
import org.meetmybar.meetmybarapi.models.modif.Bar;
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

    @Override
    public Bar getBarByName(String barName) {
        return this.barRepository.getBarByName(barName);
    }

    @Override
    public Bar getBarByAddress(String barAddress) {
        return this.barRepository.getBarByAddress(barAddress);
    }

    @Override
    public Bar getBarById(int barId) {
        return this.barRepository.getBarById(barId);
    }

    @Override
    public Bar createBar(Bar bar) {
        return this.barRepository.createBar(bar);
    }

    @Override
    public Bar modifyBar(Bar bar) {
        if (bar == null || bar.getId() == null) {
            throw new IllegalArgumentException("Bar and bar ID must not be null");
        }
        return this.barRepository.modifyBar(bar);
    }
}
