
public class Passenger implements java.io.Serializable {

    private String firstName;
    private int secondsInQueue;
    private String passengerSeatNo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getSecondsInQueue() {
        return secondsInQueue;
    }

    public void setSecondsInQueue(int secondsInQueue) {
        this.secondsInQueue = secondsInQueue;
    }

    public String getPassengerSeatNo() {
        return passengerSeatNo;
    }

    public void setPassengerSeatNo(String passengerSeatNo) {
        this.passengerSeatNo = passengerSeatNo;
    }

    public void displayReport(){
        System.out.println("Name: " + getFirstName() + "-" +
                "Seat number: " + getPassengerSeatNo() + "-" +
                "Number of seconds in queue: " + getSecondsInQueue()+"s"   );
        System.out.println();
    }

}
