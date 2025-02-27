package org.meetmybar.meetmybarapi.repository;


import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.meetmybar.meetmybarapi.models.dto.Drink.TypeEnum.NON_DEFINI;

@Repository
public class DrinkRepository {

    private static final String SQL_GET_DRINKS =
            "SELECT id, name, brand, degree, type FROM DRINK";

    private static final String SQL_GET_DRINK_BY_NAME =
            "SELECT id, name, brand, degree, type FROM DRINK WHERE name = :name";

    private static final String SQL_GET_DRINK_BY_ID =
            "SELECT id, name, brand, degree, type FROM DRINK WHERE id = :id";

    private static final String SQL_DELETE_DRINK =
            "DELETE FROM DRINK WHERE id = :id";

    private static final String SQL_INSERT_DRINK =
            "INSERT INTO DRINK (name, brand, degree, type) VALUES (:name, :brand, :degree, :type)";

    private static final String SQL_UPDATE_DRINK =
            "UPDATE DRINK SET name = :name, brand = :brand, degree = :degree, type = :type WHERE id = :id";

    // SQL pour la table LINK_BAR_DRINK

    private static final String SQL_INSERT_DRINK_BAR = 
        "INSERT INTO LINK_BAR_DRINK (id_bar, id_drink, volume, price) VALUES (:idBar, :idDrink, :volume, :price)";

    private static final String SQL_UPDATE_DRINK_BAR_PRICE = 
    "UPDATE LINK_BAR_DRINK SET price = :price WHERE id_bar = :idBar AND id_drink = :idDrink AND volume = :volume";

    private static final String SQL_DELETE_DRINK_BAR = 
        "DELETE FROM LINK_BAR_DRINK WHERE id_bar = :idBar AND id_drink = :idDrink AND volume = :volume";

    private static final String SQL_DELETE_DRINK_LINKS = 
        "DELETE FROM LINK_BAR_DRINK WHERE id_drink = :id";

    @Inject
    private NamedParameterJdbcTemplate drinkTemplate;

    public List<Drink> getDrinks() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            return drinkTemplate.query(SQL_GET_DRINKS, map, (r, s) -> {
                var drink = new Drink();
                drink.setId(r.getInt("id"));
                drink.setName(r.getString("name"));
                drink.setBrand(r.getString("brand"));
                drink.setAlcoholDegree(r.getDouble("degree"));
                drink.setType(Drink.TypeEnum.fromValue(r.getString("type")));
                return drink;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching drinks: " + e.getMessage(), e);
        }
    }

    public Drink getDrinkByName(String drinkName){
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("name", drinkName);

            return drinkTemplate.queryForObject(
                    SQL_GET_DRINK_BY_NAME,
                    params,
                    (rs, rowNum) -> {
                        Drink drink = new Drink();
                        drink.setId(rs.getInt("id"));
                        drink.setName(rs.getString("name"));
                        drink.setBrand(rs.getString("brand"));
                        drink.setAlcoholDegree(rs.getDouble("degree"));
                        drink.setType(Drink.TypeEnum.fromValue(rs.getString("type")));
                        return drink;
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching drink with name " + drinkName + ": " + e.getMessage(), e);
        }
    }

    public Drink getDrinkById(int drinkId) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", drinkId);

            return drinkTemplate.queryForObject(
                    SQL_GET_DRINK_BY_ID,
                    params,
                    (rs, rowNum) -> {
                        Drink drink = new Drink();
                        drink.setId(rs.getInt("id"));
                        drink.setName(rs.getString("name"));
                        drink.setBrand(rs.getString("brand"));
                        drink.setAlcoholDegree(rs.getDouble("degree"));
                        drink.setType(Drink.TypeEnum.fromValue(rs.getString("type")));
                        return drink;
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching drink with id " + drinkId + ": " + e.getMessage(), e);
        }
    }

    public Drink deleteDrink(int drinkId) {
        try {
            // On récupère d'abord la boisson pour pouvoir la retourner après suppression
            Drink existingDrink = getDrinkById(drinkId);

            if (existingDrink == null) {
                throw new RuntimeException("Drink not found with id: " + drinkId);
            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", drinkId);

            // Supprimer d'abord les liens dans la table de liaison
            drinkTemplate.update(SQL_DELETE_DRINK_LINKS, params);

            // Ensuite supprimer la boisson
            int rowsAffected = drinkTemplate.update(SQL_DELETE_DRINK, params);

            if (rowsAffected > 0) {
                return existingDrink;
            } else {
                throw new RuntimeException("Failed to delete drink with id: " + drinkId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting drink: " + e.getMessage(), e);
        }
    }

    public Drink createDrink(Drink drink) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("name", drink.getName());
            params.put("brand", drink.getBrand());
            params.put("degree", drink.getAlcoholDegree());
            params.put("type", drink.getType().getValue());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            drinkTemplate.update(SQL_INSERT_DRINK, new MapSqlParameterSource(params), keyHolder, new String[]{"id"});

            Number newId = keyHolder.getKey();
            if (newId != null) {
                drink.setId(newId.intValue());
                return drink;
            } else {
                throw new RuntimeException("Failed to create drink - no ID returned");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating drink: " + e.getMessage(), e);
        }
    }

    public Drink updateDrink(Drink drink) {
        try {
            // Vérifie d'abord si la boisson existe
            Drink existingDrink = getDrinkById(drink.getId());
            if (existingDrink == null) {
                throw new RuntimeException("Drink not found with id: " + drink.getId());
            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", drink.getId());
            params.put("name", drink.getName());
            params.put("brand", drink.getBrand());
            params.put("degree", drink.getAlcoholDegree());
            params.put("type", drink.getType().getValue());

            int rowsAffected = drinkTemplate.update(SQL_UPDATE_DRINK, params);

            if (rowsAffected > 0) {
                return drink;
            } else {
                throw new RuntimeException("Failed to update drink with id: " + drink.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating drink: " + e.getMessage(), e);
        }
    }

    public void addDrinkToBar(int idBar, int idDrink, double volume, double price) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("idBar", idBar);
            params.put("idDrink", idDrink);
            params.put("volume", volume);
            params.put("price", price);

            int rowsAffected = drinkTemplate.update(SQL_INSERT_DRINK_BAR, params);
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Échec de l'association boisson-bar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'association boisson-bar: " + e.getMessage(), e);
        }
    }
    
    public void updateDrinkBar(int idBar, int idDrink, double volume, double newPrice) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idBar", idBar);
            params.put("idDrink", idDrink);
            params.put("volume", volume);
            params.put("price", newPrice);

            int rowsAffected = drinkTemplate.update(SQL_UPDATE_DRINK_BAR_PRICE, params);
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Association bar-boisson non trouvée");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du prix: " + e.getMessage(), e);
        }
    }

    public void deleteDrinkBar(int idBar, int idDrink, double volume) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idBar", idBar);
            params.put("idDrink", idDrink);
            params.put("volume", volume);

            int rowsAffected = drinkTemplate.update(SQL_DELETE_DRINK_BAR, params);
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Association bar-boisson non trouvée");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'association: " + e.getMessage(), e);
        }
    }

}