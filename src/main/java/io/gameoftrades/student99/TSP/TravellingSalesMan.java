/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student99.TSP;

/**
 *
 * @author Rebano
 */

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.student99.CityRoutes;
import io.gameoftrades.student99.antRaceAlgorithm.AntRace;

import java.util.ArrayList;
import java.util.List;

public class TravellingSalesMan implements StedenTourAlgoritme, Debuggable {


    private Debugger debugger;
    private Kaart map;
    private List<Stad> cities;

    private List<Stad> fixedCities;
    private ArrayList<CityRoutes> cityPaths;
    private ArrayList<Cities> cityGroupList;
    private AntRace antrace;

 

    /**
     * Run the algorithm to find the best path.
     */
    public void runAlgorithm() {
        
        // Loop through cities and set a list with routes
        for(int start = 0; start < fixedCities.size(); start++) {
            final ArrayList<Stad> fastestRoute = new ArrayList<>();
            int totalPathCost = 0;

            fastestRoute.add(this.cities.get(start));
            this.cities.remove(start);

            // current city
            int cityNumber = 0;

            // do this untill there are no more cities to loop through
            // --Store the quickest
            // --loop through all cities
            while(!this.cities.isEmpty()) {
                // Store the fastest path
                int quickest = -1;
                int length = 0;

                // Loop through the list of cities
                for(int i = 0; i < cities.size(); i++) {
                    // Get the path length, checks if the path is already learned
                    if(!cityPaths.isEmpty())
                        length = getPathLength(fastestRoute, length, i);

                        // Get executed once, because the cityPaths list is empty at the start
                        // calculates the path-length from a certain city to another one.
                    else
                        length = calculateFastestPath(fastestRoute.get(fastestRoute.size() - 1), cities.get(i));

                    // If the path is faster than the current one, pick this city as the fastest option.
                    if(quickest == -1 || length <= quickest) {
                        quickest = length;
                        cityNumber = i;
                    }
                }

                // Increase the total path cost
                totalPathCost += quickest;

                // Add the city to the fastest route list, and remove it from the current list
                fastestRoute.add(this.cities.get(cityNumber));
                this.cities.remove(cityNumber);
            }

            // Add the fastest route to the city group along with the total path cost
            this.cityGroupList.add(new Cities(fastestRoute, totalPathCost));

            // Reset the city ArrayList.
            this.cities = new ArrayList<>(fixedCities);

            // Show a debug message
            System.out.println(start + " - 'Nearest Neighbour' combinations calculated...");
        }
    }

    /**
     * TODO: Specify method description.
     *
     * @param fastestRoute List of cities that define the fastest route.
     * @param pathLength   CityRoutes length.
     * @param i            City index.
     * @return CityRoutes length.
     */
    private int getPathLength(ArrayList<Stad> fastestRoute, int pathLength, int i) {
        // Define whether we succeed
        boolean success = false;

        // Loop through the list of cityPaths
        for(CityRoutes cityPath : cityPaths) {
            // Check if a cityPath between 2 given cities is in the cityPaths list (from start to end, and end to start), continue the loop if it isn't
            if((!cityPath.getStart().equals(fastestRoute.get(fastestRoute.size() - 1).getCoordinaat()) ||
                !cityPath.getEnd().equals(cities.get(i).getCoordinaat())) &&
                (!cityPath.getEnd().equals(fastestRoute.get(fastestRoute.size() - 1).getCoordinaat()) ||
                    !cityPath.getStart().equals(cities.get(i).getCoordinaat())))
                continue;

            // Redefine the cityPath length
            pathLength = cityPath.getLength();

            // Set the success flag
            success = true;
        }

        // If the path is not in the path list, calculate it and put in in the cityPaths list
        if(!success)
            pathLength = calculateFastestPath(fastestRoute.get(fastestRoute.size() - 1), cities.get(i));

        // Return the path length
        return pathLength;
    }

    /**
     * Calculate the fastest path between two cities.
     *
     * @param first  First city.
     * @param second Second city.
     * @return Cost of fastest path.
     */
    private int calculateFastestPath(Stad first, Stad second) {
        // Calculate the fastest path between the two given cities
        final Pad path = antrace.bereken(this.map, first.getCoordinaat(), second.getCoordinaat());

        // Add the path to the list of cityPaths
        cityPaths.add(new CityRoutes(first, second, path));

        // Return the total time for the calculated path
        return path.getTotaleTijd();
    }

       @Override
    public List<Stad> bereken(Kaart kaart, List<Stad> list) {
        map = kaart;

        // Instantiate and set variables
        cityPaths = new ArrayList<>();
        antrace = new AntRace();
        cities = new ArrayList<>(list);
        fixedCities = new ArrayList<>(cities);
        cityGroupList = new ArrayList<>();

        runAlgorithm();

        // Keep track of the lowest path cost
        int lowestPathCost = -1;
        int index = 0;

        // Loop through the list of city groups, to find the lowest path cost
        for(int i = 0; i < cityGroupList.size(); i++) {
            // Store the best city group
            if(lowestPathCost == -1 || cityGroupList.get(i).getTotalPathCost() < lowestPathCost) {
                lowestPathCost = cityGroupList.get(i).getTotalPathCost();
                index = i;
            }
        }

        // Show a debug message
        System.out.println("The fastest route using the 'Nearest-Neighbour Algorithm' takes " + cityGroupList.get(index).getTotalPathCost() + " moves.");
        System.out.println("Paths learned: " + cityPaths.size() + ".");

        // Visually debug the path
        debugger.debugSteden(map, this.cityGroupList.get(index).getCities());

        // Get the list of cities and return it as fastest path
        return cityGroupList.get(index).getCities();
    }
    
    /**
     * String representation of this class, which defines the algorithm name.
     *
     * @return Algorithm name.
     */
    @Override
    public String toString() {
        return "Nearest Neighbour Algorithm";
    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }
}
