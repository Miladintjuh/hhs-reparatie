package io.gameoftrades.model.markt.actie;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.util.Assert;

/**
 * De NavigeerActie beweegt de handelaar stapsgewijs over de kaart. 
 */
public class NavigeerActie implements Actie {

    private Coordinaat van;
    private Richting richting;

    public NavigeerActie(Coordinaat van, Richting richting) {
        Assert.notNull(van);
        Assert.notNull(richting);
        this.van = van;
        this.richting = richting;
    }

    @Override
    public HandelsPositie voerUit(HandelsPositie positie) {
        return HandelsPositie.navigeer(positie, van, richting);
    }

    @Override
    public boolean isMogelijk(HandelsPositie positie) {
        return van.equals(positie.getCoordinaat()) && !positie.isKlaar();
    }

    @Override
    public String toString() {
        return "Navigeer(" + van + " naar " + richting + ")";
    }

}
