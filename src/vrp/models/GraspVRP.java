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
  private int auxiliarCost;
  private int maxIterations;

  /**
   * Constructor of the class.
   * @param model The model of the problem.
   */
  public GraspVRP(DataModel model) {
    super(model);
    this.maxCandidates = 2;
    this.auxiliarCost = 0;
    this.maxIterations = 100;
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
    for (int i = 0; i < this.maxIterations; i++) {
      int[][] currentSolution = this.constructSolution();
      currentSolution = this.localSearch(currentSolution);
      this.updateSolution(currentSolution);
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

    while (!this.allVisited()) {
      int[][] customersPerVehicle = this.candidateList(solution);
      int[] customerPerVehicle = this.randomElement(customersPerVehicle);
      for (int i = 0; i < numberOfVehicles; i++) {
        int currentCustomer = customerPerVehicle[i];
        int lastFromVehicle = solution[i][solution[i].length - 1];
        this.auxiliarCost += this.model.distance(lastFromVehicle, currentCustomer);
        solution[i] = this.addCustomer(solution[i], currentCustomer);
        this.model.setCustomer(currentCustomer);
      }
    }
    
    this.model.resetCustomers();
    return solution;
  }

  /**
   * Create the candidate list.
   * 
   * @param currentSolution The current solution.
   * @return The candidate list.
   */
  private int[][] candidateList(int[][] currentSolution) {
    int numberOfVehicles = this.model.getNumberOfVehicles();
    int[][] elements = new int[numberOfVehicles][this.maxCandidates];
    
    for (int i = 0; i < numberOfVehicles; i++) {
      elements[i] = this.candidates(i, currentSolution[i]);
    }

    return elements;
  }

  /**
   * Create the candidate list for a vehicle.
   * 
   * @param vehicle The vehicle.
   * @param solution The solution.
   * @return The candidate list.
   */
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

  /**
   * Get a random element from the candidate list.
   * 
   * @param elements The candidate list.
   * @return The random elements.
   */
  private int[] randomElement(int[][] elements) {
    int[] result = new int[elements.length];
    for (int i = 0; i < elements.length; i++) {
      result[i] = elements[i][(int) (Math.random() * elements[i].length)];
    }
    return result;
  }

  /**
   * Search locally solutions.
   * 
   * @param solution The solution.
   * @return The local solution.
   */
  private int[][] localSearch(int[][] solution) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  /**
   * Update the solution.
   * 
   * @param currentSolution The current solution.
   */
  private void updateSolution(int[][] currentSolution) {
    if (this.auxiliarCost < this.cost) {
      this.cost = this.auxiliarCost;
      this.routes = currentSolution;
    }
    this.auxiliarCost = 0;
  }
}
