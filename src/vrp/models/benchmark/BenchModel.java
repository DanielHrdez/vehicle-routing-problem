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
public class BenchModel {
  private List<DataModel> dataModels;
  private int counter;

  /**
   * Constructor of the class.
   * @param dataModels The data models.
   */
  public BenchModel(List<DataModel> dataModels) {
    this.dataModels = dataModels;
    this.counter = 0;
  }

  /**
   * Run the benchmark.
   * @param model The model of the problem.
   * @return The results of the benchmark.
   */
  public List<List<String>> run(VehicleRouting model) {
    List<List<String>> results = new ArrayList<>();
    boolean isGrasp = model instanceof GraspVRP;
    List<String> header = new ArrayList<>();
    header.add("Problema");
    header.add("Número de Vehiculos");
    if (isGrasp) header.add("Número de Candidatos");
    header.add("Ejecución");
    header.add("Distancia Total Recorrida");
    header.add("CPU Time (ns)");
    results.add(header);
    int numberIterations = model instanceof GreedyVRP ? 3 : 11;

    for (int i = 2; i < numberIterations; i++) {
      int execution = 0;
      for (DataModel dataModel : this.dataModels) {
        model.setModel(dataModel);
        if (isGrasp) ((GraspVRP) model).setMaxCandidates(i);
        long start = System.nanoTime();
        model.solve();
        long end = System.nanoTime();
        long time = end - start;
        dataModel.resetCustomers();

        List<String> currentResult = new ArrayList<>();
        currentResult.add(String.format("%08d", ++counter));
        currentResult.add(String.valueOf(dataModel.getNumberOfVehicles()));
        if (isGrasp) currentResult.add(Integer.toString(i));
        currentResult.add(Integer.toString(++execution));
        currentResult.add(Integer.toString(model.getCost()));
        currentResult.add(String.valueOf(time));
        results.add(currentResult);
      }
    }

    return results;
  }
}
