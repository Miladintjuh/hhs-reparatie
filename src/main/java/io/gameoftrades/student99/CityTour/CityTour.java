package io.gameoftrades.student99.CityTour;

import io.gameoftrades.student99.antRaceAlgorithm.*;
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
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.*;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.student99.WereldLaderImpl;
import java.lang.reflect.Array;
/**
 * Zie http://blaeul.de/s.php?l=de&d=antraces 
 */
public class CityTour implements StedenTourAlgoritme, Debuggable {

   
    @Override
    public ArrayList<Stad> bereken(Kaart kaart, List<Stad> cities) {
        Integer[][] tijden = new Integer[kaart.getHoogte()][kaart.getBreedte()];
        List<Travel> mieren = new ArrayList<>();
        Ant ouder = new Ant(kaart.getTerreinOp(start), tijden);
        mieren.add(new Travel(ouder));
        Ant winnaar = race(kaart, mieren, tijden, eind);
        debug.debugRaster(kaart, tijden);
        return backtrack(winnaar, kaart, );
    }
    
    private Pad backtrack(Ant winnaar, Kaart kaart, Coordinaat start, Integer[][] tijden) {
        List<Richting> pad = new ArrayList<Richting>();
        Coordinaat current = winnaar.getCoordinaat();
        Coordinaat vorig = null;
        Ant kind =  winnaar;
        do {
            Ant ouder = kind.getParent();
            if (ouder!=null) {
                vorig = ouder.getCoordinaat();
                pad.add(Richting.tussen(vorig, current));
            }
            kind = ouder;
            current = vorig;
        } while (kind != null);
        Collections.reverse(pad);
        
        Richting[] path2 = new Richting[pad.size()];
        path2 = pad.toArray(path2);

        PadImpl result = new PadImpl(path2, winnaar.getCost());
        debug.debugPad(kaart, start, result);
        return result;
    }

    private Ant race(Kaart kaart, List<Travel> mieren, Integer[][] tijden, Coordinaat eind) {
        while (true) {
            List<Travel> volgendeGeneratie = new ArrayList<>();
            for (Travel reizendeMier : mieren) {
                reizendeMier.next();
                if (reizendeMier.isDaar()) {
                    Ant mier = reizendeMier.getMier();
                    boolean spawn = mier.setGearriveerd(tijden);
                    if (eind.equals(mier.getCoordinaat())) {
                        return mier;
                    }
                    if (spawn) {
                        for (Richting r : mier.getTerrein().getMogelijkeRichtingen()) {
                            Coordinaat volgende = mier.getCoordinaat().naar(r);
                            if (isLeeg(tijden, volgende)) {
                                Ant kind = new Ant(mier, kaart.getTerreinOp(volgende));
                                volgendeGeneratie.add(new Travel(kind));
                            }
                        }
                    }
                } else {
                    volgendeGeneratie.add(reizendeMier);
                }
            }
            debug.debugRaster(kaart, tijden);
            mieren = volgendeGeneratie;
        }
    }

    private boolean isLeeg(Integer[][] tijden, Coordinaat volgende) {
        return tijden[volgende.getY()][volgende.getX()] == null;
    }

    public void dumpTijden(Integer[][] tijden) {
        for (int y = 0; y < tijden.length; y++) {
            for (int x = 0; x < tijden[0].length; x++) {
                if (tijden[y][x] == null) {
                    System.out.print("--");
                } else {
                    if (tijden[y][x] < 10) {
                        System.out.print(".");
                    }
                    System.out.print(tijden[y][x]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
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