/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp;

import vrp.graph.Graph;

/**
 * This abstract class represents a vehicle routing problem.
 */
public abstract class VehicleRouting {
  private Graph graph;
  private int[][] solution;
  private int[] costs;

  /**
   * Constructor of the class.
   *
   * @param graph The graph of the problem.
   * It must be a graph with a vertex for each customer.
   */
  public VehicleRouting(Graph graph) {
    this.graph = graph;
  }

  /**
   * Returns the solution of the problem.
   * 
   * @return The solution of the problem.
   */
  public int[][] getSolution() {
    return solution;
  }

  /**
   * Returns the costs of the solution.
   * 
   * @return The costs of the solution.
   */
  public int[] getCosts() {
    return costs;
  }

  /**
   * Solves the vehicle routing problem for the given graph.
   */
  public abstract void solve();

  /**
   * Decide which node to visit next on each vehicle.
   */
  public abstract void heuristic();
}
