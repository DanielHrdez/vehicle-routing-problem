/**
 * Universidad de La Laguna
 * Diseño y análisis de algoritmos
 * Grado en Ingeniería Informática
 *
 * @author: Daniel Hernández de León
 * @since 09/04/2022
 * @version 1.0.0
 */

package vrp.solution;

import java.util.*;

public class Routes {
  List<List<Integer>> routes;
  int cost;

  public Routes() {
    this.routes = new ArrayList<>();
    this.cost = 0;
  }

  public Routes(int numberOfRoutes) {
    this.routes = new ArrayList<>();
    for (int i = 0; i < numberOfRoutes; i++) {
      this.routes.add(new ArrayList<>());
    }
    this.cost = 0;
  }

  public List<List<Integer>> getRoutes() {
    return this.routes;
  }

  public List<Integer> getRoute(int route) {
    return this.routes.get(route);
  }

  public Integer getCustomer(int route, int position) {
    return this.routes.get(route).get(position);
  }

  public Integer lastCustomerFromRoute(int route) {
    return this.getCustomer(route, this.getNumberOfCustomerByRoute(route) - 1);
  }

  public int getNumberOfRoutes() {
    return this.routes.size();
  }

  public int getNumberOfCustomerByRoute(int route) {
    return this.routes.get(route).size();
  }

  public void addCustomer(int route, int customer) {
    this.routes.get(route).add(customer);
  }

  public int getCost() {
    return this.cost;
  }

  public int sumCost(int newCost) {
    return this.cost += newCost;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Routes:\n");
    for (List<Integer> route : this.routes) {
      sb.append("  " + route.toString() + "\n");
    }
    sb.append("Cost: " + this.cost);
    return sb.toString();
  }
}
