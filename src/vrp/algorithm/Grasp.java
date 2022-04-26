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

import vrp.algorithm.base.*;
import vrp.algorithm.construction.GreedyRandom;
import vrp.solution.Routes;
import vrp.algorithm.localsearch.base.*;
import vrp.algorithm.localsearch.reinsertion.*;
import vrp.algorithm.util.Functions;

/**
 * This class represents a model.
 */
public class Grasp extends Algorithm {
  private int candidates = 2;
  private int maxIterations = 1000;
  private int maxIterationsWithoutImprovement = 500;
  private int iterationsWithoutImprovement = 0;
  private LocalSearch localSearchAlgorithm = new ReinsertionInterRoute();

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
   */
  private void updateSolution(Routes currentSolution) {
    this.iterationsWithoutImprovement++;
    if (currentSolution.getCostSearch() < this.routes.getCostSearch()) {
      this.routes = currentSolution;
      this.iterationsWithoutImprovement = 0;
    }
  }
}
