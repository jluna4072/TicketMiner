package model.users;

public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int userID;
    private String userType;

    public User() {
    }

    public User(int userID, String firstName, String lastName, String username, String password, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userID = userID;
        this.userType = userType;
    }

    public String getFirstName() { 
        return firstName; 
    }

    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }
    
    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public int getUserID() { 
        return userID; 
    }
    public void setUserID(int userID) { 
        this.userID = userID; 
    }
    
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void login() {
    }
    
    public void logout() {
    }
}
