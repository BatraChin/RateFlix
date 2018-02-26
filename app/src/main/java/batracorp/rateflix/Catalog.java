package batracorp.rateflix;

/**
 * Created by Lupe on 6/2/2017.
 */

public class Catalog {

    private Movie[] movies;
    private int quantity;

    public Catalog(){
        movies=new Movie[10];
        quantity=0;
    }

    public void addMovie(Movie movie){
        movies[quantity]=movie;
        quantity++;
    }
}
