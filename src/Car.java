public class Car {
    private int length; // A width variable will need to be implemented later for graphical representation
    private int roadId;
    private int segmentOfRoad;

    Car(int roadID){
        length = 1;
        segmentOfRoad = 1;
        roadId = roadID;
    }
    Car(String typeOfVehicle, int vehicleLength, int roadID){
        length = vehicleLength;
        roadId = roadID;
        segmentOfRoad = 1;
    }



}
