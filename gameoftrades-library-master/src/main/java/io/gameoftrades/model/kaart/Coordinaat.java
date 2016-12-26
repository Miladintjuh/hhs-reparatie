package io.gameoftrades.model.kaart;

/**
 * Een coordinaat (x,y) op een kaart.
 * <p>Gebruik <code>Coordinaat.op(x,y)</code> om nieuwe instanties te maken.</p>
 */
public final class Coordinaat {

    /**
     * maakt een nieuwe coordinaat op de gegeven coordinaten.
     * @param x het x coordinaat.
     * @param y het y coordinaat.
     * @return het coordinaat.
     */
    public static Coordinaat op(int x, int y) {
        return new Coordinaat(x, y);
    }

    private int x;
    private int y;

    private Coordinaat(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * geeft het coordinaat in de gegeven richting.
     * @param richting de richting waarvan het coordinaat gewenst is.
     * @return het coordinaat.
     */
    public Coordinaat naar(Richting richting) {
        switch (richting) {
        case NOORD:
            return new Coordinaat(x, y - 1);
        case OOST:
            return new Coordinaat(x + 1, y);
        case ZUID:
            return new Coordinaat(x, y + 1);
        case WEST:
            return new Coordinaat(x - 1, y);
        }
        throw new IllegalArgumentException(String.valueOf(richting));
    }

    /**
     * berekent de afstand tussen dit en het gegeven coordinaat.
     * @param c het coordinaat.
     * @return de afstand.
     */
    public double afstandTot(Coordinaat c) {
        int x1 = c.x - this.x;
        int y1 = c.y - this.y;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinaat) {
            return (((Coordinaat) obj).getX() == x) && (((Coordinaat) obj).getY() == y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return x + y * 1024;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
