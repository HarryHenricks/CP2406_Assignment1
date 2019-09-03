public class Road {
    private int numSegments;
    private double speedLimit;
    private String orientation;
    private int roadID; // This will need to be a 2 dimensional array later, or 2 variables representing both dimensions

    Road(int numberOfSegments, int positionOfRoad){
        roadID = positionOfRoad;
        numSegments = numberOfSegments;
        orientation = "Horizontal";
        speedLimit = 50.0/3; // 60km/hr in m/s let this be the default limit
    }
    Road(int numberOfSegments, String Orientation, double speedLim, int positionOnRoad){
        roadID = positionOnRoad;
        numSegments = numberOfSegments;
        orientation = Orientation;
        speedLimit = speedLim;
    }
    double getSpeedLimit() {
        return speedLimit;
    }
    int getNumSegments(){
        return  numSegments;
    }
    String getOrientation(){
        return orientation;
    }
    int getRoadID(){
        return roadID;
    }
}
