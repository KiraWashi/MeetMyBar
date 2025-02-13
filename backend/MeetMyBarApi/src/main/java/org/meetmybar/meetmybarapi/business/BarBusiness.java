package org.meetmybar.meetmybarapi.business;



import org.meetmybar.meetmybarapi.models.modif.Bar;

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

    /**
     * getBarByName
     * Récupère un bar avec son nom en base de données
     *
     * @param barName
     * @return Bar
     */
    public Bar getBarByName(String barName);



    /**
     * getBarByName
     * Récupère un bar avec son adresse en base de données
     *
     * @param barAddress
     * @return Bar
     */
    public Bar getBarByAddress(String barAddress);

    /**
     * getBarByName
     * Récupère un bar avec son id en base de données
     *
     * @param barId
     * @return Bar
     */
    public Bar getBarById(int barId);

    /**
     * createBar
     * Ajoute un bar
     *
     * @param bar
     * @return Bar
     */
    public Bar createBar(Bar bar);
}
