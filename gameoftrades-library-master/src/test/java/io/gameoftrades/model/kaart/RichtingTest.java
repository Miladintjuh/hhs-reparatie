package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Richting;

public class RichtingTest {

    @Test
    public void zouMoetenOmkeren() {
        assertEquals(Richting.NOORD, Richting.ZUID.omgekeerd());
        assertEquals(Richting.ZUID, Richting.NOORD.omgekeerd());
        assertEquals(Richting.WEST, Richting.OOST.omgekeerd());
        assertEquals(Richting.OOST, Richting.WEST.omgekeerd());
    }

    @Test
    public void zouRichtingMoetenBepalen() {
        assertEquals(Richting.NOORD, Richting.tussen(Coordinaat.op(1, 1), Coordinaat.op(1, 0)));
        assertEquals(Richting.ZUID, Richting.tussen(Coordinaat.op(1, 1), Coordinaat.op(1, 2)));
        assertEquals(Richting.OOST, Richting.tussen(Coordinaat.op(1, 1), Coordinaat.op(2, 1)));
        assertEquals(Richting.WEST, Richting.tussen(Coordinaat.op(1, 1), Coordinaat.op(0, 1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void zouGeenRichtingKunnenBepalenWanneerDiagonaal() {
        Richting.tussen(Coordinaat.op(1, 1), Coordinaat.op(2, 2));
    }

}
