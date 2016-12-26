package io.gameoftrades.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.HandelsPositie;

/**
 * Rendert ascii art representaties van de kaart en paden.
 */
public class AsciiArtDebugger implements Debugger {

    /**
     * rendert een kaart met een lijst van coordinaten.
     * @param kaart de kaart.
     * @param c de lijst van coordinaten.
     */
    @Override
    public void debugCoordinaten(Kaart kaart, List<Coordinaat> c) {
        System.out.println(render(kaart, 1, (pos) -> c.contains(pos) ? "." : null));
    }

    @Override
    public void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> open, Map<Coordinaat, ?> closed) {
        System.out.println(render(kaart, 3, (pos) -> {
            Object o = open.get(pos);
            if (o == null) {
                o = closed.get(pos);
            }
            if (o != null) {
                String tmp = String.valueOf(o);
                while (tmp.length() < 2) {
                    tmp = tmp + " ";
                }
                return tmp.substring(0, 2);
            } else {
                return null;
            }
        }));
    }

    @Override
    public void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> map) {
        System.out.println(render(kaart,3,(pos) -> {
           Object o = map.get(pos);
           if(o!=null) {
                String tmp = String.valueOf(o);
                while (tmp.length() < 2) {
                    tmp = tmp + " ";
                }
                return tmp.substring(0, 2);
           } else {
               return null;
           }
        }));
    }

    @Override
    public void debugPad(Kaart kaart, Coordinaat start, Pad pad) {
        List<Coordinaat> c = new ArrayList<>();
        c.add(start);
        for (Richting r : pad.getBewegingen()) {
            start = start.naar(r);
            c.add(start);
        }
        debugCoordinaten(kaart, c);
    }

    @Override
    public void debugRaster(Kaart kaart, Integer[][] raster) {
        System.out.println(render(kaart, 3, (pos) -> {
            Integer value = raster[pos.getY()][pos.getX()];
            if (value == null) {
                return null;
            }
            return (value < 10 ? "0" : "") + value.toString();
        }));
    }

    @Override
    public void debugSteden(Kaart kaart, List<Stad> steden) {
        System.out.println(String.valueOf(steden));
    }

    @Override
    public PlanControl speelPlanAf(Handelsplan plan, HandelsPositie initieel) {
        return new PlanControl() {
            private HandelsPositie positie = initieel;
            private int stap = 0;
            @Override
            public void nextStep() {
                Actie a = plan.getActies().get(stap);
                if (a.isMogelijk(positie)) {
                    HandelsPositie volgende = a.voerUit(positie);
                    System.out.println(positie + " -> " + a + " -> " + volgende);
                    positie = volgende;
                } else {
                    System.out.println(positie + " -> " + a + " -> Actie NIET mogelijk!");
                }
                stap++;
            }
            
            @Override
            public boolean hasNextStep() {
                return stap < plan.getActies().size();
            }
        };
    }

    @Override
    public void debugHandel(Kaart kaart, List<Handel> handel) {
        System.out.println(String.valueOf(handel));
    }

    @Override
    public void debugActies(Kaart kaart, HandelsPositie positie, List<Actie> acties) {
        for (Actie a : acties) {
            if (a.isMogelijk(positie)) {
                HandelsPositie volgendePositie = a.voerUit(positie);
                System.out.println(positie + " -> " + a + " -> " + volgendePositie);
                positie = volgendePositie;
            } else {
                System.out.println(positie + " -> " + a + " -> Actie NIET mogelijk!");
                return;
            }
        }
    }

    /**
     * algemene render functie die een callback doet voor ieder coordinaat. 
     * @param kaart de kaart.
     * @param vakBreedte de breedte van 1 vak op de kaart.
     * @param func de functie.
     * @return de gerenderde kaart as string.
     */
    public String render(Kaart kaart, int vakBreedte, Function<Coordinaat, String> func) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < kaart.getHoogte(); y++) {
            for (int x = 0; x < kaart.getBreedte(); x++) {
                Coordinaat c = Coordinaat.op(x, y);
                char str = kaart.getTerreinOp(c).getTerreinType().getLetter();
                String alt = func.apply(c);
                if (alt != null) {
                    sb.append(alt);
                    for (int t = 0; t < vakBreedte - alt.length(); t++) {
                        sb.append(" ");
                    }
                } else {
                    sb.append(str);
                    for (int t = 0; t < vakBreedte - 1; t++) {
                        sb.append(" ");
                    }
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

}
