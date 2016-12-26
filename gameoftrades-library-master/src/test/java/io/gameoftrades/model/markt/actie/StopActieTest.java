package io.gameoftrades.model.markt.actie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.kaart.Richting;

public class StopActieTest extends AbstractActieTest {

    @Test
    public void zouMoetenStoppen() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 0, 0, 10);
        assertFalse(positie.isGestopt());
        assertFalse(positie.isKlaar());

        StopActie stopActie = new StopActie();
        assertTrue(stopActie.isMogelijk(positie));
        HandelsPositie result = stopActie.voerUit(positie);

        assertTrue(result.isGestopt());
        assertTrue(result.isKlaar());
    }

    @Test(expected = IllegalArgumentException.class)
    public void zouNietMogelijkMoetenZijnAlsActiesOpZijn() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 0, 0, 0);
        assertFalse(positie.isGestopt());
        assertTrue(positie.isKlaar());

        StopActie stopActie = new StopActie();
        assertFalse(stopActie.isMogelijk(positie));
        stopActie.voerUit(positie);
    }

    @Test
    public void andereActiesMogenNietMogelijkZijnNaStoppen() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 0, 0, 10);
        StopActie stopActie = new StopActie();
        positie = stopActie.voerUit(positie);
        
        assertFalse(new BeweegActie(kaart, stad1, stad2, new MockPad()).isMogelijk(positie));
        assertFalse(new KoopActie(h1).isMogelijk(positie));
        assertFalse(new VerkoopActie(h4).isMogelijk(positie));
        assertFalse(new NavigeerActie(stad1.getCoordinaat(), Richting.NOORD).isMogelijk(positie));
        assertFalse(new StopActie().isMogelijk(positie));
    }
}
