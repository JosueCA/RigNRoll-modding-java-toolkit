package rnrscenario.configurators;

import org.w3c.dom.Node;
import rnrscenario.config.Config;
import scenarioXml.XmlFilter;

public final class SpecialCargoConfig extends Config {
  private static final String MY_NODE = "chase_special_cargo";
  
  private final double[] banditsDelay = new double[3];
  
  private double blowRadius = 8.0D;
  
  private int gameDifficultyLevel = 0;
  
  public SpecialCargoConfig() {
    super("chases", "chase_special_cargo", 4);
    this.banditsDelay[0] = 241.0D;
    this.banditsDelay[1] = 181.0D;
    this.banditsDelay[2] = 121.0D;
  }
  
  protected void loadFromNode(Node source) {
    if (null != source) {
      XmlFilter rootChildrenFilter = new XmlFilter(source.getChildNodes());
      Node timingsNode = rootChildrenFilter.nodeNameNext("timings");
      if (null != timingsNode)
        Config.fillTripleValues((new XmlFilter(timingsNode.getChildNodes())).nodeNameNext("call_from_bandits_delay"), this.banditsDelay); 
      rootChildrenFilter.goOnStart();
      Node distancesNode = rootChildrenFilter.nodeNameNext("distances");
      if (null != distancesNode) {
        Node distanceNode = (new XmlFilter(distancesNode.getChildNodes())).nodeNameNext("player_bounding_sphere_radius");
        if (null != distanceNode && null != distanceNode.getTextContent())
          try {
            this.blowRadius = Double.parseDouble(distanceNode.getTextContent());
          } catch (NumberFormatException e) {
            System.err.print("SpecialCargoConfig.loadFromNode: " + e.getMessage());
          }  
      } 
    } 
  }
  
  protected void setGameLevel(int gameLevel) {
    this.gameDifficultyLevel = gameLevel;
  }
  
  public double getBlowRadius() {
    return this.blowRadius;
  }
  
  public double getBanditsDelay() {
    return this.banditsDelay[this.gameDifficultyLevel];
  }
}
