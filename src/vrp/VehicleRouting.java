package vrp;

import vrp.graph.Graph;

public abstract class VehicleRouting {
  private Graph graph;

  public VehicleRouting(Graph graph) {
    this.graph = graph;
  }

  public abstract int[][] solve();
}
