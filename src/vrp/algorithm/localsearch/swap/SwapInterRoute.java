/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.localsearch.swap;

import vrp.algorithm.localsearch.base.LocalSearchResult;
import vrp.solution.Routes;

public class SwapInterRoute extends Swap {
  protected LocalSearchResult implementation(int rute1, int customer1) {
    Routes bestSolution = this.solution.clone();
    boolean improved = false;
    for (int rute2 = 0; rute2 < numberOfVehicles; rute2++) {
      if (rute1 == rute2) continue;
      if (solution.getRouteSize(rute2) == 2) continue;
      for (int customer2 = 1; customer2 < solution.getRouteSize(rute2) - 1; customer2++) {
        Routes newSolution = this.swap(solution, rute1, customer1, rute2, customer2);
        if (newSolution.getCost() < bestSolution.getCost()) {
          bestSolution = newSolution;
          improved = true;
        }
      }
    }
    return new LocalSearchResult(bestSolution, improved);
  }
}
