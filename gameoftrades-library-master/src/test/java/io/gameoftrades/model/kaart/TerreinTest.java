package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;

public class TerreinTest {

    @Test
    public void zouTerreinMoetenMaken() {
        Kaart kaart = new Kaart(1, 1);
        Terrein t = new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(TerreinType.BERG, t.getTerreinType());
        assertEquals(Coordinaat.op(0, 0), t.getCoordinaat());
        assertEquals(kaart.getTerreinOp(Coordinaat.op(0, 0)), t);
    }

    @Test
    public void zouRichtingenMoetenBepalen() {
        Kaart kaart = new Kaart(3, 3);
        Terrein t = new Terrein(kaart, Coordinaat.op(1, 1), TerreinType.BERG);
        for (Richting r:Richting.values()) {
            new Terrein(kaart, Coordinaat.op(1, 1).naar(r), TerreinType.BERG);
        }
        assertEquals(4, t.getMogelijkeRichtingen().length);
    }

    @Test
    public void zouRichtingenMoetenBepalenMetToegankelijkheid() {
        Kaart kaart = new Kaart(3, 3);
        Terrein t = new Terrein(kaart, Coordinaat.op(1, 1), TerreinType.BERG);
        for (Richting r : Richting.values()) {
            new Terrein(kaart, Coordinaat.op(1, 1).naar(r), TerreinType.ZEE);
        }
        assertEquals(0, t.getMogelijkeRichtingen().length);
    }

    @Test
    public void zouRichtingenMoetenBepalenBinnenKaart() {
        Kaart kaart = new Kaart(1, 1);
        Terrein t = new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(0, t.getMogelijkeRichtingen().length);
    }

}
