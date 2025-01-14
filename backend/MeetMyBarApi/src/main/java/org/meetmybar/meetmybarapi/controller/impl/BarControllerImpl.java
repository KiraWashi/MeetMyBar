package org.meetmybar.meetmybarapi.controller.impl;

import org.meetmybar.meetmybarapi.business.BarBusiness;
import org.meetmybar.meetmybarapi.controller.api.BarController;

import org.meetmybar.meetmybarapi.models.dto.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
public class BarControllerImpl implements BarController {

    private final BarBusiness barBusiness;

    @Autowired
    public BarControllerImpl(BarBusiness barBusiness) {
        this.barBusiness = barBusiness;
    }

    @Override
    public ResponseEntity<Bar> getBarByName(@PathVariable("barName") Object barName) {
        try {
            // TODO: Implement actual bar retrieval logic
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<Bar>> getBar() {
        try {
            List<Bar> bars = this.barBusiness.getBar();
            return ResponseEntity.ok(bars);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Bar> getBarByAddress(Object barAddress) {
        // TODO: Implement get bar by address logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Bar> modifyBar(Bar bar) {
        // TODO: Implement update logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Bar> addBar(Bar bar) {
        // TODO: Implement create logic
        return ResponseEntity.ok().build();
    }
}