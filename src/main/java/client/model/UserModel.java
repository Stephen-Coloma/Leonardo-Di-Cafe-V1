package client.model;

public class UserModel {
    private String fullName;
    private String username;
    private String address;
    private String email;
    private String password;

    // Constructors
    public UserModel(String fullName, String username, String address, String email, String password){
        this.fullName = fullName;
        this.username = username;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress() {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }

    public void registerUser() {
        // Logic to save the user details to the XML File
    }

    public boolean validateCredentials(String username, String password) {
        // Logic to check if the provided username and password match
        return this.username.equals(username) && this.password.equals(password);
    }
}// End of UserModel Class
