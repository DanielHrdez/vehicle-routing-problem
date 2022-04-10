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
  protected int[][] routes;
  protected int cost;

  /**
   * Constructor of the class.
   *
   * @param model The model of the problem.
   * It must be a model with a vertex for each customer.
   */
  public VehicleRouting(DataModel model) {
    this.model = model;
    this.routes = new int[model.getNumberOfVehicles()][1];
    this.cost = 0;
  }

  /**
   * Returns the routes of the problem.
   * 
   * @return The routes of the problem.
   */
  public int[][] routes() {
    return routes;
  }

  /**
   * Returns the cost of the solution.
   * 
   * @return The cost of the solution.
   */
  public int cost() {
    return cost;
  }

  /**
   * Solves the vehicle routing problem for the given graph.
   */
  public abstract void solve();

  protected boolean allVisited() {
    return this.model.getNumberOfVisitedCustomers() == this.model.getNumberOfCustomers();
  }

  protected int[] add(int[] array, int element) {
    int[] newArray = new int[array.length + 1];
    for (int i = 0; i < array.length; i++) {
      newArray[i] = array[i];
    }
    newArray[array.length] = element;
    return newArray;
  }
}
