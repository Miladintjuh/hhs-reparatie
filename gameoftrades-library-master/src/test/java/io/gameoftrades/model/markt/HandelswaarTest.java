package io.gameoftrades.model.markt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.markt.Handelswaar;

public class HandelswaarTest {

    @Test
    public void zouHandelswaarMoetenMaken() {
        assertEquals("schapen", new Handelswaar("schapen").getNaam());
    }

    @Test
    public void zouGelijkMoetenZijn() {
        assertTrue(new Handelswaar("schapen").equals(new Handelswaar("schapen")));
    }

    @Test
    public void zouNietGelijkMoetenZijn() {
        assertFalse(new Handelswaar("stenen").equals(new Handelswaar("schapen")));
    }

}
