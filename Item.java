/* 
 * Item class 
 */
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

    /*
     * Item constructor 
     */
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

    /*
     * Method to show options available for an item 
     */
    public void showOptions(){
        String toPrint= "\nOptions for: "+this.name+"\n";
        if (this.isEdible){
            toPrint+="You can [eat the "+this.name+"]\n";
        }
        if (this.isDrinkable){
            toPrint+="You can [drink the "+this.name+"]\n";
        } 
        if (this.isClimbable){
            toPrint+="You can [climb the "+this.name+"]\n";
        }
        if (this.isTakeable){
            toPrint+="You can [take the "+this.name+"]\n";
        }
        if (this.isJumpOffable){
            toPrint+="You can [jump off of the "+this.name+"]\n";
        }
        System.out.println(toPrint);
    }

    /*
     * Getter for item name 
     */
    public String getName(){
        return this.name;
    }

    /*
     * Getter for item description
     */
    public String getDescription(){
        return this.description;
    }

    /*
     * Getter for item isClimbable 
     */
    public Boolean isClimbable(){
        return this.isClimbable;
    }

    /*
     * Getter for item isEdible 
     */
    public Boolean isEdible(){
        return this.isEdible;
    }

    /* 
     * Getter for item isDrinkable 
     */
    public Boolean isDrinkable(){
        return this.isDrinkable;
    }
    
    /*
     * Getter for isJumpOffAble
     */
    public Boolean isJumpOffAble(){
        return this.isJumpOffable;
    }

    /*
     * Setter for this.parent
     */
    public void addParent(Item i){
        this.parent=i;
    }

    /*
     * Getter for hasParent
     */
    public boolean hasParent(){
        return this.hasParent;
    }

    /*
     * Getter for parent 
     */
    public Item getParent(){
        //add error testing 
        return this.parent;
    }

    /*
     * Setter for child 
     */
    public void addChild(Item i){
        this.child=i;
    }

    /* 
     * Getter for hasChild
     */
    public boolean hasChild(){
        return this.hasChild;
    }

    /*
     * Getter for child
     */
    public Item getChild(){
        return this.child;
    }
}