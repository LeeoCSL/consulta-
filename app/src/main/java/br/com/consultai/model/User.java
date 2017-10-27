package br.com.consultai.model;

/**
 * Created by leonardo.ribeiro on 27/10/2017.
 */

public class User {

    protected String key;
    protected String name;
    protected String dtn;
    protected String email;
    protected String password;



    public User() {

    }

    public User(String key, String name, String dtn, String email, String password) {
        this.key = key;
        this.name = name;
        this.dtn = dtn;
        this.email = email;
        this.password = password;


    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDtn() {
        return dtn;
    }

    public void setDtn(String dtn) {
        this.dtn = dtn;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    @Override
    public String toString() {
        return "Customer{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", dtn='" + dtn + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
