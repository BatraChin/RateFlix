package batracorp.rateflix;

/**
 * Created by Lupe on 6/2/2017.
 */

public class Movie {

    private String name;
    private float punctuation;
    private Comment[]   comments ;
    private int quantityComments;
    private String description;

    public Movie(String name,String description){
        this.name=name;
        this.description=description;
        comments= new Comment[10];
        quantityComments=0;
        description=new String();
    }

    public void addComment(Comment c){
        comments[quantityComments]=c;
        quantityComments++;
    }

    public void calculatePunctuation(){
        float average=0;
        for(int i=0;i<quantityComments;i++)
            average+=comments[i].getPunctuation();
        average/=quantityComments;
        punctuation=average;
    }
}
