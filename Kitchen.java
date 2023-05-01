import java.util.ArrayList;

public class Kitchen extends Room{
    
    public Kitchen(String name, String description){
        super(name, description);
    }

    public void conversation(ArrayList<String> wordList){
        //inherit from Room
        super.conversation(wordList);
        //we are addressing an item 
        //if (this.addressing!=null){
            //if it's the first in the items list (TABLE)
           // if(this.addressing==items.get(0)){
                //print options 
        //        // this.addressing.showOptions();
        //     }
        // }

    }
}
