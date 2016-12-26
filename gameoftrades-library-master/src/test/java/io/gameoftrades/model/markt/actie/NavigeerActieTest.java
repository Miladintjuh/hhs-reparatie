package io.gameoftrades.model.markt.actie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import io.gameoftrades.model.markt.actie.NavigeerActie;

public class NavigeerActieTest extends AbstractActieTest {

    @Test
    public void zouMoetenNavigeren() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 0, 0,10);
        NavigeerActie a = new NavigeerActie(Coordinaat.op(0, 0), Richting.OOST);
        
        assertTrue(a.isMogelijk(positie));

        positie = a.voerUit(positie);
        assertEquals(Coordinaat.op(1, 0), positie.getCoordinaat());
        assertNull(positie.getStad());
        assertEquals(1, positie.getTotaalActie());
    }

    @Test
    public void zouNietMoetenNavigeren() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 0, 0, 10);
        assertFalse(new NavigeerActie(Coordinaat.op(1, 0), Richting.OOST).isMogelijk(positie));
    }

}
