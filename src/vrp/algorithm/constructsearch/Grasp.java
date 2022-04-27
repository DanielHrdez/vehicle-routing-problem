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

/**
 * This class represents a model.
 */
public class Grasp extends ConstructSearch {
  /**
   * Solve the problem using the Grasp algorithm.
   */
  public void implementation() {
    this.routes.sumCost(Integer.MAX_VALUE);
    this.routes.sumCostSearch(Integer.MAX_VALUE);
    this.iterationsWithoutImprovement = 0;

    for (int i = 0; i < this.maxIterations; i++) {
      Routes currentSolution = GreedyRandom.constructSolution(this.dataModel, this.candidates, this.maxCustomersByRoute);
      currentSolution = this.localSearchAlgorithm.search(currentSolution, this.dataModel, this.maxCustomersByRoute);
      this.updateSolution(currentSolution);
      if (this.iterationsWithoutImprovement > this.maxIterationsWithoutImprovement) {
        break;
      }
    }
  }
}
