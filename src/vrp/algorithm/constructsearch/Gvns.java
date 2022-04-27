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
    this.routes.setCostSearch(Integer.MAX_VALUE);
    
    for (int i = 0; i < this.maxIterations; i++) {
      for (int shake = 1; shake < this.maxShakes; shake++) {
        Routes currentSolution = this.RandomShake(this.routes, shake);
        currentSolution = this.variableDescent(currentSolution);
        if (this.updateSolution(currentSolution)) shake = 0;
      }
    }
  }

  private Routes RandomShake(Routes routes, int shake) {
    Routes result = routes.clone();
    for (int i = 0; i < shake; i++) {
      result = this.localSearchAlgorithm.search(result, this.dataModel, this.maxCustomersByRoute, true);
    }
    return result;
  }

  private Routes variableDescent(Routes routes) {
    Routes result = routes.clone();
    for (int shake = 1; shake < this.maxShakes; shake++) {
      result = this.localSearchAlgorithm.search(routes.clone(), this.dataModel, this.maxCustomersByRoute, false);
      if (result.getCostSearch() < routes.getCostSearch()) {
        routes = result.clone();
        shake = 0;
      }
    }
    return routes;
  }
}
