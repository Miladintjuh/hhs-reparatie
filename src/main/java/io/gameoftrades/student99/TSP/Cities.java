package io.gameoftrades.student99.TSP;
/**
 *
 * @author Rebano
 */
import io.gameoftrades.model.kaart.Stad;

import java.util.ArrayList;

public class Cities {

    private ArrayList<Stad> cities;

    private int totalPathCost;

    public Cities(ArrayList<Stad> cities, int totalPathCost) {
        this.cities = cities;
        this.totalPathCost = totalPathCost;
    }

    public int getTotalPathCost() {

        return totalPathCost;
    }

    public ArrayList<Stad> getCities() {

        return this.cities;
    }
}
