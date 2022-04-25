/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.base;

import vrp.data.DataModel;
import vrp.solution.Routes;
import vrp.algorithm.*;

/**
 * Algorithm base class.
 */
public abstract class Algorithm {
  protected DataModel dataModel;
  protected Routes routes;
  private int maxCustomersByRoute;
  private double PERCENTAGE_CUSTOMERS_BY_ROUTE = 0.1;
  
  /**
   * Run the algorithm.
   * @param dataModel The data model.
   * @return The solution.
   */
  public Routes run(DataModel dataModel) {
    this.dataModel = dataModel;
    this.routes = new Routes(dataModel.getNumberOfVehicles());
    this.setMaxCustomersByRoute();
    this.implementation();
    return this.routes;
  }

  /**
   * Abstract method to implement the algorithm.
   */
  protected abstract void implementation();

  /**
   * Setter of the maximum number of customers by route.
   */
  protected void setMaxCustomersByRoute() {
    int numberCustomers = this.dataModel.getNumberOfCustomers();
    int numberVehicles = this.dataModel.getNumberOfVehicles();
    this.maxCustomersByRoute = (int) Math.round((numberCustomers / numberVehicles) + (numberCustomers * PERCENTAGE_CUSTOMERS_BY_ROUTE)) + 1;
  }
  
  /**
   * Add the depot to all the vehicles.
   */
  protected void addDepot(Routes routes) {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    int depot = this.dataModel.getDepot();
    this.dataModel.setCustomer(depot);

    for (int i = 0; i < numberOfVehicles; i++) {
      try {
        routes.sumCost(this.dataModel.distance(routes.lastCustomerFromRoute(i), depot));
        routes.addCustomer(i, depot);
      } catch (Exception e) {
        routes.addCustomer(i, depot);
        routes.sumCost(this.dataModel.distance(routes.lastCustomerFromRoute(i), depot));
      }
    }
  }

  /**
   * Returns the algorithm.
   * @param algorithmType The algorithm type.
   * @return The algorithm.
   */
  public static Algorithm getAlgorithm(String algorithmType) {
    switch (algorithmType) {
      case "Greedy": return new Greedy();
      case "Grasp": return new Grasp();
    }
    throw new IllegalArgumentException("Algorithm type not found");
  }

  /**
   * Returns the algorithm name.
   * @return The algorithm name.
   */
  public String getAlgorithmType() {
    return this.getClass().getSimpleName();
  }

  /**
   * Check if the route is full.
   * @param routes The routes.
   * @param route The route.
   * @return True if the route is full.
   */
  protected boolean full(Routes routes, int route) {
    return routes.getRouteSize(route) >= this.maxCustomersByRoute;
  }
}
