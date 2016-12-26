package io.gameoftrades.model.markt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.util.Assert;

/**
 * Een handelsplan is een lijst van uit te voeren acties die zo veel mogelijk winst per tijdseenheid probeert op te leveren. 
 */
public class Handelsplan {

    private List<Actie> acties;

    public Handelsplan(List<Actie> acties) {
        Assert.notNull(acties);
        this.acties = new ArrayList<>(acties);
    }

    public List<Actie> getActies() {
        return Collections.unmodifiableList(acties);
    }

}
