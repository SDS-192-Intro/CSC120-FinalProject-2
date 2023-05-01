
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import com.google.common.graph.*;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    String location;
    Item holding;
    Item climbedOn;
    Boolean haveDrunk;
    Boolean haveEaten;
    Scanner in;
    ImmutableGraph<Room> map;
    Kitchen kitchen;
    Garden garden;
    Bedroom bedroom;
    Parlor parlor;
    Bathroom bathroom;
    Item blanket;
    Item windowSeat;
    Timer timer;


    public Game(){
        //Construct rooms

        //kitchen 
        Kitchen kitchen=new Kitchen("kitchen", "a black-and-white tiled clean kitchen. ");
        this.kitchen=kitchen;
        Item table=new Item("table","A sturdy wooden table", false,false,true,false,true, false,true);
        Item milk=new Item("milk","A glass of milk",false,true,false,false,false,true,false);
        table.addChild(milk);
        milk.addParent(table);
        this.kitchen.addItem(table);
        this.kitchen.addItem(milk);

        //garden
        Garden garden=new Garden("garden","a lovely enclosed garden space with lots of flowers and a light breeze. ");
        this.garden=garden;
        Item pond=new Item("pond","a stone pond in the center filled with water",false, false, false, false, false, false, true);
        Item fish=new Item("fish","appetizing koi fish swimming lazily", true, false, false, false, false, true, false);
        pond.addChild(fish);
        fish.addParent(pond);
        this.garden.addItem(pond);
        this.garden.addItem(fish);


        //bedroom
        Bedroom bedroom= new Bedroom("bedroom", "a spacious yet cozy bedroom. ");
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

        //parlor
        Parlor parlor=new Parlor("Parlor", "a homey parlor room. ");
        this.parlor=parlor;
        Item windowSeat=new Item("window seat","a cozy yet bare window seat drenched in sunlight",false,false,true, false,true,false,false);
        this.windowSeat=windowSeat;
        this.parlor.addItem(windowSeat);

        //bathroom
        Bathroom bathroom= new Bathroom("bathroom", "a tiled and clean bathroom. ");
        this.bathroom=bathroom;
        Item bathtub=new Item("bathtub","a large clawfoot bathtub",false,false,true,false,false,false,false);
        Item toilet=new Item("toilet","a toilet with water inside",false,false,true,false,true,false,true);
        Item water=new Item("water", "cool, clean refreshing water in the toilet",false,true,false,false,false,true,false);
        toilet.addChild(water);
        water.addParent(toilet);
        this.bathroom.addItem(bathtub);
        this.bathroom.addItem(toilet);
        this.bathroom.addItem(water);

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

    public void changeClimbedOn(Item i){
        this.climbedOn=i;
    }

    public void changeHaveEaten(Boolean b){
        this.haveEaten=b;
    }

    public void changeHaveDrunk(Boolean b){
        this.haveDrunk=b;
    }

    public void changeHolding(Item i){
        this.holding=i;
    }
        


    public void play(){

        System.out.println("Welcome! In this game, you are a cat in a house searching for the best place to take a nap. In order to nap, you must...");
        System.out.println("* have eaten");
        System.out.println("* have drunk something");
        System.out.println("* have a blanket to sleep on");
        System.out.println("* be in direct sunlight\n");
        System.out.println("But be careful! Time is running out-- the sun is setting, and you have to get a good nap in before the sunset\n\n");
        //TIMER SECTION 
        System.out.println("Right now, it is 12 pm. The sun is going to set at 7 pm. Cat time is different than human time (nine lives and all), so you will get warnings throughout the game of the time.");
        System.out.println("If you fail to nap before sunset, you will lose the game :(");
        
        Timer timer=new Timer();
        long first=60000;
        long second=12000;
        long third=18000;
        //first warning
        timer.schedule(new TimerTask(){
            public void run(){
                System.out.println("It's already 2:24 pm! Hurry-- you have to nap before the sun sets. Remember, you have to: ");
                System.out.println("* have eaten");
                System.out.println("* have drunk something");
                System.out.println("* have a blanket to sleep on");
                System.out.println("* be in direct sunlight \nBefore you can nap.");
            }
        },first);
        //second warning
        timer.schedule(new TimerTask(){
            public void run(){
                System.out.println("It's already 3:48 pm! Hurry-- you have to nap before the sun sets. Remember, you have to: ");
                System.out.println("* have eaten");
                System.out.println("* have drunk something");
                System.out.println("* have a blanket to sleep on");
                System.out.println("* be in direct sunlight \nBefore you can nap.");
            }
        },second);
        //third warning
        timer.schedule(new TimerTask(){
            public void run(){
                System.out.println("It's already 5:12 pm! Hurry-- you have to nap before the sun sets. Remember, you have to: ");
                System.out.println("* have eaten");
                System.out.println("* have drunk something");
                System.out.println("* have a blanket to sleep on");
                System.out.println("* be in direct sunlight \nBefore you can nap.");
            }
        },third);
        

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

            //turn response into an array list of words 
            ArrayList<String> wordList = new ArrayList<String>();
            for(String word : response.split(" ")) {
                wordList.add(word);
            }

            //check that response is more than one word
            if(wordList.size()<2){
                System.out.println("Your response should be more than one word. Try saying 'Go to' a room or an item.");
                continue;
            }

            try{
                String word1=wordList.get(0);
                String word2=wordList.get(1);
                String word3=wordList.get(2);
                boolean isGo=false;
                if (word1.equals("Go")||word1.equals("go")){
                    isGo=true;
                }
                if (isGo&& word2.equals("to")&& this.isARoom(word3)){
                   //make array list with all the neighbors for a given room
        
                   ArrayList<Room> neighbors=this.getNeighbors(locationAsRoom);

                    if(neighbors.contains(this.turnNameToRoom(word3))){
                        this.location=word3;
                        locationAsRoom=this.turnNameToRoom(this.location);

                        System.out.println("\nRight now, you are in "+locationAsRoom.getDescription()+locationAsRoom.lookAround());
                        System.out.println("There are doors connecting to: ");
                        this.printNeighbors(this.turnNameToRoom(this.location));

                        continue;
                    }
                    if(this.isARoom(word3)){
                        System.out.println("There is not a door from the "+this.location+" to the "+ word3+". The "+this.location+" connects to: ");
                        this.printNeighbors(locationAsRoom);
                        continue;
                   }
               }
            } catch (Exception e){
                
            }


            if (this.location.equals("kitchen")){
                this.kitchen.conversation(wordList);
            }
            if(this.location.equals("garden")){
                this.garden.conversation(wordList);
            }
            if(this.location.equals("bedroom")){
                this.bedroom.conversation(wordList);
            }
            if(this.location.equals("bathroom")){
                this.bathroom.conversation(wordList);
            }
            if(this.location.equals("parlor")){
                this.parlor.conversation(wordList);
            }

            

        }
    }
    

}
