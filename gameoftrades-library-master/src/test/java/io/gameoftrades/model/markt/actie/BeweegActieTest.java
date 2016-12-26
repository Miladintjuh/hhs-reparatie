package io.gameoftrades.model.markt.actie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import io.gameoftrades.model.markt.actie.BeweegActie;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import io.gameoftrades.model.markt.actie.NavigeerActie;

public class BeweegActieTest extends AbstractActieTest {

    @Test
    public void zouMoetenKunnenBewegenVanStad1() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 1, 1, 8);
        BeweegActie a = new BeweegActie(kaart, stad1, stad2, new MockPad());
        assertTrue(a.isMogelijk(positie));
    }

    @Test
    public void zouNietMoetenKunnenBewegenVanStad2() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 1, 1, 8);
        BeweegActie a = new BeweegActie(kaart, stad2, stad1, new MockPad());
        assertFalse(a.isMogelijk(positie));
    }

    @Test
    public void zouNietMoetenKunnenBewegenVanStad1ZonderBeweginspunten() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 1, 1, 0);
        BeweegActie a = new BeweegActie(kaart, stad2, stad1, new MockPad());
        assertFalse(a.isMogelijk(positie));
    }

    @Test
    public void zouMoetenBewegenVanStad1NaarStad2() {
        HandelsPositie positie = new HandelsPositie(wereld, stad1, 1, 1, 8);
        BeweegActie a = new BeweegActie(kaart, stad1, stad2, new MockPad());
        HandelsPositie result = a.voerUit(positie);
        
        assertEquals(stad2, result.getStad());
        assertEquals(2, result.getTotaalActie());
    }

    @Test
    public void zouMoetenOmzettenNaarNavigatieActies() {
        BeweegActie a = new BeweegActie(kaart, stad1, stad2, new MockPad());
        List<NavigeerActie> navs = a.naarNavigatieActies();
        assertEquals(2, navs.size());
    }

}
