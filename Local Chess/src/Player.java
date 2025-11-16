/**
 * Player class used to decide who's turn it is.
 * Has one value which is the color which will be either white or black depending on whose turn it is
 */
public class Player {
    String color;

    Player(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(String color){
        this.color = color;
    }
}
