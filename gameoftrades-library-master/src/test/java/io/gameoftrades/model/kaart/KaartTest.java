package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;

public class KaartTest {

    @Test
    public void zouKaartMoetenMaken() {
        assertEquals(5, new Kaart(5, 6).getBreedte());
        assertEquals(6, new Kaart(5, 6).getHoogte());
    }

    @Test
    public void zouKaartMoetenVullen() {
        Kaart kaart = new Kaart(1, 1);
        assertNull(kaart.getTerreinOp(Coordinaat.op(0, 0)));
        Terrein t1 = new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(t1, kaart.getTerreinOp(Coordinaat.op(0, 0)));
    }

    @Test
    public void zouKaartMoetenNietMoetenOverschijven() {
        Kaart kaart = new Kaart(1, 1);
        assertNull(kaart.getTerreinOp(Coordinaat.op(0, 0)));
        Terrein t1 = new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(t1, kaart.getTerreinOp(Coordinaat.op(0, 0)));
        new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(t1, kaart.getTerreinOp(Coordinaat.op(0, 0)));
    }

    @Test
    public void zouOpDeKaartMoetenLiggen() {
        assertTrue(new Kaart(1, 1).isOpKaart(Coordinaat.op(0, 0)));
    }

    @Test
    public void zouNietOpDeKaartMoetenLiggen() {
        assertFalse(new Kaart(1, 1).isOpKaart(Coordinaat.op(1, 1)));
    }

    @Test
    public void zouNaarAanliggendTerreinMoetenKijken() {
        Kaart kaart = new Kaart(2, 1);
        Terrein t1= new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        Terrein t2 = new Terrein(kaart, Coordinaat.op(1, 0), TerreinType.BOS);
        
        assertEquals(TerreinType.BOS, kaart.kijk(t1, Richting.OOST).getTerreinType());
        assertEquals(TerreinType.BERG, kaart.kijk(t2, Richting.WEST).getTerreinType());
    }

}
