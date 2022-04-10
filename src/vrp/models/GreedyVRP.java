/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.models;

import vrp.data.DataModel;
import vrp.models.base.VehicleRouting;

/**
 * GreedyVRP is a class that implements
 * the Greedy algorithm for the Vehicle Routing Problem.
 */
public class GreedyVRP extends VehicleRouting {
  /**
   * Constructor of the class.
   * @param model The model of the problem.
   */
  public GreedyVRP(DataModel model) {
    super(model);
  }

  /**
   * Solve the problem using the Greedy algorithm.
   */
  public void solve() {
    this.farthestCustomers();
    while (this.allVisited()) {
      this.closestCustomers();
    }
    this.addDepot();
  }

  /**
   * Find the farthest customers from the depot.
   */
  private void farthestCustomers() {
    int numberOfCustomers = this.model.getNumberOfCustomers();
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int depot = this.model.getDepot();
    this.model.setCustomer(depot);

    for (int i = 0; i < numberOfCustomers; i++) {
      for (int j = 0; j < numberOfVehicles; j++) {
        if (this.model.distance(depot, i) > this.model.distance(depot, this.routes[j][0])) {
          this.routes[j] = new int[] {i};
          break;
        }
      }
    }

    for (int i = 0; i < numberOfVehicles; i++) {
      int customer = this.routes[i][0];
      this.model.setCustomer(customer);
      this.cost += this.model.distance(depot, customer);
    }
  }

  /**
   * Find the closest customers from the current route.
   */
  private void closestCustomers() {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int numberOfCustomers = this.model.getNumberOfCustomers();

    for (int i = 0; i < numberOfVehicles; i++) {
      int minimum = Integer.MAX_VALUE;
      int closestCustomer = -1;
      for (int j = 0; j < this.routes[i].length; j++) {
        for (int k = 0; k < numberOfCustomers; k++) {
          int currentDistance = this.model.distance(this.routes[i][j], k);
          if (currentDistance < minimum && !this.model.getCustomer(k)) {
            minimum = currentDistance;
            closestCustomer = k;
          }
        }
      }
      this.routes[i] = this.addCustomer(this.routes[i], closestCustomer);
      this.model.setCustomer(closestCustomer);
      this.cost += minimum;
    }
  }

  /**
   * Add the depot to all the vehicles.
   */
  private void addDepot() {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int depot = this.model.getDepot();

    for (int i = 0; i < numberOfVehicles; i++) {
      this.cost += this.model.distance(this.routes[i][this.routes[i].length - 1], depot);
      this.routes[i] = this.addCustomer(this.routes[i], depot);
    }
  }
}
