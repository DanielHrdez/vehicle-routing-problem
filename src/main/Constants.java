/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package main;

public class Constants {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  public static final String ANSI_BOLD = "\u001B[1m";
  public static final String ANSI_ITALIC = "\u001B[3m";
  public static final String ANSI_UNDERLINE = "\u001B[4m";
  public static final String ANSI_BLINK = "\u001B[5m";
  public static final String ANSI_INVISIBLE = "\u001B[8m";
  public static final String ANSI_STRIKETHROUGH = "\u001B[9m";

  private static String VRP = """
    _    __     __    _      __        ____              __  _                ____             __    __             
   | |  / /__  / /_  (_)____/ /__     / __ \\____  __  __/ /_(_)___  ____ _   / __ \\_________  / /_  / /__  ____ ___ 
   | | / / _ \\/ __ \\/ / ___/ / _ \\   / /_/ / __ \\/ / / / __/ / __ \\/ __ `/  / /_/ / ___/ __ \\/ __ \\/ / _ \\/ __ `__ \\
   | |/ /  __/ / / / / /__/ /  __/  / _, _/ /_/ / /_/ / /_/ / / / / /_/ /  / ____/ /  / /_/ / /_/ / /  __/ / / / / /
   |___/\\___/_/ /_/_/\\___/_/\\___/  /_/ |_|\\____/\\__,_/\\__/_/_/ /_/\\__, /  /_/   /_/   \\____/_.___/_/\\___/_/ /_/ /_/ 
                                                                 /____/                                                     
""";
  private static int vrpSize = VRP.length() / 2 + 16;
  private static String firstHalf = VRP.substring(0, vrpSize);
  private static String secondHalf = VRP.substring(vrpSize);

  public static final String TITLE = ANSI_BLUE + firstHalf + ANSI_YELLOW + secondHalf + ANSI_RESET;

  private static String ansiName = ANSI_BOLD + ANSI_ITALIC + ANSI_UNDERLINE;
  public static final String NAME = ansiName + "Daniel Hernández de León" + ANSI_RESET;
  public static final String EMAIL = ansiName + "alu0101331720@ull.edu.es" + ANSI_RESET;
  
}
