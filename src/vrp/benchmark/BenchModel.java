/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.benchmark;

import vrp.*;
import vrp.algorithm.*;
import vrp.data.DataModel;
import vrp.io.PrintTable;
import main.Constants;

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
    boolean isGreedy = model.algorithmType().equals("Greedy");
    List<String> header = new ArrayList<>();
    header.add(Constants.ANSI_CYAN + "Problema" + Constants.ANSI_RESET);
    header.add(Constants.ANSI_CYAN + "Número de Vehiculos" + Constants.ANSI_RESET);
    if (!isGreedy) header.add(Constants.ANSI_CYAN + "Número de Candidatos" + Constants.ANSI_RESET);
    header.add(Constants.ANSI_CYAN + "Ejecución" + Constants.ANSI_RESET);
    header.add(Constants.ANSI_CYAN + "Distancia Total Recorrida" + Constants.ANSI_RESET);
    header.add(Constants.ANSI_CYAN + "CPU Time (ns)" + Constants.ANSI_RESET);
    results.add(header);
    int numberIterations = isGreedy ? 3 : 4;
    int numberOfColumns = header.size();

    PrintTable.printTitleHeader(model.algorithmType(), header);
    for (int i = 2; i < numberIterations; i++) {
      int execution = 0;
      for (DataModel dataModel : this.dataModels) {
        model.setModel(dataModel);
        if (!isGreedy) ((Grasp) model.getAlgorithm()).setCandidates(i);
        long start = System.nanoTime();
        model.solve();
        long end = System.nanoTime();
        long time = end - start;
        dataModel.resetCustomers();

        List<String> currentResult = new ArrayList<>();
        currentResult.add(Integer.toString(++this.counter));
        currentResult.add(String.valueOf(dataModel.getNumberOfVehicles()));
        if (!isGreedy) currentResult.add(Integer.toString(i));
        currentResult.add(Integer.toString(++execution));
        if (isGreedy) currentResult.add(Integer.toString(model.getCost()));
        else currentResult.add(model.getFullCost());
        currentResult.add(String.valueOf(time));
        PrintTable.printRow(currentResult);
        results.add(currentResult);
      }
    }

    PrintTable.printBottom(numberOfColumns);
    return results;
  }
}
