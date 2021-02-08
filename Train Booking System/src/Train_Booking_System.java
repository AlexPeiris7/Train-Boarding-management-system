import com.mongodb.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeString.toLowerCase;

public class Train_Booking_System  extends Application {
    Scene view_seats, empty_seats, add_customer;

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //calling first method
        first();
    }
    public void first() {
        //initializing needed lists with hashmaps inside needed for the code
        //list mainly needed for direction
        HashMap<String, List> booked_toCmb = new HashMap<String, List>();
        List <HashMap>toCmb = new ArrayList<HashMap>();
        toCmb.add(booked_toCmb);
        HashMap<String, List> booked_toBad = new HashMap<String, List>();
        List <HashMap>toBadulla = new ArrayList<HashMap>();
        toBadulla.add(booked_toBad);
        //to show in empty seats and view seats the booked seats
        menu(toCmb,toBadulla);
    }
    public void menu(List <HashMap> toCmb,List <HashMap> toBadulla) {

        System.out.println("A/C Compartment in Denuwara Menike Train to Badulla");
        System.out.println("Press A - to add a customer to a seat");
        System.out.println("Press V - to view all seats");
        System.out.println("Press E - to display empty seats");
        System.out.println("Press D - to delete customer from seat");
        System.out.println("Press F - to find the seat for a given customer name");
        System.out.println("Press O - to view seats ordered alphabetically by name");
        System.out.println("Press S - to store program data into a file");
        System.out.println("Press L - to load program data from file");
        System.out.println("Press Q - to quit the program");
        System.out.print("Enter letter:");
        Scanner sc1 = new Scanner(System.in);
        String User_input = toLowerCase(sc1.nextLine());

        switch (User_input) {
            case "v":
                System.out.println("View seats");
                viewSeat(toCmb,toBadulla);
                menu(toCmb,toBadulla);
                break;
            case "a":
                System.out.println("Add customer to seats");
                addSeat(toCmb,toBadulla);
                menu(toCmb,toBadulla);
                break;
            case "e":
                System.out.println("Empty seats");
                emptySeat(toCmb,toBadulla);
                menu(toCmb,toBadulla);
                break;
            case "d":
                System.out.println("Delete customer from seat");
                deleteSeat(toCmb,toBadulla);
                System.out.println("");
                menu(toCmb,toBadulla);
                break;
            case "f":
                System.out.println("Find the seat for a given customer name");
                findSeat(toCmb,toBadulla);
                System.out.println("");
                menu(toCmb,toBadulla);
                break;
            case "o":
                System.out.println("View seats ordered alphabetically by name");
                orderSeat(toCmb,toBadulla);
                System.out.println("");
                menu(toCmb,toBadulla);
                break;
            case "s":
                System.out.println("Store program data into a file");
                saveData(toCmb,toBadulla);
                System.out.println("");
                menu(toCmb,toBadulla);
                break;
            case "l":
                System.out.println("Load program data from file");
                loadData(toCmb,toBadulla);
                System.out.println("");
                menu(toCmb,toBadulla);
                break;
            case "q":
                quit();
                break;
            default:
                System.out.println("Wrong Input");
                menu(toCmb,toBadulla);
                break;
        }
    }
    //method to display seats used in viewSeat method and emptySeat method
    public void displayButtons_View_Empty(List show_booked,FlowPane root,String scenetype){
        for (int button = 1; button <= 42; button++) {
            Button addseats_btn = new Button(Integer.toString(button));
            addseats_btn.setId(Integer.toString(button));
            addseats_btn.setMinSize(70, 70);
            //Checking if button id is in booked list(already booked) setting
            // if yes set button color to blue else to powderblue
            if (show_booked.contains(addseats_btn.getId())) {
                //string scenetype used to identify if we are printing buttons for which scene
                if (scenetype.equals("empty")) {
                    addseats_btn.setStyle("-fx-background-color:gray ");
                    addseats_btn.setDisable(true);
                    addseats_btn.setText("");
                }else {
                    addseats_btn.setStyle("-fx-background-color:red ");
                    addseats_btn.setDisable(true);
                }
            } else {
                addseats_btn.setStyle("-fx-background-color:powderblue ");
            }
            //adding objects to the scene
            root.getChildren().add(addseats_btn);
        }
    }
    //method to display seats used in addSeats method
    public void displayButtons_AddSeats(List show_booked,FlowPane root ,List seats){
        //Displaying buttons and setting ID's
        for (int button = 1; button <= 42; button++) {
            Button addseats_btn = new Button(Integer.toString(button));
            addseats_btn.setId(Integer.toString(button));
            addseats_btn.setMinSize(70, 70);
            //Checking if button id is in booked list(already booked) setting
            // if yes set button color to red else to powderblue
            if (show_booked.contains(addseats_btn.getId())) {
                addseats_btn.setStyle("-fx-background-color:red ");
                addseats_btn.setDisable(true);
            } else {
                addseats_btn.setStyle("-fx-background-color:powderblue ");
            }
            //adding objects to the scene
            root.getChildren().add(addseats_btn);
            //When clicking the buttons , button id gets appended to booked_seats list
            //color and text of buttons change to let user know what he selected
            addseats_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addseats_btn.setText("Selected");
                    seats.add(addseats_btn.getId());
                    addseats_btn.setStyle("-fx-background-color:yellow ");
                }
            });
        }
    }

    //Passing all the lists as a parameter - view seats
    public void viewSeat(List <HashMap> toCmb,List <HashMap> toBadulla) {
        Stage window = new Stage();
        //View Seats scene
        //Displaying buttons in order horizontally
        FlowPane root1 = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        root1.setPadding(new Insets(50));
        root1.setStyle("-fx-background-color:gray ");
        HashMap<String, List> temp = new HashMap<>();
        Label empty = new Label("");
        empty.setMinSize(250,20);
        ComboBox direction = new ComboBox();
        direction.setPromptText("Select direction");
        direction.getItems().addAll("Colombo to Badulla","Badulla to Colombo");
        root1.getChildren().addAll(empty,direction);
        //string scenetype used to identify if we are printing buttons for which scene
        String scenetype = "view";
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                direction.setDisable(true);
                if (direction.getValue().equals("Badulla to Colombo")){
                    // setting temp hashmap same as booked_Seat hmap
                    temp.putAll(toCmb.get(0));
                    List show_booked = new ArrayList();
                    //setting the list of lists to a normal list(used to display booked seats)
                    temp.values().forEach(show_booked::addAll);
                    //Displaying buttons
                    displayButtons_View_Empty(show_booked,root1,scenetype);
                }else if(direction.getValue().equals("Colombo to Badulla")) {
                    // setting temp hashmap same as booked_Seat hmap
                    temp.putAll(toBadulla.get(0));
                    List show_booked = new ArrayList();
                    //setting the list of lists to a normal list(used to display booked seats)
                    temp.values().forEach(show_booked::addAll);
                    //Displaying buttons
                    displayButtons_View_Empty(show_booked,root1,scenetype);
                }
            }
        };
        direction.setOnAction(event);
        view_seats = new Scene(root1, 550, 620);
        window.setScene(view_seats);
        window.setTitle("Train Booking System - View Seats");
        window.showAndWait();
    }

    //Passing all the lists as a parameter - Add customer to seat
    //adding seats - depending on direction user has to clicked buttons to book seats
    public void addSeat(List <HashMap> toCmb,List <HashMap> toBadulla) {
        List seats = new ArrayList();
        Stage window = new Stage();
        //Add seats scene
        //Displaying buttons in order horizontally
        FlowPane root2 = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        root2.setPadding(new Insets(50));
        root2.setStyle("-fx-background-color:gray ");
        add_customer = new Scene(root2, 545, 800);
        //Textfields for name
        TextField name = new TextField("");
        name.setPromptText("Enter name:");
        name.setPrefWidth(400);
        name.setMaxWidth(400);
        DatePicker date = new DatePicker(LocalDate.now().plusDays(1));
        date.setDayCellFactory(picker -> new DateCell() { 
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                //disabling past and current days to that you can book seats from next day onwards
                LocalDate tomo = LocalDate.now().plusDays(1);
                setDisable(empty || date.compareTo(tomo) < 0 );
            }
        });
        date.setMinSize(250,20);
        ComboBox direction = new ComboBox();
        direction.setDisable(true);
        date.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                direction.setDisable(false);
            }
        });
        direction.setPromptText("Select direction");
        direction.getItems().addAll("Colombo to Badulla","Badulla to Colombo");
        root2.getChildren().addAll(date,direction);
        HashMap<String, List> temp = new HashMap<>();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                direction.setDisable(true);
                if (direction.getValue().equals("Badulla to Colombo")){
                    // setting temp hashmap same as booked_Seat hmap
                    temp.putAll(toCmb.get(0));
                    List show_booked = new ArrayList();
                    //setting the list of lists to a normal list(used to display booked seats)
                    temp.values().forEach(show_booked::addAll);
                    displayButtons_AddSeats(show_booked,root2,seats);
                    //confirm button adds customer name and tel num to appropriate list
                    Button confirm_btn = new Button();
                    confirm_btn.setText("Confirm");
                    //disabling button until user chooses a seat
                    confirm_btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            toCmb.get(0).put(name.getText(), seats);
                            window.close();
                        }
                    });
                    //cancel button removes the selected buttons(in order to let user have a change of mind)
                    Button cancel_btn = new Button();
                    cancel_btn.setText("Cancel");
                    cancel_btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            window.close();
                        }
                    });
                    root2.getChildren().addAll(confirm_btn, cancel_btn);
                }else if(direction.getValue().equals("Colombo to Badulla")) {
                    // setting temp hashmap same as booked_Seat hmap
                    temp.putAll(toBadulla.get(0));
                    List show_booked = new ArrayList();
                    //setting the list of lists to a normal list(used to display booked seats)
                    temp.values().forEach(show_booked::addAll);
                    //display buttons
                    displayButtons_AddSeats(show_booked,root2,seats);
                    //confirm button adds customer name and tel num to appropriate list
                    Button confirm_btn = new Button();
                    confirm_btn.setText("Confirm");
                    //disabling button until user chooses a seat
                    confirm_btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            toBadulla.get(0).put(name.getText(), seats);
                            window.close();
                        }
                    });
                    //cancel button removes the selected buttons(in order to let user have a change of mind)
                    Button cancel_btn = new Button();
                    cancel_btn.setText("Cancel");
                    //disabling button until user chooses a seat
                    cancel_btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            window.close();
                        }
                    });
                    root2.getChildren().addAll(confirm_btn, cancel_btn);
                }
            }
        };
        direction.setOnAction(event);
        root2.getChildren().addAll(name);
        window.setScene(add_customer);
        window.setTitle("Train Booking System - Add customer to seat");
        window.showAndWait();
    }


    //Passing all the lists as a parameter - empty seats
    //Displaying only empty seats
    public void emptySeat(List <HashMap> toCmb,List <HashMap> toBadulla) {
        Stage window = new Stage();
        //Empty Seats scene
        //Displaying buttons in order horizontally
        FlowPane root3 = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        root3.setPadding(new Insets(50));
        root3.setStyle("-fx-background-color:gray ");
        HashMap<String, List> temp = new HashMap<>();
        //align buttons to next row
        Label empty = new Label("");
        empty.setMinSize(250,20);
        ComboBox direction = new ComboBox();
        direction.setPromptText("Select direction");
        direction.getItems().addAll("Colombo to Badulla","Badulla to Colombo");
        root3.getChildren().addAll(empty,direction);
        //string scenetype used to identify if we are printing buttons for which scene
        String scenetype = "empty";
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                direction.setDisable(true);
                if (direction.getValue().equals("Badulla to Colombo")){
                    // setting temp hashmap same as booked_Seat hmap
                    temp.putAll(toCmb.get(0));
                    List show_booked = new ArrayList();
                    //setting the list of lists to a normal list(used to display booked seats)
                    temp.values().forEach(show_booked::addAll);
                    //Displaying buttons
                    displayButtons_View_Empty(show_booked,root3,scenetype);
                }else if(direction.getValue().equals("Colombo to Badulla")) {
                    // setting temp hashmap same as booked_Seat hmap
                    temp.putAll(toBadulla.get(0));
                    List show_booked = new ArrayList();
                    //setting the list of lists to a normal list(used to display booked seats)
                    temp.values().forEach(show_booked::addAll);
                    //Displaying buttons
                    displayButtons_View_Empty(show_booked,root3,scenetype);

                }
            }
        };
        direction.setOnAction(event);
        view_seats = new Scene(root3, 550, 620);
        window.setScene(view_seats);
        window.setTitle("Train Booking System - Empty Seats");
        window.showAndWait();

    }
    // delete seat according to name entered by user
    public void deleteSeat(List <HashMap> toCmb,List <HashMap> toBadulla) {
        int dir=0;
        HashMap direction=new HashMap();
        while(true){
            System.out.println("Enter the number of the direction of the customer :");
            System.out.println("1 - Badulla to Colombo");
            System.out.println("2 - Colombo to Badulla");
            Scanner sc2 = new Scanner(System.in);
            dir = sc2.nextInt();
            if(dir==1 || dir==2){ break;}
            else{
                System.out.println("Wrong Input, enter correct number");
            }}
        if(dir==1){
            direction=toCmb.get(0);
        }else{
            direction=toBadulla.get(0);
        }
        String name = "";
        while(true){
            System.out.println("Enter the name of the customer to delete his/her booked seats");
            Scanner sc1 = new Scanner(System.in);
            name = sc1.nextLine();
            if (direction.containsKey(name)) {
                break;
            }else{System.out.println("There is no one called " + name + " check the spellings");
            }
        }
        direction.remove(name);
        if(dir==1){
            toCmb.get(0).remove(name);
        }else{
            toBadulla.get(0).remove(name);
        }
        System.out.println("Reservations have been deleted");
        System.out.println();
    }
    //displaying name according to name entered by user
    public void findSeat(List <HashMap> toCmb,List <HashMap> toBadulla) {
        String name = "";
        //user validation
        while(true){
            System.out.println("Enter the name of the customer to find his/her booked seats");
            Scanner sc1 = new Scanner(System.in);
            name = sc1.nextLine();
            if (toBadulla.get(0).containsKey(name)|| toCmb.get(0).containsKey(name) ) {
                break;
            }else{System.out.println("There is no one called " + name + " check the spellings");
            }
        }
        //displaying names according to user input
        if (toBadulla.get(0).containsKey(name)) {
            System.out.println(name + " booked: " + toBadulla.get(0).get(name)+" from Colombo to Badulla");
        }
        if (toCmb.get(0).containsKey(name)) {
            System.out.println(name + " booked: " + toCmb.get(0).get(name)+" from Badulla to Colombo");
        }
        System.out.println();
    }
    //displaying all names in alphabetical order and the seats booked by them
    public void orderSeat(List <HashMap> toCmb,List <HashMap> toBadulla) {
        //Adding key values in booked_seats hashmap to a list
        List<String> keys = new ArrayList<>();
        keys.addAll(toCmb.get(0).keySet());
        keys.addAll(toBadulla.get(0).keySet());
        while(true){
            int swaps=0;
            for (int x=0;x<(keys.size()-1);x++){
                //checking if list.get(x) is larger than the next element using compareto and >0 if larger(<0 if smaller)
                // compareToIgnoreCase so that it takes the strings as same format
                if (keys.get(x).compareToIgnoreCase(keys.get(x+1))>0){
                    String a=keys.get(x);
                    String b=keys.get(x+1);
                    // replacing the positon of larger string to smaller and viceversa
                    keys.set(x,b );
                    keys.set(x+1,a );
                    //adding 1 to swap var if swap is made to check if the loop should stop or not
                    swaps=swaps+1;
                }
            }
            //if no swaps are made list is in alphabetical order
            if (swaps==0){
                break;
             }
        }
        //displaying names in order
        for (int y=0;y<keys.size();y++){
            //display keys and values
            if(toCmb.get(0).containsKey(keys.get(y))){
                System.out.println(keys.get(y)+" : "+toCmb.get(0).get(keys.get(y))+" From Badulla to Colombo");
            }else if(toBadulla.get(0).containsKey(keys.get(y))){
                System.out.println(keys.get(y)+" : "+toBadulla.get(0).get(keys.get(y))+" From Colombo to Badulla");
            }
        }
    }
    //saving data to files and db which can be loaded again through loadData method
    public void saveData(List <HashMap> toCmb,List <HashMap> toBadulla) {
        //choosing between file handling and datdase
        String choice = "";
        //user validation
        while (true) {
            System.out.println("Enter F to save as a file");
            System.out.println("Enter D to save in MongoDB");
            Scanner sc1 = new Scanner(System.in);
            choice = sc1.nextLine();
            if (choice.toLowerCase().equals("f") || choice.toLowerCase().equals("d")) {
                break;
            } else {
                System.out.println("Wrong Input try again");
            }
        }
        if (choice.toLowerCase().equals("f")) {
            //try and except instead of throws exception
            try {
                //writing files from code to files
                FileOutputStream FOS_toBadulla = new FileOutputStream("Colombo-Badulla_data.txt");
                ObjectOutputStream OOS_toBadulla = new ObjectOutputStream(FOS_toBadulla);
                OOS_toBadulla.writeObject(toBadulla.get(0));
                FOS_toBadulla.close();
                OOS_toBadulla.flush();
                OOS_toBadulla.close();
                FileOutputStream FOS_toCmb = new FileOutputStream("Badulla-Colombo_data.txt");
                ObjectOutputStream OOS_toCmb = new ObjectOutputStream(FOS_toCmb);
                OOS_toCmb.writeObject(toCmb.get(0));
                FOS_toCmb.close();
                OOS_toCmb.flush();
                OOS_toCmb.close();
                System.out.println("Successfully saved data into file");
            } catch (Exception e) {
            }
        } else {

            //copying hashmaps to list as keys and values separately
            List toCol_val = new ArrayList(toCmb.get(0).values());
            List toCol_keys = new ArrayList(toCmb.get(0).keySet());
            List toBad_val = new ArrayList(toBadulla.get(0).values());
            List toBad_keys = new ArrayList(toBadulla.get(0).keySet());
            //connecting to server
            MongoClient client = new MongoClient("localhost",27017);
            //creating a db
            DB db = client.getDB( "train_sys" );
            DBCollection collection = db.getCollection("train_data");
            //creating basic objects which contain keys and values of hashmaps
            BasicDBObject obj1 = new BasicDBObject("_id", "ToCol_Values").append("data", toCol_val);
            BasicDBObject obj2 = new BasicDBObject("_id", "ToCol_Keys").append("data", toCol_keys);
            BasicDBObject obj3 = new BasicDBObject("_id", "ToBad_Values").append("data", toBad_val);
            BasicDBObject obj4 = new BasicDBObject("_id", "ToBad_Keys").append("data", toBad_keys);
            DBCursor cursor = collection.find();
            //remove existing data
            while (cursor.hasNext()) {
                collection.remove(cursor.next());
            }
            //adding objects to db collection
            collection.insert(obj1);
            collection.insert(obj2);
            collection.insert(obj3);
            collection.insert(obj4);

            //checking if values have been saved
            DBCursor cursor1 = collection.find();
            while(cursor1.hasNext()) {
                System.out.println(cursor1.next());
            }

        }
    }
    public void loadData(List <HashMap> toCmb,List <HashMap> toBadulla) {
        //choosing between file handling and datdase
        String choice ="";
        while(true){
            System.out.println("Enter F to load from a file");
            System.out.println("Enter D to load from MongoDB");
            Scanner sc1 = new Scanner(System.in);
            choice = sc1.nextLine();
            if (choice.toLowerCase().equals("f")||choice.toLowerCase().equals("d")) {
                break;
            }else{System.out.println("Wrong Input try again");
            }
        }
        if(choice.toLowerCase().equals("f")) {
        try {
            // reading file and extracting objects
            FileInputStream FIS_toBadulla = new FileInputStream("Colombo-Badulla_data.txt");
            ObjectInputStream OIS_toBadulla = new ObjectInputStream(FIS_toBadulla);
            HashMap<String,List> toBadulla_readfile =(HashMap<String,List>)OIS_toBadulla.readObject();
            toBadulla.get(0).putAll(toBadulla_readfile);

            FileInputStream FIS_toCmb = new FileInputStream("Badulla-Colombo_data.txt");
            ObjectInputStream OIS_toCmb = new ObjectInputStream(FIS_toCmb);
            HashMap<String,List> toCmc_readfile =(HashMap<String,List>)OIS_toCmb.readObject();
            toCmb.get(0).putAll(toCmc_readfile);
            System.out.println("Successfully loaded data into file");
        }
        catch (Exception e){}}
        else {

        }

    }


    public void quit() {
        System.out.println("Exiting the system");
        //quits the program
        Platform.exit();
    }
}