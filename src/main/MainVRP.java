/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package main;

import vrp.VehicleRouting;
import vrp.benchmark.*;
import vrp.data.*;
import vrp.io.PrintTable;
import vrp.io.WriteCSV;

import java.util.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * MainVRP is the main class of the program.
 */
public class MainVRP {
  /**
   * Main method of the program.
   */
  public static void main(String[] args) throws Exception {
    MainVRP.printTitle();
    List<DataModel> dataModels = MainVRP.readDataFiles();
    List<List<List<String>>> results = MainVRP.runAlgorithms(dataModels);
    MainVRP.writeFiles(results);
    MainVRP.printResults(results);
  }

  /**
   * Prints the title of the program.
   */
  static void printTitle() {
    System.out.println(Constants.TITLE);
    System.out.println(Constants.NAME + " - " + Constants.EMAIL + "\n");
  }

  /**
   * Reads the data files.
   * @return List of data models.
   */
  static List<DataModel> readDataFiles() {
    List<DataModel> dataModels = new ArrayList<>();
    File folder = new File(Constants.DATA_FOLDER);
    File[] listOfFiles = folder.listFiles();
    
    for (File file : listOfFiles) {
      if (file.isFile()) {
        dataModels.add(ReadDataModel.read(file.getAbsolutePath()));
      }
    }

    return dataModels;
  }

  /**
   * Runs the algorithms.
   * @param dataModels List of data models.
   * @return List of results.
   */
  static List<List<List<String>>> runAlgorithms(List<DataModel> dataModels) {
    List<List<List<String>>> results = new ArrayList<>();
    VehicleRouting vrp = new VehicleRouting();
    vrp.setAlgorithm("Greedy");
    BenchModel benchmark = new BenchModel(dataModels);
    results.add(benchmark.run(vrp));
    vrp.setAlgorithm("Grasp");
    results.add(benchmark.run(vrp));
    return results;
  }

  /**
   * Writes the results in files.
   * @param results List of results.
   * @throws Exception If the file cannot be written.
   */
  static void writeFiles(List<List<List<String>>> results) throws Exception {
    Files.createDirectories(Paths.get(Constants.OUTPUT_FOLDER));
    WriteCSV writer = new WriteCSV(Constants.OUTPUT_FOLDER + "/greedy.csv");
    writer.write(results.get(0));
    writer = new WriteCSV(Constants.OUTPUT_FOLDER + "/grasp.csv");
    writer.write(results.get(1));
  }

  /**
   * Prints the results.
   * @param results List of results.
   */
  static void printResults(List<List<List<String>>> results) {
    PrintTable.print("Greedy", results.get(0));
    PrintTable.print("Grasp", results.get(1));
  }
}
