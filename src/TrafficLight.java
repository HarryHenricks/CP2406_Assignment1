class TrafficLight {
    private boolean status; // let a boolean value to represent green/ red light, ignore yellow lights for simplicity
    private int roadId;
    private int segmentOfRoad;

    TrafficLight(int roadId, int segmentOfRoad){
        status = false; // by default the light is red
        this.roadId = roadId;
        this.segmentOfRoad = segmentOfRoad;
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
    void changeStatus(boolean status){
        this.status = status;
    }

}
