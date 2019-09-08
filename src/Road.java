public class Road {
    private int numSegments;
    private double speedLimit;
    private String orientation;
    private int roadId; // This will need to be a 2 dimensional array later, or 2 variables representing both dimensions
    private int nextRoadId; // This will keep track of the roads that connect to the current road, for part 2 this
    // may need to store more than 1 road. If the next road variable is empty then the current road is the last road
    private boolean startRoad; // if true then this road is a start road ie one that can spawn a car

    Road(int numSegments, int roadId, int nextRoadId, boolean startRoad){
        this.roadId = roadId;
        this.nextRoadId = nextRoadId;
        this.numSegments = numSegments;
        orientation = "Horizontal";
        this.startRoad = startRoad;
        speedLimit = 60.0/3; // 60km/hr in m/s let this be the default limit
    }
    Road(int numSegments, String orientation, double speedLimit, int roadId, int nextRoadId, boolean startRoad){
        this.roadId = roadId;
        this.nextRoadId = nextRoadId;
        this.numSegments = numSegments;
        this.orientation = orientation;
        this.startRoad = startRoad;
        this.speedLimit = speedLimit;
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
        return roadId;
    }
    int getNextRoadId(){
        return nextRoadId;
    }
    boolean getStartRoad(){
        return startRoad;
    }
}
