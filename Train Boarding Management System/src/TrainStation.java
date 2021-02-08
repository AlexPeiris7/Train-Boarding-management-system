import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;

public class TrainStation extends Application {

    Scene viewQueue;
    private List <Passenger> waitingRoom = new ArrayList();
    PassengerQueue trainQueue = new PassengerQueue();

    public static void main(String [] args){

        launch(args);

    }

    public void start(Stage primaryStage) throws Exception {

        setPassengerDetails();
        //setting up a clone watting room for display purposes in PAssengerQueue class
        setCloneWaitRoom();
        setNotArrived();



        while(true){
        System.out.println("Press A - to add passenger to the train queue");
        System.out.println("Press V - to view the train queue");
        System.out.println("Press D - to delete passenger from the train queue");
        System.out.println("Press S - to store train queue data in a plain text file");
        System.out.println("Press L - to load data back from the file into the train queue");
        System.out.println("Press R - to run the simulation and produce report");
        System.out.print("Enter letter:");
        Scanner sc1 = new Scanner(System.in);
        String User_input = toLowerCase(sc1.nextLine());

        switch (User_input) {
            case "a":
                //checking if queue is full
                if(trainQueue.isFull()){
                    System.out.println("Queue is full! or Waiting room is empty!");
                    break;
                }
                //random number taken from rolling a die
                int passengersAdded;
                //avoid index out of bound exception if array lenght is exceeded
                while (true) {
                    passengersAdded = sixSidedDie(1);
                    if (waitingRoom.size() >= passengersAdded) {
                        break;
                    }
                }
                //adding passenger objects to queue
                for (int x = 0; x < passengersAdded; x++) {
                    trainQueue.add(waitingRoom.get(x));
                }
                //removing added objects from waiting room
                for (int x = 0; x < passengersAdded; x++) {
                    waitingRoom.remove(0);
                }

                trainQueue.display(passengersAdded,"a");

                break;
            case "v":
                trainQueue.display(0,"v");
                break;
            case "d":
                //checking if queue is empty
                if(trainQueue.isEmpty()){
                    System.out.println("Queue is empty!");
                    break;
                }
                trainQueue.delete();
                break;
            case "s":
                saveDataWaitingRoom();
                trainQueue.saveDataQueueArray();
                break;
            case "l":
                loadDataWaitingRoom();
                trainQueue.loadDataQueueArray();
                break;
            case "r":
                    trainQueue.simulation();
                break;
            default:
                System.out.println("Wrong Input");
                break;
        }
        }
    }
    public void setPassengerDetails(){
        try {
            //temporary list to store the hashmap in the file
            HashMap <String,List> tempHmap = new HashMap <String,List>();
            FileInputStream FIS_passengers = new FileInputStream("C:\\Users\\thary\\IdeaProjects\\PP2 CW-1(Remake)\\data.txt");
            ObjectInputStream OIS_passengers = new ObjectInputStream(FIS_passengers);
            HashMap<String,List> passengers_file =(HashMap<String,List>)OIS_passengers.readObject();
            tempHmap.putAll(passengers_file);
            List <String> tempList = new ArrayList<String>((tempHmap.keySet()));
            List <String> passengerNames = new ArrayList<String>();
            //check how many seats customer has booked and each time add name of customer to passengerList
            // per booked seat(eg: if customer a booked two seats, his name will be in the list two times)
            for (int x = 0;x<tempList.size();x++){
                int numOfSeats = tempHmap.get(tempList.get(x)).size();
                for (int y = 0;y<numOfSeats;y++){
                    passengerNames.add(tempList.get(x));
                }
            }
            //splitting names and dates(in hashmap keys have been saved as a string which contains name and booked date)
            for (int x = 0;x<passengerNames.size();x++){
                String[] split = passengerNames.get(x).split("=");
                passengerNames.set(x,split[0]);
            }
            //d
            List <String> passengerSeats = new ArrayList<>();
            tempHmap.values().forEach(passengerSeats::addAll);
            //ordering the list by booked seats number(both passengerSeats and passegerNames)
            //with bubble sort algo to add in queue
            while(true){
                int swaps=0;
                for (int x=0;x<(passengerSeats.size()-1);x++){
                    if (Integer.parseInt(passengerSeats.get(x))>Integer.parseInt(passengerSeats.get(x+1))){
                        //swapping the two in passengerSeat
                        String a=passengerSeats.get(x);
                        String b=passengerSeats.get(x+1);
                        passengerSeats.set(x,b );
                        passengerSeats.set(x+1,a );
                        //swapping the two objects in passengerNames as well as index of both the lists are related to
                        //each other(ex: first seat in list passengerSeat belongs to first name in passengerNames )
                        String aa=passengerNames.get(x);
                        String bb=passengerNames.get(x+1);
                        //swapping the two in passengerSeat
                        passengerNames.set(x,bb );
                        passengerNames.set(x+1,aa );
                        swaps=swaps+1;
                    }
                }
                //if no swaps are made list is in order
                if (swaps==0){
                    break;
                }
            }
            //adding names and seats to waitingRoom list and waitingRoomSeatNo list
            waitingRoom.clear();
            for(int x = 0 ; x<passengerNames.size();x++){
                Passenger passenger = new Passenger();
                passenger.setFirstName(passengerNames.get(x));
                passenger.setPassengerSeatNo(passengerSeats.get(x));
                waitingRoom.add(passenger);
            }
        }
        catch (Exception e){
            System.out.println("Loading to waiting room unsuccessfull");
            System.out.println(e);
        }

    }
    //Six sided die for both getting the seconds and for choosing the number of passengers that
    // get moved to the train queue
    public int sixSidedDie(int times){
        int minNum = 1;
        int maxNum = 6;
        int randNum = 0;
        for(int x = 0 ; x < times ; x++){
            int dieRoll = (int)(Math.random() * (maxNum - minNum + 1) + minNum);
            randNum = randNum + dieRoll ;
        }
        return randNum;
    }
    public void setCloneWaitRoom(){
        for(int x = 0; x < waitingRoom.size(); x++){
            trainQueue.setCloneWaitingRoom(waitingRoom.get(x));
        }
    }
    public void saveDataWaitingRoom() {
        try {
            //writing files from code to files
            FileOutputStream FOS_waitingRoom = new FileOutputStream("waitingRoomData.txt");
            ObjectOutputStream OOS_waitingRoom = new ObjectOutputStream(FOS_waitingRoom);
            OOS_waitingRoom.writeObject(waitingRoom);
            OOS_waitingRoom.close();
            System.out.println("Successfully saved data into file");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void loadDataWaitingRoom(){
        try {
            // reading file and extracting objects
            FileInputStream FIS_waitingRoom = new FileInputStream("waitingRoomData.txt");
            ObjectInputStream OIS_waitingRoom = new ObjectInputStream(FIS_waitingRoom);
            List <Passenger> readObject =(List <Passenger>)OIS_waitingRoom.readObject();
            waitingRoom.clear();
            for(int x =0;x<readObject.size();x++){
                waitingRoom.add(readObject.get(x));
            }
            System.out.println("Successfully loaded data into file");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
    //setting passengers who havent arrived with random , choosing 1 to 3 passengers and adding them to the list
    // notArrived in the PassengerQueue class
    public void setNotArrived(){
        int rand = (int)(Math.random() * (3 - 1 + 1) + 1);
        for(int x =0;x<rand;x++){
            int rand1 = (int)(Math.random() * (42 - 1 + 1) + 1);
            trainQueue.setNotArrived(rand1);
        }
    }

}