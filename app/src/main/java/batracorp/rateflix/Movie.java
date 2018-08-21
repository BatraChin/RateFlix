package batracorp.rateflix;

/**
 * Created by Batrachin on 6/2/2017.
 */

public class Movie {

    private String name;
    private String description;
    private String picture;
    private float Rating;


    /*private Comment[]   comments ;
    private int quantityComments;*/


    public Movie(String name,String description,float Rating,String picture){
        this.name=name;
        this.description=description;
        this.picture=picture;
        this.Rating=Rating;


        /*comments= new Comment[10];
        quantityComments=0;*/
    }

    public String getTitle(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getPicture(){
        return picture;
    }
    public float  getRating(){return Rating;}
    public String toString(){
        return getTitle()+"\n"+getDescription()+"\n" +getPicture()+"\n"+getRating();
    }

   /* public void addComment(Comment c){
        comments[quantityComments]=c;
        quantityComments++;
    }

    public void calculateRating(){
        float average=0;
        for(int i=0;i<quantityComments;i++)
            average+=comments[i].getRating();
        average/=quantityComments;
        Rating=average;
    }*/

}
