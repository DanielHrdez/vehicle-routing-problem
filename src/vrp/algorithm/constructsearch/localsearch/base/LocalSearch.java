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

  public Routes randomSearch(Routes solution, DataModel dataModel, int maxCustomersByRoute) {
    this.solution = solution;
    this.dataModel = dataModel;
    this.maxCustomersByRoute = maxCustomersByRoute;
    this.numberOfVehicles = this.solution.getNumberOfRoutes();
    Random random = new Random();
    int randomRoute = random.nextInt(this.numberOfVehicles);
    int routeSize = this.solution.getRouteSize(randomRoute);
    while (routeSize <= 2) {
      randomRoute = random.nextInt(this.numberOfVehicles);
      routeSize = this.solution.getRouteSize(randomRoute);
    }
    int randomCustomer = random.nextInt(1, routeSize - 1);
    return this.randomImplementation(randomRoute, randomCustomer);
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
        if (this.solution.getRouteSize(route) == 2) continue;
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
