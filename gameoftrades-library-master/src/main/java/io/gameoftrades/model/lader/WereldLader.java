package io.gameoftrades.model.lader;

import io.gameoftrades.model.Wereld;

/**
 * Verantwoordelijk voor het laden van de wereld vanuit een bestand. 
 */
public interface WereldLader {

    /**
     * laad de wereld van de gegeven classpath resource.
     * Wanneer de gegeven resource niet voldoet aan het formaat moet een <b>java.lang.IllegalArgumentException</b> gegooit worden. 
     * @param resource de resource.
     * @return de wereld.
     */
    Wereld laad(String resource);

}
