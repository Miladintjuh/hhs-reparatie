package io.gameoftrades.model.kaart;

/**
 * Een stuk van de af te leggen route.
 * 
 * <b>LET OP: Je moet zelf een implementatie maken van deze klasse!</b>
 */
public interface Pad {

    /**
     * @return de totale tijd (in bewegingspunten) die dit pad kost.
     */
    int getTotaleTijd();

    /**
     * @return de bewegingen waaruit het pad bestaat.
     */
    Richting[] getBewegingen();

    /**
     * @return het pad in omgekeerde richting.
     */
    Pad omgekeerd();

    /**
     * bepaalt op welk coordinaat je uit komt als je het pad volgt.
     * @param start het start coordinaat.
     * @return het eindpunt van het pad.
     */
    Coordinaat volg(Coordinaat start);

}
