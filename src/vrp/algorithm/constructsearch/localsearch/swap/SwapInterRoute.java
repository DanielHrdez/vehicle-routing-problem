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

import vrp.solution.Routes;

/**
 * This class represents a swap inter route local search algorithm.
 */
public class SwapInterRoute extends Swap {
  /**
   * Implement the swap inter route local search algorithm.
   */
  protected Routes implementation(int route1, int customer1) {
    Routes bestSolution = this.solution.clone();
    for (int route2 = 0; route2 < this.numberOfVehicles; route2++) {
      if (route1 == route2) continue;
      int routeSize = this.solution.getRouteSize(route2) - 1;
      if (routeSize == 1) continue;
      for (int customer2 = 1; customer2 < routeSize; customer2++) {
        Routes newSolution = this.swap(this.solution, route1, customer1, route2, customer2);
        if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
          bestSolution = newSolution;
        }
      }
    }
    return bestSolution;
  }
}
