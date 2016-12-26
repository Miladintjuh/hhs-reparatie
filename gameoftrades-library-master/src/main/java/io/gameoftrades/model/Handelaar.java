package io.gameoftrades.model;

import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.lader.WereldLader;

/**
 * De Handelaar probeert zo snel mogelijk zo veel mogelijk geld te verdienen.
 * <p>
 * Om dit te kunnen doen moet hij in staat zijn om:
 * </p>
 * <ul>
 * <li>het snelste pad tussen twee steden te kunnen vinden (opdracht 2)</li>
 * <li>een route te bepalen waarbij hij, zo snel mogelijk, iedere stad slechts 1 keer aandoet (opdracht 3)</li>
 * <li>een optimaal handelsplan samen te stellen (opdracht 4)</li>
 * </ul>
 * <p>Echter voordat het zo ver is moet eerst de handelswereld geladen kunnen worden via de Loader (opdracht 1).</p> 
 */
public interface Handelaar {

    /**
     * Maakt een nieuwe instantie van een WereldLader.
     * @return de lader om handels werelden in te laden (opdracht 1).
     */
    WereldLader nieuweWereldLader();

    /**
     * Maakt een nieuwe instantie van het snelste pad algoritme.
     * @return het algoritme om het snelste pad tussen 2 steden te bepalen (opdracht 2).
     */
    SnelstePadAlgoritme nieuwSnelstePadAlgoritme();

    /**
     * Maakt een nieuwe instantie van het steden tour algoritme.
     * @return het algoritme om een zo optimaal mogelijke steden tour te bepalen (opdracht 3).
     */
    StedenTourAlgoritme nieuwStedenTourAlgoritme();

    /**
     * Maakt een nieuwe instantie van het handelsplan algoritme.
     * @return het algoritme om zo veel mogelijk geld te verdienen in zo min mogelijk tijd (opdracht 4).
     */
    HandelsplanAlgoritme nieuwHandelsplanAlgoritme();

}
