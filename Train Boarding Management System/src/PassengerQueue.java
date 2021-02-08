
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class PassengerQueue{

    Scene addToQueue, reportScene ,viewScene;

    private Passenger[] queueArray = new Passenger[42];
    private int first=0;
    private int last=0;
    private int maxStayInQueue=0;
    private int lenght=0;
    private int maxLenght=0;
    private int maxNoPassengersInQueue=42;
    private List <Passenger> deletedPassengers = new ArrayList();
    private List <Passenger> cloneWaitingRoom = new ArrayList();
    private List <Passenger> boardedPassengers = new ArrayList();
    private List <Integer> queueTimes=new ArrayList<Integer>();
    private List <Integer> notArrivedList=new ArrayList<Integer>();



    public void add(Passenger next){
        //adding passengers to queue(circular array)
        queueArray[last] = next;
        last = (last+1);
        lenght++;
        setMaxLenght(lenght);

    }

    public boolean isEmpty(){
        return getLenght()==0;
    }
    public boolean isFull(){
        return getLenght()==maxNoPassengersInQueue;
    }

    public int getLenght(){
        return lenght;
    }

    public void display(int numPassengersAdded,String scene) {
        if (scene.equals("a")) {
            List passengersAdded = new ArrayList();
            for (int r = 0; r < numPassengersAdded; r++) {
                try {
                    //-r because im taking last added elements in the array from back of array
                    // -1 because index start at 0 and getLenght(number of passengers in the queue) starts with 1
                    passengersAdded.add(0, queueArray[getLenght() - r - 1 + first].getFirstName());
                } catch (NullPointerException e) {
                }
                ;
            }
            //------------------Queue pane----------------
            //View queque scene
            Stage window = new Stage();
            SplitPane splitPane = new SplitPane();
            //Displaying buttons in order horizontally
            FlowPane leftPane = new FlowPane();
            FlowPane queue = new FlowPane(Orientation.VERTICAL, 5, 5);
            queue.setPadding(new Insets(50));
            Label queuelbl = new Label("Queue");
            queuelbl.setMinSize(545, 50);
            queuelbl.setStyle("-fx-background-color:powderblue ");
            queuelbl.setFont(javafx.scene.text.Font.font("Impact", FontWeight.EXTRA_BOLD, 20));
            queuelbl.setAlignment(Pos.CENTER);
            Label passengersNamesMoved = new Label("Passengers added to queue: " + passengersAdded);
            passengersNamesMoved.setMinSize(545, 50);
            passengersNamesMoved.setStyle("-fx-background-color:powderblue ");
            passengersNamesMoved.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.EXTRA_BOLD, 11));
            passengersNamesMoved.setAlignment(Pos.CENTER);
            for (int x = 0; x < getLenght() + deletedPassengers.size(); x++) {
                Label passengerInLine = new Label();
                passengerInLine.setMinSize(62, 40);
                try {
                    passengerInLine.setText(queueArray[x + first].getFirstName());
                } catch (NullPointerException e) {
                    continue;
                }
                //passengerInLine.setId("Passenger"+Integer.toString(x));
                passengerInLine.setStyle("-fx-background-color:blue ");
                passengerInLine.setAlignment(Pos.CENTER);
                passengerInLine.setFont(javafx.scene.text.Font.font("Verdana", 11));
                passengerInLine.setTextFill(WHITE);
                queue.getChildren().add(passengerInLine);
            }
            leftPane.getChildren().add(passengersNamesMoved);
            leftPane.getChildren().add(queuelbl);
            leftPane.getChildren().add(queue);

            //---------------------waiting room pane--------------
            //removing passengers added to queue from cloneWaitingRoom
            for (int x = 0; x < getLenght() + deletedPassengers.size(); x++) {
                cloneWaitingRoom.remove(queueArray[x]);
            }
            //Displaying buttons in order horizontally
            FlowPane rightPane = new FlowPane();
            FlowPane waitRoomGUI = new FlowPane(Orientation.HORIZONTAL, 5, 5);
            waitRoomGUI.setPadding(new Insets(10, 50, 50, 50));
            Label waitRoomlbl = new Label("Waiting room");
            waitRoomlbl.setMinSize(545, 50);
            waitRoomlbl.setStyle("-fx-background-color:powderblue ");
            waitRoomlbl.setFont(javafx.scene.text.Font.font("Impact", FontWeight.EXTRA_BOLD, 20));
            waitRoomlbl.setAlignment(Pos.CENTER);
            Label passengersMoved = new Label("Number of Passengers added to queue: " + numPassengersAdded);
            passengersMoved.setMinSize(545, 50);
            passengersMoved.setStyle("-fx-background-color:powderblue ");
            passengersMoved.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
            passengersMoved.setAlignment(Pos.CENTER);
            for (int x = 0; x < cloneWaitingRoom.size() + first; x++) {
                Label passengerInWaitRoom = new Label();
                passengerInWaitRoom.setMinSize(62, 62);
                try {
                    passengerInWaitRoom.setText(cloneWaitingRoom.get(x + first).getFirstName());
                } catch (Exception e) {
                    continue;
                }
                //passengerInLine.setId("Passenger"+Integer.toString(x));
                passengerInWaitRoom.setStyle("-fx-background-color:blue ");
                passengerInWaitRoom.setAlignment(Pos.CENTER);
                passengerInWaitRoom.setFont(javafx.scene.text.Font.font("Verdana", 11));
                passengerInWaitRoom.setTextFill(WHITE);
                waitRoomGUI.getChildren().add(passengerInWaitRoom);
            }
            rightPane.getChildren().add(passengersMoved);
            rightPane.getChildren().add(waitRoomlbl);
            rightPane.getChildren().add(waitRoomGUI);

            splitPane.getItems().add(rightPane);
            splitPane.getItems().add(leftPane);

            addToQueue = new Scene(splitPane, 1100, 620);
            window.setScene(addToQueue);
            window.setTitle("Train Booking System - View Seats");
            window.showAndWait();
        }
        else if(scene.equals("v")){
            Stage window = new Stage();
            FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 5, 5);
            flowPane.setPadding(new Insets(50));
            Label label = new Label("View Queue");
            label.setMinSize(1000, 50);
            label.setStyle("-fx-background-color:powderblue ");
            label.setFont(javafx.scene.text.Font.font("Impact", FontWeight.EXTRA_BOLD, 20));
            label.setAlignment(Pos.CENTER);
            flowPane.getChildren().add(label);
            for (int x = 0; x < 42; x++) {
                Label seat = new Label();
                seat.setMinSize(40, 40);
                seat.setText(Integer.toString(x+1));
                seat.setStyle("-fx-background-color:blue ");
                seat.setAlignment(Pos.CENTER);
                seat.setFont(javafx.scene.text.Font.font("Verdana", 11));
                seat.setTextFill(WHITE);
                Label passenger = new Label("");
                passenger.setMinSize(150, 40);
                passenger.setStyle("-fx-background-color:blue ");
                passenger.setAlignment(Pos.CENTER);
                passenger.setFont(javafx.scene.text.Font.font("Verdana", 11));
                passenger.setTextFill(WHITE);

                for (int y = 0; y < 42; y++) {
                    try {
                        try{
                        if(boardedPassengers.get(y).getPassengerSeatNo().equals(Integer.toString(x+1))){
                            passenger.setText("Boarded");
                        }}
                        catch (Exception e){}
                        //checking if seat number owner is deleted/not arrived or if he already boarder train
                        if (queueArray[y].getPassengerSeatNo().equals(Integer.toString(x+1)) &&
                                !passenger.getText().equals("Boarded")&&(!notArrivedList.contains(x+1))){

                            passenger.setText(queueArray[y].getFirstName());
                        }

                    } catch (NullPointerException e) {
                        continue;
                    }
                }
                flowPane.getChildren().addAll(seat,passenger);
            }
            viewScene = new Scene(flowPane, 1100, 620);
            window.setScene(viewScene);
            window.setTitle("Train Booking System - View Seats");
            window.showAndWait();
        }
    }
    public void delete() {
        System.out.println("Enter seat number of the passenger you want to delete:");
        Scanner sc = new Scanner(System.in);
        int seatNumber = sc.nextInt();
        System.out.println();
        for (int x = 0; x < maxNoPassengersInQueue; x++) {
            try{
            if (Integer.parseInt(queueArray[x].getPassengerSeatNo()) == seatNumber) {
                System.out.println("Passengers deleted:"+queueArray[x].getFirstName());
                deletedPassengers.add(queueArray[x]);
                //setting array to null as arrays cannot be removed
                queueArray[x]=null;
                lenght--;
                //decreasing maxNoPassengersInQueue as when one passenger has been deleted the size shoud
                //be decreased
                maxNoPassengersInQueue--;
                for (int r = x; r < maxNoPassengersInQueue - 1; r++) {
                    //moving the deleted object to the back part of the array in order to reorder it
                    Passenger temp = queueArray[r];
                    queueArray[r]=queueArray[r+1];
                    queueArray[r+1]=temp;
                }

                break;
            }}
            catch (Exception e){}
        }
    }
    public void setCloneWaitingRoom(Passenger passenger){
        cloneWaitingRoom.add(passenger);
    }

    //run simulation

    public void setMaxLenght(int lenght){
        //comparing and setting the maximum lenght of not null objects attained
        if(lenght>maxLenght){
            maxLenght=lenght;
        }
    }
    public Passenger remove(){
        //removing objects by setting the first integer to the index where the passengers habe not been boarded yet
        //used for the simulation
        Passenger passengerRemoved = queueArray [first];
        boardedPassengers.add(queueArray [first]);
        first=(first+1);
        lenght--;
        maxNoPassengersInQueue--;
        return passengerRemoved;
    }
    public int getMaxStay(){
        maxStayInQueue=0;
        for (int x = 0; x < queueTimes.size(); x++){
            maxStayInQueue= maxStayInQueue + queueTimes.get(x);
        }
        return maxStayInQueue;
    }
    public float totalCheckTime(){
        float totalCheckTime = 0;
        for (int x = 0; x < queueTimes.size(); x++){
            totalCheckTime= totalCheckTime + queueTimes.get(x);
        }
        return totalCheckTime;
    }
    public float totalQueueTime(){
        float totalQueueTime = 0;
        //calculating total time in queue(total time in queue of 1st+tot of 2nd+tot of 3rd etc)
        //nested for works as it adds every time in the list for example in first loop calculates tot time of last
        // second loop calc of one before last and so goes on
        int temp = queueTimes.size();
        for (int y = 0; y < queueTimes.size(); y++){
            for (int x = 0; x < temp; x++){
                totalQueueTime=totalQueueTime+queueTimes.get(x);
            }
            temp--;
        }
        return totalQueueTime;
    }
    public int maxCheckTime(){
        int maxCheckTime=0;
        for (int x = 0; x < queueTimes.size(); x++){
            if(queueTimes.get(x)>maxCheckTime){maxCheckTime=queueTimes.get(x);}
        }
        return maxCheckTime;
    }
    public int minCheckTime(){
        int minCheckTime=18;
        for (int x = 0; x < queueTimes.size(); x++){
            if(queueTimes.get(x)<minCheckTime){minCheckTime=queueTimes.get(x);}
        }
        return minCheckTime;
    }
    public void consoleReport(){
        System.out.println(first);
        TrainStation trainStation = new TrainStation();
        System.out.println();
        for (int r = 0; r < first; r++) {
            try{
            queueArray[r].setSecondsInQueue(trainStation.sixSidedDie(3));
            queueTimes.add(queueArray[r].getSecondsInQueue());
            queueArray[(r)].displayReport();}
            catch (Exception e ){}
        }

        System.out.println("Average time to check tickets : " + totalCheckTime()/queueTimes.size() );
        System.out.println("Average stay time in queue : " + totalQueueTime()/queueTimes.size() );
        System.out.println("Maximum lenght of the queue :" + maxLenght);
        System.out.println("Minimum waiting time to check ticket: " + minCheckTime()+"s");
        System.out.println("Maximum waiting time to check ticket: " + maxCheckTime()+"s");
        System.out.println("Minimum waiting time in queue(first passenger in queue): " + minCheckTime()+"s");
        System.out.println("Maximum waiting time in queue(last passenger in queue): " + getMaxStay()+"s");
        System.out.println();
    }
    public void simulation(){
        // simulation for R option in the train STation class
        // removinng and creating reports happen here
        int temp=lenght;
        for(int x = 0 ; x<temp ; x++ ){
            try{
                remove();
            }
            catch (Exception e){}
        }
        consoleReport();
        guiReport();
        fileReport();
    }
    public void guiReport(){
        Stage window = new Stage();
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        flowPane.setPadding(new Insets(10,50,50,50));
        Label report = new Label("Report");
        report.setMinSize(1250,30);
        report.setStyle("-fx-background-color:blue ");
        report.setAlignment( Pos.CENTER );
        report.setFont( javafx.scene.text.Font.font("Verdana",11) );
        report.setTextFill( WHITE );
        flowPane.getChildren().addAll(report);

        for (int x = 0 ; x < boardedPassengers.size() ; x++){
            try {
                Label breaker = new Label();
                Label passengerName = new Label();
                passengerName.setText("Name: " + boardedPassengers.get(x).getFirstName());
                labelStyles(passengerName, 1);
                Label passengerSeatNumber = new Label();
                passengerSeatNumber.setText("Seat no.: " + boardedPassengers.get(x).getPassengerSeatNo());
                labelStyles(passengerSeatNumber, 1);
                Label passengerSecondsInQueue = new Label();
                passengerSecondsInQueue.setText("Seconds in Queue: " + Integer.toString(boardedPassengers.get(x).getSecondsInQueue()));
                labelStyles(passengerSecondsInQueue, 1);
                breaker.setMinSize(5, 20);
                breaker.setStyle("-fx-background-color:blue ");
                flowPane.getChildren().addAll(passengerName, passengerSeatNumber, passengerSecondsInQueue, breaker);
            }
            catch (Exception e){}
        }
        Label label1 = new Label("Average time to check tickets : " + totalCheckTime()/queueTimes.size()+"s" );
        Label label2 = new Label("Average stay time in queue : " + totalQueueTime()/queueTimes.size()+"s" );
        Label label3 = new Label("Maximum lenght of the queue :" + maxLenght);
        Label label4 = new Label("Minimum waiting time to check ticket: " + minCheckTime()+"s");
        Label label5 = new Label("Maximum waiting time to check ticket: " + maxCheckTime()+"s");
        Label label6 = new Label("Minimum waiting time in queue(first passenger in queue): " + minCheckTime()+"s");
        Label label7 = new Label("Maximum waiting time in queue(last passenger in queue): " + getMaxStay()+"s");
        labelStyles(label1,2);
        labelStyles(label2,2);
        labelStyles(label3,2);
        labelStyles(label4,2);
        labelStyles(label5,2);
        labelStyles(label6,2);
        labelStyles(label7,2);

        flowPane.getChildren().addAll(label1,label2,label3,label4,label5,label6,label7);
        reportScene = new Scene(flowPane, 1350, 800);

        window.setScene(reportScene);

        window.showAndWait();
    }
    //created this method in order to avoid code duplication
    public void labelStyles(Label label, int labelType) {
        if (labelType==1) {
            label.setMinSize(200, 20);
            label.setStyle("-fx-background-color:powderblue ");
            label.setAlignment(Pos.CENTER);
            label.setFont(javafx.scene.text.Font.font("Verdana", 11));
            label.setTextFill(BLACK);
        }else{
            label.setMinSize(1245, 20);
            label.setStyle("-fx-background-color:powderblue ");
            label.setAlignment(Pos.CENTER);
            label.setFont(javafx.scene.text.Font.font("Verdana", 11));
            label.setTextFill(BLACK);
        }
    }
    public void fileReport(){
        try {
            FileWriter fileWriter = new FileWriter("report.txt");
            for (int r = 0; r < first; r++) {
                try {
                    fileWriter.write("Name: " + queueArray[r].getFirstName() + " | | ");
                    fileWriter.write("Seat Number: " + queueArray[r].getPassengerSeatNo() + " | | ");
                    fileWriter.write("Seconds in Queue: " + queueArray[r].getSecondsInQueue() + " | | " + "\n");
                }catch (Exception e){}
            }
            fileWriter.write("Average time to check tickets : " + totalCheckTime()/queueTimes.size()+"s"+"\n");
            fileWriter.write("Average stay time in queue : " + totalQueueTime()/queueTimes.size()+"s"+"\n");
            fileWriter.write("Maximum lenght of the queue :" + maxLenght+"\n");
            fileWriter.write("Minimum waiting time to check ticket: " + minCheckTime()+"s"+"\n");
            fileWriter.write("Maximum waiting time to check ticket: " + maxCheckTime()+"s"+"\n");
            fileWriter.write("Minimum waiting time in queue(first passenger in queue): " + minCheckTime()+"s"+"\n");
            fileWriter.write("Maximum waiting time in queue(last passenger in queue): " + totalCheckTime()+"s"+"\n");


            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File writing unsuccessfull.");
        }
    }
        public void saveDataQueueArray(){
        try {
            //writing files from code to files
            FileOutputStream FOS_waitingRoom = new FileOutputStream("queueArrayData.txt");
            ObjectOutputStream OOS_waitingRoom = new ObjectOutputStream(FOS_waitingRoom);
            OOS_waitingRoom.writeObject(queueArray);
            FOS_waitingRoom.close();
            OOS_waitingRoom.flush();
            OOS_waitingRoom.close();
            System.out.println("Successfully saved data into file");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //adding each and every needed variable as everything is needed when loading in order to make program run properly
            try {
                List allData = new ArrayList();
                allData.add(first);
                allData.add(last);
                allData.add(maxStayInQueue);
                allData.add(lenght);
                allData.add(maxLenght);
                allData.add(maxNoPassengersInQueue);
                allData.add(deletedPassengers);
                allData.add(cloneWaitingRoom);
                allData.add(boardedPassengers);
                allData.add(queueTimes);
                //writing files from code to files
                FileOutputStream FOS_waitingRoom = new FileOutputStream("queueArrayData2.txt");
                ObjectOutputStream OOS_waitingRoom = new ObjectOutputStream(FOS_waitingRoom);
                OOS_waitingRoom.writeObject(allData);
                FOS_waitingRoom.close();
                OOS_waitingRoom.flush();
                OOS_waitingRoom.close();
                System.out.println("Successfully saved data into file");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
    }
    public void loadDataQueueArray(){
        try {
            // reading file and extracting objects
            FileInputStream FIS_waitingRoom = new FileInputStream("queueArrayData.txt");
            ObjectInputStream OIS_waitingRoom = new ObjectInputStream(FIS_waitingRoom);
            Passenger[] readObject =(Passenger[]) OIS_waitingRoom.readObject();
            for(int x =0;x<readObject.length;x++){
                queueArray[x]=readObject[x];
            }
            System.out.println("Successfully loaded data into file");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        try {
            // reading file and extracting objects
            FileInputStream FIS_waitingRoom = new FileInputStream("queueArrayData2.txt");
            ObjectInputStream OIS_waitingRoom = new ObjectInputStream(FIS_waitingRoom);
            List readObject =(List) OIS_waitingRoom.readObject();
            first=(Integer) readObject.get(0);
            last=(Integer) readObject.get(1);
            maxStayInQueue=(Integer) readObject.get(2);
            lenght=(Integer) readObject.get(3);
            maxLenght=(Integer) readObject.get(4);
            maxNoPassengersInQueue=(Integer) readObject.get(5);
            deletedPassengers=(List<Passenger>)readObject.get(6);
            cloneWaitingRoom=(List<Passenger>) readObject.get(7);
            boardedPassengers=(List<Passenger>) readObject.get(8);
            queueTimes=(List<Integer>) readObject.get(9);

            System.out.println("Successfully loaded data into file");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
    //setting not arrived list
    public void setNotArrived(int notArrived){
        notArrivedList.add(notArrived);
    }
}