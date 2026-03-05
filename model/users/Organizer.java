package model.users;

public class Organizer extends User {
    public Organizer() {
    }
    public Organizer(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }
    public void createEvent() {
    }
}
