package io.gameoftrades.model.markt.actie;

/**
 * Een handels actie van de handelaar, zoals kopen, verkopen en navigeren. 
 */
public interface Actie {

    /**
     * voert de handeling uit op de positie en geeft de nieuwe positie terug.
     * @param positie de positie.
     * @return de nieuwe positie nadat de handeling is uitgevoerd.
     */
    HandelsPositie voerUit(HandelsPositie positie);

    /**
     * geeft aan of deze actie mogelijk is met deze positie.
     * @param positie de handels postie.
     * @return true als de actie mogelijk is.
     */
    boolean isMogelijk(HandelsPositie positie);
}
