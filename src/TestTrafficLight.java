import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTrafficLight {
    @Test void checkTrafficLight(){
        TrafficLight trafficLight = new TrafficLight(1, 5);
        assertEquals(trafficLight.getRoadId(),1);
        assertEquals(trafficLight.getSegmentOfRoad(), 5);
    }
}