public class Road {
    private int numSegments;
    private int speedLimit;
    private String orientation;
    private int roadID; // This will need to be a 2 dimensional array later, or 2 variables representing both dimensions

    Road(int numberOfSegments, int positionOfRoad){
        roadID = positionOfRoad;
        numSegments = numberOfSegments;
        orientation = "Horizontal";
    }
    Road(int numberOfSegments, String Orientation, int speedLim, int positionOnRoad){
        roadID = positionOnRoad;
        numSegments = numberOfSegments;
        orientation = Orientation;
        speedLimit = speedLim;
    }
    public int getSpeedLimit() {
        return speedLimit;
    }
    public int getNumSegments(){
        return  numSegments;
    }
    public String getOrientation(){
        return orientation;
    }
    public int getRoadPosition(){
        return roadID;
    }
}
