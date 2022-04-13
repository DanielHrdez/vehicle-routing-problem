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
    String ANSI_CYAN = "\u001B[36m";
    
    System.out.format(
      "%-40s" + ANSI_BOLD +
      title + "%n" + ANSI_RESET, ""
    );
    boolean header = false;
    String lineSeparator = "+-";
    lineSeparator += "-".repeat(8) + "-+-";
    lineSeparator += "-".repeat(19) + "-+-";
    if (results.get(0).size() != 5) lineSeparator += "-".repeat(20) + "-+-";
    lineSeparator += "-".repeat(9) + "-+-";
    lineSeparator += "-".repeat(25) + "-+-";
    lineSeparator += "-".repeat(13) + "-+%n";

    for (List<String> result : results) {
      String leftAlignFormat;
      if (result.size() == 5) {
        if (!header) System.out.format(lineSeparator);
        leftAlignFormat = "| %-8s | %-19s | %-9s | %-25s | %-13s |%n";
        if (!header) {
          System.out.format(leftAlignFormat, ANSI_CYAN + result.get(0) + ANSI_RESET, ANSI_CYAN + result.get(1) + ANSI_RESET, ANSI_CYAN + result.get(2) + ANSI_RESET, ANSI_CYAN + result.get(3) + ANSI_RESET, ANSI_CYAN + result.get(4) + ANSI_RESET);
          System.out.format(lineSeparator);
          header = true;
        } else {
          System.out.format(leftAlignFormat, result.get(0), result.get(1), result.get(2), result.get(3), result.get(4));
        }
      } else {
        if (!header) System.out.format(lineSeparator);
        leftAlignFormat = "| %-8s | %-19s | %-20s | %-9s | %-25s | %-13s |%n";
        if (!header) {
          System.out.format(leftAlignFormat, ANSI_CYAN + result.get(0) + ANSI_RESET, ANSI_CYAN + result.get(1) + ANSI_RESET, ANSI_CYAN + result.get(2) + ANSI_RESET, ANSI_CYAN + result.get(3) + ANSI_RESET, ANSI_CYAN + result.get(4) + ANSI_RESET, ANSI_CYAN + result.get(5) + ANSI_RESET);
          System.out.format(lineSeparator);
          header = true;
        } else {
          System.out.format(leftAlignFormat, result.get(0), result.get(1), result.get(2), result.get(3), result.get(4), result.get(5));
        }
      }
    }
    System.out.format(lineSeparator);
    System.out.println();
  }
}
