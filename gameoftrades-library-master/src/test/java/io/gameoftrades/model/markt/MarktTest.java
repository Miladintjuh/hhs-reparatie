package io.gameoftrades.model.markt;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

public class MarktTest {

    private Stad stad1;
    private Stad stad2;
    private Handel h1;
    private Handel h2;
    private Handel h3;

    @Before
    public void init() {
        stad1 = new Stad(Coordinaat.op(1, 1), "Stad1");
        stad2 = new Stad(Coordinaat.op(2, 2), "Stad2");
        h1 = new Handel(stad1, HandelType.BIEDT, new Handelswaar("schapen"), 1);
        h2 = new Handel(stad2, HandelType.VRAAGT, new Handelswaar("schapen"), 1);
        h3 = new Handel(stad1, HandelType.VRAAGT, new Handelswaar("stenen"), 1);
    }

    @Test
    public void zouHandelMoetenHebben() {
        Markt markt = new Markt(Arrays.asList(new Handel[] { h1, h2, h3 }));
        assertEquals(3, markt.getHandel().size());
        assertEquals(2, markt.getVraag().size());
        assertEquals(1, markt.getAanbod().size());
    }

}
