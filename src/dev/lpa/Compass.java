package dev.lpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Compass {

    private String locationAt;

    public Compass() {
        this.locationAt = "road";
    }

    public void getDirection() {

        Map<String, Location> map = new HashMap<>(PlacesData.getPlacesMap());
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {

            Location location = map.get(this.getLocationAt());
            location.printLocation();

            System.out.println("Select Your Compass Direction (Q to quit) >>");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "N", "S", "W", "E" -> {
                    if (location.placesToGo().get(input) == null) {
                        System.out.printf("Invalid Compass Direction: %s%n", input);
                        continue;
                    }
                    this.setLocationAt(location.placesToGo().get(input));
                }
                case "Q" -> flag = false;
                default -> System.out.printf("Invalid Compass Direction: %s%n", input);
            }
        }
    }

    public String getLocationAt() {
        return locationAt;
    }

    private void setLocationAt(String locationAt) {
        this.locationAt = locationAt;
    }

    public void printMap() {
        PlacesData.getPlacesMap().forEach((k, v) -> System.out.println(k + " " + v.placesToGo()));
    }

    private static class PlacesData {

        public static String places = """
                road,at the end of the road, W: hill, E:well house,S:valley,N:forest
                hill,on top of hill with a view in all directions,N:forest, E:road
                well house,inside a well house for a small spring,W:road,N:lake,S:stream
                valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream
                forest,at the edge of a thick dark forest,S:road,E:lake
                lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house
                stream,near a stream with a rocky bed,W:valley, N:well house
                """;

        private static String[] getPlacesData() {

            return PlacesData.places.split("\n");
        }

        public static Map<String, Location> getPlacesMap() {

            Map<String, Location> map = new HashMap<>();
            Map<String, String> placesToGoMap = new HashMap<>();
            String[] places = getPlacesData();

            for (String p : places) {

                String[] placePieces = p.split(",");
                for (String placesToGo : placePieces) {
                    if (placesToGo.contains(":")) {
                        String[] destinationCommands = placesToGo.split(":");
                        placesToGoMap.put(destinationCommands[0].trim(), destinationCommands[1].trim());
                    }
                }
                map.put(placePieces[0], new Location(placePieces[0], placePieces[1], placesToGoMap));
                placesToGoMap.clear();
            }
            return map;
        }
    }
}
