package io.gameoftrades.model.kaart;

import java.util.ArrayList;
import java.util.List;

import io.gameoftrades.util.Assert;

/**
 * De beschrijving van een stuk terrein op de kaart.
 * 
 *  <p>
 *  Een terrein heeft een TerreinType dat de eigenschappen van het terrein beschrijft, 
 *  een coordinaat dat aangeeft waar het op de kaart ligt en de kaart waar het terrein
 *  bij hoort.
 *  </p> 
 */
public final class Terrein {

    private Coordinaat coordinaat;
    private TerreinType terreinType;
    private Kaart eigenaar;

    public Terrein(Kaart eigenaar, Coordinaat coordinaat, TerreinType type) {
        Assert.notNull(eigenaar);
        Assert.notNull(coordinaat);
        Assert.notNull(type);
        this.eigenaar = eigenaar;
        this.coordinaat = coordinaat;
        this.terreinType = type;
        this.eigenaar.setTerrein(coordinaat, this);
    }

    public TerreinType getTerreinType() {
        return terreinType;
    }

    public Coordinaat getCoordinaat() {
        return coordinaat;
    }

    /**
     * Geeft de mogelijke richtingen waarin bewogen kan worden vanaf deze plaats op de kaart.
     * Houdt rekening met de grenzen van de kaart en ontoegankelijk terrein zoals de Zee.  
     * @return de mogelijke richtingen.
     */
    public Richting[] getMogelijkeRichtingen() {
        List<Richting> results = new ArrayList<>();
        for (Richting r : Richting.values()) {
            Coordinaat naar = this.coordinaat.naar(r);
            if (eigenaar.isOpKaart(naar)) {
                Terrein t = eigenaar.getTerreinOp(naar);
                if (t.getTerreinType().isToegankelijk()) {
                    results.add(r);
                }
            }
        }
        return results.toArray(new Richting[results.size()]);
    }

    @Override
    public String toString() {
        return "Terrein(" + getCoordinaat() + "," + getTerreinType() + ")";
    }

}
