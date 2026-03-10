package model.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public User viewMember(String identifier, HashMap<String, User> userMap) {
        try {
            int id = Integer.parseInt(identifier);
            for (User u : userMap.values()) {
                if (u.getUserID() == id) return u;
            }
            return null;
        } catch (NumberFormatException e) {
        }
        if (userMap.containsKey(identifier)) {
            return userMap.get(identifier);
        }
        // Try by full name — only return if exactly one match
        List<User> matches = findMembersByName(identifier, userMap);
        if (matches.size() == 1) {
            return matches.get(0);
        }
        return null;
    }

    // Returns all members whose full name matches the given name (case-insensitive)
    public List<User> findMembersByName(String name, HashMap<String, User> userMap) {
        List<User> matches = new ArrayList<>();
        for (User u : userMap.values()) {
            String fullName = u.getFirstName() + " " + u.getLastName();
            if (fullName.equalsIgnoreCase(name)) {
                matches.add(u);
            }
        }
        return matches;
    }

    // Update a member's name
    public void updateMemberName(User user, String newFirstName, String newLastName) {
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
    }

    // Update a member's username — re-keys the map. Returns true if successful.
    public boolean updateMemberUsername(User user, String newUsername, HashMap<String, User> userMap) {
        if (!isUsernameUnique(newUsername, userMap)) {
            return false;
        }
        userMap.remove(user.getUsername());
        user.setUsername(newUsername);
        userMap.put(newUsername, user);
        return true;
    }

    // Update a member's password
    public void updateMemberPassword(User user, String newPassword) {
        user.setPassword(newPassword);
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
