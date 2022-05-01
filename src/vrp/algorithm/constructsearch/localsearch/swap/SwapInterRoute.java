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
 * This class represents a swap inter route local search algorithm.
 */
public class SwapInterRoute extends Swap {
  /**
   * Implement the swap inter route local search algorithm.
   */
  protected Routes implementation(int route1, int customer1, int iterations) {
    Routes bestSolution = this.solution.clone();
    for (int route2 = 0; route2 < this.numberOfVehicles; route2++) {
      if (route1 == route2) continue;
      int routeSize = this.solution.getRouteSize(route2) - 1;
      if (routeSize == 1) continue;
      for (int customer2 = 1; customer2 < routeSize; customer2++) {
        Routes newSolution = this.swap(this.solution, route1, customer1, route2, customer2);
        if (iterations > 0) {
          newSolution = this.search(newSolution, this.dataModel, this.maxCustomersByRoute, iterations - 1);
        }
        if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
          bestSolution = newSolution;
        }
      }
    }
    return bestSolution;
  }

  protected Routes randomImplementation(int randomRoute1, int randomCustomer1) {
    Random random = new Random();
    int randomRoute2 = random.nextInt(this.numberOfVehicles);
    int routeSize = this.solution.getRouteSize(randomRoute2);
    while (randomRoute1 == randomRoute2 || routeSize <= 2) {
      randomRoute2 = random.nextInt(this.numberOfVehicles);
    }
    int randomCustomer2 = random.nextInt(1, routeSize - 1);
    return this.swap(this.solution, randomRoute1, randomCustomer1, randomRoute2, randomCustomer2);
  }
}
