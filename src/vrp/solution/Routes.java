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

/**
 * This class represents a solution of a vehicle routing problem.
 */
public class Routes implements Cloneable {
  private List<List<Integer>> routes;
  private int cost;
  private int costSearch;

  /**
   * Constructor of the class.
   */
  public Routes() {
    this.routes = new ArrayList<>();
    this.cost = 0;
  }

  /**
   * Constructor of the class.
   * @param numberOfRoutes The number of routes.
   */
  public Routes(int numberOfRoutes) {
    this.routes = new ArrayList<>();
    for (int i = 0; i < numberOfRoutes; i++) {
      this.routes.add(new ArrayList<>());
    }
    this.cost = 0;
  }

  /**
   * Getter of the routes.
   * @return The routes.
   */
  public List<List<Integer>> getRoutes() {
    return this.routes;
  }

  /**
   * Setter of the routes.
   * @param routeIndex The index of the route.
   * @param route The route.
   */
  public void setRoute(int routeIndex, List<Integer> route) {
    this.routes.set(routeIndex, route);
  }

  /**
   * Getter of the route specified by the index.
   * @param route The index of the route.
   * @return The route.
   */
  public List<Integer> getRoute(int route) {
    return this.routes.get(route);
  }

  /**
   * Getter of customer specified by the route and the index.
   * @param route The index of the route.
   * @param position The index of the customer.
   * @return The customer.
   */
  public Integer getCustomer(int route, int position) {
    return this.routes.get(route).get(position);
  }

  /**
   * Return the last customer of the route specified by the index.
   * @param route The index of the route.
   * @return The last customer.
   */
  public Integer lastCustomerFromRoute(int route) {
    return this.getCustomer(route, this.getRouteSize(route) - 1);
  }

  /**
   * Getter of the number of routes.
   * @return The number of routes.
   */
  public int getNumberOfRoutes() {
    return this.routes.size();
  }

  /**
   * Getter of the size of the route specified by the index.
   * @param route The index of the route.
   * @return The size of the route.
   */
  public int getRouteSize(int route) {
    return this.routes.get(route).size();
  }

  /**
   * Add a customer to the route specified by the index.
   * @param route The index of the route.
   * @param customer The customer.
   */
  public void addCustomer(int route, int customer) {
    this.routes.get(route).add(customer);
  }

  /**
   * Add a customer to the route specified by the index at a given position.
   * @param route The index of the route.
   * @param customer The customer.
   * @param position The position.
   */
  public void addCustomer(int route, int customer, int position) {
    this.routes.get(route).add(position, customer);
  }

  /**
   * Getter of the cost.
   * @return The cost.
   */
  public int getCost() {
    return this.cost;
  }

  /**
   * Getter of the cost.
   * @return The cost.
   */
  public int getCostSearch() {
    return this.costSearch;
  }

  /**
   * Setter of the cost.
   * @param cost The cost.
   */
  public void setCostSearch(int cost) {
    this.costSearch = cost;
  }

  /**
   * Sum a new cost to the current cost.
   * @param newCost The new cost.
   */
  public void sumCost(int newCost) {
    this.cost += newCost;
  }

  /**
   * Sum a new cost to the current cost.
   * @param newCost The new cost.
   */
  public void sumCostSearch(int newCost) {
    this.costSearch += newCost;
  }

  /**
   * String representation of the routes.
   * @return The string representation.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Routes:\n");
    for (List<Integer> route : this.routes) {
      sb.append("  " + route.toString() + "\n");
    }
    sb.append("Cost: " + this.cost);
    return sb.toString();
  }

  /**
   * Clone the object.
   * @return The clone.
   */
  public Routes clone() {
    Routes clone = new Routes();
    clone.routes = new ArrayList<>(this.routes.size());
    for (List<Integer> route : this.routes) {
      clone.routes.add(new ArrayList<>(route));
    }
    clone.cost = this.cost;
    clone.costSearch = this.costSearch;
    return clone;
  }

  /**
   * Remove a customer from the route specified by the index.
   * @param route The index of the route.
   * @param position The index of the customer.
   */
  public void removeCustomer(int route, int position) {
    this.routes.get(route).remove(position);
  }

  /**
   * Set a customer in the route specified by the index.
   * @param route The index of the route.
   * @param position The index of the customer.
   * @param customer The customer.
   */
  public void setCustomer(int route, int position, int customer) {
    this.routes.get(route).set(position, customer);
  }
}
