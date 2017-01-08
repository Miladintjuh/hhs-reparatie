package io.gameoftrades.student99.antRaceAlgorithm;
/**
 *
 * @author Rebano
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.student49.PadImpl;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;



public class AntRace implements SnelstePadAlgoritme, Debuggable {
   
    private Ant race(Kaart kaart, List<Travel> ants, Integer[][] costs, Coordinaat eind) {
        while (true) {
            List<Travel> nextGen = new ArrayList<>();
            for (Travel travelAnt : ants) {
                travelAnt.next();
                if (travelAnt.isDaar()) {
                    Ant ant = travelAnt.getMier();
                    boolean spawn = ant.atLocation(costs);
                    if (eind.equals(ant.getCoordinate())) {
                        return ant;
                    }
                    if (spawn) {
                        for (Richting r : ant.getTerrain().getMogelijkeRichtingen()) {
                            Coordinaat volgende = ant.getCoordinate().naar(r);
                            if (isEmpty(costs, volgende)) {
                                Ant child = new Ant(ant, kaart.getTerreinOp(volgende));
                                nextGen.add(new Travel(child));
                            }
                        }
                    }
                } else {
                    nextGen.add(travelAnt);
                }
            }
            debug.debugRaster(kaart, costs);
            ants = nextGen;
        }
    }

    private Pad backtrack(Ant prime, Kaart map, Coordinaat start, Integer[][] costs) {
       //instantiate the variables
        List<Richting> path = new ArrayList<Richting>();
        Coordinaat current = prime.getCoordinate();
        Coordinaat previous = null;
        Ant child =  prime;
        
        while (child != null) {
            Ant parent = child.getParent();
            if (parent!=null) {
                previous = parent.getCoordinate();
                path.add(Richting.tussen(previous, current));
            }
            child = parent;
            current = previous;
        } 
        //reverse the path so it can be drawn
        Collections.reverse(path);
        
        Richting[] path2 = new Richting[path.size()];
        path2 = path.toArray(path2);

        PadImpl result = new PadImpl(path2, prime.getCost());
        //send the info to the gui
        debug.debugPad(map, start, result);
        return result;
    }


    
    
    public Pad bereken(Kaart map, Coordinaat start, Coordinaat end) {
        Integer[][] costs = new Integer[map.getHoogte()][map.getBreedte()];
        List<Travel> ants = new ArrayList<>();
        Ant parent = new Ant(map.getTerreinOp(start), costs);
        ants.add(new Travel(parent));
        Ant prime = race(map, ants, costs, end);
        debug.debugRaster(map, costs);
        return backtrack(prime, map, start, costs);
    }
    private boolean isEmpty(Integer[][] costs, Coordinaat next) {
        return costs[next.getY()][next.getX()] == null;
    }

    @Override
    public String toString() {
        return "AntRace";
    }

    private Debugger debug = new DummyDebugger();

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }
}