package br.com.consultai.model;

import java.util.Date;

/**
 * Created by leonardo.ribeiro on 17/11/2017.
 */

public class Cartao {

    protected String apelido;
    protected String numero;
    protected float saldo;
    protected boolean estudante;

    public Cartao(){

    }

    public Cartao( String apelido, String numero,  float saldo, boolean estudante ) {
        this.apelido = apelido;
        this.numero = numero;
        this.saldo = saldo;
        this.estudante = estudante;

    }

    public String getApelido(){
        return apelido;
    }

    public void setApelido(String apelido){
        this.apelido = apelido;
    }
    public String getNumero(){
        return numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
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
}
