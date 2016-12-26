package io.gameoftrades.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Markt;

public class WereldTest {

    @Test
    public void zouWereldMoetenMaken() {
        Kaart kaart = new Kaart(0, 0);
        List<Stad> steden = new ArrayList<>();
        Markt markt = new Markt(new ArrayList<>());

        Wereld wereld = new Wereld(kaart, steden, markt);

        assertEquals(kaart, wereld.getKaart());
        assertEquals(steden, wereld.getSteden());
        assertFalse(steden == wereld.getSteden());
        assertEquals(markt, wereld.getMarkt());
    }

}
