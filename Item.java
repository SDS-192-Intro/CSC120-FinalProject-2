public class Item {
    
    String name;
    String description;
    boolean isEdible;
    boolean isDrinkable;
    boolean isClimbable;
    boolean isTakeable;
    boolean isJumpOffable;
    boolean hasParent;
    Item parent;
    boolean hasChild;
    Item child; 

    //constructor
    public Item(String name, String description, boolean isEdible, boolean isDrinkable, boolean isClimbable, boolean isTakeable, boolean isJumpOffable, boolean hasParent, boolean hasChild){
        this.name=name;
        this.description=description;
        this.isEdible=isEdible;
        this.isDrinkable=isDrinkable;
        this.isClimbable=isClimbable;
        this.isTakeable=isTakeable;
        this.isJumpOffable=isJumpOffable;
        this.hasParent=hasParent;
        if (this.hasParent){
            this.parent=null;
        }
        this.hasChild=hasChild;
        if (this.hasChild){
            this.child=null;
        }

    }

    //show options method 
    public void showOptions(){
        String toPrint= "\nOptions for: "+this.name+"\n";
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

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Boolean isClimbable(){
        if(this.isClimbable){
            return true;
        }else{
            return false;
        }
    }

    public Boolean isEdible(){
        if(this.isEdible){
            return true;
        }else{
            return false;
        }
    }

    public Boolean isDrinkable(){
        if(this.isDrinkable){
            return true;
        }else{
            return false;
        }
    }

    public void addParent(Item i){
        this.parent=i;
    }

    public boolean hasParent(){
        if (this.hasParent){
            return true;
        }else{
            return false;
        }
    }

    public Item getParent(){
        //add error testing 
        return this.parent;
    }

    public void addChild(Item i){
        this.child=i;
    }

    public boolean hasChild(){
        if(this.hasChild){
            return true;
        }else{
            return false;
        }
    }

    public Item getChild(){
        return this.child;
    }
}