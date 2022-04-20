/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.algorithm.base;

import vrp.data.DataModel;
import vrp.Routes;

public abstract class Algorithm {
  protected DataModel dataModel;
  protected Routes routes;
  public abstract Routes run(DataModel dataModel);
}
