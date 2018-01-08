package br.com.consultai.model;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renan.boni on 24/11/2017.
 */

public class Mobile {

    @SerializedName("id_usuario")
    private String idUsuario;

    @SerializedName("serial_mobile")
    private String serialMobile;

    private String modelo;

    @SerializedName("sistema_operacional")
    private String sistemaOperacional;

    public Mobile(){}

    public Mobile(String idUsuario, String serialMobile, String modelo, String sistemaOperacional) {
        this.idUsuario = idUsuario;
        this.serialMobile = serialMobile;
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
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
        return "Mobile{" +
                "idUsuario='" + idUsuario + '\'' +
                ", serialMobile='" + serialMobile + '\'' +
                ", modelo='" + modelo + '\'' +
                ", sistemaOperacional='" + sistemaOperacional + '\'' +
                '}';
    }
}
