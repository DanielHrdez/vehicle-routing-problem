/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.models.base;

import vrp.data.DataModel;

/**
 * This abstract class represents a vehicle routing problem.
 */
public abstract class VehicleRouting {
  protected DataModel model;
  protected int[][] solution;
  protected int[] costs;

  /**
   * Constructor of the class.
   *
   * @param model The model of the problem.
   * It must be a model with a vertex for each customer.
   */
  public VehicleRouting(DataModel model) {
    this.model = model;
  }

  /**
   * Returns the solution of the problem.
   * 
   * @return The solution of the problem.
   */
  public int[][] getSolution() {
    return solution;
  }

  /**
   * Returns the costs of the solution.
   * 
   * @return The costs of the solution.
   */
  public int[] getCosts() {
    return costs;
  }

  /**
   * Solves the vehicle routing problem for the given graph.
   */
  public abstract void solve();

  /**
   * Decide which node to visit next on each vehicle.
   */
  protected abstract void heuristic();

  protected boolean allVisited() {
    return (this.model.numerOfVisitedCustomers() / this.model.numberOfCustomers()) == 1;
  }
}
