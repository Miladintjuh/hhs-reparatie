package io.gameoftrades.model.algoritme;

import java.util.List;

import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;

public interface StedenTourAlgoritme {

    /**
     * bepaalt de beste volgorde van reizen tussen de steden zodat dit het minste tijd kost en
     * iedere stad slechts 1 keer wordt aangedaan (opdracht 3).
     * @param kaart de kaart waarop de steden liggen. 
     * @param steden de steden die aangedaan moeten worden.
     * @return een lijst op volgorde waarin de steden aangedaan moeten worden.
     */
    List<Stad> bereken(Kaart kaart, List<Stad> steden);

}
