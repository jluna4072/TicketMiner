package model.users;

public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String userID;

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
    
    public String getUserID() { 
        return userID; 
    }
    public void setUserID(String userID) { 
        this.userID = userID; 
    }
    
    public void login() {
    }
    
    public void logout() {
    }
}
