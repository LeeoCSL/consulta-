package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by leonardo.ribeiro on 17/11/2017.
 */

public class Cartao {

    @SerializedName("id_usuario")
    private String idUsuario;

    @SerializedName("numero_cartao")
    private String numeroCartao;

    @SerializedName("login_token")
    private String loginToken;

    private String apelido;
    private double saldo;
    private int estudante;

    public Cartao(){}

    public Cartao(String idUsuario, String numeroCartao, String loginToken, String apelido, double saldo, int estudante) {
        this.idUsuario = idUsuario;
        this.numeroCartao = numeroCartao;
        this.loginToken = loginToken;
        this.apelido = apelido;
        this.saldo = saldo;
        this.estudante = estudante;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getEstudante() {
        return estudante;
    }

    public void setEstudante(int estudante) {
        this.estudante = estudante;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "idUsuario='" + idUsuario + '\'' +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", loginToken='" + loginToken + '\'' +
                ", apelido='" + apelido + '\'' +
                ", saldo=" + saldo +
                ", estudante=" + estudante +
                '}';
    }
}
