/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch.localsearch.opt;

import vrp.algorithm.constructsearch.localsearch.base.LocalSearch;
import vrp.solution.Routes;

/**
 * This class represents a 2-opt local search.
 */
public class TwoOpt extends LocalSearch {
  /**
   * Implement the 2-opt local search.
   */
  protected Routes implementation(int route, int customer1) {
    Routes bestSolution = this.solution.clone();
    int routeSize = this.solution.getRouteSize(route) - 1;
    for (int customer2 = customer1 + 1; customer2 < routeSize; customer2++) {
      Routes newSolution = this.twoOptSwap(this.solution, route, customer1, customer2);
      if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
        bestSolution = newSolution;
      }
    }
    return bestSolution;
  }
  
  /**
   * Swap two customers in a route.
   * @param previousRoutes The previous solution.
   * @param indexRoute The index of the route.
   * @param firstIndex The index of the first customer.
   * @param secondIndex The index of the second customer.
   * @return The new solution.
   */
  private Routes twoOptSwap(Routes previousRoutes, int indexRoute, int firstIndex, int secondIndex) {
    Routes newRoutes = previousRoutes.clone();
    Integer firstCustomer = previousRoutes.getCustomer(indexRoute, firstIndex);
    Integer previousFirstCustomer = previousRoutes.getCustomer(indexRoute, firstIndex - 1);
    newRoutes.sumCostSearch(-this.dataModel.distance(previousFirstCustomer, firstCustomer));
    for (int i = secondIndex; i >= firstIndex; i--) {
      int position = secondIndex - i + firstIndex;
      Integer customer = previousRoutes.getCustomer(indexRoute, position);
      Integer previousCustomer = newRoutes.getCustomer(indexRoute, position - 1);
      Integer secondCustomer = previousRoutes.getCustomer(indexRoute, i);
      Integer nextCustomer = previousRoutes.getCustomer(indexRoute, position + 1);
      newRoutes.sumCostSearch(-this.dataModel.distance(customer, nextCustomer));
      newRoutes.setCustomer(indexRoute, position, secondCustomer);
      newRoutes.sumCostSearch(this.dataModel.distance(previousCustomer, secondCustomer));
    }
    Integer lastCustomer = previousRoutes.getCustomer(indexRoute, secondIndex + 1);
    newRoutes.sumCostSearch(this.dataModel.distance(firstCustomer, lastCustomer));
    return newRoutes;
  }
}
