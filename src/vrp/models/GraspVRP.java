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

import java.util.*;
import java.util.stream.IntStream;

public class GraspVRP extends VehicleRouting {
  private int maxCandidates = 2;
  private int auxiliarCost = 0;
  private int maxIterations = 100;

  public GraspVRP() {
    super();
  }

  /**
   * Constructor of the class.
   * @param model The model of the problem.
   */
  public GraspVRP(DataModel model) {
    super(model);
  }

  /**
   * Setter of the max candidates.
   * 
   * @param maxCandidates The max candidates.
   */
  public void setMaxCandidates(int maxCandidates) {
    this.maxCandidates = maxCandidates;
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
  public void solve() {
    this.cost = Integer.MAX_VALUE;
    for (int i = 0; i < this.maxIterations; i++) {
      int[][] currentSolution = this.constructSolution();
      currentSolution = this.localSearch(currentSolution);
      if (this.updateSolution(currentSolution)) i = 0;
    }
  }

  /**
   * Construct a solution.
   * 
   * @return The solution.
   */
  private int[][] constructSolution() {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int[][] solution = new int[numberOfVehicles][1];
    this.model.setCustomer(this.model.getDepot());

    while (!this.allVisited()) {
      for (int i = 0; i < numberOfVehicles; i++) {
        int[] candidateList = this.candidateList(i, solution[i]);
        try {
          int currentCustomer = this.randomElement(candidateList);
          int lastFromVehicle = solution[i][solution[i].length - 1];
          this.auxiliarCost += this.model.distance(lastFromVehicle, currentCustomer);
          solution[i] = this.addCustomer(solution[i], currentCustomer);
          this.model.setCustomer(currentCustomer);
        } catch (Exception e) {
          break;
        }
      }
    }
    
    this.model.resetCustomers();
    return solution;
  }

  /**
   * Create the candidate list.
   * 
   * @param solution The current solution.
   * @return The candidate list.
   */
  private int[] candidateList(int vehicle, int[] solution) {
    int[] candidates = new int[this.maxCandidates];
    int lastFromVehicle = solution[solution.length - 1];
    
    for (int i = 0; i < this.maxCandidates; i++) {
      int closest = Integer.MAX_VALUE;
      for (int notVisited : this.model.getNotVisitedCustomers()) {
        if (IntStream.of(candidates).anyMatch(c -> c == notVisited)) continue;
        int distance = this.model.distance(lastFromVehicle, notVisited);
        if (distance < closest) {
          closest = distance;
          candidates[i] = notVisited;
        }
      }
    }

    if (candidates[candidates.length - 1] != 0) return candidates;
    return Arrays.stream(candidates).filter(c -> c != 0).toArray();
  }

  /**
   * Get a random element from the candidate list.
   * 
   * @param candidates The candidate list.
   * @return The random elements.
   */
  private int randomElement(int[] candidates) {
    int maxLength = 0;
    for (int i = 0; i < candidates.length; i++) {
      if (candidates[i] != 0) maxLength++;
    }
    return candidates[(int) (Math.random() * maxLength)];
  }

  /**
   * Search locally solutions.
   * 
   * @param solution The solution.
   * @return The local solution.
   */
  private int[][] localSearch(int[][] solution) {
    return solution;
  }

  /**
   * Update the solution.
   * 
   * @param currentSolution The current solution.
   */
  private boolean updateSolution(int[][] currentSolution) {
    int previousCost = this.auxiliarCost;
    this.auxiliarCost = 0;
    if (previousCost < this.cost) {
      this.cost = previousCost;
      this.routes = currentSolution;
      return true;
    }
    return false;
  }
}
