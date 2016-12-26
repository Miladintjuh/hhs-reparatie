package io.gameoftrades.model.kaart;

import io.gameoftrades.util.Assert;

/**
 * De kaart van de spelwereld, bestaande uit Terrein op een 2 dimensionale matrix met een breedte en hoogte. 
 */
public final class Kaart {

    private Terrein[][] terrein;

    public Kaart(int breedte, int hoogte) {
        Assert.notNegative(breedte);
        Assert.notNegative(hoogte);
        terrein = new Terrein[hoogte][breedte];
    }

    void setTerrein(Coordinaat c, Terrein t) {
        if (terrein[c.getY()][c.getX()] == null) {
            terrein[c.getY()][c.getX()] = t;
        }
    }

    public int getBreedte() {
        if (terrein.length == 0) {
            return 0;
        }
        return terrein[0].length;
    }

    public int getHoogte() {
        return terrein.length;
    }

    public Terrein getTerreinOp(Coordinaat c) {
        if ((c.getX() < 0) || (c.getY() < 0) || (c.getX() >= getBreedte()) || (c.getY() >= getHoogte())) {
            throw new IllegalArgumentException(c + " ligt buiten de Kaart.");
        }
        return terrein[c.getY()][c.getX()];
    }

    public boolean isOpKaart(Coordinaat c) {
        int x=c.getX();
        int y=c.getY();
        return ((x >= 0) && (y >= 0) && (x < getBreedte()) && (y < getHoogte()));
    }

    public Terrein kijk(Terrein t, Richting r) {
        return getTerreinOp(t.getCoordinaat().naar(r));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < getHoogte(); y++) {
            for (int x = 0; x < getBreedte(); x++) {
                sb.append(terrein[y][x].getTerreinType().getLetter());
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
