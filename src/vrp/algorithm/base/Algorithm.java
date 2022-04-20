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
import vrp.algorithm.*;

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
      try {
        routes.sumCost(this.dataModel.distance(routes.lastCustomerFromRoute(i), depot));
        routes.addCustomer(i, depot);
      } catch (Exception e) {
        routes.addCustomer(i, depot);
        routes.sumCost(this.dataModel.distance(routes.lastCustomerFromRoute(i), depot));
      }
    }
  }

  public static Algorithm getAlgorithm(String algorithmType) {
    switch (algorithmType) {
      case "Greedy": return new Greedy();
      case "Grasp": return new Grasp();
    }
    throw new IllegalArgumentException("Algorithm type not found");
  }

  public String getAlgorithmType() {
    return this.getClass().getSimpleName();
  }
}
