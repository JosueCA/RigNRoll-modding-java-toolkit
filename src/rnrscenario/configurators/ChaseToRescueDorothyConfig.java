package rnrscenario.configurators;

import org.w3c.dom.Node;
import rnrscenario.config.Config;
import scenarioXml.XmlFilter;

public final class ChaseToRescueDorothyConfig extends Config {
  private static final String MY_NODE = "chase_rescue_dorothy";
  
  private final int[] timeToRescue = new int[3];
  
  private final double[] distanceToShoot = new double[] { 100.0D, 100.0D, 100.0D };
  private final double[] distanceToRescue = new double[] { 300.0D, 300.0D, 300.0D };
  
  
  private final double[] distanceToKill = new double[] { 10.0D, 10.0D, 10.0D };
  
  private int gameDifficultyLevel = 0;
  
  public ChaseToRescueDorothyConfig() {
    super("chases", "chase_rescue_dorothy", 2);
  }
  
  protected void loadFromNode(Node source) {
    if (null == source)
      return; 
    XmlFilter traversal = new XmlFilter(source.getChildNodes());
    Node timingsNode = traversal.nodeNameNext("timings");
    if (null != timingsNode) {
      String[] timings = Config.loadTripleValuesOrNull((new XmlFilter(timingsNode.getChildNodes())).nodeNameNext("time_to_rescue"));
      if (null != timings)
        try {
          assert timings.length == this.timeToRescue.length;
          int[] temporaryTimings = new int[timings.length];
          for (int i = 0; i < timings.length; i++)
            temporaryTimings[i] = Integer.parseInt(timings[i]); 
          System.arraycopy(temporaryTimings, 0, this.timeToRescue, 0, timings.length);
        } catch (NumberFormatException e) {
          System.err.println("illigal data in scenario config file");
        }  
    } 
    Node distancesNode = traversal.goOnStart().nodeNameNext("distances");
    if (null != distancesNode) {
      XmlFilter distancesNodesTraversal = new XmlFilter(distancesNode.getChildNodes());
      Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_shoot"), this.distanceToShoot);
      Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_rescue"), this.distanceToRescue);
      Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_kill"), this.distanceToKill);
    } 
  }
  
  protected void setGameLevel(int gameLevel) {
    reloadData();
    this.gameDifficultyLevel = gameLevel;
  }
  
  public int getTimeToRescue() {
    reloadData();
    return this.timeToRescue[this.gameDifficultyLevel];
  }
  
  public double getDistanceToRescue() {
    reloadData();
    return this.distanceToRescue[this.gameDifficultyLevel];
  }
  
  public double getDistanceToKill() {
    reloadData();
    return this.distanceToKill[this.gameDifficultyLevel];
  }
  
  public double getDistanceToShoot() {
    reloadData();
    return this.distanceToShoot[this.gameDifficultyLevel];
  }
}
