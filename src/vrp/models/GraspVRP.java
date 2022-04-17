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

/**
 * This class represents a model.
 */
public class GraspVRP extends VehicleRouting {
  private int maxCandidates = 2;
  private int auxiliarCost = 0;
  private int maxIterations = 10;
  private int timesTwoOpt;

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
    this.timesTwoOpt = 0;
    this.cost = Integer.MAX_VALUE;
    for (int i = 0; i < this.maxIterations; i++) {
      int[][] currentSolution = this.constructSolution();
      currentSolution = this.localSearch(currentSolution);
      this.updateSolution(currentSolution);
    }
    System.out.println(this.timesTwoOpt);
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
    // for (int[] vehicle : solution) {
    //   System.out.print("[");
    //   for (int customer : vehicle) {
    //     System.out.print(customer + ", ");
    //   }
    //   System.out.println("\b\b]");
    // }
    // System.out.println("Cost: " + this.auxiliarCost);

    solution = this.twoOpt(solution);

    // localSolution = this.threeOpt(solution);
    
    // for (int[] vehicle : solution) {
    //   System.out.print("[");
    //   for (int customer : vehicle) {
    //     System.out.print(customer + ", ");
    //   }
    //   System.out.println("\b\b]");
    // }
    // System.out.println("Cost: " + this.auxiliarCost);
    // System.exit(0);
    return solution;
  }

  private int[][] twoOpt(int[][] solution) {
    int depot = this.model.getDepot();
    boolean improved = true;

    while (improved) {
      improved = false;
      int index = 0;
      for (int[] vehicle : solution) {
        for (int i = 0; i < vehicle.length - 1; i++) {
          if (vehicle[i] == depot) continue;
          for (int j = i + 1; j < vehicle.length; j++) {
            if (vehicle[j] == depot) continue;
            int[][] twoOpt = solution.clone();
            twoOpt[index] = this.twoOptSwap(vehicle, i, j);
            int twoOptCost = this.calculateCost(twoOpt);
            if (twoOptCost < this.auxiliarCost) {
              this.timesTwoOpt++;
              this.auxiliarCost = twoOptCost;
              solution = twoOpt;
              improved = true;
              break;
            }
          }
          if (improved) break;
        }
        if (improved) break;
        index++;
      }
    }

    return solution;
  }

  private int[] twoOptSwap(int[] array, int firstIndex, int secondIndex) {
    int[] newArray = new int[array.length];
    for (int i = 0; i < firstIndex; i++) newArray[i] = array[i];
    for (int i = firstIndex; i <= secondIndex; i++) newArray[i] = array[secondIndex - (i - firstIndex)];
    for (int i = secondIndex + 1; i < array.length; i++) newArray[i] = array[i];
    return newArray;
  }

  private int[][] threeOpt(int[][] solution) {
    for (int[] tour : solution) {
      while (true) {
        int delta = 0;
        for (int[] nodes : this.allSegments(tour.length - 1)) {
          int[][] reversed = this.reverseSegment(tour, nodes);
          tour = reversed[0];
          delta += reversed[1][0];
        }
        if (delta >= 0) break;
      }
    }
    return solution;
  }

  private int[][] allSegments(int length) {
    int depot = this.model.getDepot();
    List<int[]> segments = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      if (i == depot) continue;
      for (int j = i + 2; j < length; j++) {
        if (j == depot) continue;
        for (int k = j + 2; k < length + 1; k++) {
          if (k == depot) continue;
          segments.add(new int[] { i, j, k });
        }
      }
    }
    return segments.toArray(new int[segments.size()][]);
  }

  private int[][] reverseSegment(int[] tour, int[] nodes) {
    int first = nodes[0];
    int second = nodes[1];
    int third = nodes[2];
    int a = tour[first - 1];
    int b = tour[first];
    int c = tour[second - 1];
    int d = tour[second];
    int e = tour[third - 1];
    int f = tour[third % tour.length];
    int distance0 = this.model.distance(a, b) + this.model.distance(c, d) + this.model.distance(e, f);
    int distance1 = this.model.distance(a, c) + this.model.distance(b, d) + this.model.distance(e, f);
    int distance2 = this.model.distance(a, b) + this.model.distance(c, e) + this.model.distance(d, f);
    int distance3 = this.model.distance(a, d) + this.model.distance(e, b) + this.model.distance(c, f);
    int distance4 = this.model.distance(f, b) + this.model.distance(c, d) + this.model.distance(e, a);
    
    int[] newTour = new int[tour.length];
    if (distance0 > distance1) {
      for (int i = 0; i < first; i++) newTour[i] = tour[i];
      for (int i = first; i < second; i++) newTour[i] = tour[second - (i - first + 1)];
      for (int i = second; i < tour.length; i++) newTour[i] = tour[i];
      return new int[][] {newTour, new int[] {-distance0 + distance1}};
    } else if (distance0 > distance2) {
      for (int i = 0; i < second; i++) newTour[i] = tour[i];
      for (int i = second; i < third; i++) newTour[i] = tour[third - (i - second + 1)];
      for (int i = third; i < tour.length; i++) newTour[i] = tour[i];
      return new int[][] {newTour, new int[] {-distance0 + distance2}};
    } else if (distance0 > distance4) {
      for (int i = 0; i < first; i++) newTour[i] = tour[i];
      for (int i = first; i < third; i++) newTour[i] = tour[third - (i - first + 1)];
      for (int i = third; i < tour.length; i++) newTour[i] = tour[i];
      return new int[][] {newTour, new int[] {-distance0 + distance4}};
    } else if (distance0 > distance3) {
      for (int i = 0; i < first; i++) newTour[i] = tour[i];
      int[] temporal = new int[third - first];
      for (int i = second; i < third; i++) temporal[i - second] = tour[i];
      for (int i = first; i < second; i++) temporal[i - first + third - second] = tour[i];
      for (int i = first; i < third; i++) newTour[i] = temporal[i - first];
      for (int i = third; i < tour.length; i++) newTour[i] = tour[i];
      return new int[][] {newTour, new int[] {-distance0 + distance3}};
    }
    return new int[][] {tour, new int[] {0}};
  }

  private int calculateCost(int[][] solution) {
    int cost = 0;
    for (int[] vehicle : solution) {
      for (int i = 0; i < vehicle.length - 1; i++) {
        cost += this.model.distance(vehicle[i], vehicle[i + 1]);
      }
    }
    return cost;
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
