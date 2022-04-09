/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 *
 * Class description:

 */

package vrp.models;

import vrp.VehicleRouting;
import vrp.graph.Graph;

/**
 * GreedyVRP is a class that implements
 * the Greedy algorithm for the Vehicle Routing Problem.
 */
public class GreedyVRP extends VehicleRouting {
  /**
   * Constructor of the class.
   * @param graph The graph of the problem.
   */
  public GreedyVRP(Graph graph) {
    super(graph);
  }

  /**
   * Solve the problem using the Greedy algorithm.
   */
  public void solve() {
    // n is the number of vehicles
    // get the n largest nodes from depot
    // get the closest nodes from previous
    // calc centroids
    // get closest nodes from centroids
    // repeat until all nodes are visited
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  /**
   * Calculates the heuristic of the algorithm.
   */
  public void heuristic() {}
}
