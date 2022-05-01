/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch.localsearch.swap;

import java.util.Random;

import vrp.solution.Routes;

/**
 * This class represents a swap intra route local search algorithm.
 */
public class SwapIntraRoute extends Swap {
  /**
   * Implement the swap intra route local search algorithm.
   */
  protected Routes implementation(int route1, int customer1, int iterations, int ignoreCustomer) {
    Routes bestSolution = this.solution.clone();
    int routeSize = this.solution.getRouteSize(route1) - 1;
    for (int customer2 = customer1 + 1; customer2 < routeSize; customer2++) {
      if (customer2 == ignoreCustomer) continue;
      Routes newSolution = this.swap(this.solution, route1, customer1, route1, customer2);
      if (iterations > 0) {
        newSolution = this.search(
          newSolution,
          this.dataModel,
          this.maxCustomersByRoute,
          iterations - 1,
          customer2
        );
      }
      if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
        bestSolution = newSolution;
      }
    }
    return bestSolution;
  }
  
  protected Routes randomImplementation(int randomRoute, int randomCustomer1) {
    Random random = new Random();
    int routeSize = this.solution.getRouteSize(randomRoute) - 1;
    int randomCustomer2 = random.nextInt(randomCustomer1, routeSize);
    int counter = 0;
    while (randomCustomer1 >= randomCustomer2) {
      randomCustomer2 = random.nextInt(randomCustomer1, routeSize);
      if (++counter > this.numberOfVehicles) return this.solution;
    }
    return this.swap(this.solution, randomRoute, randomCustomer1, randomRoute, randomCustomer2);
  }
}
