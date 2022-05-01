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
import vrp.algorithm.constructsearch.localsearch.base.LocalSearchType;
import vrp.solution.Routes;

public class Gvns extends ConstructSearch {
  private int maxShakes = 4;

  public void setMaxShakes(int maxShakes) {
    this.maxShakes = maxShakes;
  }

  protected Routes construction(Routes routes) {
    for (int shake = 1; shake <= this.maxShakes; shake++) {
      Routes currentSolution = this.localSearchAlgorithm.randomSearch(
          routes,
          this.dataModel,
          this.maxCustomersByRoute,
          shake
      );
      currentSolution = this.variableDescent(currentSolution);
      if (currentSolution.getCostSearch() < routes.getCostSearch()) {
        routes = currentSolution;
        shake = 0;
      }
    }
    return routes;
  }

  private Routes variableDescent(Routes routes) {
    Routes result = routes.clone();
    LocalSearchType[] localSearchs = LocalSearchType.values();
    LocalSearchType previous = null;
    for (int i = 0; i < localSearchs.length; i++) {
      if (previous != null && previous.equals(localSearchs[i])) continue;
      this.setLocalSearchType(localSearchs[i]);
      routes = this.localSearchAlgorithm.search(result, this.dataModel, this.maxCustomersByRoute);
      if (routes.getCostSearch() < result.getCostSearch()) {
        previous = localSearchs[i];
        result = routes.clone();
        i = -1;
      }
    }
    return result;
  }
}
