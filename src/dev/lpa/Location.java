package dev.lpa;

import java.util.HashMap;
import java.util.Map;

public record Location(String location, String description, Map<String, String> placesToGo) {

    public Location(String location, String description, Map<String, String> placesToGo) {
        this.location = location;
        this.description = description;
        this.placesToGo = new HashMap<>(placesToGo);
    }

    public void printLocation() {
        System.out.printf("%n*** %s ***%nFrom here, you can see:%n", String.valueOf(this.description().charAt(0)).toUpperCase() + this.description().substring(1));
        this.placesToGo().forEach((k, v) -> {
            String direction = switch (k) {
                case "N" -> "North";
                case "S" -> "South";
                case "W" -> "West";
                case "E" -> "East";
                default -> throw new IllegalStateException("Unexpected value: " + k);
            };
            System.out.printf("\tA %s to the %s (%s)%n".formatted(v, direction, k));
        });
    }

}
