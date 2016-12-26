package io.gameoftrades.model.markt.actie;

import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.util.Assert;

/**
 * De VerkoopActie verkoopt de huidige beschikbare voorraad tegen de actuele prijs. 
 */
public class VerkoopActie implements Actie {

    private Handel handel;

    public VerkoopActie(Handel handel) {
        Assert.notNull(handel);
        Assert.equals(HandelType.VRAAGT, handel.getHandelType());
        this.handel = handel;
    }

    @Override
    public HandelsPositie voerUit(HandelsPositie positie) {
        if (!positie.getStad().equals(handel.getStad())) {
            throw new IllegalArgumentException("Handelaar is niet in " + handel.getStad() + " maar in " + positie.getStad());
        }
        int opVoorraad = positie.getVoorraad().get(handel.getHandelswaar());
        int geld = opVoorraad * handel.getPrijs();
        return HandelsPositie.verkoop(positie, opVoorraad, handel.getHandelswaar(), geld);
    }

    @Override
    public boolean isMogelijk(HandelsPositie positie) {
        if (!handel.getStad().equals(positie.getStad())) {
            return false;
        }
        if (!positie.isActieBeschikbaar(1)) {
            return false;
        }
        Integer value = positie.getVoorraad().get(handel.getHandelswaar());
        return value != null && value > 0;
    }

    @Override
    public String toString() {
        return "Verkoop(" + handel + ")";
    }

}
