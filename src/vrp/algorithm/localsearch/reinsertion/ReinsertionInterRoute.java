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
import vrp.solution.Routes;

public class ReinsertionInterRoute extends LocalSearch {
  protected Routes implementation(int route1, int customer1) {
    Routes bestSolution = this.solution.clone();
    for (int route2 = 0; route2 < this.numberOfVehicles; route2++) {
      if (route1 == route2) continue;
      int routeSize = this.solution.getRouteSize(route2) - 1;
      if (routeSize == 1) continue;
      for (int customer2 = 1; customer2 < routeSize; customer2++) {
        Routes newSolution = this.insertInter(this.solution, route1, customer1, route2, customer2);
        if (newSolution.getCost() < bestSolution.getCost()) {
          bestSolution = newSolution;
        }
      }
    }
    return bestSolution;
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
