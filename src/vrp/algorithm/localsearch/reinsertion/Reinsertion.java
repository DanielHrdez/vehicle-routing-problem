package vrp.algorithm.localsearch.reinsertion;

import vrp.algorithm.localsearch.base.LocalSearch;
import vrp.solution.Routes;

public abstract class Reinsertion extends LocalSearch {
  /**
   * Inserts a customer in a route.
   * @param routes The routes of the solution.
   * @param from The route where the customer is from.
   * @param targetPosition The position where the customer is.
   * @param to The route where the customer is going be.
   * @param position The position where the customer is going be.
   * @return The new solution.
   */
  protected Routes insert(Routes routes, int from, int targetPosition, int to, int position) {
    Routes newRoutes = routes.clone();
    Integer previousTarget = routes.getCustomer(from, targetPosition - 1);
    Integer targetCustomer = routes.getCustomer(from, targetPosition);
    Integer nextTarget = routes.getCustomer(from, targetPosition + 1);
    Integer destinyPrevious = routes.getCustomer(to, position - 1);
    Integer destinyCustomer = routes.getCustomer(to, position);
    newRoutes.sumCostSearch(-this.dataModel.distance(previousTarget, targetCustomer));
    newRoutes.sumCostSearch(-this.dataModel.distance(targetCustomer, nextTarget));
    newRoutes.sumCostSearch(-this.dataModel.distance(destinyPrevious, destinyCustomer));
    newRoutes.removeCustomer(from, targetPosition);
    if (from == to && targetPosition < position) position -= 1;
    newRoutes.addCustomer(to, targetCustomer, position);
    newRoutes.sumCostSearch(this.dataModel.distance(previousTarget, nextTarget));
    newRoutes.sumCostSearch(this.dataModel.distance(destinyPrevious, targetCustomer));
    newRoutes.sumCostSearch(this.dataModel.distance(targetCustomer, destinyCustomer));
    return newRoutes;
  }
}
