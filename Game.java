
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import com.google.common.graph.*;

public class Game {

    String location;
    Item holding;
    Item climbedOn;
    Boolean haveDrunk;
    Boolean haveEaten;
    Scanner in;
    ImmutableGraph map;
    Kitchen kitchen;
    Garden garden;
    Bedroom bedroom;
    LivingRoom living;
    Bathroom bathroom;
    Item blanket;
    Item windowSeat;


    public Game(){
        //Construct rooms

        //kitchen 
        Kitchen kitchen=new Kitchen("kitchen", "A black-and-white tiled clean kitchen");
        this.kitchen=kitchen;
        Item table=new Item("table","a sturdy wooden table", false,false,true,false,true, false,true);
        Item milk=new Item("milk","A glass of milk",false,true,false,false,false,true,false);
        table.addChild(milk);
        milk.addParent(table);
        this.kitchen.addItem(table);
        this.kitchen.addItem(milk);

        //garden
        Garden garden=new Garden("garden","A lovely enclosed garden space with lots of flowers and a light breeze");
        this.garden=garden;
        Item pond=new Item("pond","a stone pond in the center filled with water and fish",false, false, false,false, false, false, true);
        Item pondWater=new Item("water in pond","cold, refreshing pond water",false, true, false, false, false, true, false);
        Item fish=new Item("fish","appetizing koi fish swimming lazily", true, false, false, false, false, true, false);
        pond.addChild(pondWater);
        pond.addChild(fish);
        pondWater.addParent(pond);
        fish.addParent(pond);
        this.garden.addItem(pond);
        this.garden.addItem(pondWater);
        this.garden.addItem(fish);


        //bedroom
        Bedroom bedroom= new Bedroom("bedroom", "A spacious yet cozy bedroom ");
        this.bedroom=bedroom;
        Item bed=new Item("bed","A large bed with a blanket on top of it.",false,false,true,false,true,false,true);
        Item blanket=new Item("blanket","A cozy handmade afghan blanket",false,false, false,true,false, true,false);
        this.blanket=blanket;
        bed.addChild(blanket);
        this.blanket.addParent(bed);
        Item dresser=new Item("dresser","A handsome chestnut dresser filled with woolen sweaters",false,false,false,false,false,false,false);
        this.bedroom.addItem(bed);
        this.bedroom.addItem(blanket);
        this.bedroom.addItem(dresser);

        //living room 
        LivingRoom living=new LivingRoom("living room", "a homey living room");
        this.living=living; 
        Item windowSeat=new Item("window seat","a cozy yet bare window seat drenched in sunlight",false,false,true, false,true,false,false);
        this.windowSeat=windowSeat;
        this.living.addItem(windowSeat);

        //bathroom
        Bathroom bathroom= new Bathroom("bathroom", "a tiled and clean bathroom");
        this.bathroom=bathroom;
        Item bathtub=new Item("bathtub","a large clawfoot bathtub",false,false,true,false,false,false,false);
        Item toilet=new Item("toilet","a toilet with water inside",false,false,true,false,true,false,true);
        Item toiletWater=new Item("toilet water","water in the toilet",false, true,false,false,false,true,false);
        toilet.addChild(toiletWater);
        toiletWater.addParent(toilet);
        this.bathroom.addItem(bathtub);
        this.bathroom.addItem(toilet);
        this.bathroom.addItem(toiletWater);

        //Build graph 
        ImmutableGraph<Room> house = GraphBuilder.undirected()
            .<Room>immutable()
            .putEdge(kitchen,bedroom)
            .putEdge(kitchen,garden)
            .putEdge(kitchen,living)
            .putEdge(living,bedroom)
            .putEdge(bedroom,bathroom)
            .build();

        //set the house to this.map
        this.map=house; 
        //set the current location to kitchen
        this.location=kitchen.getName();
        //set current statuses
        this.holding=null;
        this.climbedOn=null;
        this.haveDrunk=false;
        this.haveEaten=false;

    }

    //method to see if cat can nap
    public boolean canNap(){
        if(this.location.equals(this.living.getName())&& this.haveDrunk && this.haveEaten && this.holding==this.blanket&&this.climbedOn==this.windowSeat){
            return true;
        }
        else{
            return false;
        }
    }
    //method to get neighbors
    public ArrayList<Room> getNeighbors(Room current){
        Set<Room> setOfNeighbors= new HashSet<Room>();
        ArrayList<Room> listOfNeighbors= new ArrayList<Room>();

        setOfNeighbors=map.adjacentNodes(current);

        for (Room r: setOfNeighbors){
            listOfNeighbors.add(r);
        }

        return listOfNeighbors;
    }

    //print neighbors
    public void printNeighbors(Room current){
        for (Room r : this.getNeighbors(current)){
            System.out.println(r.getName());
        }    
    }
    

}
