package io.gameoftrades.model.kaart;

/**
 * Beschrijft de verschillende terrein typen.
 * <p>
 * Een terrein kan ontoegangkelijk zijn (zoals de ZEE) of toegankelijk. 
 * Een toegankelijk terrein kent een aantal bewegingspunten dat aangeeft hoeveel tijd het kost
 * om over het terrein te navigeren. Een BERG (5) kost meer moeite dan GRASLAND (1).  
 * </p> 
 * <p>
 * Een terrein type heeft ook een letter om op de kaart te kunnen aangeven wat voor type het
 * terrein is.
 * </p>
 */
public enum TerreinType {

    /** de ontoegankelijke zee. */
    ZEE(false, 'Z'),
    /** de verradelijke bergen die beweging belemmeren */
    BERG(true, 5, 'R'),
    /** bossen met slingerende paden */
    BOS(true, 3, 'B'),
    /** het overzichtelijke grasland */
    GRASLAND(true, 1, 'G'),
    /** stad, waar de handel is. */
    STAD(true, 1, 'S');

    private boolean toegankelijk;
    private int bewegingspunten;
    private char letter;

    private TerreinType(boolean toegankelijk, char letter) {
        this(toegankelijk, -1, letter);
    }

    private TerreinType(boolean toegankelijk, int bewegingspunten, char letter) {
        this.toegankelijk = toegankelijk;
        this.bewegingspunten = bewegingspunten;
        this.letter = letter;
    }

    public int getBewegingspunten() {
        if (!isToegankelijk()) {
            throw new IllegalArgumentException(this + " is niet toegankelijk.");
        }
        return bewegingspunten;
    }

    public boolean isToegankelijk() {
        return toegankelijk;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    public String toString() {
        return "Type(" + this.name() + "," + toegankelijk + "," + bewegingspunten + ")";
    }

    public static TerreinType fromLetter(char c) {
        for (TerreinType tt : TerreinType.values()) {
            if (tt.getLetter() == c) {
                return tt;
            }
        }
        throw new IllegalArgumentException("Onbekend TereinType " + c);
    }

}
