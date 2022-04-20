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

import vrp.algorithm.base.Algorithm;
import vrp.data.DataModel;

import java.util.*;

/**
 * This abstract class represents a vehicle routing problem.
 */
public class VehicleRouting {
  private DataModel dataModel;
  private Algorithm algorithm;
  private Routes routes;

  /**
   * Constructor of the class.
   */
  public VehicleRouting() {
    this.dataModel = null;
    this.routes = null;
  }

  /**
   * Constructor of the class.
   *
   * @param dataModel The model of the problem.
   * It must be a model with a vertex for each customer.
   */
  public VehicleRouting(DataModel dataModel) {
    this.dataModel = dataModel;
    this.routes = new Routes(dataModel.getNumberOfVehicles());
  }

  /**
   * Setter of the model.
   * @param dataModel The model of the problem.
   */
  public void setModel(DataModel dataModel) {
    this.dataModel = dataModel;
    this.routes = new Routes(dataModel.getNumberOfVehicles());
  }

  /**
   * Returns the routes of the problem.
   * 
   * @return The routes of the problem.
   */
  public List<List<Integer>> getRoutes() {
    return this.routes.getRoutes();
  }

  /**
   * Returns the cost of the solution.
   * 
   * @return The cost of the solution.
   */
  public int getCost() {
    return this.routes.getCost();
  }

  /**
   * Solves the vehicle routing problem for the given graph.
   */
  public void solve() {
    this.routes = this.algorithm.run(this.dataModel);
    this.dataModel.resetCustomers();
  }

  /**
   * @return Return the string representation of the routes.
   */
  public String toString() {
    return new StringBuilder()
      .append("VehicleRouting {\n")
      .append(this.dataModel.toString())
      .append(this.routes.toString())
      .append("}")
      .toString();
  }
}
