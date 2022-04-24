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

import java.util.*;
import java.util.stream.IntStream;

/**
 * This class represents a model.
 */
public class Grasp extends Algorithm {
  private int maxCandidates = 2;
  private int maxIterations = 1000;
  private int maxIterationsWithoutImprovement = 100;
  private int iterationsWithoutImprovement = 0;

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
  private Routes localSearch(Routes solution) {
    int improved = 2;
    int previousCost = solution.getCost();
    while (improved >= 2) {
      improved = 0;
      solution = this.reinsertionInter(solution);
      if (solution.getCost() < previousCost) {
        improved++;
        previousCost = solution.getCost();
      }
      solution = this.reinsertionIntra(solution);
      if (solution.getCost() < previousCost) {
        improved++;
        previousCost = solution.getCost();
      }
      solution = this.swapIntra(solution);
      if (solution.getCost() < previousCost) {
        improved++;
        previousCost = solution.getCost();
      }
      solution = this.swapInter(solution);
      if (solution.getCost() < previousCost) {
        improved++;
        previousCost = solution.getCost();
      }
      solution = this.twoOpt(solution);
      if (solution.getCost() < previousCost) {
        improved++;
        previousCost = solution.getCost();
      }
    }
    return solution;
  }

  private Routes reinsertionInter(Routes solution) {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    Routes bestSolution = solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int rute1 = 0; rute1 < numberOfVehicles; rute1++) {
        if (solution.getRouteSize(rute1) == 2) continue;
        for (int customer1 = 1; customer1 < solution.getRouteSize(rute1) - 1; customer1++) {
          for (int rute2 = 0; rute2 < numberOfVehicles; rute2++) {
            if (rute1 == rute2) continue;
            if (solution.getRouteSize(rute2) == 2) continue;
            for (int customer2 = 1; customer2 < solution.getRouteSize(rute2) - 1; customer2++) {
              Routes newSolution = this.insertInter(solution, rute1, customer1, rute2, customer2);
              if (newSolution.getCost() < bestSolution.getCost()) {
                bestSolution = newSolution;
                improved = true;
              }
            }
          }
        }
      }
      solution = bestSolution;
    }
    return bestSolution;
  }

  private Routes reinsertionIntra(Routes solution) {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    Routes bestSolution = solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int rute = 0; rute < numberOfVehicles; rute++) {
        if (solution.getRouteSize(rute) == 2) continue;
        for (int customer1 = 1; customer1 < solution.getRouteSize(rute) - 2; customer1++) {
          for (int customer2 = customer1 + 1; customer2 < solution.getRouteSize(rute) - 1; customer2++) {
            Routes newSolution = this.insertIntra(solution, rute, customer1, customer2);
            if (newSolution.getCost() < bestSolution.getCost()) {
              bestSolution = newSolution;
              improved = true;
            }
          }
        }
      }
      solution = bestSolution;
    }
    return bestSolution;
  }

  private Routes insertIntra(Routes routes, int rute, int targetPosition, int position) {
    Routes newRoutes = routes.clone();
    Integer previousTarget = newRoutes.getCustomer(rute, targetPosition - 1);
    Integer targetCustomer = newRoutes.getCustomer(rute, targetPosition);
    Integer nextTarget = routes.getCustomer(rute, targetPosition + 1);
    Integer destinyCustomer = routes.getCustomer(rute, position);
    Integer destinyNext = routes.getCustomer(rute, position + 1);
    newRoutes.sumCost(-this.dataModel.distance(previousTarget, targetCustomer));
    newRoutes.sumCost(-this.dataModel.distance(targetCustomer, nextTarget));
    newRoutes.sumCost(-this.dataModel.distance(destinyCustomer, destinyNext));
    newRoutes.removeCustomer(rute, targetPosition);
    newRoutes.addCustomer(rute, targetCustomer, position);
    newRoutes.sumCost(this.dataModel.distance(previousTarget, nextTarget));
    newRoutes.sumCost(this.dataModel.distance(destinyCustomer, targetCustomer));
    newRoutes.sumCost(this.dataModel.distance(targetCustomer, destinyNext));
    return newRoutes;
  }

  private Routes insertInter(Routes routes, int from, int targetPosition, int to, int position) {
    Routes newRoutes = routes.clone();
    Integer previousTarget = newRoutes.getCustomer(from, targetPosition - 1);
    Integer targetCustomer = newRoutes.getCustomer(from, targetPosition);
    Integer nextTarget = routes.getCustomer(from, targetPosition + 1);
    Integer destinyPrevious = routes.getCustomer(to, position - 1);
    Integer destinyCustomer = routes.getCustomer(to, position);
    newRoutes.sumCost(-this.dataModel.distance(previousTarget, targetCustomer));
    newRoutes.sumCost(-this.dataModel.distance(targetCustomer, nextTarget));
    newRoutes.sumCost(-this.dataModel.distance(destinyPrevious, destinyCustomer));
    newRoutes.removeCustomer(from, targetPosition);
    newRoutes.addCustomer(to, targetCustomer, position);
    newRoutes.sumCost(this.dataModel.distance(previousTarget, nextTarget));
    newRoutes.sumCost(this.dataModel.distance(destinyPrevious, targetCustomer));
    newRoutes.sumCost(this.dataModel.distance(targetCustomer, destinyCustomer));
    return newRoutes;
  }

  private Routes swapIntra(Routes solution) {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    Routes bestSolution = solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int rute = 0; rute < numberOfVehicles; rute++) {
        if (solution.getRouteSize(rute) == 2) continue;
        for (int customer1 = 1; customer1 < solution.getRouteSize(rute) - 2; customer1++) {
          for (int customer2 = customer1 + 1; customer2 < solution.getRouteSize(rute) - 1; customer2++) {
            if (customer1 == customer2) continue;
            Routes newSolution = this.swap(solution, rute, customer1, rute, customer2);
            if (newSolution.getCost() < bestSolution.getCost()) {
              bestSolution = newSolution;
              improved = true;
            }
          }
        }
      }
      solution = bestSolution;
    }
    return bestSolution;
  }

  private Routes swapInter(Routes solution) {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    Routes bestSolution = solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int rute1 = 0; rute1 < numberOfVehicles; rute1++) {
        if (solution.getRouteSize(rute1) == 2) continue;
        for (int customer1 = 1; customer1 < solution.getRouteSize(rute1) - 1; customer1++) {
          for (int rute2 = 0; rute2 < numberOfVehicles; rute2++) {
            if (rute1 == rute2) continue;
            if (solution.getRouteSize(rute2) == 2) continue;
            for (int customer2 = 1; customer2 < solution.getRouteSize(rute2) - 1; customer2++) {
              Routes newSolution = this.swap(solution, rute1, customer1, rute2, customer2);
              if (newSolution.getCost() < bestSolution.getCost()) {
                bestSolution = newSolution;
                improved = true;
              }
            }
          }
        }
      }
      solution = bestSolution;
    }
    return bestSolution;
  }

  private Routes swap(Routes routes, int from, int customer1Position, int to, int customer2Position) {
    Routes newRoutes = routes.clone();
    Integer previousCustomer1 = newRoutes.getCustomer(from, customer1Position - 1);
    Integer customer1 = routes.getCustomer(from, customer1Position);
    Integer nextCustomer1 = routes.getCustomer(from, customer1Position + 1);
    Integer previousCustomer2 = routes.getCustomer(to, customer2Position - 1);
    Integer customer2 = routes.getCustomer(to, customer2Position);
    Integer nextCustomer2 = routes.getCustomer(to, customer2Position + 1);
    newRoutes.sumCost(-this.dataModel.distance(previousCustomer1, customer1));
    newRoutes.sumCost(-this.dataModel.distance(customer1, nextCustomer1));
    if (previousCustomer2 != customer1) {
      newRoutes.sumCost(-this.dataModel.distance(previousCustomer2, customer2));
    }
    newRoutes.sumCost(-this.dataModel.distance(customer2, nextCustomer2));
    newRoutes.setCustomer(from, customer1Position, customer2);
    newRoutes.setCustomer(to, customer2Position, customer1);
    newRoutes.sumCost(this.dataModel.distance(previousCustomer1, customer2));
    if (previousCustomer2 != customer1) {
      newRoutes.sumCost(this.dataModel.distance(customer2, nextCustomer1));
      newRoutes.sumCost(this.dataModel.distance(previousCustomer2, customer1));
    } else {
      newRoutes.sumCost(this.dataModel.distance(customer2, customer1));
    }
    newRoutes.sumCost(this.dataModel.distance(customer1, nextCustomer2));
    return newRoutes;
  }
  
  private Routes twoOpt(Routes solution) {
    Routes bestSolution = solution.clone();
    boolean improved = true;
    while (improved) {
      improved = false;
      for (int i = 0; i < solution.getNumberOfRoutes(); i++) {
        int size = solution.getRouteSize(i) - 1;
        for (int j = 1; j < size - 1; j++) {
          for (int k = j + 1; k < size; k++) {
            Routes newSolution = this.twoOptSwap(solution, i, j, k);
            if (newSolution.getCost() < bestSolution.getCost()) {
              bestSolution = newSolution;
              improved = true;
            }
          }
        }
      };
      solution = bestSolution;
    }
    return bestSolution;
  }

  private Routes twoOptSwap(Routes previousRoutes, int indexRoute, int firstIndex, int secondIndex) {
    Routes newRoutes = previousRoutes.clone();
    Integer firstCustomer = previousRoutes.getCustomer(indexRoute, firstIndex);
    Integer previousFirstCustomer = previousRoutes.getCustomer(indexRoute, firstIndex - 1);
    newRoutes.sumCost(-this.dataModel.distance(previousFirstCustomer, firstCustomer));
    for (int i = secondIndex; i >= firstIndex; i--) {
      int position = secondIndex - i + firstIndex;
      Integer customer = previousRoutes.getCustomer(indexRoute, position);
      Integer previousCustomer = newRoutes.getCustomer(indexRoute, position - 1);
      Integer secondCustomer = previousRoutes.getCustomer(indexRoute, i);
      Integer nextCustomer = previousRoutes.getCustomer(indexRoute, position + 1);
      newRoutes.sumCost(-this.dataModel.distance(customer, nextCustomer));
      newRoutes.setCustomer(indexRoute, position, secondCustomer);
      newRoutes.sumCost(this.dataModel.distance(previousCustomer, secondCustomer));
    }
    Integer lastCustomer = previousRoutes.getCustomer(indexRoute, secondIndex + 1);
    newRoutes.sumCost(this.dataModel.distance(firstCustomer, lastCustomer));
    return newRoutes;
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
