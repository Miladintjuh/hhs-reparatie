package io.gameoftrades.model.markt.actie;

import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.util.Assert;

/**
 * De KoopActie koopt zoveel Handel als dat er ruimte en geld is. 
 */
public class KoopActie implements Actie {

    private Handel handel;

    public KoopActie(Handel handel) {
        Assert.notNull(handel);
        Assert.equals(HandelType.BIEDT, handel.getHandelType());
        this.handel = handel;
    }

    @Override
    public HandelsPositie voerUit(HandelsPositie positie) {
        if (!positie.getStad().equals(handel.getStad())) {
            throw new IllegalArgumentException("Handelaar is niet in " + handel.getStad() + " maar in " + positie.getStad());
        }
        int maxKoop = positie.getKapitaal() / handel.getPrijs();
        int teKopen = Math.min(positie.getRuimte(), maxKoop);
        int geld = teKopen * handel.getPrijs();
        return HandelsPositie.koop(positie, teKopen, handel.getHandelswaar(), geld);
    }

    @Override
    public boolean isMogelijk(HandelsPositie positie) {
        return handel.getStad().equals(positie.getStad()) &&
                positie.getRuimte() > 0 && positie.getKapitaal() >= handel.getPrijs()
                && positie.isActieBeschikbaar(1);
    }

    @Override
    public String toString() {
        return "Koop(" + handel + ")";
    }

}
