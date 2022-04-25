/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.localsearch.base;

import vrp.data.DataModel;
import vrp.solution.Routes;

public abstract class LocalSearch {
  protected int numberOfVehicles;
  protected Routes solution;
  protected DataModel dataModel;

  public Routes search(Routes solution, DataModel dataModel) {
    this.dataModel = dataModel;
    this.solution = solution;
    this.numberOfVehicles = this.solution.getNumberOfRoutes();
    Routes bestSolution = this.solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int route = 0; route < this.numberOfVehicles; route++) {
        if (this.solution.getRouteSize(route) == 2) continue;
        int routeSize = this.solution.getRouteSize(route) - 1;
        for (int customer = 1; customer < routeSize; customer++) {
          Routes result = this.implementation(route, customer);
          if (result.getCost() < bestSolution.getCost()) {
            bestSolution = result;
            improved = true;
          }
        }
      }
      this.solution = bestSolution;
    }
    return bestSolution;
  }

  protected abstract Routes implementation(int rute, int customer);
}
