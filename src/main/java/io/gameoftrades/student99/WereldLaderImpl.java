package io.gameoftrades.student99;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class WereldLaderImpl implements WereldLader {

    private ArrayList<ArrayList<String>> mapReader;
    private Kaart map;
    private List<Stad> cities;
    private Markt market;
    private List<Handel> dealers;

    @Override
    public Wereld laad(String resource) {

        Scanner in = new Scanner(this.getClass().getResourceAsStream(resource));
        mapReader = new ArrayList<>();
        ArrayList<String> mapText = new ArrayList<>();
        ArrayList<String> cityText = new ArrayList<>();
        ArrayList<String> marketText = new ArrayList<>();

        mapReader.add(mapText);
        mapReader.add(cityText);
        mapReader.add(marketText);

        int index = 0;

        while (in.hasNextLine()) {
            String line = in.nextLine();
            line = line.replaceAll("\\s", "");
            if (line.matches("\\d+")) {
                index++;
            } else {
                mapReader.get(index).add(line);
            }
        }

        loadMap();
        loadCities();
        loadMarkets();

        return new Wereld(map, cities, market);
    }

    private void loadMap() {
        String xy = mapReader.get(0).get(0);
        String[] xyArray = xy.split(",");

        int breedte = Integer.parseInt(xyArray[0]);
        int hoogte = Integer.parseInt(xyArray[1]);

        if (!checkKaart(breedte, hoogte)) {
            JOptionPane.showMessageDialog(null,
                    "Incorrecte kaart...",
                    "FATAL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            map = new Kaart(breedte, hoogte);

            for (int y = 1; y <= hoogte; y++) {
                char[] charArray = mapReader.get(0).get(y).toCharArray();
                for (int x = 0; x < breedte; x++) {
                    //System.out.print("[" + charArray[x] + " " + x + " " + (y - 1) + " ]");                                       
                    Terrein t = new Terrein(map, Coordinaat.op(x, y - 1), TerreinType.fromLetter(charArray[x]));
                }
                //System.out.println("");
            }
        }
    }

    public void loadCities() {
        cities = new ArrayList<>();

        for (String s : mapReader.get(1)) {
            String[] split = s.split(",");
            Coordinaat c = Coordinaat.op(Integer.parseInt(split[0]) - 1, Integer.parseInt(split[1]) - 1);
            if (c.getX() < 0 || c.getY() < 0 || c.getX() > map.getBreedte() || c.getY() > map.getHoogte()) {
                JOptionPane.showMessageDialog(null,
                        "Incorrecte stad...",
                        "FATAL ERROR",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("One or more coordinates were outside of the map " + c);
            }
            Stad city = new Stad(c, split[2]);
            cities.add(city);

            System.out.println(split[0] + " " + split[1] + " " + split[2]);
            System.out.println(c);
   
       }
        
    }

    private void loadMarkets() {
        dealers = new ArrayList<>();

        for (String s : mapReader.get(2)) {
            String[] split = s.split(",");
            HandelType merchType;
            if (!split[1].equals("BIEDT") && !split[1].equals("VRAAGT")) {
                JOptionPane.showMessageDialog(null,
                        "Incorrecte Markt...",
                        "FATAL ERROR",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Verkeerde Handel");
            } else if (split[1].equals("BIEDT")) {
                merchType = HandelType.BIEDT;
            } else {
                merchType = HandelType.VRAAGT;
            }
            Handel handel = new Handel(zoekStadBijNaam(split[0]), merchType, new Handelswaar(split[2]), Integer.parseInt(split[3]));
            dealers.add(handel);

        }
        market = new Markt(dealers);
    }

    private Stad zoekStadBijNaam(String naam) {
        for (Stad s : cities) {
            if (s.getNaam().equals(naam)) {
                return s;
            }
        }
        return null;
    }

    private boolean checkKaart(int x, int y) {
        if (x == 0 && y == 0) {
            return true;
        }
        int bArray = mapReader.get(0).get(1).length();
        int hArray = mapReader.get(0).size() - 1;

        return !(x != bArray || y != hArray);
    }
}
