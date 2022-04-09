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

import vrp.VehicleRouting;
import vrp.graph.Graph;

public class GraspVRP extends VehicleRouting {
  /**
   * Constructor of the class.
   * @param graph The graph of the problem.
   */
  public GraspVRP(Graph graph) {
    super(graph);
  }

  /**
   * Solve the problem using the Grasp algorithm.
   */
  public void solve() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  /**
   * Calculates the heuristic of the algorithm.
   */
  public void heuristic() {}
}
