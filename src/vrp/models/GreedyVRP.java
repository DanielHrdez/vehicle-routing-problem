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
    this.closestCustomers();
    while (this.allVisited()) {
      this.closestCustomers();
    }
    this.addDepot();
  }

  private int[] farthestCustomers() {
    int numberOfCustomers = this.model.numberOfCustomers();
    int numberOfVehicles = this.model.numberOfVehicles();
    int[] farthestCustomers = new int[numberOfVehicles];
    int depot = this.model.depot();
    for (int i = 0; i < numberOfCustomers; i++) {
      for (int j = 0; j < numberOfVehicles; j++) {
        if (this.model.distance(0, i) > this.model.distance(0, farthestCustomers[j])) {
          farthestCustomers[j] = i;
          break;
        }
      }
    }
  }

  private int[] closestCustomers() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  private int[] addDepot() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  /**
   * Calculates the heuristic of the algorithm.
   */
  protected void heuristic() {}
}
