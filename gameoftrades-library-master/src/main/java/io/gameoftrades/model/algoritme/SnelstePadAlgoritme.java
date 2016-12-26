package io.gameoftrades.model.algoritme;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;

public interface SnelstePadAlgoritme {

    /**
     * bepaalt het snelste pad tussen twee coordinaten (opdracht 2).
     * @param kaart de kaart met het terrein dat de snelheid van reizen bepaalt.
     * @param start het coordinaat waarvan vertrokken wordt.
     * @param eind het coordinaat waar naar toe gereist wordt. 
     * @return het snelste pad tussen de twee coordinaten (dat is niet altijd het kortste pad!)
     */
    Pad bereken(Kaart kaart, Coordinaat start, Coordinaat eind);

}
