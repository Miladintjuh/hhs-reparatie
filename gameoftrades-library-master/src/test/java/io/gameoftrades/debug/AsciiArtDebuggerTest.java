package io.gameoftrades.debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.gameoftrades.debug.Debugger.PlanControl;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import io.gameoftrades.model.markt.actie.KoopActie;
import io.gameoftrades.model.markt.actie.NavigeerActie;

public class AsciiArtDebuggerTest {

    private Debugger debug;
    private Kaart kaart;

    @Before
    public void init() {
        debug = new AsciiArtDebugger();
        kaart = new Kaart(3, 3);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                new Terrein(kaart, Coordinaat.op(x, y), TerreinType.GRASLAND);
            }
        }
    }

    @Test
    public void debugCoordinatenLijst() {
        debug.debugCoordinaten(kaart, Collections.singletonList(Coordinaat.op(1, 1)));
    }

    @Test
    public void debugCoordinatenMap() {
        debug.debugCoordinaten(kaart, Collections.singletonMap(Coordinaat.op(1, 1), "!!"));
    }

    @Test
    public void debugCoordinatenDoubleMap() {
        debug.debugCoordinaten(kaart, Collections.singletonMap(Coordinaat.op(1, 1), "!!"), Collections.singletonMap(Coordinaat.op(1, 2), "??"));
    }

    @Test
    public void debugRaster() {
        Integer[][] raster = new Integer[3][3];
        raster[0][1] = 42;
        debug.debugRaster(kaart, raster);
    }

    @Test
    public void debugSteden() {
        debug.debugSteden(kaart, Collections.singletonList(new Stad(Coordinaat.op(1, 1), "x")));
    }

    @Test
    public void debugPad() {
        debug.debugPad(kaart, Coordinaat.op(0, 0), new Pad() {

            @Override
            public int getTotaleTijd() {
                return 0;
            }

            @Override
            public Richting[] getBewegingen() {
                return new Richting[] { Richting.ZUID, Richting.OOST, Richting.ZUID };
            }

            @Override
            public Pad omgekeerd() {
                return null;
            }

            @Override
            public Coordinaat volg(Coordinaat start) {
                return null;
            }

        });
    }

    @Test
    public void debugHandel() {
        List<Handel> handel = new ArrayList<>();
        handel.add(new Handel(new Stad(Coordinaat.op(1, 1), "Stad"), HandelType.BIEDT, new Handelswaar("x"), 42));
        debug.debugHandel(kaart, handel);
    }

    @Test
    public void debugPlanActies() {
        List<Handel> handel = new ArrayList<>();
        Stad stad = new Stad(Coordinaat.op(1, 1), "Stad");
        handel.add(new Handel(stad, HandelType.BIEDT, new Handelswaar("x"), 42));
        Wereld w = new Wereld(kaart, Collections.singletonList(stad), new Markt(handel));
        HandelsPositie positie = new HandelsPositie(w, stad, 100, 1, 100);

        List<Actie> acties = new ArrayList<>();
        acties.add(new KoopActie(handel.get(0)));
        acties.add(new NavigeerActie(stad.getCoordinaat(), Richting.ZUID));
        acties.add(new KoopActie(handel.get(0))); // Can't!

        debug.debugActies(kaart, positie, acties);
    }

    @Test
    public void zouPlanMoetenAfspelen() {
        List<Handel> handel = new ArrayList<>();
        Stad stad = new Stad(Coordinaat.op(1, 1), "Stad");
        handel.add(new Handel(stad, HandelType.BIEDT, new Handelswaar("x"), 42));
        Wereld w = new Wereld(kaart, Collections.singletonList(stad), new Markt(handel));
        HandelsPositie positie = new HandelsPositie(w, stad, 100, 1, 100);

        List<Actie> acties = new ArrayList<>();
        acties.add(new KoopActie(handel.get(0)));
        acties.add(new NavigeerActie(stad.getCoordinaat(), Richting.ZUID));
        acties.add(new KoopActie(handel.get(0))); // Can't!

        PlanControl ctrl = debug.speelPlanAf(new Handelsplan(acties), positie);
        while (ctrl.hasNextStep()) {
            ctrl.nextStep();
        }
    }

}
