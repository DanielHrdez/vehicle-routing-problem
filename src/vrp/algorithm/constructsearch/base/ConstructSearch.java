/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch.base;

import vrp.algorithm.base.Algorithm;
import vrp.algorithm.constructsearch.localsearch.base.*;
import vrp.algorithm.constructsearch.localsearch.reinsertion.*;
import vrp.algorithm.util.Functions;
import vrp.solution.Routes;

public abstract class ConstructSearch extends Algorithm {
  protected int candidates = 2;
  protected int maxIterations = 1000;
  protected int maxIterationsWithoutImprovement = 100;
  protected int iterationsWithoutImprovement = 0;
  protected LocalSearch localSearchAlgorithm = new ReinsertionInterRoute();

  /**
   * Setter of the local search algorithm.
   * @param localSearchType The local search algorithm.
   */
  public void setLocalSearchType(LocalSearchType localSearchType) {
    this.localSearchAlgorithm = Functions.setLocalSearchType(localSearchType);
  }

  /**
   * Update the solution.
   * 
   * @param currentSolution The current solution.
   * @return True if the solution has been updated.
   */
  protected boolean updateSolution(Routes currentSolution) {
    this.iterationsWithoutImprovement++;
    if (currentSolution.getCostSearch() < this.routes.getCostSearch()) {
      this.routes = currentSolution;
      this.iterationsWithoutImprovement = 0;
      return true;
    }
    return false;
  }

  /**
   * Setter of the max candidates.
   * 
   * @param candidates The max candidates.
   */
  public void setCandidates(int candidates) {
    this.candidates = candidates;
  }

  /**
   * Setter of the max iterations.
   * 
   * @param maxIterations The max iterations.
   */
  public void setMaxIterations(int maxIterations) {
    this.maxIterations = maxIterations;
  }
}
