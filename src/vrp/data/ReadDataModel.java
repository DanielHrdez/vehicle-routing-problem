/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.data;

import java.io.*;

/**
 * Class to read a model from a file.
 */
public class ReadDataModel {
  /**
   * Reads a model from a file.
   * @param fileName The name of the file.
   * @return The model read.
   */
  public static DataModel read(String fileName) {
    BufferedReader bufferedReader = ReadDataModel.readBuffer(fileName);
    int numberOfCustomers = ReadDataModel.readElement(bufferedReader) + 1;
    int numberOfVehicles = ReadDataModel.readElement(bufferedReader);
    int[][] distanceMatrix = ReadDataModel.readDistanceMatrix(bufferedReader, numberOfCustomers);
    ReadDataModel.closeFile(bufferedReader);
    return new DataModel(numberOfVehicles, numberOfCustomers, distanceMatrix);
  }

  /**
   * Reads the distance matrix from a file.
   * @param bufferedReader The buffered reader to read the file.
   * @param numberOfCustomers The number of customers.
   * @return The distance matrix.
   */
  private static int[][] readDistanceMatrix(BufferedReader bufferedReader, int numberOfCustomers) {
    int[][] distanceMatrix = new int[numberOfCustomers][numberOfCustomers];
    ReadDataModel.nextTokens(bufferedReader);
    for (int i = 0; i < numberOfCustomers; i++) {
      String[] distances = ReadDataModel.nextTokens(bufferedReader);
      for (int j = 0; j < numberOfCustomers; j++) {
        distanceMatrix[i][j] = Integer.parseInt(distances[j]);
      }
    }
    return distanceMatrix;
  }

  /**
   * Close a given file.
   * @param bufferedReader The buffered reader.
   */
  private static void closeFile(BufferedReader bufferedReader) {
    try {
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the next element of the file.
   * @param bufferedReader The buffered reader.
   * @return The next element.
   */
  private static int readElement(BufferedReader bufferedReader) {
    String[] tokens = ReadDataModel.nextTokens(bufferedReader);
    return Integer.parseInt(tokens[1]);
  }

  /**
   * Reads the buffer of the file.
   * @param fileName The name of the file.
   * @return The buffered reader.
   */ 
  private static BufferedReader readBuffer(String fileName) {
    BufferedReader bufferedReader = null;
    try {
      File file = new File(fileName);
      FileReader fileReader = new FileReader(file);
      bufferedReader = new BufferedReader(fileReader);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bufferedReader;
  }

  /**
   * Reads the next tokens of the file.
   * @param bufferedReader The buffered reader.
   * @return The next tokens.
   */
  private static String[] nextTokens(BufferedReader bufferedReader) {
    String[] tokens = null;
    try {
      tokens = bufferedReader.readLine().split("\\s+");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tokens;
  }
}
