
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
    Parlor parlor;
    Bathroom bathroom;
    Item blanket;
    Item windowSeat;


    public Game(){
        //Construct rooms

        //kitchen 
        Kitchen kitchen=new Kitchen("kitchen", "A black-and-white tiled clean kitchen");
        this.kitchen=kitchen;
        Item table=new Item("table","A sturdy wooden table", false,false,true,false,true, false,true);
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

        //parlor room 
        Parlor parlor=new Parlor("parlor room", "a homey parlor room");
        this.parlor=parlor;
        Item windowSeat=new Item("window seat","a cozy yet bare window seat drenched in sunlight",false,false,true, false,true,false,false);
        this.windowSeat=windowSeat;
        this.parlor.addItem(windowSeat);

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
            .putEdge(this.kitchen,this.bedroom)
            .putEdge(this.kitchen,this.garden)
            .putEdge(this.kitchen,this.parlor)
            .putEdge(this.parlor,this.bedroom)
            .putEdge(this.bedroom,this.bathroom)
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
        if(this.location.equals(this.parlor.getName())&& this.haveDrunk && this.haveEaten && this.holding==this.blanket&&this.climbedOn==this.windowSeat){
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
            String room=r.getName();
            room=room.substring(0, 1).toUpperCase() + room.substring(1);
            System.out.println("The "+room);
        }    
    }

    public boolean isARoom(String room){
        if (room.equals("bathroom") || room.equals("bedroom") || room.equals("garden")|| room.equals("parlor")||room.equals("kitchen")){
            return true;
        }else{
            return false;
        }
    }

    public Room turnNameToRoom(String name){
        if(name.equals("kitchen")||name.equals("Kitchen")){
            return this.kitchen;
        }
        if(name.equals("bathroom")||name.equals("Bathroom")){
            return this.bathroom;
        }
        if(name.equals("bedroom")){
            return this.bedroom;
        }
        if(name.equals("garden")){
            return this.garden;
        }
        if (name.equals("parlor")){
            return this.parlor;
        }
        else{
            //change 
            return this.kitchen;
        }
    }

    public void play(){

        System.out.println("Welcome! In this game, you are a cat in a house searching for the best place to take a nap. In order to nap, you must...");
        System.out.println("* have eaten");
        System.out.println("* have drunk something");
        System.out.println("* have a blanket to sleep on");
        System.out.println("* be in direct sunlight\n");
        System.out.println("But be careful! Time is running out-- the sun is setting, and you have to get a good nap in before the sunset\n\n");
        System.out.println("In this house, there is: ");
        System.out.println("* A kitchen, "+this.kitchen.getDescription());
        System.out.println("* A garden, "+this.garden.getDescription());
        System.out.println("* A parlor, "+this.parlor.getDescription());
        System.out.println("* A bedroom, "+this.bedroom.getDescription());
        System.out.println("* A bathroom, "+this.bathroom.getDescription());
        System.out.println("\nYou can 'Go to' a room, 'Go to' something within a room, 'Look around' to see what's in a room, and 'Show options' if you have gone to an object");
        System.out.println("\nRight now, you are in the kitchen. "+this.kitchen.lookAround());
        System.out.println("There are doors connecting to: ");
        this.printNeighbors(this.turnNameToRoom(this.location));
        System.out.println();

        //put timer here

        in=new Scanner(System.in);

        while(!this.canNap()){
            System.out.println();

            Room locationAsRoom=this.turnNameToRoom(this.location);

            System.out.println("What would you like to do?");
            System.out.print(">>> ");

            String response=in.nextLine();

            //First, try to parse to see if you need to change location 
            try{
                String word1=response.split(" ")[0];
                String word2=response.split(" ")[1];
                String word3=response.split(" ")[2];
    
    
                if (word1.equals("Go")||word1.equals("go")&& word2.equals("to")&& this.isARoom(word3)){
                   //make array list with all the neighbors for a given room
                    ArrayList<Room> neighbors=this.getNeighbors(locationAsRoom);


                    if(neighbors.contains(this.turnNameToRoom(word3))){
                        this.location=word3;
                        System.out.println("Changed current location");
                    }
                    else{
                        System.out.println("There is not a door from the "+this.location+" to the "+ word3+". The "+this.location+" connects to: ");
                        this.printNeighbors(locationAsRoom);
                    }
                }
            } catch (Exception e){
                //I don't care if it doesn't work 
            }



            

        }
    }
    

}
