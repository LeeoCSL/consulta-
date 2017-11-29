package br.com.consultai.model;

/**
 * Created by leonardo.ribeiro on 28/11/2017.
 */


/**
 * Created by leonardo.ribeiro on 27/10/2017.
 */

public class Usuario2 {
    protected String id_usuario;
    protected String login_token;




    public Usuario2() {

    }

    public Usuario2(String id_usuario, String login_token) {

        this.id_usuario = id_usuario;
        this.login_token = login_token;

    }



    public String getId_uasuario(){
        return id_usuario;
    }

    public void setId_Usuario(String id_usuario){
        this.id_usuario = id_usuario;
    }

    public String getLogin_token(){
        return login_token;
    }

    public void setLogin_token(String login_token){
        this.login_token = login_token;
    }


    @Override
    public String toString() {
        return "User{" +

                ", id='" + id_usuario + '\'' +
                ", login_token='" + login_token + '\'' +
                '}';
    }
}
