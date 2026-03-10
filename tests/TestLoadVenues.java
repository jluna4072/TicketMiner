package tests;

import java.util.HashMap;
import model.venues.Arena;
import model.venues.Auditorium;
import model.venues.Stadium;
import model.venues.Venue;
import utility.DataManager;

public class TestLoadVenues {

    public static void main(String[] args) {
        DataManager dm = new DataManager();
        HashMap<Integer, Venue> venues = dm.loadVenues("data/Venue_List_PA1.csv");

        System.out.println("=== Venue CSV Load Test ===");
        System.out.println("Total venues loaded: " + venues.size());

        // Expect 6 venues
        if (venues.size() == 6) {
            System.out.println("PASS: Expected 6 venues");
        } else {
            System.out.println("FAIL: Expected 6 venues, got " + venues.size());
        }

        // Test venue 1: Don Haskins Center (Arena)
        Venue v1 = venues.get(1);
        if (v1 != null) {
            System.out.println("\n-- Venue 1 Test (Don Haskins Center) --");
            System.out.println("Name: " + v1.getName());
            System.out.println("Type: " + v1.getType());
            System.out.println("Capacity: " + v1.getCapacity());
            System.out.println("Cost: " + v1.getCost());
            System.out.println("Class: " + v1.getClass().getSimpleName());

            if (v1 instanceof Arena) {
                System.out.println("PASS: Venue 1 is an Arena");
            } else {
                System.out.println("FAIL: Venue 1 should be Arena, got " + v1.getClass().getSimpleName());
            }
        } else {
            System.out.println("FAIL: Venue with ID 1 not found");
        }

        // Test venue 2: Sun Bowl Stadium (Stadium)
        Venue v2 = venues.get(2);
        if (v2 != null) {
            System.out.println("\n-- Venue 2 Test (Sun Bowl Stadium) --");
            System.out.println("Name: " + v2.getName());
            System.out.println("Capacity: " + v2.getCapacity());
            System.out.println("Class: " + v2.getClass().getSimpleName());

            if (v2 instanceof Stadium) {
                System.out.println("PASS: Venue 2 is a Stadium");
            } else {
                System.out.println("FAIL: Venue 2 should be Stadium, got " + v2.getClass().getSimpleName());
            }
        } else {
            System.out.println("FAIL: Venue with ID 2 not found");
        }

        // Test venue 3: Magoffin Auditorium (Auditorium)
        Venue v3 = venues.get(3);
        if (v3 != null) {
            System.out.println("\n-- Venue 3 Test (Magoffin Auditorium) --");
            System.out.println("Name: " + v3.getName());
            System.out.println("Class: " + v3.getClass().getSimpleName());

            if (v3 instanceof Auditorium) {
                System.out.println("PASS: Venue 3 is an Auditorium");
            } else {
                System.out.println("FAIL: Venue 3 should be Auditorium, got " + v3.getClass().getSimpleName());
            }
        } else {
            System.out.println("FAIL: Venue with ID 3 not found");
        }

        // Test venue 4: San Jacinto Plaza (Open Air)
        // NOTE: CSV has type "Open Air" but loadVenues switch checks "OpenAir".
        // If this fails, the switch case string needs to match the CSV value.
        Venue v4 = venues.get(4);
        if (v4 != null) {
            System.out.println("\n-- Venue 4 Test (San Jacinto Plaza) --");
            System.out.println("Name: " + v4.getName());
            System.out.println("Type field: " + v4.getType());
            System.out.println("Class: " + v4.getClass().getSimpleName());
        } else {
            System.out.println("FAIL: Venue with ID 4 not found");
        }

        System.out.println("\n=== Test Complete ===");
    }
}
