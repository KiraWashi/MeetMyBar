package org.meetmybar.meetmybarapi.business.impl;


import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.business.DrinkBusiness;
import org.meetmybar.meetmybarapi.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkBusinessImpl implements DrinkBusiness {

    private final DrinkRepository drinkRepository;

    public DrinkBusinessImpl(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    public List<Drink> getDrinks() {
        return this.drinkRepository.getDrinks();
    }

    @Override
    public Drink getDrinkByName(String drinkName) {
        // TODO: Implement avec le repository
        return null;
    }

    @Override
    public Drink createDrink(Drink drink) {
        // TODO: Implement avec le repository
        return null;
    }

    @Override
    public Drink updateDrink(Drink drink) {
        // TODO: Implement avec le repository
        return null;
    }

    @Override
    public Drink deleteDrink(Drink drink) {
        // TODO: Implement avec le repository
        return null;
    }
}