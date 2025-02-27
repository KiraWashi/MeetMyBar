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

    /**
     * Associe une boisson à un bar avec son volume et son prix
     * @param idBar identifiant du bar
     * @param idDrink identifiant de la boisson
     * @param volume volume de la boisson
     * @param price prix de la boisson
     * @return la boisson associée
     */
    Drink addDrinkToBar(int idBar, int idDrink, double volume, double price);

    /**
     * Met à jour le prix d'une boisson dans un bar
     * @param idBar identifiant du bar
     * @param idDrink identifiant de la boisson
     * @param volume volume de la boisson
     * @param newPrice nouveau prix de la boisson
     * @return la boisson mise à jour
     */
    Drink updateDrinkBar(int idBar, int idDrink, double volume, double newPrice);

    /**
     * Supprime l'association entre une boisson et un bar
     * @param idBar identifiant du bar
     * @param idDrink identifiant de la boisson
     * @param volume volume de la boisson
     * @return la boisson supprimée de l'association
     */
    Drink deleteDrinkBar(int idBar, int idDrink, double volume);
}