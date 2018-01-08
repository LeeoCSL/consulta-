package br.com.consultai.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renan.boni on 24/11/2017.
 */

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private char sexo;

    @SerializedName("notification_token")
    private String notificationToken;

    @SerializedName("login_token")
    private String loginToken;

    @SerializedName("id_usuario")
    private String idUsuario;

    @SerializedName("serial_mobile")
    private String serialMobile;

    private String modelo;

    @SerializedName("sistema_operacional")
    private String sistemaOperacional;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getSerialMobile() {
        return serialMobile;
    }

    public void setSerialMobile(String serialMobile) {
        this.serialMobile = serialMobile;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", sexo=" + sexo +
                ", notificationToken='" + notificationToken + '\'' +
                ", loginToken='" + loginToken + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", serialMobile='" + serialMobile + '\'' +
                ", modelo='" + modelo + '\'' +
                ", sistemaOperacional='" + sistemaOperacional + '\'' +
                '}';
    }
}
