package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.kaart.TerreinType;

public class TerreinTypeTest {

    @Test
    public void zouToegankelijkMoetenZijnOfNiet() {
        assertFalse(TerreinType.ZEE.isToegankelijk());
        assertTrue(TerreinType.BERG.isToegankelijk());
        assertTrue(TerreinType.BOS.isToegankelijk());
        assertTrue(TerreinType.GRASLAND.isToegankelijk());
        assertTrue(TerreinType.STAD.isToegankelijk());
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeeHeeftGeenBewegingspunten() {
        TerreinType.ZEE.getBewegingspunten();
    }

    @Test
    public void zouBeweginspuntenMoetenKosten() {
        assertEquals(5, TerreinType.BERG.getBewegingspunten());
        assertEquals(3, TerreinType.BOS.getBewegingspunten());
        assertEquals(1, TerreinType.GRASLAND.getBewegingspunten());
        assertEquals(1, TerreinType.STAD.getBewegingspunten());
    }

    @Test
    public void zouVanLetterTeMakenMoetenZijn() {
        assertEquals(TerreinType.ZEE, TerreinType.fromLetter('Z'));
        assertEquals(TerreinType.BERG, TerreinType.fromLetter('R'));
        assertEquals(TerreinType.BOS, TerreinType.fromLetter('B'));
        assertEquals(TerreinType.GRASLAND, TerreinType.fromLetter('G'));
        assertEquals(TerreinType.STAD, TerreinType.fromLetter('S'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void zouNietVanLetterTeMakenMoetenZijn() {
        TerreinType.fromLetter('@');
    }

}
