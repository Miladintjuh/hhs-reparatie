package io.gameoftrades.student99.antRaceAlgorithm;
/**
 *
 * @author Rebano
 */
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;

/**
 *
 * @author Rebano
 */
  class Ant implements Comparable<Ant> {
        private Ant parent;
        private Terrein terrain;

        private int cost;

        public Ant(Terrein start, Integer[][] costs) {
            this.terrain = start;
            cost = 0;
        }

        public Ant(Ant ouder, Terrein terrain) {
            this.parent = ouder;
            this.terrain = terrain;
            this.cost = ouder.getCost() + terrain.getTerreinType().getBewegingspunten();
        }

        /**
         * @return true if this ant arrives.
         */
        public boolean atLocation(Integer[][] costs) {
            int y = getCoordinate().getY();
            int x = getCoordinate().getX();
            if (costs[y][x] == null) {
                costs[y][x] = this.cost;
                return true;
            }
            return false;
        }

        public Ant getParent() {
            return parent;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public int compareTo(Ant m) {
            return this.cost - m.cost;
        }

        public Terrein getTerrain() {
            return terrain;
        }

        public Coordinaat getCoordinate() {
            return terrain.getCoordinaat();
        }

        @Override
        public String toString() {
            return cost + ":" + getCoordinate();
        }
    }