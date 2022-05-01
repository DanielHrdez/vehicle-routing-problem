/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.constructsearch;

import vrp.algorithm.constructsearch.base.ConstructSearch;
import vrp.solution.Routes;

/**
 * This class represents a model.
 */
public class Grasp extends ConstructSearch {
  /**
   * Solve the problem using the Grasp algorithm.
   * @param routes The routes
   * @return The local search
   */
  protected Routes construction(Routes routes) {
    return this.localSearchAlgorithm.search(routes, this.dataModel, this.maxCustomersByRoute);
  }
}
