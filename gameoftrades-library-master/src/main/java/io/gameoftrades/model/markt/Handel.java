package io.gameoftrades.model.markt;

import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.util.Assert;

/**
 * Handel is het aanbod of de vraag van bepaalde Handelswaar in een bepaalde plaats voor een bepaalde prijs. 
 */
public final class Handel {

    private Stad stad;
    private HandelType type;
    private Handelswaar handelswaar;
    private int prijs;

    /**
     * maakt nieuwe handel.
     * @param stad de stad waarin de handel zit.
     * @param type het soort handel (vraag of aanbod).
     * @param handelswaar de handelswaar.
     * @param prijs de prijs per eenheid van de handel.
     */
    public Handel(Stad stad, HandelType type, Handelswaar handelswaar, int prijs) {
        Assert.notNull(stad);
        Assert.notNull(type);
        Assert.notNull(handelswaar);
        Assert.positive(prijs);
        this.stad = stad;
        this.type = type;
        this.handelswaar = handelswaar;
        this.prijs = prijs;
    }

    public Stad getStad() {
        return stad;
    }

    public HandelType getHandelType() {
        return type;
    }

    public Handelswaar getHandelswaar() {
        return handelswaar;
    }

    public int getPrijs() {
        return prijs;
    }

    public String toString() {
        return "Handel(" + stad + "," + type + "," + handelswaar + "," + prijs + ")";
    }
}
