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

import vrp.data.*;
import vrp.models.*;
import vrp.models.benchmark.*;

import java.util.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * MainVRP is the main class of the program.
 */
public class MainVRP {
  public static void main(String[] args) throws Exception {
    MainVRP.printTitle();
    List<DataModel> dataModels = MainVRP.readDataFiles();
    List<List<List<String>>> results = MainVRP.runAlgorithms(dataModels);
    MainVRP.writeFiles(results);
    MainVRP.printResults(results);
  }

  static void printTitle() {
    System.out.println(Constants.TITLE);
    System.out.println(Constants.NAME + " - " + Constants.EMAIL + "\n");
  }

  static List<DataModel> readDataFiles() throws Exception {
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

  static List<List<List<String>>> runAlgorithms(List<DataModel> dataModels) throws Exception {
    List<List<List<String>>> results = new ArrayList<>();
    GreedyVRP greedy = new GreedyVRP();
    GraspVRP grasp = new GraspVRP();
    BenchModel benchmark = new BenchModel(dataModels);
    results.add(benchmark.run(greedy));
    results.add(benchmark.run(grasp));
    return results;
  }

  static void writeFiles(List<List<List<String>>> results) throws Exception {
    Files.createDirectories(Paths.get(Constants.OUTPUT_FOLDER));
    WriteCSV writer = new WriteCSV(Constants.OUTPUT_FOLDER + "/greedy.csv");
    writer.write(results.get(0));
    writer = new WriteCSV(Constants.OUTPUT_FOLDER + "/grasp.csv");
    writer.write(results.get(1));
  }

  static void printResults(List<List<List<String>>> results) {
    PrintTable printer = new PrintTable();
    printer.print("Greedy", results.get(0));
    printer.print("Grasp", results.get(1));
  }
}
