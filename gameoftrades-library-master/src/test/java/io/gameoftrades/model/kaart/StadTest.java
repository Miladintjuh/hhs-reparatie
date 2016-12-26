package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Stad;

public class StadTest {

    @Test
    public void zouStadMoetenMaken() {
        assertEquals("Stad", new Stad(Coordinaat.op(1, 1), "Stad").getNaam());
        assertEquals(Coordinaat.op(1, 1), new Stad(Coordinaat.op(1, 1), "Stad").getCoordinaat());
    }
}
