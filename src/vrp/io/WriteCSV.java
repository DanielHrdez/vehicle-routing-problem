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
  private String fileName;

  /**
   * Constructor of the class.
   * @param filename The name of the file.
   */
  public WriteCSV(String filename) {
    this.fileName = filename;
  }

  /**
   * Writes the solution of the problem in a csv file.
   * @param results The solution of the problem.
   */
  public void write(List<List<String>> results) {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
      for (List<String> result : results) {
        for (String value : result) {
          writer.append(value);
          writer.append(DELIMITER);
        }
        writer.append(NEW_LINE);
      }
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
