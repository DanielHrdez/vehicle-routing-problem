package vrp.graph;

public class ReadGraph {
  public Graph read(String fileName) {
    Graph graph = new Graph();
    try {
      java.io.File file = new java.io.File(fileName);
      java.io.FileReader fileReader = new java.io.FileReader(file);
      java.io.BufferedReader bufferedReader = new java.io.BufferedReader(fileReader);
      String[] tokens = bufferedReader.readLine().split(" ");
      int numberOfCustomers = Integer.parseInt(tokens[1]);
      tokens = bufferedReader.readLine().split(" ");
      int numberOfVehicles = Integer.parseInt(tokens[1]);
      int[][] distanceMatrix = new int[numberOfCustomers][numberOfCustomers];
      for (int i = 0; i < numberOfCustomers; i++) {
        String[] distances = bufferedReader.readLine().split(" ");
        for (int j = 0; j < numberOfCustomers; j++) {
          distanceMatrix[i][j] = Integer.parseInt(distances[j]);
        }
      }
      graph.setDistanceMatrix(distanceMatrix);
      graph.setNumberOfCustomers(numberOfCustomers);
      graph.setNumberOfVehicles(numberOfVehicles);
      bufferedReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return graph;
  }
}
