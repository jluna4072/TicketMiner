package model.users;

import java.util.HashMap;
import model.events.Event;
import model.venues.Venue;

public class Admin extends User {

    public Admin() {
    }
    public Admin(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }

    public void addMember(User user, HashMap<String, User> userMap) {
        userMap.put(user.getUsername(), user);
    }

    public void viewMember(String identifier) {
    }

    public void updateMember(String identifier) {
    }

    public void deleteMember(String username, HashMap<String, User> userMap) {
        userMap.remove(username);
    }

    public void addVenue(Venue venue) {
    }

    public void viewVenue(String identifier) {
    }

    public void updateVenue(String identifier) {
    }

    public void deleteVenue(String identifier) {
    }

    public void addEvent(Event event) {
    }

    public void viewEvent(String identifier) {
    }

    public void updateEvent(String identifier) {
    }

    public void deleteEvent(String identifier) {
    }

    public boolean isUsernameUnique(String username, HashMap<String, User> userMap) {
        return !userMap.containsKey(username);
        
    }
}
