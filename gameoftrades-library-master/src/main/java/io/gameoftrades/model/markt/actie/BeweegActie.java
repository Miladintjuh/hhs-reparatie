package io.gameoftrades.model.markt.actie;

import java.util.ArrayList;
import java.util.List;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.util.Assert;

/**
 * Beweegt de handelaar van de ene stad naar de andere door middel van het gegeven Pad.
 * <p>
 * Deze BeweegActie kan worden uitgepakt naar individuele Navigeer acties.
 * </p>
 */
public class BeweegActie implements Actie {

    private Stad van;
    private Stad naar;
    private int bewegingsPunten;
    private Pad pad;
    private Kaart kaart;

    /**
     * maakt een nieuwe beweeg actie.
     * @param kaart de kaart waarop bewogen wordt.
     * @param van de stad waaruit vertrokken wordt.
     * @param naar de stad waarnaartoe gereisd wordt.
     * @param pad het pad dat gevolgd wordt.
     */
    public BeweegActie(Kaart kaart, Stad van, Stad naar, Pad pad) {
        Assert.notNull(kaart);
        Assert.notNull(van);
        Assert.notNull(naar);
        Assert.notNull(pad);
        this.kaart = kaart;
        this.van = van;
        this.naar = naar;
        this.pad = pad;
        this.bewegingsPunten = pad.getTotaleTijd();
    }

    @Override
    public HandelsPositie voerUit(HandelsPositie positie) {
        return HandelsPositie.beweeg(positie, van, naar, bewegingsPunten);
    }

    public List<NavigeerActie> naarNavigatieActies() {
        List<NavigeerActie> nav = new ArrayList<>();
        Coordinaat positie = van.getCoordinaat();
        for (Richting r : pad.getBewegingen()) {
            nav.add(new NavigeerActie(positie, r));
            positie = kaart.kijk(kaart.getTerreinOp(positie), r).getCoordinaat();
        }
        return nav;
    }

    @Override
    public boolean isMogelijk(HandelsPositie positie) {
        return van.equals(positie.getStad()) && positie.isActieBeschikbaar(bewegingsPunten);
    }

    @Override
    public String toString() {
        return "Beweeg(" + van + "->" + naar + " voor " + bewegingsPunten + ")";
    }

}
