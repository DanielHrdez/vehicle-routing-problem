/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.io;

import java.util.List;
import java.io.*;

/**
 * This class writes the solution of the problem in a csv file.
 */
public class WriteCSV {
  private static final String DELIMITER = ",";
  private static final String NEW_LINE = "\n";

  /**
   * Writes the solution of the problem in a csv file.
   * @param filename The name of the file.
   * @param results The solution of the problem.
   */
  public static void write(String filename, List<List<String>> results) {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) {
      for (List<String> result : results) {
        for (String value : result) {
          writer.append(value + DELIMITER);
        }
        writer.append(NEW_LINE);
      }
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Clear the file.
   * @param filename The name of the file.
   */
  public static void clearFile(String filename) {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) {
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Add a line to the file.
   * @param filename The name of the file.
   * @param result The line to add.
   */
  public static void add(String filename, List<String> result) {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true), "UTF-8")) {
      for (String value : result) {
        writer.append(value + DELIMITER);
      }
      writer.append(NEW_LINE);
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
