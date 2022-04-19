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

import vrp.VehicleRouting;
import vrp.data.DataModel;

import java.util.*;
import java.util.stream.IntStream;

/**
 * This class represents a model.
 */
public class GraspVRP extends VehicleRouting {
  private int maxCandidates = 2;
  private int auxiliarCost = 0;
  private int maxIterations = 3500;
  private int maxIterationsWithoutImprovement = 500;
  private int iterationsWithoutImprovement = 0;

  /**
   * Constructor of the class.
   */
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
    this.iterationsWithoutImprovement = 0;
    for (int i = 0; i < this.maxIterations; i++) {
      int[][] currentSolution = this.constructSolution();
      currentSolution = this.localSearch(currentSolution);
      this.updateSolution(currentSolution);
      if (this.iterationsWithoutImprovement > this.maxIterationsWithoutImprovement) {
        break;
      }
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
    int depot = this.model.getDepot();
    this.model.setCustomer(depot);

    while (!this.allVisited()) {
      int minimumDistance = Integer.MAX_VALUE;
      int minimumCustomer = -1;
      int vehicle = -1;
      for (int i = 0; i < numberOfVehicles; i++) {
        int[] candidateList = this.candidateList(i, solution[i]);
        try {
          int currentCustomer = this.randomElement(candidateList);
          int lastFromVehicle = solution[i][solution[i].length - 1];
          int distance = this.model.distance(lastFromVehicle, currentCustomer);
          if (distance < minimumDistance) {
            minimumDistance = distance;
            minimumCustomer = currentCustomer;
            vehicle = i;
          }
        } catch (Exception e) {
          break;
        }
      }
      this.auxiliarCost += minimumDistance;
      solution[vehicle] = this.addCustomer(solution[vehicle], minimumCustomer);
      this.model.setCustomer(minimumCustomer);
    }

    for (int i = 0; i < numberOfVehicles; i++) {
      solution[i] = this.addCustomer(solution[i], depot);
      this.auxiliarCost += this.model.distance(solution[i][solution[i].length - 1], depot);
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
  private void updateSolution(int[][] currentSolution) {
    this.iterationsWithoutImprovement++;
    if (this.auxiliarCost < this.cost) {
      this.cost = this.auxiliarCost;
      this.routes = currentSolution;
      this.iterationsWithoutImprovement = 0;
    }
    this.auxiliarCost = 0;
  }
}
