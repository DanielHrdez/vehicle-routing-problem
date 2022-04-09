package vrp.graph;

public class Graph {
  private int numberOfVehicles;
  private int numberOfCustomers;
  private int[][] distanceMatrix;

  public Graph() {
    this.numberOfVehicles = 0;
    this.numberOfCustomers = 0;
    this.distanceMatrix = null;
  }

  public Graph(int numberOfVehicles, int numberOfCustomers, int[][] distanceMatrix) {
    this.numberOfVehicles = numberOfVehicles;
    this.numberOfCustomers = numberOfCustomers;
    this.distanceMatrix = distanceMatrix;
  }

  public int getNumberOfVehicles() {
    return numberOfVehicles;
  }

  public void setNumberOfVehicles(int numberOfVehicles) {
    this.numberOfVehicles = numberOfVehicles;
  }

  public int getNumberOfCustomers() {
    return numberOfCustomers;
  }

  public void setNumberOfCustomers(int numberOfCustomers) {
    this.numberOfCustomers = numberOfCustomers;
  }

  public int[][] getDistanceMatrix() {
    return distanceMatrix;
  }

  public void setDistanceMatrix(int[][] distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
  }
}
