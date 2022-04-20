/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm;

import vrp.algorithm.base.*;
import vrp.data.DataModel;
import vrp.solution.Routes;

/**
 * GreedyVRP is a class that implements
 * the Greedy algorithm for the Vehicle Routing Problem.
 */
public class Greedy extends Algorithm {
  /**
   * Solve the problem using the Greedy algorithm.
   */
  public Routes run(DataModel dataModel) {
    this.dataModel = dataModel;
    this.routes = new Routes(dataModel.getNumberOfVehicles());
    this.addDepot(this.routes);
    while (!this.dataModel.allVisited()) {
      this.closestCustomers();
    }
    this.addDepot(this.routes);
    this.dataModel.resetCustomers();
    return this.routes;
  }

  /**
   * Find the closest customers from the current route.
   */
  private void closestCustomers() {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    int minimumCustomer = -1;
    int minimumDistance = Integer.MAX_VALUE;
    int route = -1;

    for (int i = 0; i < numberOfVehicles; i++) {
      int minimum = Integer.MAX_VALUE;
      int closestCustomer = -1;
      int end = this.routes.lastCustomerFromRoute(i);
      for (int notVisitedCustomer : this.dataModel.getNotVisitedCustomers()) {
        int currentDistance = this.dataModel.distance(end, notVisitedCustomer);
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
      this.routes.addCustomer(route, minimumCustomer);
      this.dataModel.setCustomer(minimumCustomer);
      this.routes.sumCost(minimumDistance);
    }
  }
}
