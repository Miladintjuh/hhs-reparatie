package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Richting;

public class CoordinaatTest {

    @Test
    public void zouCoordinaatMoetenMaken() {
        assertEquals(1, Coordinaat.op(1, 2).getX());
        assertEquals(2, Coordinaat.op(1, 2).getY());
    }

    @Test
    public void zouCoordinaatGelijkZijn() {
        assertTrue(Coordinaat.op(1, 2).equals(Coordinaat.op(1, 2)));
        assertFalse(Coordinaat.op(2, 1).equals(Coordinaat.op(1, 2)));
        assertFalse(Coordinaat.op(2, 1).equals("pindakaas"));
    }

    @Test
    public void zouCoordinaatInRichtingMoetenBewegen() {
        assertEquals(Coordinaat.op(1, 0), Coordinaat.op(1, 1).naar(Richting.NOORD));
        assertEquals(Coordinaat.op(1, 2), Coordinaat.op(1, 1).naar(Richting.ZUID));
        assertEquals(Coordinaat.op(0, 1), Coordinaat.op(1, 1).naar(Richting.WEST));
        assertEquals(Coordinaat.op(2, 1), Coordinaat.op(1, 1).naar(Richting.OOST));
    }

    @Test
    public void zouAfstandMoetenBepalen() {
        assertEquals(1.0, Coordinaat.op(1, 1).afstandTot(Coordinaat.op(2, 1)), 0.01);
        assertEquals(1.0, Coordinaat.op(1, 1).afstandTot(Coordinaat.op(1, 2)), 0.01);
        assertEquals(1.4142, Coordinaat.op(1, 1).afstandTot(Coordinaat.op(2, 2)), 0.01);
    }

}
