package org.meetmybar.meetmybarapi.business.impl;


import org.meetmybar.meetmybarapi.business.BarBusiness;
import org.meetmybar.meetmybarapi.models.modif.Bar;
import org.meetmybar.meetmybarapi.repository.BarRepository;
import org.meetmybar.meetmybarapi.exception.BarNotFoundException;
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
        Bar bar = this.barRepository.getBarByName(barName);
        if (bar == null) {
            throw new BarNotFoundException("nom: " + barName);
        }
        return bar;
    }

    @Override
    public Bar getBarByAddress(String barAddress) {
        Bar bar = this.barRepository.getBarByAddress(barAddress);
        if (bar == null) {
            throw new BarNotFoundException("adresse: " + barAddress);
        }
        return bar;
    }

    @Override
    public Bar getBarById(int barId) {
        Bar bar = this.barRepository.getBarById(barId);
        if (bar == null) {
            throw new BarNotFoundException(String.valueOf(barId));
        }
        return bar;
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
        
        // Vérifier si le bar existe avant modification
        try {
            this.barRepository.getBarById(bar.getId());
        } catch (Exception e) {
            throw new BarNotFoundException(bar.getId().toString());
        }
        
        return this.barRepository.modifyBar(bar);
    }

    @Override
    public Bar deleteBar(Integer barId) {
        if (barId == null) {
            throw new IllegalArgumentException("L'ID du bar ne peut pas être null");
        }
        
        // Récupérer le bar avant suppression pour le retourner
        Bar bar = this.barRepository.getBarById(barId);
        if (bar == null) {
            throw new BarNotFoundException(barId.toString());
        }
        
        this.barRepository.deleteBar(barId);
        return bar;
    }
}