package io.gameoftrades.student97;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.lader.WereldLader;

/**
 * Dit is een test resource om de HandelaarScanner te testen. 
 */
public class HandelaarImpl implements Handelaar {

    public HandelaarImpl(String msg) {
    }

    @Override
    public WereldLader nieuweWereldLader() {
        return null;
    }

    @Override
    public SnelstePadAlgoritme nieuwSnelstePadAlgoritme() {
        return null;
    }

    @Override
    public StedenTourAlgoritme nieuwStedenTourAlgoritme() {
        return null;
    }

    @Override
    public HandelsplanAlgoritme nieuwHandelsplanAlgoritme() {
        return null;
    }

}
