package org.meetmybar.meetmybarapi.business;



import org.meetmybar.meetmybarapi.models.dto.Bar;

import java.util.List;

public interface BarBusiness {

    /**
     * getBar
     * Récupère tout les bars en base de données
     *
     * @param
     * @return List of Bar
     */
    public List<Bar> getBar();
}
