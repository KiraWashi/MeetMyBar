package org.meetmybar.meetmybarapi.controller.impl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.meetmybar.meetmybarapi.controller.api.DrinkController;


import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.business.DrinkBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DrinkControllerImpl implements DrinkController {

    private final DrinkBusiness drinkBusiness;

    private static final Logger logger = LoggerFactory.getLogger(DrinkControllerImpl.class);


    @Autowired
    public DrinkControllerImpl(DrinkBusiness drinkBusiness) {
        this.drinkBusiness = drinkBusiness;
    }

    @Override
    public ResponseEntity<Drink> deleteDrink(int drinkId) {
        try {
            Drink deletedDrink = drinkBusiness.deleteDrink(drinkId);
            return ResponseEntity.ok(deletedDrink);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
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
    public ResponseEntity<Drink> getDrinkByDrinkName(String drinkName) {
        try {
            Drink drink = drinkBusiness.getDrinkByName(drinkName);
            return ResponseEntity.ok(drink);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> getDrinkByDrinkId(int drinkId) {
        try {
            Drink drink = drinkBusiness.getDrinkById(drinkId);
            if (drink == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(drink);
        } catch (Exception e) {
            logger.error("Error fetching drink with id " + drinkId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> patchDrink(@Valid Drink drink) {
        try {
            Drink updatedDrink = drinkBusiness.updateDrink(drink);
            return ResponseEntity.ok(updatedDrink);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid drink data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error updating drink", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> postDrink(@Valid Drink drink) {
        try {
            Drink createdDrink = drinkBusiness.createDrink(drink);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDrink);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid drink data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error creating drink", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> addDrinkBar(int idBar, int idDrink, double volume, double price) {
        try {
            // Vérification de l'existence de la boisson
            Drink existingDrink = drinkBusiness.getDrinkById(idDrink);
            if (existingDrink == null) {
                logger.warn("Boisson non trouvée avec l'ID: {}", idDrink);
                return ResponseEntity.notFound().build();
            }

            Drink associatedDrink = drinkBusiness.addDrinkToBar(idBar, idDrink, volume, price);
            return ResponseEntity.ok(associatedDrink);
        } catch (IllegalArgumentException e) {
            logger.warn("Données invalides: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            // Vérifier si l'erreur est due à une contrainte de clé étrangère (bar non trouvé)
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint fails")) {
                logger.warn("Bar non trouvé avec l'ID: {}", idBar);
                return ResponseEntity.notFound().build();
            }
            logger.error("Erreur lors de l'association boisson-bar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> updateDrinkBar(int idBar, int idDrink, double volume, double newPrice) {
        try {
            // Vérification de l'existence de la boisson
            Drink existingDrink = drinkBusiness.getDrinkById(idDrink);
            if (existingDrink == null) {
                logger.warn("Boisson non trouvée avec l'ID: {}", idDrink);
                return ResponseEntity.notFound().build();
            }

            Drink updatedDrink = drinkBusiness.updateDrinkBar(idBar, idDrink, volume, newPrice);
            return ResponseEntity.ok(updatedDrink);
        } catch (IllegalArgumentException e) {
            logger.warn("Données invalides: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Association bar-boisson non trouvée")) {
                logger.warn("Association bar-boisson non trouvée: {}", e.getMessage());
                return ResponseEntity.notFound().build();
            }
            logger.error("Erreur lors de la mise à jour du prix", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Drink> deleteDrinkBar(int idBar, int idDrink, double volume) {
        try {
            // Vérification de l'existence de la boisson
            Drink existingDrink = drinkBusiness.getDrinkById(idDrink);
            if (existingDrink == null) {
                logger.warn("Boisson non trouvée avec l'ID: {}", idDrink);
                return ResponseEntity.notFound().build();
            }

            Drink deletedDrink = drinkBusiness.deleteDrinkBar(idBar, idDrink, volume);
            return ResponseEntity.ok(deletedDrink);
        } catch (IllegalArgumentException e) {
            logger.warn("Données invalides: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Association bar-boisson non trouvée")) {
                logger.warn("Association bar-boisson non trouvée: {}", e.getMessage());
                return ResponseEntity.notFound().build();
            }
            logger.error("Erreur lors de la suppression de l'association", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
