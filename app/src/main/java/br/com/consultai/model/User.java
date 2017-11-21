package br.com.consultai.model;

/**
 * Created by leonardo.ribeiro on 27/10/2017.
 */

public class User {

//    protected String key;
//    protected String name;
//    protected String dtn;
    protected String user_email;
    protected String user_password;
//    protected String sexo;
    protected String device_brand;
    protected String notification_token;
    protected String user_id;
    protected String user_saldo;
//    protected String user_tipo;




    public User() {

    }
// String key, String name, String dtn, String sexo,  String user_tipo
    public User( String user_email, String user_password,  String device_brand, String notification_token, String user_id, String user_saldo ) {
//        this.key = key;
//        this.name = name;
//        this.dtn = dtn;
        this.user_email = user_email;
        this.user_password = user_password;
//        this.sexo = sexo;
        this.device_brand = device_brand;
        this.notification_token = notification_token;
        this.user_id = user_id;
        this.user_saldo = user_saldo;
//this.user_tipo = user_tipo;
    }



    public String getUser_saldo(){
        return user_saldo;
    }

    public void setUser_saldo(String user_saldo){
        this.user_saldo = user_saldo;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getDevice_brand(){
        return device_brand;
    }

    public void setDevice_brand(String device_brand){
        this.device_brand = device_brand;
    }

    public String getNotification_token(){
        return notification_token;
    }

    public void setNotification_token(String notification_token){
        this.notification_token = notification_token;
    }

//    public String getUser_tipo(){
//        return user_tipo;
//    }
//
//    public void setUser_tipo(String user_tipo){
//        this.user_tipo = user_tipo;
//    }

//    public String getSexo(){
//        return sexo;
//    }
//
//    public void setSexo(String sexo){
//        this.sexo = sexo;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

//    public String getDtn() {
//        return dtn;
//    }
//
//    public void setDtn(String dtn) {
//        this.dtn = dtn;
//    }
    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }




    @Override
    public String toString() {
        return "Customer{" +
//                "key='" + key + '\'' +
//                ", name='" + name + '\'' +
//                ", dtn='" + dtn + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
