/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.localsearch.reinsertion;

import vrp.solution.Routes;

/**
 * Class implementing the reinsertion of a customer inter routes.
 */
public class ReinsertionIntraRoute extends Reinsertion {
  /**
   * Implementation of the reinsertion of a customer inter routes.
   */
  protected Routes implementation(int route, int customer1) {
    Routes bestSolution = this.solution.clone();
    int routeSize = this.solution.getRouteSize(route);
    for (int customer2 = 1; customer2 < customer1 - 1; customer2++) {
      Routes newSolution = this.insert(this.solution, route, customer1, route, customer2);
      if (newSolution.getCost() < bestSolution.getCost()) {
        bestSolution = newSolution;
      }
    }
    for (int customer2 = customer1 + 2; customer2 < routeSize; customer2++) {
      Routes newSolution = this.insert(this.solution, route, customer1, route, customer2);
      if (newSolution.getCost() < bestSolution.getCost()) {
        bestSolution = newSolution;
      }
    }
    return bestSolution;
  }
}
