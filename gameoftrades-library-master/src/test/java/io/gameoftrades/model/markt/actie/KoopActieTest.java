package io.gameoftrades.model.markt.actie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KoopActieTest extends AbstractActieTest {

    @Test
    public void zouMoetenKunnenKopenOpLocatie() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 10, 10, 8);
        assertTrue(new KoopActie(h1).isMogelijk(positie));
        assertFalse(new KoopActie(h2).isMogelijk(positie));
    }

    @Test
    public void zouNietMoetenKunnenKopen() {
        assertFalse(new KoopActie(h1).isMogelijk(new HandelsPositie(wereld, stad1, 0, 10, 8)));
        assertFalse(new KoopActie(h1).isMogelijk(new HandelsPositie(wereld, stad1, 10, 0, 8)));
        assertFalse(new KoopActie(h1).isMogelijk(new HandelsPositie(wereld, stad1, 10, 10, 0)));
    }

    @Test
    public void kopenKostEenActie() {
        assertTrue(new KoopActie(h1).isMogelijk(new HandelsPositie(wereld, stad1, 10, 10, 1)));
    }

    @Test
    public void zouKoopActieUitMoetenVoeren() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 11, 10, 8);
        HandelsPositie result = new KoopActie(h1).voerUit(positie);

        assertEquals(1, result.getKapitaal());
        assertEquals(5, result.getRuimte());
        assertEquals("{schapen=5}", String.valueOf(result.getVoorraad()));
    }

}
