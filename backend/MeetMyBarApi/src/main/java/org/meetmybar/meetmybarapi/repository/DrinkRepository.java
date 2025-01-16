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

    private static final String SQL_GET_DRINK_BY_NAME =
            "SELECT id, name, brand, degree FROM DRINK WHERE name = :name";

    private static final String SQL_GET_DRINK_BY_ID =
            "SELECT id, name, brand, degree FROM DRINK WHERE id = :id";

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
                        return drink;
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching drink with id " + drinkId + ": " + e.getMessage(), e);
        }
    }
}