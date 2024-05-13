import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Order {
    String identifier;
    String coordinate;
    Date timestamp;
    Date outTime;

    Order(String identifier, String coordinate, String timestamp) throws ParseException {
        this.identifier = identifier;
        this.coordinate = coordinate;
        this.timestamp = new SimpleDateFormat("HH:mm:ss").parse(timestamp);
        this.outTime = new SimpleDateFormat("HH:mm:ss").parse(timestamp);
    }
}

public class DroneDeliveryScheduler {
    static Date currentTime = null;
    static boolean processOutput = false;

    static Date calculateDeliveryTime(Order order) {

        int xIndex = order.coordinate.indexOf('E') != -1 ? order.coordinate.indexOf('E') : order.coordinate.indexOf('W');
        int yIndex = order.coordinate.indexOf('N') != -1 ? order.coordinate.indexOf('N') : order.coordinate.indexOf('S');

        int xCoord = Integer.parseInt(order.coordinate.substring(xIndex + 1));
        if (order.coordinate.charAt(xIndex) == 'W') {
            xCoord = -xCoord;
        }

        int yCoord = Integer.parseInt(order.coordinate.substring(yIndex + 1, xIndex));
        if (order.coordinate.charAt(yIndex) == 'S') {
            yCoord = -yCoord;
        }

        try {

            double distance = (Math.sqrt(Math.pow(Math.abs(xCoord), 2) + Math.pow(Math.abs(yCoord), 2)) * 60);

            if (processOutput) {
                if (currentTime == null) {
                    currentTime = new SimpleDateFormat("HH:mm:ss").parse("06:00:00");
                }

                if (currentTime != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currentTime);
                    cal.add(Calendar.SECOND, (int) (2 * distance));
                    order.outTime = cal.getTime();
                }
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(order.timestamp);
            cal.add(Calendar.SECOND, (int) (2 * distance));
            return cal.getTime();
        } catch (Exception e) {
            return new Date();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {

        List<Order> orders = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            orders.add(new Order(parts[0], parts[1], parts[2]));
        }
        br.close();

        orders.sort(Comparator.comparing(DroneDeliveryScheduler::calculateDeliveryTime));

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
        processOutput = true;
        for (Order order : orders) {
            calculateDeliveryTime(order);
            bw.write(String.format("%s %tT\n", order.identifier, currentTime));
            currentTime = order.outTime;
        }

        double npsScore = calculateNPS(orders);
        bw.write(String.format("NPS %.2f\n", npsScore));
        bw.close();
    }

    static double calculateNPS(List<Order> orders) {
        int promoters = 0;
        int detractors = 0;
        for (Order order : orders) {
            if (((order.outTime.getTime() / (1000 * 60 * 60)) % 24) - ((order.timestamp.getTime() / (1000 * 60 * 60)) % 24) <= 1) {
                promoters++;
            } else if (((order.outTime.getTime() / (1000 * 60 * 60)) % 24) - ((order.timestamp.getTime() / (1000 * 60 * 60)) % 24) > 4) {
                detractors++;
            }
        }
        double totalOrders = orders.size();
        return ((promoters / totalOrders) - (detractors / totalOrders)) * 100;
    }
}
