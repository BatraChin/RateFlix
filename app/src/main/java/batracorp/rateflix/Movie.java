package batracorp.rateflix;

/**
 * Created by Batrachin on 6/2/2017.
 */

public class Movie {

    private String name;
    private String description;
    private String picture;


    private Comment[]   comments ;
    private int quantityComments;
    private float punctuation;


    public Movie(String name,String description,String picture){
        this.name=name;
        this.description=description;
        this.picture=picture;


        comments= new Comment[10];
        quantityComments=0;
    }

    public String getTitle(){
        return name;
    }
    public String getDescription(){
        return description;
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
