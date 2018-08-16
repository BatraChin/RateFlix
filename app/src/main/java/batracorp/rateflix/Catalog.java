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

    public void addMovie(String Title, String Description, String Picture){

        Movie movie =new Movie(Title,Description,Picture);
        if (quantity > movies.length) {
            duplicar();
        }

        movies[quantity]=movie;
        quantity++;
    }

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
