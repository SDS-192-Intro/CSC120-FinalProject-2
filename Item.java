public class Item {
    
    String name;
    String description;
    boolean isEdible;
    boolean isDrinkable;
    boolean isClimbable;
    boolean isTakeable;
    boolean isJumpOffable;

    //constructor
    public Item(String name, String description, boolean isEdible, boolean isDrinkable, boolean isClimbable, boolean isTakeable, boolean isJumpOffable){
        this.name=name;
        this.description=description;
        this.isEdible=isEdible;
        this.isDrinkable=isDrinkable;
        this.isClimbable=isClimbable;
        this.isTakeable=isTakeable;
        this.isJumpOffable=isJumpOffable;
    }

    //show options method 
    public void showOptions(){
        String toPrint= "Options for: "+this.name+"\n";
        toPrint+=description+"\n";
        if (this.isEdible){
            toPrint+="You can eat the "+this.name+"\n";
        }
        if (this.isDrinkable){
            toPrint+="You can drink the "+this.name+"\n";
        } 
        if (this.isClimbable){
            toPrint+="You can climb the "+this.name+"\n";
        }
        if (this.isTakeable){
            toPrint+="You can take the "+this.name+"\n";
        }
        if (this.isJumpOffable){
            toPrint+="You can jump off of the "+this.name+"\n";
        }
        System.out.println(toPrint);
    }
}