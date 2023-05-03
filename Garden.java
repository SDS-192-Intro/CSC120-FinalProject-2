import java.util.ArrayList;

public class Garden extends Room {

    String name;
    String description;
    
    public Garden(String name, String description,Game game){
        super(name, description,game);
    }
    
    //Override for pond (change wording)
    public void conversation(ArrayList<String> wordList){
        try{
        } catch (Exception e){}
    }

}
