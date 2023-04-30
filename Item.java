public class Item {
    
    String name;
    String description;
    boolean isEdible;
    boolean isDrinkable;
    boolean isClimbable;
    boolean isTakeable;
    boolean isJumpOffable;
    boolean onItem;
    Item parent;
    boolean hasItem;
    Item child; 

    //constructor
    public Item(String name, String description, boolean isEdible, boolean isDrinkable, boolean isClimbable, boolean isTakeable, boolean isJumpOffable, boolean onItem, boolean hasItem){
        this.name=name;
        this.description=description;
        this.isEdible=isEdible;
        this.isDrinkable=isDrinkable;
        this.isClimbable=isClimbable;
        this.isTakeable=isTakeable;
        this.isJumpOffable=isJumpOffable;
        this.onItem=onItem;
        if (this.onItem){
            this.parent=null;
        }
        this.hasItem=hasItem;
        if (this.hasItem){
            this.child=null;
        }

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

    public void addParent(Item i){
        this.parent=i;
    }

    public void addChild(Item i){
        this.child=i;
    }
}