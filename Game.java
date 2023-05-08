
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
import java.util.concurrent.TimeUnit;

/*
 * Class that keeps track of the cat's status, runs Scanner, and sends input to different room classes 
 */
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
    Boolean success;
    Integer start;


    /*
     * Constructor for the game: constructs each room, items within the room, organizes the rooms into a Guava map, and sets up game attributes 
     */
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
        Item pond=new Item("pond","A stone pond in the center filled with water",false, false, true, false, true, false, true);
        Item fish=new Item("fish","Appetizing koi fish swimming lazily", true, false, false, false, false, true, false);
        pond.addChild(fish);
        fish.addParent(pond);
        this.garden.addItem(pond);
        this.garden.addItem(fish);


        //bedroom
        Bedroom bedroom= new Bedroom("bedroom", "a spacious yet cozy bedroom. ",this);
        this.bedroom=bedroom;
        Item bed=new Item("bed","A large bed with a blanket on top of it",false,false,true,false,true,false,true);
        Item blanket=new Item("blanket","A cozy handmade afghan blanket",false,false, false,true,false, true,false);
        this.blanket=blanket;
        bed.addChild(blanket);
        this.blanket.addParent(bed);
        Item dresser=new Item("dresser","A handsome chestnut dresser filled with woolen sweaters",false,false,true,false,true,false,false);
        this.bedroom.addItem(bed);
        this.bedroom.addItem(blanket);
        this.bedroom.addItem(dresser);

        //parlor
        Parlor parlor=new Parlor("parlor", "a homey parlor room. ",this);
        this.parlor=parlor;
        Item windowSeat=new Item("window seat","A cozy yet bare window seat drenched in sunlight",false,false,true, false,true,false,false);
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
        
        //start the success boolean
        this.success=false;

        //get start time of game
        this.start=getTimeSeconds();

    }

    /*
     * Method to print out strings with a delay in between each character 
     * @param: String toPrint
     */
    public void slowPrint(String toPrint) {
        for (int i = 0; i<toPrint.length(); i++) {
          char c = toPrint.charAt(i);
          System.out.print(c);
          try {
            TimeUnit.MILLISECONDS.sleep(17);
          }
          catch (Exception e) {
          }
        }
        System.out.println("");
    }

    /*
     * Method to check whether the cat can nap yet or not 
     * @returns: boolean 
     */
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
    
    /*
     * Method to print out the status of the cat: the time left, whether it can nap yet or not. 
     */
    public void printNapStatus(){  
        String currentTime=this.timeConvert(this.current=getTimeSeconds()-this.start);
        System.out.println("\nIt is currently: "+currentTime+" PM. Remember, you need to take a nap before 7:00 PM!");
        if(this.haveEaten){
            this.slowPrint("* You have eaten.");
        } else{
            this.slowPrint("* You have NOT YET eaten.");
        }
        if(this.haveDrunk){
            this.slowPrint("* You have drunk something.");
        }else{
            this.slowPrint("* You have NOT YET drunk something.");
        }
        if(Objects.nonNull(this.holding)){
            if(this.holding.equals(this.blanket)){
                this.slowPrint("* You are holding a blanket to sleep on.");
            } 
        }else{
            this.slowPrint("* You are NOT YET holding a blanket to sleep on.");
        }
        if(this.location.equals("parlor")&&this.climbedOn.equals(this.windowSeat)){
            this.slowPrint("* You are in a sunny spot to sleep.");
        }else{
            this.slowPrint("* You are NOT YET in a sunny spot to sleep.");
        }
    }
    
    /*
     * Method that returns a list of neighbors based on the current room
     * @param: current Room
     * @returns: Array list of neighbors 
     */ 
    public ArrayList<Room> getNeighbors(Room current){
        Set<Room> setOfNeighbors= new HashSet<Room>();
        ArrayList<Room> listOfNeighbors= new ArrayList<Room>();

        setOfNeighbors=map.adjacentNodes(current);

        for (Room r: setOfNeighbors){
            listOfNeighbors.add(r);
        }

        return listOfNeighbors;
    }

    /*
     * Method that prints a list of neighbors 
     * @param: current Room
     */
    public void printNeighbors(Room current){
        for (Room r : this.getNeighbors(current)){
            this.slowPrint("the "+r.getName()+" [go to the "+r.getName()+"]");
        }    
    }

    /*
     * Method that checks if a string is a room 
     * @param: String room 
     * @returns: boolean
     */
    public boolean isARoom(String room){
        if (room.equals("bathroom") || room.equals("bedroom") || room.equals("garden")|| room.equals("parlor")||room.equals("kitchen")){
            return true;
        }else{
            return false;
        }
    }

    /*
     * Method that returns a room given a corresponding string
     * @param: String name
     * @returns: Room 
     */
    public Room turnNameToRoom(String name){
        if(name.equals("kitchen")||name.equals("Kitchen")){
            return this.kitchen;
        }
        if(name.equals("bathroom")||name.equals("Bathroom")){
            return this.bathroom;
        }
        if(name.equals("bedroom")||name.equals("Bedroom")){
            return this.bedroom;
        }
        if(name.equals("garden")||name.equals("Garden")){
            return this.garden;
        }
        if (name.equals("parlor")||name.equals("Parlor")){
            return this.parlor;
        }
        //as to not throw an error 
        else{
            return this.kitchen;
        }
    }

    /*
     * Getter for this.location
     */
    public String getLocation(){
        return this.location;
    }

    /*
     * Getter for this.climbedOn
     */
    public Item getClimbedOn(){
        return this.climbedOn;
    }

    /*
     * Getter for this.haveDrunk
     */
    public Boolean getHaveDrunk(){
        return this.haveDrunk;
    }

    /*
     * Getter for this.getHaveEaten
     */
    public Boolean getHaveEaten(){
        return this.haveEaten;
    }

    /*
     * Setter for this.climbedOn
     */
    public void changeClimbedOn(Item i){
        this.climbedOn=i;
    }

    /*
     * Setter for this.haveEaten
     */
    public void changeHaveEaten(Boolean b){
        this.haveEaten=b;
    }

    /*
     * Setter for this.haveDrunk
     */
    public void changeHaveDrunk(Boolean b){
        this.haveDrunk=b;
    }

    /*
     * Setter for this.changeHolding
     */
    public void changeHolding(Item i){
        this.holding=i;
    }

    /*
     * Setter for this.stuckInBath
     */
    public void changeStuckInBath(Boolean b){
        this.stuckInBath=b;
    }

    /* 
     * Setter for this.success
    */
    public void changeSuccess(Boolean b){
        this.success=b;
    }
       
    /*
     * Method to get the current time in secodns format
     */
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

    /*
     * Method to convert the time in seconds to the game time 
     * @param: int i (usually time elapsed)
     * @returns: String
     */
    public String timeConvert(int i){
        //pass in the time elapsed from the start of the game in seconds 
        //the total game is 300 sec in human time, 7 hours in cat time (7*60*60=25,200 sec)
        //convert to cat time 
        int sec=i*(25200/300);
        //Get the total hours passed
        int num=60*60;
        int hour=sec/num;
        //if the hour is 0, change to 12 
        if(hour==0){
            hour=12;
        }
        //Get the leftover number of minutes 
        int min=sec/60;
        //Initialize leftover min
        int leftoverMin=0;
        //Find the leftover amount of minutes 
        for (int j = 0; j<10; j++) {
            //if we have overshot
            if(j*60>min){
                //then do the previous one
                leftoverMin=min-(j-1)*60;
                break;
            }
            //if it's still under, continue
            else{
                continue;
            }
          }
        //If the leftover min is less than 10 
        if(leftoverMin<10){
            return hour+":"+"0"+leftoverMin;
        }
        else{
            return hour+":"+leftoverMin;
        }

    }

    /*
     * Method to play the game. Prints out instructions, starts a scanner, maintains a while loop sending the responses to room classes while game is being played
     */
    public void play(){

        System.out.println("\n"+"----------------------------------------------------------------------------------------------------------------------------------");
        this.slowPrint("Welcome! In this game, you are a cat in a house searching for the best place to take a nap. In order to nap, you must...\n");
        this.slowPrint("* have eaten");
        this.slowPrint("* have drunk something");
        this.slowPrint("* have a blanket to sleep on");
        this.slowPrint("* be in direct sunlight\n");
        this.slowPrint("But be careful! Time is running out-- the sun is setting, and you have to get a good nap in before the sunset\n\n");
        this.slowPrint("Right now, it is 12 pm. The sun is going to set at 7 pm. Cat time is different than human time (nine lives and all), so you will get warnings throughout the game of the time.");
        this.slowPrint("If you fail to nap before sunset, you will lose the game :(");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        

        this.slowPrint("\nIn this house, there is: ");
        this.slowPrint(this.kitchen.getDescription());
        this.slowPrint(this.garden.getDescription());
        this.slowPrint(this.parlor.getDescription());
        this.slowPrint(this.bedroom.getDescription());
        this.slowPrint(this.bathroom.getDescription()+"\n");

        System.out.println("------------------------");
        System.out.println("|          | bathroom  |");
        System.out.println("|  parlor  |--------   |");
        System.out.println("|                      |");
        System.out.println("|-------   |           |");
        System.out.println("|          |  bedroom  |");
        System.out.println("| kitchen              |");
        System.out.println("|          |           |");
        System.out.println("|----------------------|");
        System.out.println("");
        System.out.println("    garden   ");
    


        this.slowPrint("\nRight now, you are in the kitchen. \n\n"+this.kitchen.lookAround());
        this.slowPrint("There are doors connecting to: ");
        this.printNeighbors(this.turnNameToRoom(this.location));
        System.out.println();

        

        in=new Scanner(System.in);

        //initialize 
        this.current=getTimeSeconds(); 
        //create Boolean for end of game 
        Boolean gameGoing=(this.start-this.current<300);

        while(gameGoing && !this.readyToNap && !this.stuckInBath){
            //reset success
            this.success=false;
        
            System.out.println("");

            Room locationAsRoom=this.turnNameToRoom(this.location);

            this.slowPrint("What would you like to do?");
            System.out.print(">>> ");

            String response=in.nextLine();

            //First, try to parse to see if we need to change this.location 

            //Turn response into an array list of words 
            ArrayList<String> wordList = new ArrayList<String>();
            for(String word : response.split(" ")) {
                wordList.add(word);
            }
            
            //Check if the response is "Help"
            if(wordList.get(0).equals("Help")||wordList.get(0).equals("help")){
                this.slowPrint("\nRemember, in order to nap, you must: ");
                this.slowPrint("* have eaten");
                this.slowPrint("* have drunk something");
                this.slowPrint("* have a blanket to sleep on");
                this.slowPrint("* be in direct sunlight\n");
                this.slowPrint("\nTry these commands:");
                this.slowPrint("* [Look around]");
                this.slowPrint("* [Go to the _____]");
                this.slowPrint("* [Climb the _____]");
                this.slowPrint("* [Jump off of the _____]");
                this.slowPrint("* [Eat the _____]");
                this.slowPrint("* [Drink the _____]");
                continue;
            }

            //Check that the response is more than one word
            if(wordList.size()<2){
                this.slowPrint("\nYour response should be more than one word. Try saying 'Go to' a room or an item.");
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
                        //if they are not climbed onto something 
                        if(Objects.isNull(this.climbedOn)){
                            //Change the location to the fourth word in the response
                            this.location=word4;
                            locationAsRoom=this.turnNameToRoom(this.location);

                            //Print update 
                            this.slowPrint("\nRight now, you are in "+locationAsRoom.getDescription()+locationAsRoom.lookAround());
                            this.slowPrint("There are doors connecting to: ");
                            //Print neighbors
                            this.printNeighbors(this.turnNameToRoom(this.location));

                            //Reset current time & canNap (restarting while loop)
                            this.current=getTimeSeconds(); 
                            gameGoing=(this.current-this.start<300);
                            this.readyToNap=this.canNap();
                            continue;
                        }
                        else{
                            //Reset current time & canNap (restarting while loop)
                            this.current=getTimeSeconds(); 
                            gameGoing=(this.current-this.start<300);
                            this.readyToNap=this.canNap();
                            this.slowPrint("\nYou need to [jump off of the "+this.climbedOn.getName()+"] before you can leave the room.");
                            continue;
                        }
                    }
                //If the fourth word is NOT a neighbor 
                    else{
                        //if they are not climbed onto something 
                        if(Objects.isNull(this.climbedOn)){
                            //if the location is already the location
                            if(this.location.equals(word4)){
                                this.slowPrint("\nYou are already at the "+word4+".");

                                //Reset current time & canNap (restarting while loop)
                                this.current=getTimeSeconds(); 
                                gameGoing=(this.current-this.start<300);
                                this.readyToNap=this.canNap();
                                continue;
                            }
                            else{
                                this.slowPrint("\nThere is not a door from the "+this.location+" to the "+ word4+". The "+this.location+" connects to: ");
                                this.printNeighbors(locationAsRoom);

                                //Reset current time & canNap (restarting while loop)
                                this.current=getTimeSeconds(); 
                                gameGoing=(this.current-this.start<300);
                                this.readyToNap=this.canNap();
                                continue;
                            }
                        }
                        else{
                            this.slowPrint("\nYou need to [jump off of the "+this.climbedOn.getName()+"] before you can leave the room. Additionally, there is not a door from the "+this.location+" to the "+ word3+". The "+this.location+" connects to: ");
                            this.printNeighbors(locationAsRoom);
                            //Reset current time & canNap (restarting while loop)
                            this.current=getTimeSeconds(); 
                            gameGoing=(this.current-this.start<300);
                            this.readyToNap=this.canNap();
                            continue;
                        }
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
            gameGoing=(this.current-this.start<300);
            //call canNap to update situation
            this.readyToNap=this.canNap();
            
            //If the program has NOT parsed the response (even if it didn't work), it sends out a general error message
            if(this.success==false){
                this.slowPrint("\nThat is not a valid command. Try these: ");
                this.slowPrint("* [Look around]");
                this.slowPrint("* [Go to the _____]");
                this.slowPrint("* [Climb the _____]");
                this.slowPrint("* [Jump off of the _____]");
                this.slowPrint("* [Eat the _____]");
                this.slowPrint("* [Drink the _____]");
                this.slowPrint("* [Help]");

            }
        }
        
        if(this.canNap()){
            this.slowPrint("\nSNOOOOZE!!!! Yay! You have found something to eat, something to drink, a blanket, and a sunny spot and can now take a well-deserved nap.");
            this.slowPrint("GAME OVER.");
        }
        if(!gameGoing){
            this.slowPrint("\nThe sun has set. The room is filled with pink light. You didn't nap, but nightfall is coming and you'll have a wonderful deep sleep.");
            this.slowPrint("GAME OVER.");
        }
        if(this.stuckInBath){
            this.slowPrint("\nGAME OVER.");
        }
    }
    


}
