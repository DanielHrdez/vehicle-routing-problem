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
    String[] algorithms = {"Greedy", "Grasp", "Gvns"};
    for (String algorithm : algorithms) {
      MainVRP.runAlgorithm(dataModels, algorithm);
    }
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
  static List<List<String>> runAlgorithm(List<DataModel> dataModels, String algorithm) {
    VehicleRouting vrp = new VehicleRouting();
    vrp.setAlgorithm(algorithm);
    BenchModel benchmark = new BenchModel(dataModels);
    return benchmark.run(vrp);
  }

  /**
   * Writes the results in files.
   * @param results List of results.
   * @throws Exception If the file cannot be written.
   */
  static void writeFile(List<List<String>> results, String algorithm) throws Exception {
    Files.createDirectories(Paths.get(Constants.OUTPUT_FOLDER));
    WriteCSV.write(Constants.OUTPUT_FOLDER + algorithm + ".csv", results);
  }

  /**
   * Prints the results.
   * @param results List of results.
   */
  static void printResult(List<List<String>> results, String algorithm) {
    PrintTable.print(algorithm, results);
  }
}
