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
import vrp.algorithm.constructsearch.Grasp;
import vrp.algorithm.constructsearch.Gvns;
import vrp.algorithm.constructsearch.base.ConstructSearch;
import vrp.algorithm.constructsearch.localsearch.base.LocalSearchType;
import vrp.data.DataModel;
import vrp.io.PrintTable;
import vrp.io.WriteCSV;
import main.Constants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class Bench a vehicle routing problem.
 */
public class BenchModel {
  private List<DataModel> dataModels;

  /**
   * Constructor of the class.
   * @param dataModels The data models.
   */
  public BenchModel(List<DataModel> dataModels) {
    this.dataModels = dataModels;
  }

  /**
   * Run the benchmark.
   * @param model The model of the problem.
   * @return The results of the benchmark.
   */
  public List<List<String>> run(VehicleRouting model) {
    try {
      Files.createDirectories(Paths.get(Constants.OUTPUT_FOLDER));
    } catch (Exception e) {
      System.out.println("Error creating the output folder.");
    }
    List<List<String>> results = new ArrayList<>();
    boolean isGreedy = model.algorithmType().equals("Greedy");
    boolean isGrasp = model.algorithmType().equals("Grasp");
    boolean isGvns = model.algorithmType().equals("Gvns");
    List<String> header = new ArrayList<>();
    header.add("Problema");
    header.add("Vehiculos");
    if (isGrasp) header.add("NCandidatos");
    else if (isGvns) header.add("Agitaciónes");
    if (!isGreedy) header.add("Búsqueda Local");
    header.add("Ejecución");
    header.add("Distancia Total");
    header.add("Tiempo CPU (sg)");
    results.add(header);
    int numberIterations = isGreedy ? 3 : 4;
    int iterationsPerAlgorithm = isGreedy ? 1 : 5;
    int numberOfColumns = header.size();
    String filename = Constants.OUTPUT_FOLDER + model.algorithmType() + ".csv";

    PrintTable.printTitleHeader(model.algorithmType(), header);
    WriteCSV.clearFile(filename);
    WriteCSV.add(filename, header);
    for (int i = 2; i < numberIterations; i++) {
      for (DataModel dataModel : this.dataModels) {
        for (LocalSearchType localSearchType : LocalSearchType.values()) {
          for (int j = 1; j <= iterationsPerAlgorithm; j++) {
            model.setModel(dataModel);
            if (isGrasp) ((Grasp) model.getAlgorithm()).setCandidates(i);
            else if (isGvns) ((Gvns) model.getAlgorithm()).setMaxShakes(i);
            if (!isGreedy) ((ConstructSearch) model.getAlgorithm()).setLocalSearchType(localSearchType);
            long start = System.nanoTime();
            model.solve();
            long end = System.nanoTime();
            double time = (end - start) / 1e9;
            dataModel.resetCustomers();

            List<String> currentResult = new ArrayList<>();
            currentResult.add(dataModel.getName());
            currentResult.add(String.valueOf(dataModel.getNumberOfVehicles()));
            if (!isGreedy) currentResult.add(Integer.toString(i));
            if (!isGreedy) currentResult.add(localSearchType.toString());
            currentResult.add(Integer.toString(j));
            if (isGreedy) currentResult.add(Integer.toString(model.getCost()));
            else currentResult.add(model.getFullCost());
            currentResult.add(String.valueOf(time));
            PrintTable.printRow(currentResult, false);
            WriteCSV.add(filename, currentResult);
            results.add(currentResult);
          }
          if (isGreedy) break;
        }
      }
    }

    PrintTable.printBottom(numberOfColumns);
    return results;
  }
}
