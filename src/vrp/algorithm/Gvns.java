/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm;

import vrp.algorithm.base.Algorithm;
import vrp.algorithm.construction.GreedyRandom;
import vrp.algorithm.localsearch.base.LocalSearch;
import vrp.solution.Routes;

public class Gvns extends Algorithm {
  private int candidates = 2;
  private int maxShakes = 4;
  private LocalSearch localSearchAlgorithm;

  public setLocalSearchAlgorithm(LocalSearch localSearchAlgorithm) {
    this.localSearchAlgorithm = localSearchAlgorithm;
  }

  public void setMaxShakes(int maxShakes) {
    this.maxShakes = maxShakes;
  }

  public void implementation() {
    Routes solution = GreedyRandom.constructSolution(this.dataModel, this.candidates);
    for (int i = 1; i < this.maxShakes; i++) {
      Routes currentSolution = this.RandomShake(solution, i);
      currentSolution = this.localSearchAlgorithm.search(currentSolution, this.dataModel);
    }
  }
}
