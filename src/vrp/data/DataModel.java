/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.data;

/**
 * This class represents a model.
 */
public class DataModel {
  private int numberOfVehicles;
  private int numberOfCustomers;
  private int[][] distanceMatrix;
  private boolean[] customers;
  private int numberOfVisitedCustomers;
  private int depot;
  
  /**
   * Constructor of the class.
   *
   * @param numberOfVehicles Number of vehicles.
   * @param numberOfCustomers Number of customers.
   * @param distanceMatrix Distance matrix.
   */
  public DataModel(
      int numberOfVehicles,
      int numberOfCustomers,
      int[][] distanceMatrix
  ) {
    this.numberOfVehicles = numberOfVehicles;
    this.numberOfCustomers = numberOfCustomers;
    this.distanceMatrix = distanceMatrix;
    this.customers = new boolean[numberOfCustomers];
    this.depot = 0;
    this.numberOfVisitedCustomers = 0;
  }
  
  public int getNumberOfVisitedCustomers() {
    return this.numberOfVisitedCustomers;
  }

  /**
   * Getter of the depot of the model.
   * @return The depot of the model.
   */
  public int getDepot() {
    return this.depot;
  }

  /**
   * Getter of the customer.
   * 
   * @param position Position of the customer.
   * @return True if the customer is visited, false otherwise.
   */
  public boolean getCustomer(int position) {
    return this.customers[position];
  }

  /**
   * Setter of the customer.
   * 
   * @param position Position of the customer.
   */
  public void setCustomer(int position) {
    this.numberOfVisitedCustomers++;
    this.customers[position] = true;
  }

  /**
   * Reset the customers.
   */
  public void resetCustomers() {
    for (int i = 0; i < this.numberOfCustomers; i++) {
      this.customers[i] = false;
    }
  }

  /**
   * Getter of the number of vehicles.
   *
   * @return Number of vehicles.
   */
  public int getNumberOfVehicles() {
    return this.numberOfVehicles;
  }

  /**
   * Getter of the number of customers.
   *
   * @return Number of customers.
   */
  public int getNumberOfCustomers() {
    return this.numberOfCustomers;
  }

  /**
   * Get the distance between two customers.
   * 
   * @param from Origin customer.
   * @param to Destination customer.
   * @return Distance between the two customers.
   */
  public int distance(int from, int to) {
    return this.distanceMatrix[from][to];
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Number of vehicles: ");
    sb.append(this.numberOfVehicles);
    sb.append("\n");
    sb.append("Number of customers: ");
    sb.append(this.numberOfCustomers);
    sb.append("\n");
    sb.append("Distance matrix: \n");
    for (int i = 0; i < this.numberOfCustomers; i++) {
      for (int j = 0; j < this.numberOfCustomers; j++) {
        sb.append(this.distanceMatrix[i][j]);
        sb.append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
