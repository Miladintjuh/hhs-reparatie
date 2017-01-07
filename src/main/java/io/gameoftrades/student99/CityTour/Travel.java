/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student99.CityTour;
/**
 *
 * @author Rebano
 */
    public class Travel {
        private Ant ant;
        private int remaining;
        public Travel(Ant ant) {
            this.ant = ant;
            this.remaining = ant.getTerrein().getTerreinType().getBewegingspunten();
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



