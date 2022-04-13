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

public class PrintTable {
  public void print(String title, List<List<String>> results) {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BOLD = "\u001B[1m";
    String ANSI_UNDERLINE = "\u001B[4m";
    
    System.out.format(
      "%-40s" + ANSI_BOLD + ANSI_UNDERLINE +
      title + "%n" + ANSI_RESET, ""
    );
    boolean header = false;
    String lineSeparator = "+-";
    lineSeparator += "-".repeat(20) + "-+-";
    lineSeparator += "-".repeat(20) + "-+-";
    if (results.get(0).size() != 5) lineSeparator += "-".repeat(5) + "-+-";
    lineSeparator += "-".repeat(10) + "-+-";
    lineSeparator += "-".repeat(25) + "-+-";
    lineSeparator += "-".repeat(10) + "-+%n";

    for (List<String> result : results) {
      String leftAlignFormat;
      if (result.size() == 5) {
        if (!header) System.out.format(lineSeparator);
        leftAlignFormat = "| %-20s | %-20s | %-10s | %-25s | %-10s |%n";
        System.out.format(leftAlignFormat, result.get(0), result.get(1), result.get(2), result.get(3), result.get(4));
        if (!header) {
          System.out.format(lineSeparator);
          header = true;
        }
      } else {
        if (!header) System.out.format(lineSeparator);
        leftAlignFormat = "| %-20s | %-20s | %-5s | %-10s | %-25s | %-10s |%n";
        System.out.format(leftAlignFormat, result.get(0), result.get(1), result.get(2), result.get(3), result.get(4), result.get(5));
        if (!header) {
          System.out.format(lineSeparator);
          header = true;
        }
      }
      System.out.format(lineSeparator);
    }
  }
}
