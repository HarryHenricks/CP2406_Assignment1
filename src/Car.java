public class Car {
    private String type;
    private int length; // A width variable will need to be implemented later for graphical representation
    private int roadId;
    private int segmentOfRoad;

    Car(int roadID){
        type = "Car";
        length = 1;
        segmentOfRoad = 1;
        roadId = roadID;
    }
    Car(String typeOfVehicle, int vehicleLength, int roadID){
        type = typeOfVehicle;
        length = vehicleLength;
        roadId = roadID;
        segmentOfRoad = 1;
    }
    String getType(){
        return type;
    }
    int getLength(){
        return length;
    }
    int getRoadId(){
        return roadId;
    }
    int getSegmentOfRoad(){
        return segmentOfRoad;
    }
    void drive(){
        segmentOfRoad += 1;
    }
    void changeRoad(int newRoad){
        roadId = newRoad;
        segmentOfRoad = 1;
    }

}
