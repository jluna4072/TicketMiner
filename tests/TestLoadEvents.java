package tests;

import java.util.HashMap;
import model.events.Event;
import model.events.Sport;
import utility.DataManager;

public class TestLoadEvents {

    public static void main(String[] args) {
        DataManager dm = new DataManager();
        HashMap<Integer, Event> events = dm.loadEvents("data/Event_List_PA1.csv");

        System.out.println("=== Event CSV Load Test ===");
        System.out.println("Total events loaded: " + events.size());

        if (events.size() == 44) {
            System.out.println("PASS: Expected 44 events");
        } else {
            System.out.println("FAIL: Expected 44 events, got " + events.size());
        }

        Event first = events.get(1);
        if (first != null) {
            System.out.println("\n-- Event 1 Test --");
            System.out.println("Name: " + first.getEventName());
            System.out.println("Type: " + first.getType());
            System.out.println("Date: " + first.getDate());
            System.out.println("Time: " + first.getTime());
            System.out.println("Class: " + first.getClass().getSimpleName());

            if (first instanceof Sport) {
                System.out.println("PASS: Event 1 is a Sport");
            } else {
                System.out.println("FAIL: Event 1 should be Sport, got " + first.getClass().getSimpleName());
            }
        } else {
            System.out.println("FAIL: Event with ID 1 not found");
        }

        Event last = events.get(44);
        if (last != null) {
            System.out.println("\n-- Event 44 Test --");
            System.out.println("Name: " + last.getEventName());
            System.out.println("Type: " + last.getType());
            System.out.println("Date: " + last.getDate());
            System.out.println("Time: " + last.getTime());
        } else {
            System.out.println("FAIL: Event with ID 44 not found");
        }

        System.out.println("\n=== Test Complete ===");
    }
}
