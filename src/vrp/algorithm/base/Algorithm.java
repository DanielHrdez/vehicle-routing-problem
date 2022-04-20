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
import vrp.solution.Routes;

public abstract class Algorithm {
  protected DataModel dataModel;
  protected Routes routes;
  public abstract Routes run(DataModel dataModel);
  
  /**
   * Add the depot to all the vehicles.
   */
  protected void addDepot(Routes routes) {
    int numberOfVehicles = this.dataModel.getNumberOfVehicles();
    int depot = this.dataModel.getDepot();
    this.dataModel.setCustomer(depot);

    for (int i = 0; i < numberOfVehicles; i++) {
      routes.sumCost(this.dataModel.distance(routes.get(i, routes.size(i) - 1), depot));
      routes.add(i, depot);
    }
  }

  public static Algorithm getAlgorithm(String algorithmType) {
    try {
      return (Algorithm) Class.forName(algorithmType).getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalArgumentException("Algorithm not found");
    }
  }

  public String getAlgorithmType() {
    return this.getClass().getName();
  }
}
