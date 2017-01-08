package io.gameoftrades.student99;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.student99.CityTour.CityTour;
import io.gameoftrades.student99.antRaceAlgorithm.AntRace;

/**
 * Welkom bij Game of Trades! 
 * 
 * Voordat er begonnen kan worden moet eerst de 'studentNN' package omgenoemd worden
 * zodat iedere groep zijn eigen namespace heeft. Vervang de NN met je groep nummer.
 * Dus als je in groep 3 zit dan wordt de packagenaam 'student03' en ben je in groep
 * 42 dan wordt de package naam 'student42'.
 * 
 * Om te controleren of je het goed hebt gedaan is er de ProjectSanityTest die je kan draaien.
 * 
 */
public class HandelaarImpl implements Handelaar {

    /**
     * Opdracht 1, zie ook de handige test-set in WereldLaderImplTest.
     * @return 
     */
    @Override
    public WereldLader nieuweWereldLader() {
        return new WereldLaderImpl();
    }

    /**
     * Opdracht 2
     */
    @Override
    public SnelstePadAlgoritme nieuwSnelstePadAlgoritme() {
        // TODO Auto-generated method stub
        return new AntRace();
    }

    /**
     * Opdracht 3
     */
    @Override
    public StedenTourAlgoritme nieuwStedenTourAlgoritme() {
        // 1. Execute the city tour.
        return new CityTour();
    }

    /**
     * Opdracht 4
     */
    @Override
    public HandelsplanAlgoritme nieuwHandelsplanAlgoritme() {
        // TODO Auto-generated method stub
        return null;
    }
}
