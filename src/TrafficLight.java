class TrafficLight {
    private boolean status; // let a boolean value to represent green/ red light, ignore yellow lights for simplicity
    private int roadId;
    private int segmentOfRoad;

    TrafficLight(int roadID, int roadSegment){
        status = false; // by default the light is red
        roadId = roadID;
        segmentOfRoad = roadSegment;
    }

    boolean getStatus(){
        return status;
    }
    int getRoadId(){
        return roadId;
    }
    int getSegmentOfRoad(){
        return segmentOfRoad;
    }
    void changeStatus(boolean stat){
        status = stat;
    }

}
