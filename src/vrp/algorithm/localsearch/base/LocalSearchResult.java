/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.localsearch.base;

import vrp.solution.Routes;

public class LocalSearchResult {
  public Routes solution;
  public boolean improved;
  public LocalSearchResult(Routes solution, boolean improved) {
    this.solution = solution;
    this.improved = improved;
  }
}
