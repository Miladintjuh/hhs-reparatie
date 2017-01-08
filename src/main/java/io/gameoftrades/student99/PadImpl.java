package io.gameoftrades.student99;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
/**
 *
 * @author Rebano
 */
public class PadImpl implements Pad {

    /**
     * instantiate the class variables
     */
    private Richting[] directions;
    private PadImpl reversed = null;
    private int totalCost;

    /*
    Contructor to set the direction and costs
    */
    public PadImpl(Richting[] directions, int totalCost) {
        this.directions = directions;
        this.totalCost = totalCost;
    }

    /*
    Get the cost of traversing the path
    */    
    @Override
    public int getTotaleTijd() {
        return this.totalCost;
    }
    /*
    Get the current direction
    */
    @Override
    public Richting[] getBewegingen() {
        return this.directions;
    }

    /*  
    Reverse the path that was taken by the ants
    */

    @Override
    public Pad omgekeerd() {
        if(this.reversed != null)
            return this.reversed;
        Richting[] directionsReversed = new Richting[this.directions.length];
        for(int i = 0; i < directions.length; i++)
            directionsReversed[i] = directions[directions.length - i - 1].omgekeerd();
        this.reversed = new PadImpl(directionsReversed, this.totalCost);

        return this.reversed;
    }

    @Override
    public Coordinaat volg(Coordinaat start) {
        for(Richting direction : directions)
            start = start.naar(direction);
        return start;
    }
}