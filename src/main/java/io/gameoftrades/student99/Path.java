package io.gameoftrades.student99;
/**
 *
 * @author Rebano
 */

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;

public class Path {

    private final Coordinaat start;
    private final Coordinaat end;
    private final Pad path;

    public Path(Coordinaat start, Coordinaat end, Pad path) {
        this.start = start;
        this.end = end;
        this.path = path;
    }

 
    public Coordinaat getStart() {
        return this.start;
    }


    public Coordinaat getEnd() {
        return this.end;
    }


    public boolean isFor(Coordinaat start, Coordinaat end) {
        return this.start.equals(start) && this.end.equals(end);
    }


    public Pad getPath() {
        return this.path;
    }


    public int getLength() {
        return this.path.getTotaleTijd();
    }


    public Path reverse() {
        return new Path(this.end, this.start, this.path.omgekeerd());
    }
}
