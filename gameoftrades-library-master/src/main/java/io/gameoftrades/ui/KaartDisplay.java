package io.gameoftrades.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import io.gameoftrades.debug.Debugger.PlanControl;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.BeweegActie;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import io.gameoftrades.model.markt.actie.KoopActie;
import io.gameoftrades.model.markt.actie.NavigeerActie;
import io.gameoftrades.model.markt.actie.StopActie;
import io.gameoftrades.model.markt.actie.VerkoopActie;

/**
 * Tekent de kaart en debug resultaten.  
 */
public class KaartDisplay extends JPanel implements PlanControl {

    private static final Color PAD_KLEUR = new Color(220, 255, 220);
    private static final Color TOUR_KLEUR = new Color(40, 80, 255);
    private static final Color OVERLAY_KLEUR = new Color(240, 255, 240);

    private int tilesize = 16;

    private BufferedImage tiles;
    private BufferedImage[][] tileCache;

    private Kaart kaart;
    private Coordinaat start;
    private Pad pad;
    private List<Stad> steden;
    private int planStep = 0;
    private Handelsplan plan;

    private HandelsPositie positie;
    private Integer[][] overlay;

    private List<HandelsPositieListener> listeners = new ArrayList<>();
    private Map<Coordinaat, ?> open;
    private Map<Coordinaat, ?> closed;
    private Map<Handel, List<Handel>> handel;
    private HandelsPositie actiePositie;
    private List<Actie> acties;

    public KaartDisplay() {
        super(null);
        try {
            tiles = ImageIO.read(KaartDisplay.class.getResourceAsStream("/game-of-trades.png"));
            tileCache = new BufferedImage[1 + (tiles.getHeight() / tilesize)][1 + (tiles.getWidth() / (tilesize + 1))];
        } catch (IOException ex) {
            throw new RuntimeException("Kon tile resource niet laden", ex);
        }
    }

    public synchronized void setKaart(Kaart kaart) {
        if (this.kaart != kaart) {
            this.kaart = kaart;
            this.setPreferredSize(new Dimension((kaart.getBreedte()) * tilesize, kaart.getHoogte() * tilesize));
            reset();
        }
    }

    public void reset() {
        pad = null;
        steden = null;
        plan = null;
        planStep = 0;
        overlay = null;
        open = null;
        closed = null;
        handel = null;
        acties = null;
        actiePositie = null;
        positie = null;
        repaint();
    }

    public void setHandel(List<Handel> handel) {
        this.handel = pair(handel);
        this.repaint();
    }

    public synchronized void setPad(Coordinaat start, Pad pad) {
        this.start = start;
        this.pad = pad;
        this.repaint();
    }

    public synchronized void setSteden(List<Stad> steden) {
        this.steden = new ArrayList<>(steden);
        this.repaint();
    }

    public synchronized PlanControl setPlan(Handelsplan plan, HandelsPositie positie) {
        this.plan = plan;
        this.positie = positie;
        this.planStep = -1;
        this.repaint();
        return this;
    }

    public synchronized void nextStep() {
        if (hasNextStep()) {
            this.planStep++;
            this.positie = plan.getActies().get(planStep).voerUit(positie);
            for (HandelsPositieListener l : listeners) {
                l.setHandelsPositie(this.positie);
            }
            this.repaint();
        }
    }

    public synchronized boolean hasNextStep() {
        return planStep < plan.getActies().size() - 1;
    }

    public void setOverlay(int[][] overlay) {
        if (overlay != null) {
            Integer[][] wrap = new Integer[overlay.length][overlay[0].length];
            for (int t = 0; t < overlay.length; t++) {
                for (int y = 0; y < overlay[0].length; y++) {
                    if (overlay[t][y] != 0) {
                        wrap[t][y] = overlay[t][y];
                    }
                }
            }
            setOverlay(wrap);
        } else {
            setOverlay((Integer[][]) null);
        }
    }

    public synchronized void setOverlay(Integer[][] overlay) {
        this.overlay = overlay;
        this.repaint();
    }

    public void setCoordinaten(Map<Coordinaat, ?> coords) {
        this.open = coords;
        this.repaint();
    }

    public void setOpenClosed(Map<Coordinaat, ?> outer, Map<Coordinaat, ?> inner) {
        this.open = outer;
        this.closed = inner;
        this.repaint();
    }

    public void addHandelsPositieListener(HandelsPositieListener l) {
        listeners.add(l);
    }

    public void setActies(HandelsPositie actiePositie, List<Actie> acties) {
        this.actiePositie = actiePositie;
        this.acties = acties == null ? null : new ArrayList<>(acties);
        this.repaint();
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        if (kaart != null) {
            tekenKaart(g);
        }
        if (steden != null) {
            tekenStedenTour(g);
        }
        if (actiePositie != null && acties != null) {
            tekenActies(g, actiePositie, acties);
        }
        if (positie != null) {
            g.setColor(new Color(0.8f, 1.0f, 0.8f, 0.8f));
            Coordinaat coordinaat = positie.getCoordinaat();
            int x = coordinaat.getX() * tilesize;
            int y = coordinaat.getY() * tilesize;
            g.fillOval(x, y, 16, 16);
        }
        if (overlay != null) {
            drawOverlay(g);
        }
        if (open != null) {
            drawOpenClosed(g, open, Color.GREEN);
        }
        if (closed != null) {
            drawOpenClosed(g, closed, Color.RED);
        }
        if (handel != null) {
            tekenHandel(g);
        }
        if ((pad != null) && (start != null)) {
            tekenPad(g);
        }
    }

    private void tekenActies(Graphics2D g,HandelsPositie pos, List<Actie> as) {
        int lx = pos.getCoordinaat().getX() * tilesize + 7;
        int ly = pos.getCoordinaat().getY() * tilesize + 7;
        for (Actie a : as) {
            if (a.isMogelijk(pos)) {
                pos = a.voerUit(pos);
                int nx = pos.getCoordinaat().getX() * tilesize + 7;
                int ny = pos.getCoordinaat().getY() * tilesize + 7;
                if (a instanceof BeweegActie) {
                    g.setColor(TOUR_KLEUR);
                    g.drawLine(lx, ly, nx, ny);
                } else if (a instanceof NavigeerActie) {
                    g.setColor(PAD_KLEUR);
                    g.drawLine(lx, ly, nx, ny);
                } else if (a instanceof KoopActie) {
                    g.setColor(OVERLAY_KLEUR);
                    g.fillOval(nx - 3, ny - 3, 7, 7);
                } else if (a instanceof VerkoopActie) {
                    g.setColor(OVERLAY_KLEUR);
                    g.drawOval(nx - 3, ny - 3, 7, 7);
                } else if (a instanceof StopActie) {
                    g.setColor(OVERLAY_KLEUR);
                    g.fillRect(nx - 3, ny - 3, 7, 7);
                }
                lx = nx;
                ly = ny;
            } else {
                g.setColor(Color.RED);
                g.fillRect(lx - 3, ly - 3, 7, 7);
            }
        }
    }

    private void tekenHandel(Graphics2D g) {
        for (Map.Entry<Handel, List<Handel>> e : handel.entrySet()) {
            Coordinaat start = e.getKey().getStad().getCoordinaat();
            for (Handel dst : e.getValue()) {
                Coordinaat end = dst.getStad().getCoordinaat();
                int x = start.getX() * tilesize + 7;
                int y = start.getY() * tilesize + 7;
                g.setColor(new Color(e.getKey().getStad().hashCode() & 0x00FFFFFF | 0x000000FF));
                g.fillOval(x - 6, y - 3, 7, 7);
                int x2 = end.getX() * tilesize + 7;
                int y2 = end.getY() * tilesize + 7;
                g.setColor(new Color(dst.getHandelswaar().hashCode() & 0x00FFFFFF | 0x00FF0000));
                g.drawLine(x - 3, y, x2 + 9, y2);
                g.setColor(new Color(dst.getStad().hashCode() & 0x00FFFFFF | 0x000000FF));
                g.drawOval(x2 + 6, y2 - 3, 7, 7);
            }
        }
    }

    private Map<Handel, List<Handel>> pair(List<Handel> handel) {
        if (handel == null) {
            return null;
        }
        Map<Handel, List<Handel>> pairs = new HashMap<>();
        for (Handel h : handel) {
            if (HandelType.BIEDT.equals(h.getHandelType())) {
                if (!pairs.containsKey(h)) {
                    pairs.put(h, new ArrayList<>());
                } 
            }
        }
        for (Handel h : handel) {
            if (HandelType.VRAAGT.equals(h.getHandelType())) {
                for (Handel a : pairs.keySet()) {
                    if (h.getHandelswaar().equals(a.getHandelswaar())) {
                        pairs.get(a).add(h);
                    }
                }
            }
        }
        return pairs;
    }

    private void drawOpenClosed(Graphics2D g, Map<Coordinaat, ?> coords, Color c) {
        g.setColor(c);
        for (Map.Entry<Coordinaat, ?> e : coords.entrySet()) {
            int x = e.getKey().getX() * tilesize + 4;
            int y = e.getKey().getY() * tilesize + 12;
            g.drawString(limit(String.valueOf(e.getValue()), 2), x, y);
        }
    }

    private String limit(String s, int i) {
        if (s.length() > 2) {
            return s.substring(0, 2);
        }
        return s;
    }

    private void drawOverlay(Graphics2D g) {
        g.setColor(OVERLAY_KLEUR);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int y = 0; y < overlay.length; y++) {
            for (int x = 0; x < overlay[0].length; x++) {
                Integer value = overlay[y][x];
                if (value != null) {
                    g.drawString(String.valueOf(value), x * tilesize + 4, y * tilesize + 12);
                }
            }
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    private void tekenStedenTour(Graphics2D g) {
        g.setColor(TOUR_KLEUR);
        int lx = -1;
        int ly = -1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Stad stad : steden) {
            int x = stad.getCoordinaat().getX() * tilesize + 7;
            int y = stad.getCoordinaat().getY() * tilesize + 7;
            if (lx >= 0) {
                g.drawLine(lx, ly, x, y);
            }
            lx = x;
            ly = y;
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    private void tekenPad(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(PAD_KLEUR);
        int x = start.getX() * tilesize + 7;
        int y = start.getY() * tilesize + 7;
        g.fillOval(x - 3, y - 3, 7, 7);
        int lx = x;
        int ly = y;
        for (Richting r : pad.getBewegingen()) {
            switch (r) {
            case NOORD:
                y = y - tilesize;
                break;
            case ZUID:
                y = y + tilesize;
                break;
            case WEST:
                x = x - tilesize;
                break;
            case OOST:
                x = x + tilesize;
                break;
            }
            g.drawLine(lx, ly, x, y);
            lx = x;
            ly = y;
        }
        g.fillOval(x - 3, y - 3, 7, 7);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    private void tekenKaart(Graphics g) {
        TerreinType[][] tt = new TerreinType[3][3];
        int h = kaart.getHoogte();
        int w = kaart.getBreedte();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                for (int yy = 0; yy < tt.length; yy++) {
                    for (int xx = 0; xx < tt.length; xx++) {
                        int xt = x - 1 + xx;
                        int yt = y - 1 + yy;
                        if ((xt < 0) || (yt < 0) || (xt >= w) || (yt >= h)) {
                            tt[yy][xx] = TerreinType.ZEE;
                        } else {
                            tt[yy][xx] = kaart.getTerreinOp(Coordinaat.op(xt, yt)).getTerreinType();
                        }
                    }
                }
                BufferedImage img = toTile(tt, ((x + y) % 2) == 0);
                g.drawImage(img, x * tilesize, y * tilesize, null);
            }
        }
    }

    private BufferedImage toTile(TerreinType[][] t, boolean odd) {
        switch (t[1][1]) {
        case BERG:
            return getTile(odd ? 0 : 1, 3);
        case BOS:
            return getTile(odd ? 0 : 1, 2);
        case GRASLAND:
            return getTile(odd ? 0 : 1, 1);
        case STAD:
            return getTile(6, 0);
        case ZEE:
            int edge = t[1][2] != TerreinType.ZEE ? 1 : 0; // E
            edge |= t[1][0] != TerreinType.ZEE ? 2 : 0; // W
            edge |= t[0][1] != TerreinType.ZEE ? 4 : 0; // N
            edge |= t[2][1] != TerreinType.ZEE ? 8 : 0; // S
            switch (edge) {
            case 1:
                return getTile(2, 0);
            case 2:
                return getTile(3, 0);
            case 3:
                return getTile(3, 3);
            case 4:
                return getTile(4, 0);
            case 5:
                return getTile(2, 1);
            case 6:
                return getTile(4, 1);
            case 7:
                return getTile(2, 2);
            case 8:
                return getTile(5, 0);
            case 9:
                return getTile(5, 1);
            case 10:
                return getTile(3, 1);
            case 11:
                return getTile(3, 2);
            case 12:
                return getTile(2, 3);
            case 13:
                return getTile(5, 2);
            case 14:
                return getTile(4, 2);
            case 15:
                return getTile(4, 3);
            default:
                return getTile(odd ? 0 : 1, 0);
            }
        }
        throw new IllegalArgumentException(String.valueOf(t));
    }

    private BufferedImage getTile(int x, int y) {
        if (tileCache[y][x] == null) {
            BufferedImage tmp = new BufferedImage(tilesize, tilesize, tiles.getType());
            Graphics g = tmp.getGraphics();
            try {
                g.drawImage(tiles, -x * (tilesize + 1), -y * tilesize, null);
            } finally {
                g.dispose();
            }
            tileCache[y][x] = tmp;
        }
        return tileCache[y][x];
    }


}
