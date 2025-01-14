package org.meetmybar.meetmybarapi.repository;


import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.inject.Inject;
import java.util.List;
import java.util.HashMap;

@Repository
public class DrinkRepository {

    private static final String SQL_GET_DRINKS =
            "SELECT id, name, brand, degree FROM DRINK";

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
                return drink;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching drinks: " + e.getMessage(), e);
        }
    }

    // TODO: Ajouter les autres méthodes nécessaires
}