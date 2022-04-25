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

public class ReinsertionIntraRoute extends LocalSearch {
  protected Routes implementation(int route, int customer1) {
    Routes bestSolution = this.solution.clone();
    int routeSize = this.solution.getRouteSize(route) - 1;
    for (int customer2 = customer1 + 1; customer2 < routeSize; customer2++) {
      Routes newSolution = this.insertIntra(this.solution, route, customer1, customer2);
      if (newSolution.getCost() < bestSolution.getCost()) {
        bestSolution = newSolution;
      }
    }
    return bestSolution;
  }
  
  private Routes insertIntra(Routes routes, int rute, int targetPosition, int position) {
    Routes newRoutes = routes.clone();
    Integer previousTarget = newRoutes.getCustomer(rute, targetPosition - 1);
    Integer targetCustomer = newRoutes.getCustomer(rute, targetPosition);
    Integer nextTarget = routes.getCustomer(rute, targetPosition + 1);
    Integer destinyCustomer = routes.getCustomer(rute, position);
    Integer destinyNext = routes.getCustomer(rute, position + 1);
    newRoutes.sumCost(-this.dataModel.distance(previousTarget, targetCustomer));
    newRoutes.sumCost(-this.dataModel.distance(targetCustomer, nextTarget));
    newRoutes.sumCost(-this.dataModel.distance(destinyCustomer, destinyNext));
    newRoutes.removeCustomer(rute, targetPosition);
    newRoutes.addCustomer(rute, targetCustomer, position);
    newRoutes.sumCost(this.dataModel.distance(previousTarget, nextTarget));
    newRoutes.sumCost(this.dataModel.distance(destinyCustomer, targetCustomer));
    newRoutes.sumCost(this.dataModel.distance(targetCustomer, destinyNext));
    return newRoutes;
  }
}
