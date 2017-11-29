package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leonardo.ribeiro on 17/11/2017.
 */

public class Rotina implements Serializable{

    @SerializedName("id_rotina")
    private String idRotina;

    @SerializedName("id_usuario")
    private String idUsuario;

    @SerializedName("login_token")
    private String loginToken;

    private int domingo;
    private int segunda;
    private int terca;
    private int quarta;
    private int quinta;
    private int sexta;
    private int sabado;

    private int flag;

    private String hora;

    private double valor;

    private int tipo;

    public Rotina(){}

    public Rotina(String idRotina, String idUsuario, String loginToken, int domingo, int segunda, int terca, int quarta, int quinta, int sexta, int sabado, int flag, String hora, double valor, int tipo) {
        this.idRotina = idRotina;
        this.idUsuario = idUsuario;
        this.loginToken = loginToken;
        this.domingo = domingo;
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;
        this.flag = flag;
        this.hora = hora;
        this.valor = valor;
        this.tipo = tipo;
    }

    public void setDays(int[] days){
        domingo = days[0];
        segunda = days[1];
        terca = days[2];
        quarta = days[3];
        quinta = days[4];
        sexta = days[5];
        sabado = days[6];
    }

    public int[] getDays(){
        return new int[]{domingo, segunda, terca, quarta, quinta, sexta, sabado};
    }

    public String getIdRotina() {
        return idRotina;
    }

    public void setIdRotina(String idRotina) {
        this.idRotina = idRotina;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public int getDomingo() {
        return domingo;
    }

    public void setDomingo(int domingo) {
        this.domingo = domingo;
    }

    public int getSegunda() {
        return segunda;
    }

    public void setSegunda(int segunda) {
        this.segunda = segunda;
    }

    public int getTerca() {
        return terca;
    }

    public void setTerca(int terca) {
        this.terca = terca;
    }

    public int getQuarta() {
        return quarta;
    }

    public void setQuarta(int quarta) {
        this.quarta = quarta;
    }

    public int getQuinta() {
        return quinta;
    }

    public void setQuinta(int quinta) {
        this.quinta = quinta;
    }

    public int getSexta() {
        return sexta;
    }

    public void setSexta(int sexta) {
        this.sexta = sexta;
    }

    public int getSabado() {
        return sabado;
    }

    public void setSabado(int sabado) {
        this.sabado = sabado;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Rotina{" +
                "idRotina=" + idRotina +
                ", idUsuario='" + idUsuario + '\'' +
                ", loginToken='" + loginToken + '\'' +
                ", domingo=" + domingo +
                ", segunda=" + segunda +
                ", terca=" + terca +
                ", quarta=" + quarta +
                ", quinta=" + quinta +
                ", sexta=" + sexta +
                ", sabado=" + sabado +
                ", flag=" + flag +
                ", hora='" + hora + '\'' +
                ", valor=" + valor +
                ", tipo=" + tipo +
                '}';
    }
}
