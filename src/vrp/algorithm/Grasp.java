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
import vrp.solution.Routes;
import vrp.algorithm.localsearch.reinsertion.*;
import vrp.algorithm.localsearch.swap.*;
import vrp.algorithm.localsearch.opt.TwoOpt;

import java.util.*;
import java.util.stream.IntStream;

enum LocalSearchType {
  SWAP_INTER_ROUTE,
  SWAP_INTRA_ROUTE,
  REINSERTION_INTER_ROUTE,
  REINSERTION_INTRA_ROUTE,
  TWO_OPT
}

/**
 * This class represents a model.
 */
public class Grasp extends Algorithm {
  private int maxCandidates = 2;
  private int maxIterations = 1000;
  private int maxIterationsWithoutImprovement = 100;
  private int iterationsWithoutImprovement = 0;
  private LocalSearchType localSearchType = LocalSearchType.REINSERTION_INTER_ROUTE;

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
  public void implementation() {
    this.routes.sumCost(Integer.MAX_VALUE);
    this.iterationsWithoutImprovement = 0;

    for (int i = 0; i < this.maxIterations; i++) {
      Routes currentSolution = this.constructSolution();
      currentSolution = this.localSearch(currentSolution, this.localSearchType);
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
  private Routes constructSolution() {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    Routes solution = new Routes(numberOfVehicles);
    this.addDepot(solution);

    while (!this.dataModel.allVisited()) {
      int minimumDistance = Integer.MAX_VALUE;
      int minimumCustomer = -1;
      int vehicle = -1;
      for (int i = 0; i < numberOfVehicles; i++) {
        if (this.full(solution, i)) continue;
        int lastFromVehicle = solution.lastCustomerFromRoute(i);
        int[] candidateList = this.candidateList(lastFromVehicle);
        try {
          int currentCustomer = this.randomElement(candidateList);
          int distance = this.dataModel.distance(lastFromVehicle, currentCustomer);
          if (distance < minimumDistance) {
            minimumDistance = distance;
            minimumCustomer = currentCustomer;
            vehicle = i;
          }
        } catch (Exception e) {
          break;
        }
      }
      solution.sumCost(minimumDistance);
      solution.addCustomer(vehicle, minimumCustomer);
      this.dataModel.setCustomer(minimumCustomer);
    }

    this.addDepot(solution);
    this.dataModel.resetCustomers();
    return solution;
  }

  /**
   * Create the candidate list.
   * 
   * @param lastFromVehicle The last customer from the vehicle.
   * @return The candidate list.
   */
  private int[] candidateList(int lastFromVehicle) {
    int[] candidates = new int[this.maxCandidates];
    
    for (int i = 0; i < this.maxCandidates; i++) {
      int closest = Integer.MAX_VALUE;
      for (int notVisited : this.dataModel.getNotVisitedCustomers()) {
        if (IntStream.of(candidates).anyMatch(c -> c == notVisited)) continue;
        int distance = this.dataModel.distance(lastFromVehicle, notVisited);
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
  private Routes localSearch(Routes solution, LocalSearchType type) {
    switch (type) {
      case REINSERTION_INTER_ROUTE: return new ReinsertionInterRoute().search(solution, this.dataModel);
      case REINSERTION_INTRA_ROUTE: return new ReinsertionIntraRoute().search(solution, this.dataModel);
      case SWAP_INTER_ROUTE: return new SwapInterRoute().search(solution, this.dataModel);
      case SWAP_INTRA_ROUTE: return new SwapIntraRoute().search(solution, this.dataModel);
      case TWO_OPT: return new TwoOpt().search(solution, this.dataModel);
    }
    return solution;
  }

  public void setLocalSearchType(LocalSearchType localSearchType) {
    this.localSearchType = localSearchType;
  }

  /**
   * Update the solution.
   * 
   * @param currentSolution The current solution.
   */
  private void updateSolution(Routes currentSolution) {
    this.iterationsWithoutImprovement++;
    if (currentSolution.getCost() < this.routes.getCost()) {
      this.routes = currentSolution;
      this.iterationsWithoutImprovement = 0;
    }
  }
}
