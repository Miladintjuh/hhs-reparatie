package io.gameoftrades.model.kaart;

/**
 * De vier kompas richtingen waarin bewogen kan worden op de kaart. 
 */
public enum Richting {

    NOORD, OOST, ZUID, WEST;

    /**
     * @return de tegenovergestelde richting van de huidige.
     */
    public Richting omgekeerd() {
        switch (this) {
        case NOORD:
            return ZUID;
        case OOST:
            return WEST;
        case WEST:
            return OOST;
        case ZUID:
            return NOORD;
        }
        throw new IllegalArgumentException();
    }

    /**
     * bepaalt de richtign tussen twee coordinaten.
     * Dit werkt alleen als het exact een horizontale of verticale lijn is.
     * @param van het startpunt.
     * @param naar het eindpunt.
     * @return de richting.
     */
    public static Richting tussen(Coordinaat van, Coordinaat naar) {
        int dx = naar.getX() - van.getX();
        int dy = naar.getY() - van.getY();
        if (dx == 0) {
            if (dy > 0) {
                return ZUID;
            } else if (dy < 0) {
                return NOORD;
            }
        } else if (dy == 0) {
            if (dx > 0) {
                return OOST;
            } else if (dx < 0) {
                return WEST;
            }
        }
        throw new IllegalArgumentException(van + " -> " + naar + " is niet horizontaal of verticaal.");
    }

}
