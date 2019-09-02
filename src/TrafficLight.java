class TrafficLight {
    private boolean status; // let a boolean value to represent green/ red light, ignore yellow lights for simplicity
    private int roadId;
    private int segmentOfRoad;

    TrafficLight(int roadID){
        status = false;
        roadId = roadID;
        segmentOfRoad = 1;
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
    void changeStatus(){
        status = !status;
    }

}
