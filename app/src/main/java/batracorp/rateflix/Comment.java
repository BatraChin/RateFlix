package batracorp.rateflix;

/**
 * Created by Lupe on 6/2/2017.
 */

public class Comment {

    private User usuario;
    private String texto;
    private int   puntaje;

    public Comment(User usuario,String texto,int puntaje){

        this.usuario=usuario;
        this.texto=texto;
        this.puntaje=puntaje;
    }

    public void setText(String texto){
        this.texto=texto;
    }
    public  void setPunctuation(int puntaje){
        this.puntaje=puntaje;
    }
    public String getText(){
        return texto;
    }

    public int  getPunctuation(){
        return puntaje;
    }
    public User getUser(){
        return usuario;
    }
}
