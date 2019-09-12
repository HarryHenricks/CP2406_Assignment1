import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /* Currently the simulation will run a more complex simulation with multiple objetcs, for a simple
        simulation of just 2 roads, a traffic light and a car, replace the create objects section and write
        objects section with the code contained below in this comment

        Replace lines 24 - 32 with the following:
        Road road1 = new Road(4, 1,2, true);
        Road road2 = new Road(6, 2,0, false);
        TrafficLight trafficLight = new TrafficLight(1, 3);
        Car car1 = new Car(1);

        And replace lines 40 - 48 with:
        carList.add(car1);
        roadList.add(road1);
        roadList.add(road2);
        trafficLightList.add(trafficLight);
         */

        // Create objects for this simulation
        Road road1 = new Road(7, 1,2, true);
        Road road2 = new Road(5, 2,5, false);
        Road road3 = new Road(8, 3, 4, true);
        Road road4 = new Road(6, 4, 5, false);
        Road road5 = new Road(5, 5, 0, false);
        TrafficLight trafficLight1 = new TrafficLight(2, 4);
        TrafficLight trafficLight2 = new TrafficLight(4, 5);
        Car car1 = new Car(1);
        Car car2 = new Car(1);

        // Lists to store objects
        List<Car> carList = new ArrayList<>();
        List<Road> roadList = new ArrayList<>();
        List<TrafficLight> trafficLightList = new ArrayList<>();

        // Write objects into the lists
        carList.add(car1);
        carList.add(car2);
        roadList.add(road1);
        roadList.add(road2);
        roadList.add(road3);
        roadList.add(road4);
        roadList.add(road5);
        trafficLightList.add(trafficLight1);
        trafficLightList.add(trafficLight2);

        // set the probability of traffic lights changing
        double rateOfChange = 0.8;
        boolean endSimulation = true; // variable that keeps simulation running, set to false when all cars have reached
        // their destination, ie when car list is empty (because when car runs out of roads it is deleted)
        int maximumCars = 3; // controls the maximum number of cars able to be in the simulation
        double spawnProbability = 0.25; // controls probability that a car will spawn if it can

        //simulation loop
        while (endSimulation) {
            carList = spawnCar(roadList, carList, maximumCars, spawnProbability);
            checkCarStatus(carList); // display the status of all cars
            Thread.sleep(1500); // wait 1 second before trying to drive the car
            updateTrafficLights(trafficLightList, rateOfChange);
            carList = drive(carList, roadList, trafficLightList);
            endSimulation = endSimulation(carList);
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
                    carList.set(i, currentCar);
                } else { // else the car is at the last segment of the last road
                    System.out.println("Car is at the end of the last road and can't drive any further");
                    carList.remove(currentCar); // then delete the car as it has driven to its destination
                    --i;
                }
            } else { // car not at last segment of a road
                for (Object o : trafficLightList) { // for each traffic light check if they will stop any cars
                    currentTrafficLight = (TrafficLight) o;
                    if (currentCar.getRoadId() == currentTrafficLight.getRoadId() && currentCar.getSegmentOfRoad() + 1 ==
                            currentTrafficLight.getSegmentOfRoad() && !currentTrafficLight.getStatus()) { // if car on the same road and at segment before traffic light and traffic light is not green
                        System.out.println("Car " + (i + 1) + " is at traffic light");
                    } else {
                        canDrive = true; // else car can drive, if after running through the whole for loop, then this variable is true then use this variable to drive the car
                    }
                }
                if (canDrive){ // if car is able to drive
                    currentCar.drive(); // then car can drive
                    carList.set((i), currentCar); // update car in the array list
                }
            }
        }
        return carList;
    }

    private static void checkCarStatus(List carList) {
        Car currentCar;
        for (int i=0; i<carList.size(); ++i) {
            if(carList.get(0) == null){ // if the first car in the array is empty, then no cars in the simulation
                break;
            }
            currentCar = (Car) carList.get(i);
            System.out.println("Car " + (i+1) + " is on road " + currentCar.getRoadId() + " and is at segment " + currentCar.getSegmentOfRoad());
        }
        System.out.println();
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

    private static boolean endSimulation(List carList){
        return carList.size() != 0; // return false if the car list is empty
    }

    private static List spawnCar(List roadList, List carList, int maximumCars, double spawnProbability) {
        Road currentRoad;
        List<Road> potentialSpawnRoad = new ArrayList<>();
        if (carList.size() < maximumCars) { // if there aren't already max number of cars in the simulation
            for (Object o : roadList) {
                currentRoad = (Road) o;
                if (currentRoad.getStartRoad()) { // if the current road is able to spawn a car
                    potentialSpawnRoad.add(currentRoad); // add it to the potential spawn list
                }
            }
            double spawnValue = Math.random();
            if (spawnValue <= spawnProbability) {
                int spawnRoad = (int) (Math.random() * potentialSpawnRoad.size() + 1); // pick a random valid road to spawn a car on
                Car car = new Car(spawnRoad);
                carList.add(car);
            }
        }
        return carList;
    }
}
