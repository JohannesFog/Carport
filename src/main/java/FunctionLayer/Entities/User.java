package FunctionLayer.Entities;

/**
 * The purpose of User is to...
 * @author kasper
 */
public class User {

    public User(int phone, String email, String password, String name, String address, int zip , String role) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.zip = zip; 
        this.role = role;
    }
    
    public User ( String email, String password , String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    private int id; // just used to demo retrieval of autogen keys in UserMapper
    private String email;
    private String password; // Should be hashed and all
    private String role;
    private int phone;
    private int zip;
    private String address;
    private String name;

    public int getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole( String role ) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }
    
    public int getPhone() {
        return phone;
    }
    

}
