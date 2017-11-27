package br.com.consultai.model;

import java.util.Date;

/**
 * Created by leonardo.ribeiro on 17/11/2017.
 */

public class Cartao {

    protected String apelido;
    protected String numero_cartao;
    protected float saldo;
    protected boolean estudante;
    protected String id_usuario;
    protected String login_token;

    public Cartao(){

    }

    public Cartao( String apelido, String numero_cartao,  float saldo, boolean estudante, String id_usuario, String login_token ) {
        this.apelido = apelido;
        this.numero_cartao = numero_cartao;
        this.saldo = saldo;
        this.estudante = estudante;
        this.id_usuario = id_usuario;
        this.login_token = login_token;

    }

    public String getApelido(){
        return apelido;
    }

    public void setApelido(String apelido){
        this.apelido = apelido;
    }
    public String getNumero_cartao(){
        return numero_cartao;
    }

    public void setNumero_cartao(String numero_cartao){
        this.numero_cartao = numero_cartao;
    }
    public float getSaldo(){
        return saldo;
    }

    public void setSaldo(float saldo){
        this.saldo = saldo;
    }
    public Boolean getEstudante(){
        return estudante;
    }

    public void setEstudante(Boolean estudante){
        this.estudante = estudante;
    }

    public String getId_usuario(){
        return id_usuario;
    }

    public void setId_usuario(String id_usuario){
        this.id_usuario = id_usuario;
    }

    public String getLogin_token(){
        return login_token;
    }

    public void setLogin_token(String login_token){
        this.login_token = login_token;
    }
}
