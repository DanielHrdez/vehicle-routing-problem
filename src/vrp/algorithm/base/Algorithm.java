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
import vrp.algorithm.constructsearch.Grasp;
import vrp.algorithm.constructsearch.Gvns;
import vrp.algorithm.simple.Greedy;
import vrp.algorithm.util.Functions;

/**
 * Algorithm base class.
 */
public abstract class Algorithm {
  protected DataModel dataModel;
  protected Routes routes;
  protected int maxCustomersByRoute;
  protected int numberOfVehicles;
  private double PERCENTAGE_CUSTOMERS_BY_ROUTE = 0.1;
  
  /**
   * Run the algorithm.
   * @param dataModel The data model.
   * @return The solution.
   */
  public Routes run(DataModel dataModel) {
    this.dataModel = dataModel;
    this.numberOfVehicles = dataModel.getNumberOfVehicles();
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
    this.maxCustomersByRoute = (int) Math.round((numberCustomers / this.numberOfVehicles) + (numberCustomers * PERCENTAGE_CUSTOMERS_BY_ROUTE)) + 1;
  }
  
  protected boolean checkResult(Routes result) {
    int cost = 0;
    for (int route = 0; route < this.numberOfVehicles; route++) {
      for (int customer = 0; customer < result.getRouteSize(route) - 1; customer++) {
        cost += this.dataModel.distance(result.getCustomer(route, customer), result.getCustomer(route, customer + 1));
      }
    }
    return cost == result.getCostSearch();
  }
  
  /**
   * Add the depot to all the vehicles.
   */
  protected Routes addDepot(Routes routes) {
    return Functions.addDepot(routes, this.dataModel);
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
      case "Gvns": return new Gvns();
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
}
