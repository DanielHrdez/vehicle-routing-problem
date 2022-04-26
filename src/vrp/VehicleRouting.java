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
import vrp.solution.Routes;

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
    this.algorithm = null;
  }

  /**
   * Constructor of the class.
   *
   * @param dataModel The model of the problem.
   * It must be a model with a vertex for each customer.
   */
  public VehicleRouting(DataModel dataModel, String algorithmType) {
    this.dataModel = dataModel;
    this.routes = new Routes(dataModel.getNumberOfVehicles());
    this.algorithm = Algorithm.getAlgorithm(algorithmType);
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
   * Setter of the algorithm.
   * @param algorithmType The algorithm type.
   */
  public void setAlgorithm(String algorithmType) {
    this.algorithm = Algorithm.getAlgorithm(algorithmType);
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
  }

  /**
   * Returns the routes of the problem.
   * @return The routes of the problem.
   */
  public Routes getSolution() {
    return this.routes;
  }

  /**
   * Returns the Algorithm Type
   * @return The Algorithm Type
   */
  public String algorithmType() {
    return this.algorithm.getAlgorithmType();
  }

  /**
   * Getter of the algorithm.
   * @return The algorithm.
   */
  public Algorithm getAlgorithm() {
    return this.algorithm;
  }

  public String getFullCost() {
    return String.format("%d -> %d", this.routes.getCost(), this.routes.getCostSearch());
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
