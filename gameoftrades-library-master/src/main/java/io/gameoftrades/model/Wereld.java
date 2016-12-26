package io.gameoftrades.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Markt;
import io.gameoftrades.util.Assert;

/**
 * De handelswereld, bestaande uit een kaart, steden en natuurlijk een markt.
 */
public class Wereld {
    
    private Kaart kaart;
    private Markt markt;
    private List<Stad> steden;

    public Wereld(Kaart kaart, List<Stad> steden, Markt markt) {
        Assert.notNull(kaart);
        Assert.notNull(steden);
        Assert.notNull(markt);
        this.kaart = kaart;
        this.markt = markt;
        this.steden = new ArrayList<Stad>(steden);
    }
    
    public Kaart getKaart() {
        return kaart;
    }
    
    public Markt getMarkt() {
        return markt;
    }
    
    public List<Stad> getSteden() {
        return Collections.unmodifiableList(steden);
    }

}
