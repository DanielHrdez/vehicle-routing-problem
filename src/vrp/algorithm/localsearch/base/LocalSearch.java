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

  public Routes run(Routes solution, DataModel dataModel) {
    this.dataModel = dataModel;
    this.solution = solution;
    this.numberOfVehicles = solution.getNumberOfRoutes();
    Routes bestSolution = solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int rute = 0; rute < this.numberOfVehicles; rute++) {
        if (solution.getRouteSize(rute) == 2) continue;
        for (int customer = 1; customer < solution.getRouteSize(rute) - 1; customer++) {
          LocalSearchResult result = this.implementation(rute, customer);
          bestSolution = result.solution;
          improved = result.improved;
        }
      }
      solution = bestSolution;
    }
    return bestSolution;
  }

  protected abstract LocalSearchResult implementation(int rute, int customer);
}
