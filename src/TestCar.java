import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCar {
    @Test void checkCar(){
        Car car = new Car(1);
        assertEquals(car.getType(), "Car");
        assertEquals(car.getLength(), 1);
        assertEquals(car.getRoadId(), 1);
        assertEquals(car.getSegmentOfRoad(), 1);
    }
    @Test void checkBus(){
        Car bus = new Car("Bus", 3, 5);
        assertEquals(bus.getType(), "Bus");
        assertEquals(bus.getLength(), 3);
        assertEquals(bus.getRoadId(), 5);
        assertEquals(bus.getSegmentOfRoad(), 1);
    }
}