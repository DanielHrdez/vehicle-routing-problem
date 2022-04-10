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

  /**
   * Check if all the customers are visited.
   * 
   * @return True if all the customers are visited.
   */
  protected boolean allVisited() {
    return this.model.getNumberOfVisitedCustomers() == this.model.getNumberOfCustomers();
  }

  /**
   * Add a route to the route.
   * 
   * @param vehicleRoute The route to add.
   * @param customer The customer to add.
   * @return The new route.
   */
  protected int[] addCustomer(int[] vehicleRoute, int customer) {
    int[] newRoute = new int[vehicleRoute.length + 1];
    for (int i = 0; i < vehicleRoute.length; i++) {
      newRoute[i] = vehicleRoute[i];
    }
    newRoute[vehicleRoute.length] = customer;
    return newRoute;
  }
}
