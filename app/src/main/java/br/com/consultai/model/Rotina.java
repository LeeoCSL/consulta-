package br.com.consultai.model;

import java.util.Date;

/**
 * Created by leonardo.ribeiro on 17/11/2017.
 */

public class Rotina {
    protected String id_usuario;
    protected String hora;
    protected String valor;
    protected String weekday;
    protected String tipo;

    public Rotina(){

    }

    public Rotina( String id_usuario, String hora,  String valor, String weekday, String tipo ) {
        this.id_usuario = id_usuario;
        this.hora = hora;
        this.valor = valor;
        this.weekday = weekday;
        this.tipo = tipo;
    }

    public String getId_usuario(){
        return id_usuario;
    }

    public void setId_usuario(String id_usuario){
        this.id_usuario = id_usuario;
    }
    public String getHora(){
        return hora;
    }

    public void setHora(String hora){
        this.hora = hora;
    }
    public String getValor(){
        return valor;
    }

    public void setValor(String valor){
        this.valor = valor;
    }
    public String getWeekday(){
        return weekday;
    }

    public void setWeekday(String weekday){
        this.weekday = weekday;
    }
    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }



}
