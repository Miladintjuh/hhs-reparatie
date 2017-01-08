package io.gameoftrades.student99;
/**
 *
 * @author Rebano
 */
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;

public class CityRoutes extends Path {

    private Stad start;

    private Stad end;

    public CityRoutes(Stad start, Stad end, Pad path) {
        super(start.getCoordinaat(), end.getCoordinaat(), path);

        this.start = start;
        this.end = end;
    }

    public CityRoutes(Stad start, Stad end, Path path) {
        this(start, end, path.getPath());
    }


    public Stad getStartCity() {
        return this.start;
    }

    public Stad getEndCity() {
        return this.end;
    }

    public boolean isFor(Stad start, Stad end) {
        return super.isFor(start.getCoordinaat(), end.getCoordinaat());
    }

    @Override
    public CityRoutes reverse() {
        return new CityRoutes(this.end, this.start, getPath().omgekeerd());
    }
}
