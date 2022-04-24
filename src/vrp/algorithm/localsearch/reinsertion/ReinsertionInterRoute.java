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

import vrp.algorithm.localsearch.base.LocalSearch;
import vrp.algorithm.localsearch.base.LocalSearchResult;
import vrp.solution.Routes;

public class ReinsertionInterRoute extends LocalSearch {
  protected LocalSearchResult implementation(int rute1, int customer1) {
    Routes bestSolution = this.solution.clone();
    boolean improved = false;
    for (int rute2 = 0; rute2 < this.numberOfVehicles; rute2++) {
      if (rute1 == rute2) continue;
      if (this.solution.getRouteSize(rute2) == 2) continue;
      for (int customer2 = 1; customer2 < this.solution.getRouteSize(rute2) - 1; customer2++) {
        Routes newSolution = this.insertInter(this.solution, rute1, customer1, rute2, customer2);
        if (newSolution.getCost() < bestSolution.getCost()) {
          bestSolution = newSolution;
          improved = true;
        }
      }
    }
    return new LocalSearchResult(bestSolution, improved);
  }

  private Routes insertInter(Routes routes, int from, int targetPosition, int to, int position) {
    Routes newRoutes = routes.clone();
    Integer previousTarget = newRoutes.getCustomer(from, targetPosition - 1);
    Integer targetCustomer = newRoutes.getCustomer(from, targetPosition);
    Integer nextTarget = routes.getCustomer(from, targetPosition + 1);
    Integer destinyPrevious = routes.getCustomer(to, position - 1);
    Integer destinyCustomer = routes.getCustomer(to, position);
    newRoutes.sumCost(-this.dataModel.distance(previousTarget, targetCustomer));
    newRoutes.sumCost(-this.dataModel.distance(targetCustomer, nextTarget));
    newRoutes.sumCost(-this.dataModel.distance(destinyPrevious, destinyCustomer));
    newRoutes.removeCustomer(from, targetPosition);
    newRoutes.addCustomer(to, targetCustomer, position);
    newRoutes.sumCost(this.dataModel.distance(previousTarget, nextTarget));
    newRoutes.sumCost(this.dataModel.distance(destinyPrevious, targetCustomer));
    newRoutes.sumCost(this.dataModel.distance(targetCustomer, destinyCustomer));
    return newRoutes;
  }
}
