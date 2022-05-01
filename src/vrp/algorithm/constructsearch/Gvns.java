/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch;

import vrp.algorithm.constructsearch.base.ConstructSearch;
import vrp.algorithm.constructsearch.construction.GreedyRandom;
import vrp.solution.Routes;

public class Gvns extends ConstructSearch {
  private int maxShakes = 4;

  public void setMaxShakes(int maxShakes) {
    this.maxShakes = maxShakes;
  }

  public void implementation() {
    this.iterationsWithoutImprovement = 0;
    this.routes = GreedyRandom.constructSolution(this.dataModel, this.candidates, this.maxCustomersByRoute);
    this.routes.setCostSearch(this.routes.getCost());
    
    for (int i = 0; i < this.maxIterations; i++) {
      for (int shake = 1; shake <= this.maxShakes; shake++) {
        Routes currentSolution = this.localSearchAlgorithm.randomSearch(
            this.routes,
            this.dataModel,
            this.maxCustomersByRoute,
            shake
        );
        currentSolution = this.variableDescent(currentSolution);
        if (this.updateSolution(currentSolution)) shake = 0;
      }
      if (this.iterationsWithoutImprovement > this.maxIterationsWithoutImprovement) {
        break;
      }
    }
  }

  private Routes variableDescent(Routes routes) {
    Routes result = routes.clone();
    for (int shake = 1; shake <= this.maxShakes; shake++) {
      routes = this.localSearchAlgorithm.search(routes, this.dataModel, this.maxCustomersByRoute);
      if (routes.getCostSearch() < result.getCostSearch()) {
        result = routes.clone();
        shake = 0;
      }
      if (shake + 1 != this.maxShakes) {
        routes = this.localSearchAlgorithm.randomSearch(
            result,
            this.dataModel,
            this.maxCustomersByRoute,
            shake == 0 ? 2 : shake + 1
        );
      }
    }
    return result;
  }
}
