package org.meetmybar.meetmybarapi.controller.impl;

import org.meetmybar.meetmybarapi.controller.api.DrinkController;


import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.business.DrinkBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DrinkControllerImpl implements DrinkController {

    private final DrinkBusiness drinkBusiness;

    @Autowired
    public DrinkControllerImpl(DrinkBusiness drinkBusiness) {
        this.drinkBusiness = drinkBusiness;
    }

    @Override
    public ResponseEntity<Drink> deleteDrink(Drink drink) {
        try {
            Drink deletedDrink = drinkBusiness.deleteDrink(drink);
            return ResponseEntity.ok(deletedDrink);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<Drink>> getDrink() {
        try {
            List<Drink> drinks = drinkBusiness.getDrinks();
            return ResponseEntity.ok(drinks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> getDrinkDrinkName(Object drinkName) {
        try {
            Drink drink = drinkBusiness.getDrinkByName(drinkName.toString());
            return ResponseEntity.ok(drink);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> patchDrink(Drink drink) {
        try {
            Drink updatedDrink = drinkBusiness.updateDrink(drink);
            return ResponseEntity.ok(updatedDrink);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> postDrink(Drink drink) {
        try {
            Drink createdDrink = drinkBusiness.createDrink(drink);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDrink);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
