package io.gameoftrades.student99.CityTour;

import io.gameoftrades.model.kaart.Stad;

import java.util.ArrayList;

public class CityGroup {

    private ArrayList<Stad> cities;

    private int totalPathCost;

    public CityGroup(ArrayList<Stad> cities, int totalPathCost) {
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
