/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch.construction;

import java.util.*;
import java.util.stream.IntStream;

import vrp.algorithm.util.Functions;
import vrp.data.DataModel;
import vrp.solution.Routes;

/**
 * This class represents a Random greedy construction.
 */
public class GreedyRandom {
  /**
   * Construct a solution using the greedy random construction.
   * @param dataModel The data model.
   * @param numberOfCandidates The number of candidates.
   * @param maxCustomersByRoute The maximum number of customers by route.
   * @return The solution.
   */
  public static Routes constructSolution(DataModel dataModel, int numberOfCandidates, int maxCustomersByRoute) {
    int numberOfVehicles = dataModel.getNumberOfVehicles();
    Routes solution = new Routes(numberOfVehicles);
    Functions.addDepot(solution, dataModel);

    while (!dataModel.allVisited()) {
      int minimumDistance = Integer.MAX_VALUE;
      int minimumCustomer = -1;
      int vehicle = -1;
      for (int i = 0; i < numberOfVehicles; i++) {
        if (Functions.full(solution, i, maxCustomersByRoute)) continue;
        int lastFromVehicle = solution.lastCustomerFromRoute(i);
        int[] candidateList = candidateList(dataModel, lastFromVehicle, numberOfCandidates);
        try {
          int currentCustomer = randomElement(candidateList);
          int distance = dataModel.distance(lastFromVehicle, currentCustomer);
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
      dataModel.setCustomer(minimumCustomer);
    }

    Functions.addDepot(solution, dataModel);
    dataModel.resetCustomers();
    return solution;
  }
  
  /**
   * Create the candidate list.
   * 
   * @param lastFromVehicle The last customer from the vehicle.
   * @return The candidate list.
   */
  private static int[] candidateList(DataModel dataModel, int lastFromVehicle, int numberOfCandidates) {
    int[] candidates = new int[numberOfCandidates];
    
    for (int i = 0; i < numberOfCandidates; i++) {
      int closest = Integer.MAX_VALUE;
      for (int notVisited : dataModel.getNotVisitedCustomers()) {
        if (IntStream.of(candidates).anyMatch(c -> c == notVisited)) continue;
        int distance = dataModel.distance(lastFromVehicle, notVisited);
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
  private static int randomElement(int[] candidates) {
    int maxLength = 0;
    for (int i = 0; i < candidates.length; i++) {
      if (candidates[i] != 0) maxLength++;
    }
    return candidates[(int) (Math.random() * maxLength)];
  }
}
