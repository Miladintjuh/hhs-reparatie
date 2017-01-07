/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student99.antRaceAlgorithm;

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
            int y = getCoordinaat().getY();
            int x = getCoordinaat().getX();
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

        public Terrein getTerrein() {
            return terrain;
        }

        public Coordinaat getCoordinaat() {
            return terrain.getCoordinaat();
        }

        @Override
        public String toString() {
            return cost + ":" + getCoordinaat();
        }
    }