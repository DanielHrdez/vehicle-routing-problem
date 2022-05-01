/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch.localsearch.base;

import java.util.Random;

import vrp.data.DataModel;
import vrp.solution.Routes;

/**
 * This abstract class represents a local search algorithm.
 */
public abstract class LocalSearch {
  protected int numberOfVehicles;
  protected Routes solution;
  protected DataModel dataModel;
  protected int maxCustomersByRoute;

  public Routes randomSearch(Routes solution, DataModel dataModel, int maxCustomersByRoute, int iterations) {
    this.solution = solution.clone();
    this.dataModel = dataModel;
    this.maxCustomersByRoute = maxCustomersByRoute;
    this.numberOfVehicles = this.solution.getNumberOfRoutes();
    Random random = new Random();
    int ignoredCustomer = -1;

    for (int i = 0; i < iterations; i++) {
      int randomRoute = random.nextInt(this.numberOfVehicles);
      int routeSize = this.solution.getRouteSize(randomRoute);
      int counter = 0;
      while (routeSize <= 2) {
        randomRoute = random.nextInt(this.numberOfVehicles);
        routeSize = this.solution.getRouteSize(randomRoute);
        if (++counter > this.numberOfVehicles) return this.solution;
      }
      int randomCustomer = random.nextInt(1, routeSize - 1);
      counter = 0;
      while (randomCustomer == ignoredCustomer) {
        randomCustomer = random.nextInt(1, routeSize - 1);
        if (++counter > this.numberOfVehicles) return this.solution;
      }
      this.solution = this.randomImplementation(randomRoute, randomCustomer);
      ignoredCustomer = randomCustomer;
    }

    return this.solution;
  }

  protected abstract Routes randomImplementation(int randomRoute, int randomCustomer);
  
  /**
   * Initialize the local search algorithm.
   */
  public Routes search(Routes solution, DataModel dataModel, int maxCustomersByRoute) {
    this.dataModel = dataModel;
    this.solution = solution;
    this.numberOfVehicles = this.solution.getNumberOfRoutes();
    this.maxCustomersByRoute = maxCustomersByRoute;
    Routes bestSolution = this.solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int route = 0; route < this.numberOfVehicles; route++) {
        if (this.solution.getRouteSize(route) <= 2) continue;
        int routeSize = this.solution.getRouteSize(route) - 1;
        for (int customer = 1; customer < routeSize; customer++) {
          Routes result = this.implementation(route, customer);
          if (result.getCostSearch() < bestSolution.getCostSearch()) {
            bestSolution = result;
            improved = true;
          }
        }
      }
      this.solution = bestSolution;
    }
    return bestSolution;
  }

  protected boolean checkResult(Routes result) {
    int cost = 0;
    for (int route = 0; route < this.numberOfVehicles; route++) {
      for (int customer = 0; customer < result.getRouteSize(route) - 1; customer++) {
        cost += this.dataModel.distance(result.getCustomer(route, customer), result.getCustomer(route, customer + 1));
      }
    }
    return cost == result.getCost();
  }

  /**
   * This method is implemented by the subclasses.
   * @param rute The route.
   * @param customer The customer.
   * @return The new solution.
   */
  protected abstract Routes implementation(int rute, int customer);

}
