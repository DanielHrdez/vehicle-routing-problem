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
  public GreedyVRP() {
    super();
  }

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
    this.model.setCustomer(this.model.getDepot());
    while (!this.allVisited()) {
      this.closestCustomers();
    }
    this.addDepot();
  }

  /**
   * Find the closest customers from the current route.
   */
  private void closestCustomers() {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int minimumCustomer = -1;
    int minimumDistance = Integer.MAX_VALUE;
    int route = -1;

    for (int i = 0; i < numberOfVehicles; i++) {
      int minimum = Integer.MAX_VALUE;
      int closestCustomer = -1;
      int end = this.routes[i][this.routes[i].length - 1];
      for (int notVisitedCustomer : this.model.getNotVisitedCustomers()) {
        int currentDistance = this.model.distance(end, notVisitedCustomer);
        if (currentDistance < minimum) {
          minimum = currentDistance;
          closestCustomer = notVisitedCustomer;
        }
      }
      if (minimum < minimumDistance) {
        minimumDistance = minimum;
        minimumCustomer = closestCustomer;
        route = i;
      }
    }
    if (minimumCustomer != -1) {
      this.routes[route] = this.addCustomer(this.routes[route], minimumCustomer);
      this.model.setCustomer(minimumCustomer);
      this.cost += minimumDistance;
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

    this.model.resetCustomers();
  }
}
