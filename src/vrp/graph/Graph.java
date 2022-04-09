/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.graph;

/**
 * This class represents a graph.
 */
public class Graph {
  private int numberOfVehicles;
  private int numberOfCustomers;
  private int[][] distanceMatrix;
  private boolean[] customers;
  
  /**
   * Constructor of the class.
   *
   * @param numberOfVehicles Number of vehicles.
   * @param numberOfCustomers Number of customers.
   * @param distanceMatrix Distance matrix.
   */
  public Graph(
      int numberOfVehicles,
      int numberOfCustomers,
      int[][] distanceMatrix
  ) {
    this.numberOfVehicles = numberOfVehicles;
    this.numberOfCustomers = numberOfCustomers;
    this.distanceMatrix = distanceMatrix;
    this.customers = new boolean[numberOfCustomers];
  }

  /**
   * Getter of the customer.
   * 
   * @param position Position of the customer.
   * @return True if the customer is visited, false otherwise.
   */
  public boolean customer(int position) {
    return customers[position];
  }

  /**
   * Setter of the customer.
   * 
   * @param position Position of the customer.
   */
  public void setCustomer(int position) {
    customers[position] = true;
  }

  /**
   * Reset the customers.
   */
  public void resetCustomers() {
    for (int i = 0; i < numberOfCustomers; i++) {
      customers[i] = false;
    }
  }

  /**
   * Getter of the number of vehicles.
   *
   * @return Number of vehicles.
   */
  public int numberOfVehicles() {
    return numberOfVehicles;
  }

  /**
   * Getter of the number of customers.
   *
   * @return Number of customers.
   */
  public int numberOfCustomers() {
    return numberOfCustomers;
  }

  /**
   * Get the distance between two customers.
   * 
   * @param from Origin customer.
   * @param to Destination customer.
   * @return Distance between the two customers.
   */
  public int distance(int from, int to) {
    return distanceMatrix[from][to];
  }
}
