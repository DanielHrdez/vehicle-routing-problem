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
   * @param route The route.
   * @param customer1 The first customer.
   * @return The new solution.
   */
  protected Routes implementation(int route, int customer1) {
    Routes bestSolution = this.solution.clone();
    int routeSize = this.solution.getRouteSize(route) - 1;
    for (int customer2 = customer1 + 1; customer2 < routeSize; customer2++) {
      Routes newSolution = this.swap(this.solution, route, customer1, route, customer2);
      if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
        bestSolution = newSolution;
      }
    }
    return bestSolution;
  }

  /**
   * Random search.
   * @param randomRoute The random route.
   * @param randomCustomer1 The random customer.
   * @return The new solution.
   */
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
