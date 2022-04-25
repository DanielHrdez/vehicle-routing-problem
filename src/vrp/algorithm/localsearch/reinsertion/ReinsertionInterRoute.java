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

/**
 * Class tha implements the reinsertion of a customer inter routes.
 */
public class ReinsertionInterRoute extends LocalSearch {
  /**
   * Implementation of the reinsertion of a customer inter routes.
   */
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

  /**
   * Inserts a customer in a route.
   * @param routes The routes of the solution.
   * @param from The route where the customer is from.
   * @param targetPosition The position where the customer is.
   * @param to The route where the customer is going be.
   * @param position The position where the customer is going be.
   * @return The new solution.
   */
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
