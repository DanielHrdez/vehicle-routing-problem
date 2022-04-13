/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.models.benchmark;

import java.util.List;
import java.io.FileWriter;

public class WriteCSV {
  private static final String DELIMITER = ",";
  private static final String NEW_LINE = "\n";
  private String fileName;

  public WriteCSV(String filename) {
    this.fileName = filename;
  }

  public void write(List<List<String>> results) {
    try {
      FileWriter writer = new FileWriter(fileName);
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
