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

import java.util.*;
import main.Constants;

/**
 * This class prints a table.
 */
public class PrintTable {
  /**
   * Prints a table.
   * @param title The title of the table.
   * @param results The results of the table.
   */
  public static void print(String title, List<List<String>> results) {    
    System.out.format(
      "%40s" + Constants.ANSI_BOLD +
      title + "%n" + Constants.ANSI_RESET, ""
    );
    boolean header = false;
    int resultSize = results.get(0).size();
    String topLine = lineSeparator(resultSize, "┌─", "─┬─", "─┐");
    String lineSeparator = lineSeparator(resultSize, "├─", "─┼─", "─┤");
    String bottomLine = lineSeparator(resultSize, "└─", "─┴─", "─┘");
    String[] leftAlignFormat;
    if (resultSize != 5) leftAlignFormat = new String[] {
      "│ %8s ", "│ %19s ", "│ %20s ", "│ %9s ", "│ %25s ", "│ %13s │%n"
    };
    else leftAlignFormat = new String[] {
      "│ %8s ", "│ %19s ", "│ %9s ", "│ %25s ", "│ %13s │%n"
    };
    System.out.format(topLine);
    for (List<String> result : results) {
      if (!header) {
        for (int i = 0; i < result.size(); i++) {
          System.out.format(leftAlignFormat[i], Constants.ANSI_CYAN + result.get(i) + Constants.ANSI_RESET);
        }
        System.out.format(lineSeparator);
        header = true;
      }
      else {
        for (int i = 0; i < result.size(); i++) {
          System.out.format(leftAlignFormat[i], result.get(i));
        }
      }
    }
    System.out.format(bottomLine);
    System.out.println();
  }

  /**
   * Prints a table.
   * @param size The rows of the table.
   * @param start The start char of the line.
   * @param interception The interception char of the columns.
   * @param end The end char of the line.
   * @return The line.
   */
  private static String lineSeparator(int size, String start, String interception, String end) {
    String lineSeparator = start;
    lineSeparator += "─".repeat(8) + interception;
    lineSeparator += "─".repeat(19) + interception;
    if (size != 5) lineSeparator += "─".repeat(20) + interception;
    lineSeparator += "─".repeat(9) + interception;
    lineSeparator += "─".repeat(25) + interception;
    lineSeparator += "─".repeat(13) + end + "%n";
    return lineSeparator;
  }
}
