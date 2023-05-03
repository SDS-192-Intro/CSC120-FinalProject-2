import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Objects;

public class Room {
    
    ArrayList<Item> items = new ArrayList<Item>();
    String name;
    String description;
    Item addressing;
    Game game;

    public Room (String name, String description, Game game){
        this.name=name;
        this.description=description;
        this.addressing=null;
        this.game=game;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public void addItem(Item i){
        items.add(i);
    }

    public String lookAround(){
        String toReturn="In this room, there is: \n";
        for (int i=0; i<items.size(); i++){
            if (items.get(i).hasParent()){
                toReturn+=items.get(i).description+" on top of the "+items.get(i).getParent().getName()+"\n";
            }else{
                toReturn+=items.get(i).description+"\n";
            }
        }
        return toReturn;
    }

    public boolean isInRoom(Item i){
        if (items.contains(i)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isInRoom(String item){
        ArrayList<String> stringItems=new ArrayList<String>();
        for (Item i :items){
            stringItems.add(i.getName());
        }
        if (stringItems.contains(item)){
            return true;
        }
        else{
            return false;
        }
    }

    public Item returnItem(String item){
        for(Item i:this.items){
            if (item.equals(i.getName())){
                return i;
            }
        }
        return null;
    }

    public void conversation(ArrayList<String> wordList){
        //General note: change success attribute in game every time the program parses a response,
        //to determine whether or not to print a general error message 

        String word1=wordList.get(0);
        String word2=wordList.get(1);

        //Check if they said "Look around"
        Boolean word1IsLook=word1.equals("Look")||word1.equals("look");
        if (word1IsLook &&word2.equals("around")){
            System.out.println(this.lookAround());
        }
    
        //"Go to the *item*"
        try{
            //Find the third & fourth word 
            String word3=wordList.get(2);
            String word4=wordList.get(3);

            //word1 should be "Go" or "go", word2 should be "to"
            //word3 should be "the", and word 4 should be the *item*

            Boolean word1IsGo=word1.equals("Go")||word1.equals("go");
                
            //Check if they have said "Go to the ..."
            if (word1IsGo && word2.equals("to") && word3.equals("the")){

                //Return the string from response as an item 
                Item item=this.returnItem(word4);

                //Check if you are already at that item
                //If this.addressing is not Null 
                if (Objects.nonNull(this.addressing)){
                    //If this.addressing contains item
                    if(this.addressing.equals(item)){
                        this.game.changeSuccess(true);
                        System.out.println("\nYou are already at the "+word4+".");
                        item.showOptions();
                        //Throw new exception to avoid re-updating this.addressing (it will not print)
                        throw new RuntimeException();
                    }
                }
                
                //Check that the item is in the room 
                if (!this.isInRoom(item)){
                    this.game.changeSuccess(true);
                    System.out.println("This is either not an item or this item is not in the room.");
                    this.lookAround();
                }

                //If the item is an item and is in the room 
                else{
                    //If item has a parent 
                    if(item.hasParent){
                        //You have to be climbed on to the parent 

                        //If you haven't climbed on anything yet, then you automatically won't be able to access child 
                        if(this.game.climbedOn==null){
                            this.game.changeSuccess(true);
                            System.out.println("You have to be climbed on to the "+item.getParent().getName()+" to go to the "+word4+". Try [Go to the "+item.getParent().getName()+"] and then climbing it.");
                        }
                        //If you have climbed on something, but it is not the right parent
                        if(!this.game.climbedOn.equals(item.getParent())){
                            this.game.changeSuccess(true);
                            System.out.println("You have to be climbed on to the "+item.getParent().getName()+" to go to the "+word4);
                        }
                        //They are climbed onto the parent 
                        else{
                            this.game.changeSuccess(true);
                            this.addressing=item;
                            System.out.println("\nYou are at the "+item.getName());
                            item.showOptions();
                        }
                    }
                    //If item doesn't have a parent
                    else{
                        this.game.changeSuccess(true);
                        this.addressing=item;
                        System.out.println("\nYou are at the "+item.getName());
                        item.showOptions();
                    }
                } 
            } } catch (Exception e){}

        
        //"Climb the *item*"
        try{
            //Get third word 
            String word3=wordList.get(2);

            //Check whether they said "Climb the *item*"
            Boolean firstWordIsClimb=word1.equals("climb")||word1.equals("Climb");
            if(firstWordIsClimb && word2.equals("the")){

                //Return the string from response as an item 
                Item item=this.returnItem(word3);

                //Check whether we are addressing the item 
                if (this.addressing.equals(item)){
                    //Check that the item is climbable 
                    if(item.isClimbable()){
                        //If this.climbedOn isn't void (to avoid throwing a runtime exception)
                        if(Objects.nonNull(this.game.getClimbedOn())){
                            //Check whether we have already climbed the item
                            if(this.game.getClimbedOn().equals(item)){
                                this.game.changeSuccess(true);
                                System.out.println("\nYou have already climbed onto the "+word3+".");
                            }
                        }
                        //Then you are allowed to climb the item 
                        else{
                            this.game.changeSuccess(true);
                            System.out.println("\nYou have climbed on "+word3+".");
                            this.game.changeClimbedOn(item);
                            //if the item has a child (ex. milk on table), then show options for child
                            if(item.hasChild()){
                                //change the wording for garden 
                                if(word3.equals("Pond")||word3.equals("pond")){
                                    this.game.changeSuccess(true);
                                    System.out.println("There are "+item.getChild().getName()+" in the "+word3+".");
                                    System.out.println("\nTo get to the "+item.getChild().getName()+" try [Go to the "+item.getChild().getName()+"].");
                                }
                                else{
                                    this.game.changeSuccess(true);
                                    System.out.println("The "+item.getChild().getName()+" is on top of the "+word3+".");
                                    System.out.println("\nTo get to the "+item.getChild().getName()+" try [Go to the "+item.getChild().getName()+"].");
                                }
                            }
                        }
                    }
                    //If not climbable, say that it is not climbable & print options 
                    else{
                        this.game.changeSuccess(true);
                        System.out.println("The "+word3+" is not climbable.");
                        item.showOptions();
                    }
                }
                //If we are not addressing any item or if we are addressing another item
                if(Objects.isNull(this.addressing)||!this.addressing.equals(item)){
                    this.game.changeSuccess(true);
                    System.out.println("You cannot climb this item if you are not at it. Try 'Go to "+item.getName()+"'.");
                }
            }

        }catch(Exception e){}
        
        //Climb *item*
        try{
            //Check whether there is only two words (Don't want "Climb the *item*" to also end up here)
            if(wordList.size()==2){
                //Check whether they said "Climb *item*"
                if(word1.equals("Climb")||word1.equals("climb")){
                    //Return the item
                    Item item=this.returnItem(word2);
                    //Check whether we are addressing the item
                    if (this.addressing.equals(item)){
                        //Check that the item is climbable
                        if(item.isClimbable){
                            ///If this.climbedOn isn't void (to avoid throwing a runtime exception)
                            if(Objects.nonNull(this.game.getClimbedOn())){
                                //Check whether we have already climbed the item
                                if(this.game.getClimbedOn().equals(item)){
                                    this.game.changeSuccess(true);
                                    System.out.println("\nYou have already climbed onto the "+this.getName()+".");
                                }
                            }
                            //Then you are allowed to climb the item 
                            else{
                                this.game.changeSuccess(true);
                                System.out.println("\nYou have climbed on "+this.getName()+".");
                                this.game.changeClimbedOn(item);
                                //if the item has a child (ex. milk on table), then show options for child
                                if(item.hasChild()){
                                    System.out.println("The "+item.getChild().getName()+" is on top of the "+this.getName()+".");
                                    System.out.println("\nTo get to the "+item.getChild().getName()+" try [go to the "+item.getChild().getName()+"].");
                                }
                        }
                        }
                        //If not climbable, say that it is not climbable & print options 
                        else{
                            this.game.changeSuccess(true);
                            System.out.println("The "+this.getName()+" is not climbable.");
                            item.showOptions();
                        }   
                    }
                    //If we are not addressing any item or if we are addressing another item
                    if(Objects.isNull(this.addressing)||!this.addressing.equals(item)){
                        this.game.changeSuccess(true);
                        System.out.println("You cannot climb this item if you are not at it. Try 'Go to "+this.getName()+"'.");
                }
                }

            }
        }catch (Exception e){}


        //"Jump off of the *item*"
        try{
            //Get third, fourth, and fifth words 
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            String word5=wordList.get(4);

            //If they said "Jump off of the *item*" 
            Boolean word1isJump=word1.equals("Jump")||word1.equals("jump");
            if(word1isJump && word2.equals("off") && word3.equals("of") && word4.equals("the")){
                Item item=this.returnItem(word5);
                 //If you have NOT climbed on to the item 
                if(Objects.isNull(this.game.getClimbedOn())){
                    this.game.changeSuccess(true);
                    System.out.println("\nYou need to have climbed onto the "+item.getName()+" in order to jump off of it. Try [climb the "+item.getName()+"].");
                }

                //If you have climbed onto the item
                if(this.game.getClimbedOn().equals(item)){
                    //If it is jump-off-able
                    if(item.isJumpOffable){
                        //If you are addressing it 
                        if(this.addressing.equals(item)){
                            //Then you may jump off 
                            this.game.changeSuccess(true);
                            //Change climbedOn attribute in the game 
                            this.game.changeClimbedOn(null);
                            System.out.println("\nYou have jumped off of the "+item.getName()+".");
                            System.out.println("You are now standing next to the "+item.getName()+" in the "+item.getName()+".\n");
                            System.out.println(this.lookAround()+"\nThere are doors connecting to: \n");
                            this.game.printNeighbors(this);
                            //throw runtime exception to avoid being caught in the final conditional
                            throw new RuntimeException();
                        }
                        //if you are addressing its child
                        if(item.hasChild()){
                            if(this.addressing.equals(item.getChild())){
                                //Then you may jump off 
                                this.game.changeSuccess(true);
                                //Change climbedOn attribute in the game 
                                this.game.changeClimbedOn(null);
                                System.out.println("\nYou have jumped off of the "+item.getName()+".");
                                System.out.println("You are now standing next to the "+item.getName()+" in the "+item.getName()+".");
                                System.out.println(this.lookAround()+"\nThere are doors connecting to: \n");
                                this.game.printNeighbors(this);
                                //throw runtime exception to avoid being caught in the final conditional
                                throw new RuntimeException();
                            }
                        }
                        //If you are NOT addressing the item
                        else{
                            this.game.changeSuccess(true);
                            System.out.println("\nIn order to jump off, you must first be *at* the "+item.getName()+". Try [go to the "+item.getName()+"], and then [jump off of the "+item.getName()+"].");
                            //throw runtime exception to avoid being caught in the final conditional
                            throw new RuntimeException();
                        }
                    }
                    //If it is NOT jump-off-able (only the case for the bathtub)
                    else{
                        this.game.changeSuccess(true);
                        System.out.println("\nYou cannot jump out of the slippery porcelain bath. Unfortuntely, this means that you will not be able to find the perfect nap spot before sunset.");
                        System.out.println("Ah well, you can just rest your eyes....");
                        this.game.changeStuckInBath(true);
                        //throw runtime exception to avoid being caught in the final conditional
                        throw new RuntimeException();
                    }
                }
            }
        }
        catch(Exception e) {}

        //"Eat the *item*"
        try{
            String word3=wordList.get(2);

            //If the first two words are "Eat the "
            Boolean word1isEat=word1.equals("Eat")||word1.equals("eat");
            if(word1isEat && word2.equals("the")){
                //If we not have already eaten 
                if(this.game.getHaveEaten()==false){
                    //Turn string into item
                    Item item=this.returnItem(word3);
                    //If we are at the item 
                    if(this.addressing.equals(item)){
                        System.out.println("here2");
                        //If the item is drinkable 
                        if(item.isEdible()){
                            this.game.changeHaveEaten(true);
                            System.out.println("Gulllp!! Delicious");
                            System.out.println("Current status: ");
                            this.game.printNapStatus();
                        }
                        //If the item is not  drinkable 
                    }
                    //If we are not at the item
                    else{
                        System.out.println("You need to be at the "+word3+" in order to eat it. Try [go to the "+word3+"].");
                    }
                }
                //If we have already eaten
                else{
                    System.out.println("\nYou have already eaten the "+word3+".\n");
                    this.game.printNapStatus();
                }
            }
        }
        catch(Exception e) {}

        //"Drink the *item*"
        try{
            String word3=wordList.get(2);

            //If the first two words are "Drink the "
            Boolean word1isDrink=word1.equals("Drink")||word1.equals("drink");
            if(word1isDrink && word2.equals("the")){
                //If we not have already drunk it 
                if(this.game.getHaveDrunk()==false){
                    //Turn string into item
                    Item item=this.returnItem(word3);

                    //If we are at the item 
                    if(this.addressing.equals(item)){
                        //If the item is drinkable 
                        if(item.isDrinkable){
                            this.game.changeHaveDrunk(true);
                            System.out.println("Slurrrp!!! So refreshing!");
                            System.out.println("Current status: ");
                            this.game.printNapStatus();
                            System.out.println("\nYou are climbed on the "+item.getParent().getName()+". You can [jump off of the "+item.getParent().getName()+"]");
                        }
                        //If the item is not  drinkable 
                    }
                    //If we are not at the item
                    else{
                        System.out.println("You need to be at the "+word3+" in order to drink it. Try [go to the "+word3+"].");
                    }
                }
                //If we have already drunk it 
                else{
                    System.out.println("You have already drunk the "+word3+".\n");
                    this.game.printNapStatus();
                }
            }
        }
        catch(Exception e) {}
    }}

        // //TRY GO TO FUNCTIONS
        // try{
            
        //     //Check that the item IS an item
          

        //     //Check that the item is in the room 
            

        //     //If the item is an item and is in the room, then check if the item has a parent 
            

        //     //If the item is an item and is in the room but doesn't have a parent, change this.addressing
            
            
        //     //check if they say 'go to' that the item is in room 
        //     String word3=wordList.get(2);
        //     Item item=this.returnItem(word3);
        //     Boolean isGo=false;
        //     if (word1.equals("Go")||word1.equals("go")){
        //         isGo=true;
        //     }
        //     if(isGo&& word2.equals("to")){
        //         //if the item is not an item --fill out 
        //         //if you have jumped up on something -- fill out 
        //         //if the item is not in the room 
                
        //         if(!this.isInRoom(word3)){
        //             //fix window seat bug 
        //             if(!word3.equals("window")){
        //                 System.out.println("This is not an item or this item is not in this room. "+this.lookAround());
        //             }
        //         }
        //         //if the item IS in the room 
        //         else{
        //         //if the item has a parent, don't let them go right to the item 
        //             if (item.hasParent()){
        //                 Item parent=item.getParent();
        //                 //if we are already addressing the parent 
        //                 if(this.addressing==parent){
        //                     //then we are allowed to address the item  
        //                     this.addressing=item;
        //                     System.out.println("\nYou are at the "+item.getName());
        //                     item.showOptions();
        //                 }
        //                 else{
        //                     System.out.println("You cannot get to "+item.getName()+" without first going to "+parent.getName()+". Try calling 'Go to "+parent.getName()+"'");
        //                 }
        //                 }
        //             else{
        //                 this.addressing=item;
        //                 System.out.println("\nYou are at the "+item.getName());
        //                 item.showOptions();
        //                 if(item.hasChild){
        //                     item.getChild().getDescription();
        //                     item.getChild().showOptions();
        //                 }
        //             }
        //         }   
        //     }
        // } catch (Exception e){
        //     //i don't care
        // }


//         //TRY CLIMB
//         try{
//             //IF you are addressing the item
//             Item item2=this.returnItem(word2);
//             if (this.addressing.equals(item2)){
//                 Boolean isClimb=word1.equals("Climb")||word1.equals("climb");
//                 //if the first word is "Climb"
//                 if(isClimb){
//                     // //if the item has already been climbed 
//                     // if(this.game.getClimbedOn().equals(item2)){
//                     //     System.out.println("You have already climbed on this item.");
//                     // }
//                     //if the item isn't in room
//                     if(!this.isInRoom(item2)){
//                         System.out.println("This item is not in the room."+this.lookAround());
//                     }
//                     //if the item isn't climbable  
//                     if(!item2.isClimbable){
//                         System.out.println("This item is not climbable.");
//                         item2.showOptions();
//                     }
//                     //check if the item is in the room and if it's climbable 
//                     System.out.println(this.isInRoom(item2));
//                     System.out.println(item2.isClimbable());
//                     if (this.isInRoom(item2)&&item2.isClimbable()){

//                         this.game.changeClimbedOn(item2);
//                         System.out.println("\nYou have climbed on to the "+word2+".");
//                         if (item2.hasChild()){
//                             System.out.println("On top of the "+word2+" there is: "+item2.getChild().getDescription());
//                             item2.getChild().showOptions();
//                         }
//                     }
//                 }
//             }
//             //if not addressing item-- DEBUG!!! 
//             //else{
//                 //System.out.println("You cannot climb an item you are not at. Try 'Go to "+word2+"'");
//            // }
//         } catch (Exception e){
//             //i don't care
//         }


//         //JUMP OFF 
//         try{
//             //get third word
//             String word3=wordList.get(2);
//             //make item from third word 
//             Item item2=this.returnItem(word3);
//             //if the first two words are jump and off
//             Boolean isJump=word1.equals("jump")||word1.equals("Jump");
//             if(isJump&&word2.equals("off")){
//                 //check if the item is jumpoffable
//                 if(item2.isJumpOffAble()){
//                     //remove from climbedOn
//                     this.game.changeClimbedOn(null);
//                     System.out.println("You have jumped off of the "+word3);
//                 }
//             }
//         } catch (Exception e){
//             //i don't care
//         }


//         //EAT 
//         try{
//             Item item2=this.returnItem(word2);
//             //If you are addressing the item's parent 
//             if(this.addressing.equals(item2.getParent())){
//                 //If the first word is eat 
//                 if(word1.equals("eat")||word1.equals("Eat")){
//                     //Check whether you can eat the item 
//                     if(item2.isEdible()){
//                         this.game.changeHaveEaten(true);
//                         System.out.println("Yum! You ate the "+word2);
//                     }
//                 }
//             } 
//             //If not addressing item (debug)
//            // else{
//                // System.out.println("You cannot eat an item you are not at. Try 'Go to "+word2+"'");
//             //}

//         } catch (Exception e){
//             //I don't care
//         }


//         //DRINK 
//         try{
//             Item item2=this.returnItem(word2);
//             //If you are addressing the item's parent 
//             if(this.addressing.equals(item2.getParent())){
//                 //if you have climbed parent!! --fill in 
//                 //If the first word is eat 
//                 if(word1.equals("drink")||word1.equals("Drink")){
//                     //Check whether you can eat the item 
//                     if(item2.isDrinkable()){
//                         this.game.changeHaveDrunk(true);
//                         System.out.println("Gulp! You drank the "+word2);
//                     }
//                 }
//             } 
//             //If not addressing item (debug)
//            // else{
//                // System.out.println("You cannot drink an item you are not at. Try 'Go to "+word2+"'");
//             //}

//         } catch (Exception e){
//             //I don't care
//         }           
        

//         //Message for not taking an item you can't take 
//         try{
//             Item item2=this.returnItem(word2);
//             if(word1.equals("take")||word1.equals("Take")){
//                 if(!item2.isTakeable){
//                     System.out.println("The "+word2+" is not takeable.");
//                     item2.showOptions();
//                 }
//             }
//         } catch(Exception e){}
//     }


// }
