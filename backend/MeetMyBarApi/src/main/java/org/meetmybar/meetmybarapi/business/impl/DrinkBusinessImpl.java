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

        return this.drinkRepository.getDrinkByName(drinkName);
    }

    @Override
    public Drink getDrinkById(int drinkId) {
        return this.drinkRepository.getDrinkById(drinkId);
    }

    @Override
    public Drink createDrink(Drink drink) {
        // Validation des données
        if (drink == null) {
            throw new IllegalArgumentException("Drink cannot be null");
        }
        if (drink.getName() == null || drink.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Drink name cannot be null or empty");
        }
        if (drink.getBrand() == null || drink.getBrand().trim().isEmpty()) {
            throw new IllegalArgumentException("Drink brand cannot be null or empty");
        }
        if (drink.getAlcoholDegree() < 0) {
            throw new IllegalArgumentException("Alcohol degree cannot be negative");
        }
        if (drink.getType() == null) {
            drink.setType(Drink.TypeEnum.NON_DEFINI);
        }

        // Vérifier si une boisson avec le même nom existe déjà
        try {
            Drink existingDrink = drinkRepository.getDrinkByName(drink.getName());
            if (existingDrink != null) {
                throw new IllegalArgumentException("A drink with this name already exists");
            }
        } catch (RuntimeException ignored) {
            // Si getDrinkByName lance une exception, cela signifie que la boisson n'existe pas
            // donc on peut continuer
        }

        return drinkRepository.createDrink(drink);
    }

    @Override
    public Drink updateDrink(Drink drink) {
        // Validation des données
        if (drink == null) {
            throw new IllegalArgumentException("Drink cannot be null");
        }
        if (drink.getId() == null) {
            throw new IllegalArgumentException("Drink ID cannot be null for update");
        }
        if (drink.getName() == null || drink.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Drink name cannot be null or empty");
        }
        if (drink.getBrand() == null || drink.getBrand().trim().isEmpty()) {
            throw new IllegalArgumentException("Drink brand cannot be null or empty");
        }
        if (drink.getAlcoholDegree() < 0) {
            throw new IllegalArgumentException("Alcohol degree cannot be negative");
        }
        if (drink.getType() == null) {
            drink.setType(Drink.TypeEnum.NON_DEFINI);
        }

        // Vérifie si une autre boisson avec le même nom existe déjà (sauf si c'est la même boisson)
        try {
            Drink existingDrink = drinkRepository.getDrinkByName(drink.getName());
            if (existingDrink != null && !existingDrink.getId().equals(drink.getId())) {
                throw new IllegalArgumentException("Another drink with this name already exists");
            }
        } catch (RuntimeException ignored) {
            // Si getDrinkByName lance une exception, cela signifie que la boisson n'existe pas
            // donc on peut continuer
        }

        return drinkRepository.updateDrink(drink);
    }



    @Override
    public Drink deleteDrink(Drink drink) {
        if (drink == null || drink.getId() == null) {
            throw new IllegalArgumentException("Drink or drink ID cannot be null");
        }
        return drinkRepository.deleteDrink(drink);
    }
}