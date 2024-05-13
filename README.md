# Drone Delivery Scheduler

## Description

The Drone Delivery Scheduler is a Java program designed to efficiently schedule and manage drone deliveries based on given orders. It calculates delivery times for each order and generates an output file with the delivery schedule.

## Features

- **Order Processing:** Parses orders from an input file and calculates delivery times based on coordinates and timestamps.
- **Delivery Time Calculation:** Calculates delivery times for each order considering the distance from the delivery point.
- **Output Generation:** Generates an output file containing the delivery schedule and Net Promoter Score (NPS) calculation.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your system.

### Installation

1. Clone the repository or download the source code files.
2. Compile the Java files using `javac DroneDeliveryScheduler.java`.
3. Run the program using `java DroneDeliveryScheduler`.

### Input File Format

The input file should contain orders in the following format:

```
<identifier> <coordinate> <timestamp>
```

- `<identifier>`: Unique identifier for the order.
- `<coordinate>`: Coordinate of the delivery point in the format "XN" or "XS" for latitude and "YE" or "YW" for longitude, where X and Y are integer values.
- `<timestamp>`: Timestamp of the order in "HH:mm:ss" format.

### Output File Format

The output file will contain the delivery schedule and NPS score in the following format:

```
<identifier> <delivery_time>
NPS <score>
```

- `<identifier>`: Unique identifier of the order.
- `<delivery_time>`: Time at which the order will be delivered.
- `<score>`: Net Promoter Score (NPS) calculated based on order delivery times.

### Algorithm 

Algorithm used in the Drone Delivery Scheduler:

### 1. Reading and Parsing Orders

- The program starts by reading orders from an input file, with each line representing a single order.
- Each order consists of an identifier, coordinates, and a timestamp. These components are parsed and stored in an `Order` object.

### 2. Calculating Delivery Time

- The `calculateDeliveryTime` function is responsible for determining the delivery time for each order.
- It calculates the distance between the drone's current position and the delivery coordinates using the Euclidean distance formula.
- The distance is then converted into time, assuming a constant speed of the drone.
- The calculated delivery time is returned as a `Date` object.

### 3. Sorting Orders

- After calculating delivery times for all orders, the list of orders is sorted based on their calculated delivery times.
- This ensures that orders are processed in the order of their scheduled deliveries.

### 4. Generating Output

- Once the orders are sorted, the program proceeds to generate the delivery schedule.
- It iterates through the sorted list of orders, calculating the delivery time for each order and updating the current time accordingly.
- The delivery schedule is written to an output file, along with the Net Promoter Score (NPS) calculation.

### 5. Net Promoter Score (NPS) Calculation

- The NPS is calculated based on the delivery times of the orders.
- Promoters are customers whose orders are delivered within one hour of the estimated delivery time.
- Detractors are customers whose orders are delivered more than four hours after the estimated delivery time.
- The NPS is calculated as the percentage of promoters minus the percentage of detractors.

### Key Points:

- The algorithm efficiently schedules drone deliveries by calculating delivery times based on coordinates and timestamps.
- It considers both distance and time to estimate delivery times accurately.
- Sorting orders ensures that they are processed in the order of their scheduled deliveries, maintaining efficiency and customer satisfaction.
- The NPS calculation provides valuable feedback on the quality of service provided by the drone delivery system.

Overall, the algorithm optimizes the scheduling and management of drone deliveries, ensuring timely and efficient service to customers.
## Usage

1. Prepare an input file with orders in the specified format.
2. Run the program and provide the input file as an argument.
3. Check the generated output file for the delivery schedule and NPS score.
