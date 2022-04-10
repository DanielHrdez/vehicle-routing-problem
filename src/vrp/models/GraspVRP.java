/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.models;

import vrp.data.DataModel;
import vrp.models.base.VehicleRouting;

public class GraspVRP extends VehicleRouting {
  private int maxCandidates;

  /**
   * Constructor of the class.
   * @param model The model of the problem.
   */
  public GraspVRP(DataModel model) {
    super(model);
    this.maxCandidates = 2;
  }

  /**
   * Constructor of the class.
   * @param model The model of the problem.
   * @param maxCandidates The maximum number of candidates to be generated.
   */
  public GraspVRP(DataModel model, int maxCandidates) {
    super(model);
    this.maxCandidates = maxCandidates;
  }

  /**
   * Solve the problem using the Grasp algorithm.
   */
  public void solve() {
    while (true) {
      int[][] currentSolution = this.constructSolution();
      currentSolution = this.localSearch(currentSolution);
      this.updateSolution(currentSolution);
    }
  }

  private int[][] constructSolution() {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int[][] solution = new int[numberOfVehicles][1];

    while (!this.allVisited()) {
      int[][] elements = this.candidateList(solution);
      int[] element = this.randomElement(elements);
      for (int i = 0; i < numberOfVehicles; i++) {
        this.add(solution[i], element[i]);
      }
      this.adaptGreedy(element);
    }
    
    this.model.resetCustomers();
    return solution;
  }

  private int[][] candidateList(int[][] currentSolution) {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int[][] elements = new int[numberOfVehicles][this.maxCandidates];
    
    for (int i = 0; i < numberOfVehicles; i++) {
      elements[i] = this.candidates(i, currentSolution[i]);
    }

    return elements;
  }

  private int[] candidates(int vehicle, int[] solution) {
    int[] candidates = new int[this.maxCandidates];
    int numberOfCustomers = this.model.getNumberOfCustomers();
    int lastFromVehicle = solution[solution.length - 1];
    
    for (int i = 0; i < this.maxCandidates; i++) {
      int closest = Integer.MAX_VALUE;
      for (int j = 0; j < numberOfCustomers; j++) {
        if (!this.model.getCustomer(j)) {
          int distance = this.model.distance(lastFromVehicle, j);
          if (distance < closest) {
            closest = distance;
            candidates[i] = j;
          }
        }
      }
      this.model.setCustomer(candidates[i]);
    }

    return candidates;
  }

  private int[] randomElement(int[][] elements) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  private void adaptGreedy(int[] element) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  private int[][] localSearch(int[][] currentSolution) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  private void updateSolution(int[][] currentSolution) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }
}
