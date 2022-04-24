/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.localsearch.opt;

import vrp.algorithm.localsearch.base.LocalSearch;
import vrp.algorithm.localsearch.base.LocalSearchResult;
import vrp.solution.Routes;

public class TwoOpt extends LocalSearch {
  protected LocalSearchResult implementation(int rute, int customer1) {
    Routes bestSolution = this.solution.clone();
    boolean improved = false;
    for (int customer2 = customer1 + 1; customer2 < this.numberOfVehicles; customer2++) {
      Routes newSolution = this.twoOptSwap(solution, rute, customer1, customer2);
      if (newSolution.getCost() < bestSolution.getCost()) {
        bestSolution = newSolution;
        improved = true;
      }
    }
    return new LocalSearchResult(bestSolution, improved);
  }
  
  private Routes twoOptSwap(Routes previousRoutes, int indexRoute, int firstIndex, int secondIndex) {
    Routes newRoutes = previousRoutes.clone();
    Integer firstCustomer = previousRoutes.getCustomer(indexRoute, firstIndex);
    Integer previousFirstCustomer = previousRoutes.getCustomer(indexRoute, firstIndex - 1);
    newRoutes.sumCost(-this.dataModel.distance(previousFirstCustomer, firstCustomer));
    for (int i = secondIndex; i >= firstIndex; i--) {
      int position = secondIndex - i + firstIndex;
      Integer customer = previousRoutes.getCustomer(indexRoute, position);
      Integer previousCustomer = newRoutes.getCustomer(indexRoute, position - 1);
      Integer secondCustomer = previousRoutes.getCustomer(indexRoute, i);
      Integer nextCustomer = previousRoutes.getCustomer(indexRoute, position + 1);
      newRoutes.sumCost(-this.dataModel.distance(customer, nextCustomer));
      newRoutes.setCustomer(indexRoute, position, secondCustomer);
      newRoutes.sumCost(this.dataModel.distance(previousCustomer, secondCustomer));
    }
    Integer lastCustomer = previousRoutes.getCustomer(indexRoute, secondIndex + 1);
    newRoutes.sumCost(this.dataModel.distance(firstCustomer, lastCustomer));
    return newRoutes;
  }
}
