/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.models.benchmark;

import vrp.models.base.VehicleRouting;
import vrp.models.*;
import vrp.data.DataModel;

import java.util.*;

/**
 * This class Bench a vehicle routing problem.
 */
public class BenchModel<T> {
  private VehicleRouting model;
  private List<DataModel> dataModels;
  private List<List<String>> results;

  public BenchModel(VehicleRouting model) {
    this.model = model;
  }

  public void setModel(VehicleRouting model) {
    this.model = model;
  }

  public void addDataModel(DataModel dataModel) {
    this.dataModels.add(dataModel);
  }

  public void bench() {
    boolean isGrasp = this.model instanceof GraspVRP;
    List<String> header = new ArrayList<>();
    header.add("Problema");
    header.add("Número de Vehiculos");
    if (isGrasp) header.add("|LRC|");
    header.add("Ejecución");
    header.add("Distancia Total Recorrida");
    header.add("CPU Time");
    results.add(header);
    int numberIterations = this.model instanceof GreedyVRP ? 3 : 11;

    for (int i = 2; i < numberIterations; i++) {
      for (DataModel dataModel : this.dataModels) {
        this.model.setModel(dataModel);
        if (isGrasp) ((GraspVRP) this.model).setMaxCandidates(i);
        long start = System.nanoTime();
        this.model.solve();
        long end = System.nanoTime();
        long time = end - start;

        List<String> currentResult = new ArrayList<>();
        currentResult.add(dataModel.getName());
        currentResult.add(String.valueOf(dataModel.getNumberOfVehicles()));
        if (isGrasp) currentResult.add(Integer.toString(i));
        currentResult.add(Integer.toString(this.model.getCost()));
        currentResult.add(String.valueOf(time));
        results.add(currentResult);
      }
    }
  }

  public List<List<String>> getResults() {
    return results;
  }
}
