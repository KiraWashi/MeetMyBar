package org.meetmybar.meetmybarapi.repository;

import jakarta.inject.Inject;
import org.meetmybar.meetmybarapi.models.dto.Bar;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class BarRepository {

    private static final String SQL_GET_BAR =
            "SELECT id, name, capacity, address, city, postal_code FROM BAR";

    @Inject
    private NamedParameterJdbcTemplate barTemplate;

    public List<Bar> getBar() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            return barTemplate.query(SQL_GET_BAR, map, (r, s) -> {
                var bar = new Bar();
                bar.setId(r.getInt("id"));
                bar.setName(r.getString("name"));
                bar.setCapacity(r.getInt("capacity"));
                bar.setAddress(r.getString("address"));
                bar.setCity(r.getString("city"));
                bar.setPostalCode(r.getString("postal_code"));

                // Ajout d'une boisson d'exemple
                var drink = new Drink()
                        .alcoholDegree(7.5)
                        .id(1)
                        .name("Duchesse")
                        .brand("Lancelot");
                List<Drink> drinks = new ArrayList<>();
                drinks.add(drink);
                bar.setDrinks(drinks);

                // Ajout des horaires
                List<ScheduleDay> planning = new ArrayList<>();
                planning.add(new ScheduleDay().day("Lundi").id(1).opening("18h").closing("1h"));
                planning.add(new ScheduleDay().day("Mardi").id(2).opening("18h").closing("2h"));
                planning.add(new ScheduleDay().day("Mercredi").id(3).opening("18h").closing("1h"));
                planning.add(new ScheduleDay().day("Jeudi").id(4).opening("15h").closing("5h"));
                planning.add(new ScheduleDay().day("Vendredi").id(5).opening("18h").closing("1h"));
                planning.add(new ScheduleDay().day("Samedi").id(6).opening("18h").closing("1h"));
                planning.add(new ScheduleDay().day("Dimanche").id(7).opening("18h").closing("1h"));
                bar.setPlanning(planning);

                return bar;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bars: " + e.getMessage(), e);
        }
    }
}