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
  public DataModel read(String fileName) {
    BufferedReader bufferedReader = this.readBuffer(fileName);
    int numberOfCustomers = this.readElement(bufferedReader);
    int numberOfVehicles = this.readElement(bufferedReader);
    int[][] distanceMatrix = this.readDistanceMatrix(bufferedReader, numberOfCustomers);
    this.closeFile(bufferedReader);
    return new DataModel(numberOfVehicles, numberOfCustomers, distanceMatrix);
  }

  /**
   * Reads the distance matrix from a file.
   * @param bufferedReader The buffered reader to read the file.
   * @param numberOfCustomers The number of customers.
   * @return The distance matrix.
   */
  private int[][] readDistanceMatrix(BufferedReader bufferedReader, int numberOfCustomers) {
    int[][] distanceMatrix = new int[numberOfCustomers][numberOfCustomers];
    for (int i = 0; i < numberOfCustomers; i++) {
      String[] distances = this.nextTokens(bufferedReader);
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
  private void closeFile(BufferedReader bufferedReader) {
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
  private int readElement(BufferedReader bufferedReader) {
    String[] tokens = this.nextTokens(bufferedReader);
    return Integer.parseInt(tokens[1]);
  }

  /**
   * Reads the buffer of the file.
   * @param fileName The name of the file.
   * @return The buffered reader.
   */ 
  private BufferedReader readBuffer(String fileName) {
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
  private String[] nextTokens(BufferedReader bufferedReader) {
    String[] tokens = null;
    try {
      tokens = bufferedReader.readLine().split(" ");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tokens;
  }
}
