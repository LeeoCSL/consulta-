package br.com.consultai.model;

/**
 * Created by leonardo.ribeiro on 27/10/2017.
 */

public class User {
    protected String id;
    protected String email;
    protected String nome;
    protected String senha;
    protected String sexo;
    protected String notification_token;
    protected String serial_mobile;
    protected String modelo;
    protected String sistema_operacional;
    protected String imei;


    protected String user_saldo;
//    protected String user_tipo;




    public User() {

    }
// String key, String dtn, String sexo,  String user_tipo
    public User( String serial_mobile, String sistema_operacional, String user_email, String nome, String sexo, String user_password,  String device_brand, String notification_token, String user_id, String imei) {
//        this.key = key;
        this.nome = nome;
//        this.dtn = dtn;
        this.email = user_email;
        this.senha = user_password;
        this.sexo = sexo;
        this.modelo = device_brand;
        this.notification_token = notification_token;
        this.id = user_id;

        this.sistema_operacional = sistema_operacional;
        this.serial_mobile = serial_mobile;
        this.imei = imei;
//this.user_tipo = user_tipo;
    }

    public String getImei(){
        return imei;
    }

    public void setImei(String imei){
        this.imei = imei;
    }

    public String getSerial_mobile(){
        return serial_mobile;
    }

    public void setSerial_mobile(String serial_mobile){
        this.serial_mobile = serial_mobile;
    }

    public String getSistema_operacional(){
        return sistema_operacional;
    }

    public void setSistema_operacional(String sistema_operacional){
        this.sistema_operacional = sistema_operacional;
    }

    public String getUser_saldo(){
        return user_saldo;
    }

    public void setUser_saldo(String user_saldo){
        this.user_saldo = user_saldo;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getModelo(){
        return modelo;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
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

    public String getSexo(){
        return sexo;
    }

    public void setSexo(String sexo){
        this.sexo = sexo;
    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getDtn() {
//        return dtn;
//    }
//
//    public void setDtn(String dtn) {
//        this.dtn = dtn;
//    }
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }




    @Override
    public String toString() {
        return "Customer{" +
//                "key='" + key + '\'' +
//                ", name='" + name + '\'' +
//                ", dtn='" + dtn + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
