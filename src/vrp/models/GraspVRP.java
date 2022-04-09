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
  /**
   * Constructor of the class.
   * @param model The model of the problem.
   */
  public GraspVRP(DataModel model) {
    super(model);
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
  protected void heuristic() {}
}
