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
    printTitle(title);
    boolean header = false;
    int resultSize = results.get(0).size();
    printTop(resultSize);
    for (List<String> result : results) {
      printRow(result);
      if (!header) {
        printLine(resultSize);
        header = true;
      }
    }
    printBottom(resultSize);
    System.out.println();
  }

  public static void printRow(List<String> row) {
    String[] leftAlignFormat;
    if (row.size() != 5) leftAlignFormat = new String[] {
      "│ %8s ", "│ %19s ", "│ %20s ", "│ %9s ", "│ %25s ", "│ %13s │%n"
    };
    else leftAlignFormat = new String[] {
      "│ %8s ", "│ %19s ", "│ %9s ", "│ %25s ", "│ %13s │%n"
    };
    for (int i = 0; i < row.size(); i++) {
      System.out.format(leftAlignFormat[i], row.get(i));
    }
  }

  public static void printTop(int numberOfColumns) {
    String topLine = lineSeparator(numberOfColumns, "┌─", "─┬─", "─┐");
    System.out.format(topLine);
  }

  public static void printLine(int numberOfColumns) {
    String lineSeparator = lineSeparator(numberOfColumns, "├─", "─┼─", "─┤");
    System.out.format(lineSeparator);
  }

  public static void printHeader(List<String> header) {
    int numberOfColumns = header.size();
    printTop(numberOfColumns);
    List<String> headerColor = new ArrayList<>();
    for (int i = 0; i < numberOfColumns; i++) {
      headerColor.add(Constants.ANSI_CYAN + header.get(i) + Constants.ANSI_RESET);
    }
    printRow(headerColor);
    printLine(numberOfColumns);
  }

  public static void printTitle(String title) {
    System.out.format(
      "%40s" + Constants.ANSI_BOLD +
      title + "%n" + Constants.ANSI_RESET, ""
    );
  }

  public static void printTitleHeader(String title, List<String> header) {
    printTitle(title);
    printHeader(header);
  }

  public static void printBottom(int numberOfColumns) {
    String bottomLine = lineSeparator(numberOfColumns, "└─", "─┴─", "─┘");
    System.out.format(bottomLine);
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
