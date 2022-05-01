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
      printRow(result, false);
      if (!header) {
        printLine(resultSize);
        header = true;
      }
    }
    printBottom(resultSize);
    System.out.println();
  }

  public static void printRow(List<String> row, boolean color) {
    String separator = "│";
    List<String> leftAlignFormat = new ArrayList<>();
    leftAlignFormat.add(" %-16s ");
    leftAlignFormat.add(" %9s ");
    if (row.size() != 5) {
      leftAlignFormat.add(" %11s ");
      leftAlignFormat.add(" %-17s ");
    }
    leftAlignFormat.add(" %9s ");
    leftAlignFormat.add(" %15s ");
    leftAlignFormat.add(" %-15s ");
    for (int i = 0; i < row.size(); i++) {
      System.out.print(separator);
      if (color) System.out.print(Constants.ANSI_CYAN);
      System.out.format(leftAlignFormat.get(i), row.get(i));
      if (color) System.out.print(Constants.ANSI_RESET);
    }
    System.out.print(separator + "\n");
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
    printRow(header, true);
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
    lineSeparator += "─".repeat(16) + interception;
    lineSeparator += "─".repeat(9) + interception;
    if (size != 5) {
      lineSeparator += "─".repeat(11) + interception;
      lineSeparator += "─".repeat(17) + interception;
    }
    lineSeparator += "─".repeat(9) + interception;
    lineSeparator += "─".repeat(15) + interception;
    lineSeparator += "─".repeat(15) + end + "%n";
    return lineSeparator;
  }
}
