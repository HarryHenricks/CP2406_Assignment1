import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRoad {
    @Test void checkRoad(){
        Road road = new Road(10, 1,0, false);
        assertEquals(road.getNumSegments(), 10);
        assertEquals(road.getRoadID(), 1);
        assertEquals(road.getOrientation(), "Horizontal");
        assertEquals(road.getSpeedLimit(), 60.0/3);
        assertEquals(road.getNextRoadId(), 0);
        assertFalse(road.getStartRoad());
    }
}