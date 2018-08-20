package batracorp.rateflix;

import android.util.Log;

/**
 * Created by Batra on 6/2/2017.
 */

public class Catalog {

    private static Catalog instance=null;
    private Movie[] movies;
    private int quantity;
    private int cursor;

    private Catalog(){
        movies=new Movie[10];
        quantity=0;
        cursor=0;
    }
    public static Catalog getInstance(){

        if (instance ==null){

            instance = new Catalog();


        }
        return instance;

    }

    /**
     *
     * @return the Movie pointer by the cursor
     */
    public Movie getMovie(){
        if (quantity>0) {
            return movies[cursor];
        }
        else
            return null;
    }

    /**
     * Points the cursor of the catalog to its next available item
     */
    public void next(){
        cursor++;
        if(cursor==quantity){
            cursor=0;
        }

    }

    /**
     *
     * @param Title of the movie
     * @param Description  of the movie
     * @param Picture descripting the movie
     */
    public void addMovie(String Title, String Description, String Picture){

        Movie movie =new Movie(Title,Description,Picture);

        if (quantity > movies.length) {
            duplicar();
        }

        movies[quantity]=movie;

        quantity++;
    }

    /**
     *
     * @param movie to be removed. If not found, nothing occurs.
     */
    public void removeMovie(Movie movie){
        int i=0;
        boolean encontro=false;
        while (i<quantity && !encontro) {
            if (movies[i].equals(movie)) {
                encontro = true;
                i--;
            }
            i++;
        }
        if (encontro)
            comprimir(i);

    }
    private void duplicar(){
        Movie[] aux =  new Movie[quantity * 2];
        int i = 0;
        for (Movie m : movies) {
            aux[i] = m;
            i++;
        }
        movies = aux;
    }
    private void comprimir(int pos){
        int i;
        for (i=pos;i<quantity-1;i++)
            movies[i]=movies[i+1];
        movies[i+1]=null;
    }
}
