/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp;

import java.util.*;

public class Routes {
  List<List<Integer>> routes;
  int cost;

  public Routes() {
    this.routes = new ArrayList<>();
    this.cost = 0;
  }

  public Routes(int numberOfRoutes) {
    this.routes = new ArrayList<>(numberOfRoutes);
    this.cost = 0;
  }

  public List<List<Integer>> getRoutes() {
    return this.routes;
  }

  public int getCost() {
    return this.cost;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Routes: ");
    for (List<Integer> route : this.routes) {
      sb.append("  " + route.toString() + "\n");
    }
    sb.append("Cost: " + this.cost);
    return sb.toString();
  }
}
