
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import com.google.common.graph.*;
import java.util.Timer;
import java.util.TimerTask;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;


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
    Integer current;
    Boolean readyToNap;
    Boolean stuckInBath;


    public Game(){
        //Construct rooms

        //kitchen 
        Kitchen kitchen=new Kitchen("kitchen", "a black-and-white tiled clean kitchen. ",this);
        this.kitchen=kitchen;
        Item table=new Item("table","A sturdy wooden table", false,false,true,false,true, false,true);
        Item milk=new Item("milk","A glass of milk",false,true,false,false,false,true,false);
        table.addChild(milk);
        milk.addParent(table);
        this.kitchen.addItem(table);
        this.kitchen.addItem(milk);

        //garden
        Garden garden=new Garden("garden","a lovely enclosed garden space with lots of flowers and a light breeze. ",this);
        this.garden=garden;
        Item pond=new Item("pond","a stone pond in the center filled with water",false, false, false, false, false, false, true);
        Item fish=new Item("fish","appetizing koi fish swimming lazily", true, false, false, false, false, true, false);
        pond.addChild(fish);
        fish.addParent(pond);
        this.garden.addItem(pond);
        this.garden.addItem(fish);


        //bedroom
        Bedroom bedroom= new Bedroom("bedroom", "a spacious yet cozy bedroom. ",this);
        this.bedroom=bedroom;
        Item bed=new Item("bed","A large bed with a blanket on top of it.",false,false,true,false,true,false,true);
        Item blanket=new Item("blanket","A cozy handmade afghan blanket",false,false, false,true,false, true,false);
        this.blanket=blanket;
        bed.addChild(blanket);
        this.blanket.addParent(bed);
        Item dresser=new Item("dresser","A handsome chestnut dresser filled with woolen sweaters",false,false,true,false,true,false,false);
        this.bedroom.addItem(bed);
        this.bedroom.addItem(blanket);
        this.bedroom.addItem(dresser);

        //parlor
        Parlor parlor=new Parlor("Parlor", "a homey parlor room. ",this);
        this.parlor=parlor;
        Item windowSeat=new Item("window seat","a cozy yet bare window seat drenched in sunlight",false,false,true, false,true,false,false);
        Item couch=new Item("couch","a plump green corduroy couch",false,false,true,false,true,false,false);
        this.windowSeat=windowSeat;
        this.parlor.addItem(windowSeat);
        this.parlor.addItem(couch);

        //bathroom
        Bathroom bathroom= new Bathroom("bathroom", "a tiled and clean bathroom. ",this);
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

        //start integer for timing out
        this.current=0;

        //start ready to nap boolean
        this.readyToNap=false;

        //start stuck in bath boolean
        this.stuckInBath=false;

    }

    //method to see if cat can nap
    public boolean canNap(){
        //Ways to check if canNap() is working right 
        // System.out.println("parlor: "+ this.location.equals("parlor"));
        // System.out.println("drink: "+this.haveDrunk);
        // System.out.println("eat: "+this.haveEaten);
        // if(Objects.nonNull(this.holding)){
        //     System.out.println("hold: "+this.holding.equals(this.blanket));
        // }
        // if(Objects.nonNull(this.climbedOn)){
        //     System.out.println("climb: "+this.climbedOn.equals(this.windowSeat));
        // }

        if(Objects.nonNull(this.holding)&&Objects.nonNull(this.climbedOn)){
            if(this.location.equals("parlor")&& this.haveDrunk && this.haveEaten && this.holding.equals(this.blanket) && this.climbedOn.equals(this.windowSeat)){
                return true;
            }
            else{
                return false;
            }
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

    public Item getClimbedOn(){
        return this.climbedOn;
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

    public void changeStuckInBath(Boolean b){
        this.stuckInBath=b;
    }
        
    public Integer getTimeSeconds(){
        //getting current time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //turn it into HH:mm:ss format
        String nowDtf=dtf.format(now);
        Integer hours=60*60*Integer.parseInt(nowDtf.split(":")[0]);
        Integer minutes=60*Integer.parseInt(nowDtf.split(":")[1]);
        Integer seconds=Integer.parseInt(nowDtf.split(":")[2]);
        //find total seconds
        Integer time=hours+minutes+seconds;
        return time;
    }

    public void play(){

        System.out.println("\n"+"------------------------------------------------------------------------------------------");
        System.out.println("Welcome! In this game, you are a cat in a house searching for the best place to take a nap. In order to nap, you must...");
        System.out.println("* have eaten");
        System.out.println("* have drunk something");
        System.out.println("* have a blanket to sleep on");
        System.out.println("* be in direct sunlight\n");
        System.out.println("But be careful! Time is running out-- the sun is setting, and you have to get a good nap in before the sunset\n\n");
        System.out.println("Right now, it is 12 pm. The sun is going to set at 7 pm. Cat time is different than human time (nine lives and all), so you will get warnings throughout the game of the time.");
        System.out.println("If you fail to nap before sunset, you will lose the game :(");
        System.out.println("---------------------------------------------------------------------------------------------");
        

        System.out.println("\nIn this house, there is: ");
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

        

        in=new Scanner(System.in);

        //get start time of game
        Integer start=getTimeSeconds();
        //initialize 
        this.current=getTimeSeconds(); 
        //create Boolean for end of game 
        Boolean gameGoing=(start-this.current<300);

        while(gameGoing && !this.readyToNap && !this.stuckInBath){

            // TIMER SECTION 
            // Timer timer=new Timer();
            // long first=6000;
            // long second=120000;
            // long third=180000;
            // //first warning
            // timer.schedule(new TimerTask(){
            //     public void run(){
            //         if(this.ready){
            //             System.out.println("It's already 2:24 pm! Hurry-- you have to nap before the sun sets. Remember, you have to: ");
            //             System.out.println("* have eaten");
            //             System.out.println("* have drunk something");
            //             System.out.println("* have a blanket to sleep on");
            //             System.out.println("* be in direct sunlight \nBefore you can nap.");
            //         }
            //     }
            // },first);
            // //second warning
            // timer.schedule(new TimerTask(){
            //     public void run(){
            //         if(this.ready){
            //             System.out.println("It's already 3:48 pm! Hurry-- you have to nap before the sun sets. Remember, you have to: ");
            //             System.out.println("* have eaten");
            //             System.out.println("* have drunk something");
            //             System.out.println("* have a blanket to sleep on");
            //             System.out.println("* be in direct sunlight \nBefore you can nap.");
            //         }  
            //     }
            // },second);
            // //third warning
            // timer.schedule(new TimerTask(){
            //     public void run(Boolean ready){
            //         if(ready){
            //             System.out.println("It's already 5:12 pm! Hurry-- you have to nap before the sun sets. Remember, you have to: ");
            //             System.out.println("* have eaten");
            //             System.out.println("* have drunk something");
            //             System.out.println("* have a blanket to sleep on");
            //             System.out.println("* be in direct sunlight \nBefore you can nap.");
            //         }
            //     }
            // },third);
        

            System.out.println();

            Room locationAsRoom=this.turnNameToRoom(this.location);

            System.out.println("What would you like to do?");
            System.out.print(">>> ");

            String response=in.nextLine();

            //First, try to parse to see if we need to change this.location 

            //Turn response into an array list of words 
            ArrayList<String> wordList = new ArrayList<String>();
            for(String word : response.split(" ")) {
                wordList.add(word);
            }

            //Check that the response is more than one word
            if(wordList.size()<2){
                System.out.println("Your response should be more than one word. Try saying 'Go to' a room or an item.");
                continue;
            }

            try{
                //get words from response 
                String word1=wordList.get(0);
                String word2=wordList.get(1);
                String word3=wordList.get(2);
                String word4=wordList.get(3);

                boolean word1IsGo=word1.equals("Go")||word1.equals("go");

                //Check if the response is "Go to the *room*"
                if (word1IsGo && word2.equals("to") && word3.equals("the")&&this.isARoom(word4)){
    
                    //Make array list with all the neighbors for the current room
                    ArrayList<Room> neighbors=this.getNeighbors(locationAsRoom);

                    //If the fourth word IS a neighbor to the current room 
                    if(neighbors.contains(this.turnNameToRoom(word4))){
                        //Change the location to the fourth word in the response
                        this.location=word4;
                        locationAsRoom=this.turnNameToRoom(this.location);

                        //Print update 
                        System.out.println("\nRight now, you are in "+locationAsRoom.getDescription()+locationAsRoom.lookAround());
                        System.out.println("There are doors connecting to: ");
                        //Print neighbors
                        this.printNeighbors(this.turnNameToRoom(this.location));

                        //Reset current time & canNap (restarting while loop)
                        this.current=getTimeSeconds(); 
                        gameGoing=(this.current-start<300);
                        this.readyToNap=this.canNap();
                        continue;
                    }
                //If the fourth word is NOT a neighbor 
                    else{
                        System.out.println("There is not a door from the "+this.location+" to the "+ word3+". The "+this.location+" connects to: ");
                        this.printNeighbors(locationAsRoom);

                        //Reset current time & canNap (restarting while loop)
                        this.current=getTimeSeconds(); 
                        gameGoing=(this.current-start<300);
                        this.readyToNap=this.canNap();
                        continue;
                   }
               }
            } catch (Exception e){
            }


            //If the current location is a room, send the response to that class 
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

            //reset current time & boolean
            this.current=getTimeSeconds();
            gameGoing=(this.current-start<300);
            //call canNap to update situation
            this.readyToNap=this.canNap();
        }
        
        if(this.canNap()){
            System.out.println("/nSNOOOOZE!!!! Yay! You have found something to eat, something to drink, a blanket, and a sunny spot and can now take a well-deserved nap.");
            System.out.println("GAME OVER.");
        }
        if(!gameGoing){
            System.out.println("The sun has set. The room is filled with pink light. You didn't nap, but nightfall is coming and you'll have a wonderful deep sleep.");
            System.out.println("GAME OVER.");
        }
        if(this.stuckInBath){
            System.out.println("GAME OVER.");
        }
    }
    


}
