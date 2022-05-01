/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch.localsearch.reinsertion;

import java.util.Random;

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
    int prevCustomer1 = customer1 - 1;
    for (int customer2 = 1; customer2 < prevCustomer1; customer2++) {
      Routes newSolution = this.insert(this.solution, route, customer1, route, customer2);
      if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
        bestSolution = newSolution;
      }
    }
    for (int customer2 = customer1 + 2; customer2 < routeSize; customer2++) {
      Routes newSolution = this.insert(this.solution, route, customer1, route, customer2);
      if (newSolution.getCostSearch() < bestSolution.getCostSearch()) {
        bestSolution = newSolution;
      }
    }
    return bestSolution;
  }
    
  protected Routes randomImplementation(int randomRoute, int randomCustomer1) {
    Random random = new Random();
    int routeSize = this.solution.getRouteSize(randomRoute);
    int randomCustomer2 = random.nextInt(1, routeSize);
    int counter = 0;
    while (
        randomCustomer1 == randomCustomer2 ||
        randomCustomer2 == randomCustomer1 + 1 ||
        randomCustomer2 == randomCustomer1 - 1
    ) {
      randomCustomer2 = random.nextInt(1, routeSize);
      if (++counter > this.numberOfVehicles) return this.solution;
    }
    return this.insert(this.solution, randomRoute, randomCustomer1, randomRoute, randomCustomer2);
  }
}
