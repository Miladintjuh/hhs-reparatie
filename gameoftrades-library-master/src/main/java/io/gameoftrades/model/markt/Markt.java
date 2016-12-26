package io.gameoftrades.model.markt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.gameoftrades.util.Assert;

/**
 * Beschrijft de volledige markt van de Wereld over alle steden heen.  
 */
public final class Markt {

    private List<Handel> handel;

    public Markt(List<Handel> handel) {
        Assert.notNull(handel);
        this.handel = new ArrayList<>(handel);
    }

    /**
     * @return alle handel (vraag en aanbod).
     */
    public List<Handel> getHandel() {
        return Collections.unmodifiableList(handel);
    }

    /**
     * @return alle handel die wordt aangeboden.
     */
    public List<Handel> getAanbod() {
        return getHandel().stream()
                .filter(h -> h.getHandelType().equals(HandelType.BIEDT))
                .collect(Collectors.toList());
    }

    /**
     * @return alle handel die wordt gevraagd.
     */
    public List<Handel> getVraag() {
        return getHandel().stream()
                .filter(h -> h.getHandelType().equals(HandelType.VRAAGT))
                .collect(Collectors.toList());
    }
}
