package io.gameoftrades.model.markt;

import io.gameoftrades.util.Assert;

/**
 * Handelswaar, zoals schapen en stenen, om mee handel te drijven. 
 */
public final class Handelswaar implements Comparable<Handelswaar> {

    private String naam;

    public Handelswaar(String naam) {
        Assert.notEmpty(naam);
        this.naam = naam;
    }
    
    public String getNaam() {
        return naam;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Handelswaar) {
            return ((Handelswaar) obj).naam.equals(naam);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return naam.hashCode();
    }

    @Override
    public int compareTo(Handelswaar o) {
        return naam.compareTo(o.getNaam());
    }

    @Override
    public String toString() {
        return naam;
    }
}
