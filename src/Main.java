import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create objects for this simulation
        Road road1 = new Road(5, 1,2);
        Road road2 = new Road(5, 2,0);
        TrafficLight trafficLight = new TrafficLight(1, 3);
        Car car = new Car(1);

        // Lists to store objects
        List<Car> carList = new ArrayList<>();
        List<Road> roadList = new ArrayList<>();
        List<TrafficLight> trafficLightList = new ArrayList<>();

        // Write objects into the lists
        carList.add(car);
        roadList.add(road1);
        roadList.add(road2);
        trafficLightList.add(trafficLight);

        // set the probability of traffic lights changing
        double rateOfChange = 0.8;

        //simulation loop
        while (true) {
            checkCarStatus(carList);

            Thread.sleep(1000); // wait 1 second before trying to drive the car

            updateTrafficLights(trafficLightList, rateOfChange);
            carList = drive(carList, roadList, trafficLightList);

        }
    }

    private static List drive(List carList, List roadList, List trafficLightList) {
        Road currentRoad;
        Car currentCar;
        TrafficLight currentTrafficLight;
        List<Car> newCarList = new ArrayList<>();
        boolean canDrive = false;
        for (int i=0; i<carList.size(); ++i){ // check for every car if they can drive
            currentCar = (Car) carList.get(i);
            currentRoad = (Road) roadList.get(currentCar.getRoadId()-1);
            if (currentCar.getSegmentOfRoad() == currentRoad.getNumSegments()-1){ // if car is at last segment of road
                if (currentRoad.getNextRoadId() != 0) { // if next road exists
                    currentCar.changeRoad(currentRoad.getNextRoadId()); // then move the car to the next road
                    carList.add(currentCar);
                } else { // else the car is at the last segment of the last road
                    System.out.println("Car is at the end of the last road and can't drive any further");
                    carList.remove(i); // then delete the car as it has driven to its destination
                    --i; // as the car object has been removed, the indexing number i needs to be corrected
                }
            } else { // car not at last segment of a road
                for (int j=0; i<trafficLightList.size(); ++i) { // for each traffic light check if they will stop any cars
                    currentTrafficLight = (TrafficLight) trafficLightList.get(j);
                    if (currentCar.getRoadId() == currentTrafficLight.getRoadId() && currentCar.getSegmentOfRoad()+1 ==
                            currentTrafficLight.getSegmentOfRoad() && !currentTrafficLight.getStatus()){ // if car on the same road and at segment before traffic light and traffic light is not green
                        System.out.println("Car is at traffic light");
                    } else {
                        canDrive = true; // else car can drive, if after running through the whole for loop, then this variable is true then use this variable to drive the car
                    }
                }
                if (canDrive){ // if car is able to drive
                    currentCar.drive(); // then car can drive
                }
                newCarList.add(currentCar);
            }
        }
        return newCarList;
    }


    private static void checkCarStatus(List carList) {
        Car currentCar;
        for (int i=0; i<carList.size(); ++i) {
            currentCar = (Car) carList.get(i);
            System.out.println("Car " + (i+1) + " is on road " + currentCar.getRoadId() + " and is at segment " + currentCar.getSegmentOfRoad());
        }
    }

    private static void updateTrafficLights(List trafficLightList, double rateOfChange){ // rate of change is the percentage chance of a traffic light changing
        TrafficLight currentTrafficLight;
        double changeVar;
        for (Object o : trafficLightList) {
            changeVar = Math.random();
            currentTrafficLight = (TrafficLight) o;
            if (changeVar <= rateOfChange) { // if the random value is less than or equal to the chance of traffic light changing
                currentTrafficLight.changeStatus(); // then flip the status of the traffic light
            }
        }
    }
}

