package br.com.consultai.model;

import java.util.Date;

/**
 * Created by leonardo.ribeiro on 17/11/2017.
 */

public class Rotina {
    protected String id_usuario;
    protected Date hora;
    protected float valor;
    protected int weekday;

    public Rotina(){

    }

    public Rotina( String id_usuario, Date hora,  float valor, int weekday ) {
        this.id_usuario = id_usuario;
        this.hora = hora;
        this.valor = valor;
        this.weekday = weekday;

    }



}
