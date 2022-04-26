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
    try {
      Files.createDirectories(Paths.get(Constants.OUTPUT_FOLDER));
    } catch (Exception e) {
      System.out.println("Error creating the output folder.");
    }
    List<List<String>> results = new ArrayList<>();
    boolean isGreedy = model.algorithmType().equals("Greedy");
    List<String> header = new ArrayList<>();
    header.add("Problema");
    header.add("Número de Vehiculos");
    if (!isGreedy) header.add("Número de Candidatos");
    header.add("Ejecución");
    header.add("Distancia Total Recorrida");
    header.add("CPU Time (ns)");
    results.add(header);
    int numberIterations = isGreedy ? 3 : 4;
    int numberOfColumns = header.size();
    String filename = Constants.OUTPUT_FOLDER + model.algorithmType() + ".csv";

    PrintTable.printTitleHeader(model.algorithmType(), header);
    WriteCSV.clearFile(filename);
    WriteCSV.add(filename, header);
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
        WriteCSV.add(filename, currentResult);
        results.add(currentResult);
      }
    }

    PrintTable.printBottom(numberOfColumns);
    return results;
  }
}
