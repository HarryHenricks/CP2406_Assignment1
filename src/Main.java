import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JPanel implements ActionListener {

    private Main(){
        setBackground(Color.WHITE);
        add(startSimulationBtn);
        add(endSimulationBtn);
        startSimulationBtn.addActionListener(this);
        endSimulationBtn.addActionListener(this);
    }

    private JButton startSimulationBtn = new JButton("Run simulation");
    private JButton endSimulationBtn = new JButton("Stop simulation");

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.add(new Main());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    private void runSimulation() throws InterruptedException, IOException {

        List<Car> carList = new ArrayList<>();
        Car car1 = new Car(1);
        Car car2 = new Car(1);
        carList.add(car1);
        carList.add(car2);

        List roadList;
        List trafficLightList;

        roadList = loadRoads();
        trafficLightList = loadLights();

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
            saveData(roadList, trafficLightList);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startSimulationBtn){
            try {
                runSimulation();
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == endSimulationBtn){

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Road currentRoad;
        TrafficLight currentTrafficLight;
        int roadWidth = 15; // width of road in pixels
        int roadLength = 30; // length of a road segment in pixels
        int currentx = 0;
        int currenty = 150;

        try {
            List roadList = loadRoads();
            List trafficLightList = loadLights();

            for (int i = 0; i < roadList.size(); ++i) {
                currentRoad = (Road) roadList.get(i);
                if (currentRoad.getStartRoad()) {
                    currentx = 0; // if start road then needs to be drawn against left hand side of panel
                }
                for (int j = 0; j < currentRoad.getNumSegments(); ++j) {

                    for (Object o : trafficLightList) {
                        currentTrafficLight = (TrafficLight) o;
                        if (currentTrafficLight.getRoadId() == currentRoad.getRoadID() && currentTrafficLight.getSegmentOfRoad() == j) { // if there is a traffic light on the segment about to be drawn
                            if (currentTrafficLight.getStatus()) { // if status is true, then we will want to colour the segment green
                                g.setColor(Color.GREEN);
                                if (currentRoad.getOrientation().equals("Horizontal")) {
                                    g.fillRect(currentx, currenty, roadLength, roadWidth);
                                    currentx += roadLength;
                                } else {
                                    g.fillRect(currentx, currenty, roadWidth, roadLength);
                                    currenty += roadLength;
                                }

                            } else { // otherwise red
                                g.setColor(Color.RED);
                                if (currentRoad.getOrientation().equals("Horizontal")) {
                                    g.fillRect(currentx, currenty, roadLength, roadWidth);
                                    currentx += roadLength;
                                } else {
                                    g.fillRect(currentx, currenty, roadWidth, roadLength);
                                    currenty += roadLength;
                                }
                            }
                        }
                    }
                    g.setColor(Color.BLACK);
                    if (currentRoad.getOrientation().equals("Horizontal")) {
                        g.drawRect(currentx, currenty, roadLength, roadWidth);
                        currentx += roadLength;
                    } else {
                        g.drawRect(currentx, currenty, roadWidth, roadLength);
                        currenty += roadLength;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveData(List roadList, List trafficLightList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\CSVData\\RoadData.csv"));
        int currentNumSegments, currentRoadId, nextRoadId;
        String currentOrientation;
        boolean currentStartRoad;
        Road currentRoad;
        for (Object o : roadList) {
            currentRoad = (Road) o;
            currentNumSegments = currentRoad.getNumSegments();
            currentOrientation = currentRoad.getOrientation();
            currentRoadId = currentRoad.getRoadID();
            nextRoadId = currentRoad.getNextRoadId();
            currentStartRoad = currentRoad.getStartRoad();
            String line = currentNumSegments + "," + currentOrientation + "," + currentRoadId + "," + nextRoadId + "," + currentStartRoad + "\n";
            writer.write(line);
        }
        writer.close();
        writer = new BufferedWriter(new FileWriter("src\\CSVData\\LightData.csv"));
        int roadId, segmentOfRoad;
        TrafficLight currentLight;
        for (Object o : trafficLightList){
            currentLight = (TrafficLight) o;
            roadId = currentLight.getRoadId();
            segmentOfRoad = currentLight.getSegmentOfRoad();
            String line = roadId + "," + segmentOfRoad + "\n";
            writer.write(line);
        }
        writer.close();
    }


    private static List loadLights() throws IOException {
        String currentLine = "";
        BufferedReader reader = new BufferedReader(new FileReader("src\\CSVData\\LightData.csv"));

        List<TrafficLight> trafficLightList = new ArrayList<>();

        int roadId, segmentOfRoad;

        String[] currentData;
        while ((currentLine = reader.readLine()) != null){
            currentData = currentLine.split(",");
            roadId = Integer.parseInt(currentData[0]);
            segmentOfRoad = Integer.parseInt(currentData[1]);

            trafficLightList.add(new TrafficLight(roadId, segmentOfRoad));
        }
        return trafficLightList;
    }

    private static List loadRoads() throws IOException {
        String currentLine = "";
        BufferedReader reader = new BufferedReader(new FileReader("src\\CSVData\\RoadData.csv"));

        List<Road> roadList = new ArrayList<>();

        int numSegments, roadId, nextRoadId;
        boolean startRoad;
        String orientation;

        String[] currentData;
        while ((currentLine = reader.readLine()) != null){
            currentData = currentLine.split(",");
            numSegments = Integer.parseInt(currentData[0]);
            orientation = currentData[1];
            roadId = Integer.parseInt(currentData[2]);
            nextRoadId = Integer.parseInt(currentData[3]);
            startRoad = Boolean.parseBoolean(currentData[4]);

            roadList.add(new Road(numSegments, orientation, roadId, nextRoadId, startRoad));
        }
        return roadList;
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
