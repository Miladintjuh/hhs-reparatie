package io.gameoftrades.model.markt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;

public class HandelTest {

    private Stad stad;
    @Before
    public void init() {
        stad = new Stad(Coordinaat.op(1, 1), "Stad");
    }

    @Test
    public void zouHandelMoetenMaken() {
        Handel handel = new Handel(stad, HandelType.BIEDT, new Handelswaar("schapen"), 1);
        assertEquals(stad, handel.getStad());
        assertEquals(HandelType.BIEDT, handel.getHandelType());
        assertEquals("schapen", handel.getHandelswaar().getNaam());
        assertEquals(1, handel.getPrijs());
    }

}
