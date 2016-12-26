package io.gameoftrades.model.algoritme;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.HandelsPositie;

public interface HandelsplanAlgoritme {

    /**
     * maakt een handelsplan dat zo veel mogelijk winst maakt in zo min mogelijk tijd (opdracht 4).
     * @param wereld de wereld waarvoor het handelsplan gemaakt moet worden. 
     * @param positie de huidige positie van het handelsplan.
     * @return het plan.
     */
    Handelsplan bereken(Wereld wereld, HandelsPositie positie);

}
