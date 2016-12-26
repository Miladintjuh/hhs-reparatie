package io.gameoftrades.model.markt.actie;

/**
 * Eindigt het plan wanneer nog niet alle actie punten op zijn.
 * Eerder stoppen is soms zinvol bijvoorbeeld als er binnen het 
 * beschikbaar aantal punten geen winst meer gemaakt kan worden. 
 */
public class StopActie implements Actie {

    @Override
    public HandelsPositie voerUit(HandelsPositie positie) {
        if (positie.isKlaar()) {
            throw new IllegalArgumentException("Einde al bereikt.");
        }
        return HandelsPositie.stop(positie);
    }

    @Override
    public boolean isMogelijk(HandelsPositie positie) {
        return !positie.isGestopt() && positie.getTotaalActie() < positie.getMaxActie();
    }

    @Override
    public String toString() {
        return "StopActie()";
    }

}
