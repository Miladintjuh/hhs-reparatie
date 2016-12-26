package io.gameoftrades.debug;

import java.util.List;
import java.util.Map;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.HandelsPositie;

public interface Debugger {

    /**
     * Interface om stap voor stap door een Handelsplan te stappen. 
     */
    public interface PlanControl {

        /**
         * toont de volgende stap.
         */
        void nextStep();

        /**
         * @return true wanneer er een volgende stap is.
         */
        boolean hasNextStep();

    }

    /**
     * Toont een raster van integers op de kaart.
     * Mogelijk handig bij opdracht 1. 
     * Als de waarde van een cel op het raster null is wordt niets getoond. 
     * @param kaart de kaart waarop het raster getoond moet worden.
     * @param raster het raster, met de grootte als de kaart.
     */
    void debugRaster(Kaart kaart, Integer[][] raster);

    /**
     * Toont het pad op de kaart.
     * Mogelijk handig bij opdracht 1. 
     * @param kaart de kaart.
     * @param start het startpunt.
     * @param pad het pad dat getoond moet worden.
     */
    void debugPad(Kaart kaart, Coordinaat start, Pad pad);

    /**
     * rendert een kaart met een lijst van coordinaten.
     * Mogelijk handig bij opdracht 1.
     * @param kaart de kaart.
     * @param c de lijst van coordinaten.
     */
    public void debugCoordinaten(Kaart kaart, List<Coordinaat> c);

    /**
     * toont de eerste 2 karakters van de waarden van de map op de coordinaten van zijn key.  
     * Mogelijk handig bij opdracht 1. 
     * @param kaart de kaart.
     * @param map een map van coordinaat, value paren.
     */
    void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> map);

    /**
     * toont de eerste 2 karakters van de waarden van de map op de coordinaten van zijn key.  
     * Mogelijk handig bij opdracht 1. 
     * @param kaart de kaart.
     * @param open een map van coordinaat, value paren (in groen).
     * @param closed een map van coordinaat, value paren (in rood).
     */
    void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> open, Map<Coordinaat, ?> closed);

    /**
     * toont een lijn tussen de gegeven steden.
     * Handig bij opdracht 3.
     * @param kaart de kaart.
     * @param steden de steden.
     */
    void debugSteden(Kaart kaart, List<Stad> steden);

    /**
     * laat de handel zien op de kaart.
     * @param kaart de kaart,
     * @param handel de handel.
     */
    void debugHandel(Kaart kaart, List<Handel> handel);

    /**
     * laat de beweeg acties zien.
     * @param kaart de kaart.
     * @param positie de huidige positie.
     * @param acties de beweeg acties.
     */
    void debugActies(Kaart kaart, HandelsPositie positie, List<Actie> acties);

    /**
     * Speelt een volledig plan af op de kaart.
     * Handig bij opdracht 4. 
     * @param plan het plan.
     * @param initieel de initieele handels positie.
     * @return planControl waarmee stap voor stap het plan gespeeld kan worden.
     */
    PlanControl speelPlanAf(Handelsplan plan, HandelsPositie initieel);

}
