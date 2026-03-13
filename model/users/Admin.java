package model.users;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }
}
