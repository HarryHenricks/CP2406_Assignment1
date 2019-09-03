public class Main {
    public static void main(String[] args) throws InterruptedException {

        Road road1 = new Road(5, 1);
        Road road2 = new Road(5, 2);
        TrafficLight trafficLight = new TrafficLight(1, 3);
        Car car = new Car(1);

        Road[] roadArray = new Road[10];

        roadArray[0] = road1;
        roadArray[1] = road2;
        boolean canDrive = true;

        //simulation loop
        while (canDrive) {
            System.out.println("The car is on road " + car.getRoadId() + " and is at segment " + car.getSegmentOfRoad());

            Thread.sleep(1000); // wait 1 second before trying to drive the car

            canDrive = drive(car, roadArray, trafficLight);
            trafficLight.changeStatus(true);
        }
    }

    private static boolean drive(Car car, Road[] roadArray, TrafficLight trafficLight) {
        if (car.getSegmentOfRoad() == roadArray[car.getRoadId() - 1].getNumSegments()) { // if car at last segment of road
            if (roadArray[car.getRoadId()] != null) { // and next road object exists
                car.changeRoad(car.getRoadId() + 1); // move the car to the next road
                return true;
            } else { // otherwise the car is at the last segment and no more roads exist
                System.out.println("Car is at the end of the last road and can't drive any further");
                return false;
            }
        } else { // if the car is not at the last segment
            if (car.getRoadId() == trafficLight.getRoadId() && car.getSegmentOfRoad() + 1 == trafficLight.getSegmentOfRoad() && !trafficLight.getStatus()) { // if the car is at the segment before the traffic light and the traffic light is red
                System.out.println("At traffic light");
                return true;
            } else {
                car.drive();
                return true;
            }
        }
    }
}

