package io.gameoftrades.student99.antRaceAlgorithm;


/**
 *
 * @author Rebano
 */
    public class Travel {
        private Ant ant;
        private int remaining;
        public Travel(Ant ant) {
            this.ant = ant;
            this.remaining = ant.getTerrain().getTerreinType().getBewegingspunten();
        }

        public Ant getMier() {
            return ant;
        }

        public void next() {
            remaining--;
        }

        public boolean isDaar() {
            return remaining <= 0;
        }

        @Override
        public String toString() {
            return remaining + ":" + ant;
        }
    }


