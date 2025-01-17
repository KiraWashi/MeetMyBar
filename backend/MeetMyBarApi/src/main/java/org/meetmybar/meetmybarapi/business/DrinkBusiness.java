package org.meetmybar.meetmybarapi.business;


import org.meetmybar.meetmybarapi.models.dto.Drink;
import java.util.List;

public interface DrinkBusiness {
    /**
     * getDrink
     * Récupère toutes les boissons en base de données
     *
     * @return List of Drink
     */
    List<Drink> getDrinks();

    /**
     * getDrinkByName
     * Récupère une boisson par son nom
     *
     * @param drinkName nom de la boisson
     * @return Drink
     */
    Drink getDrinkByName(String drinkName);

    /**
     * getDrinkByName
     * Récupère une boisson par son id
     *
     * @param drinkId id de la boisson
     * @return Drink
     */
    Drink getDrinkById(int drinkId);

    /**
     * createDrink
     * Crée une nouvelle boisson
     *
     * @param drink la boisson à créer
     * @return Drink créée
     */
    Drink createDrink(Drink drink);

    /**
     * updateDrink
     * Met à jour une boisson existante
     *
     * @param drink la boisson à mettre à jour
     * @return Drink mise à jour
     */
    Drink updateDrink(Drink drink);

    /**
     * deleteDrink
     * Supprime une boisson
     *
     * @param drinkId la boisson à supprimer
     * @return Drink supprimée
     */
    Drink deleteDrink(int drinkId);
}