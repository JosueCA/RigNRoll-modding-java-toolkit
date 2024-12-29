package rnrscenario.configurators;

import java.util.logging.Level;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;
import rnrloggers.ScriptsLogger;
import rnrscenario.config.Config;
import xmlutils.XmlUtils;

public final class ChaseCochConfig extends Config {
  private static final String MY_NODE = "chase_cochraine";
  
  private int gameDifficultyLevel;
  
  private static final double DEFAULT_CATCH_TIME = 180.0D;
  
  private static final int DEFAULT_SHOOTS_COUNT = 10;
  
  private static final double DEFAULT_DISTANCE = 200.0D;
  
  private double[] secondsToCatchCoch = new double[3];
  
  private int[] shootsToKillCoch = new int[3];
  
  private double[] distanceToHitCoch = new double[3];
  
  private static final XPathExpression[] secondsToCatchCochQuery = new XPathExpression[3];
  
  private static final XPathExpression[] shootsToKillCochQuery = new XPathExpression[3];
  
  private static final XPathExpression[] distanceToHitCochQuery = new XPathExpression[3];
  
  static {
    secondsToCatchCochQuery[0] = XmlUtils.makeXpath("./timings/time_to_catch/@game_level_easy");
    secondsToCatchCochQuery[1] = XmlUtils.makeXpath("./timings/time_to_catch/@game_level_medium");
    secondsToCatchCochQuery[2] = XmlUtils.makeXpath("./timings/time_to_catch/@game_level_hard");
    shootsToKillCochQuery[0] = XmlUtils.makeXpath("./counts/shoot_to_kill_coch/@game_level_easy");
    shootsToKillCochQuery[1] = XmlUtils.makeXpath("./counts/shoot_to_kill_coch/@game_level_medium");
    shootsToKillCochQuery[2] = XmlUtils.makeXpath("./counts/shoot_to_kill_coch/@game_level_hard");
    distanceToHitCochQuery[0] = XmlUtils.makeXpath("./distances/distance_to_hit_coch/@game_level_easy");
    distanceToHitCochQuery[1] = XmlUtils.makeXpath("./distances/distance_to_hit_coch/@game_level_medium");
    distanceToHitCochQuery[2] = XmlUtils.makeXpath("./distances/distance_to_hit_coch/@game_level_hard");
  }
  
  public ChaseCochConfig() {
    super("chases", "chase_cochraine", 1);
    for (int i = 0; i < 3; i++) {
      this.secondsToCatchCoch[i] = 180.0D;
      this.shootsToKillCoch[i] = 10;
      this.distanceToHitCoch[i] = 200.0D;
    } 
  }
  
  public double getTimeToCatchCoch() {
    reloadData();
    return this.secondsToCatchCoch[this.gameDifficultyLevel];
  }
  
  public int getShootsToKillCoch() {
    reloadData();
    return this.shootsToKillCoch[this.gameDifficultyLevel];
  }
  
  public double getDistanceToHitCoch() {
    reloadData();
    return this.distanceToHitCoch[this.gameDifficultyLevel];
  }
  
  protected void loadFromNode(Node source) {
    if (null != source)
      try {
        for (int i = 0; i < 3; i++) {
          this.secondsToCatchCoch[i] = parseDouble((String)secondsToCatchCochQuery[i].evaluate(source, XPathConstants.STRING), 180.0D);
          this.distanceToHitCoch[i] = parseDouble((String)distanceToHitCochQuery[i].evaluate(source, XPathConstants.STRING), 200.0D);
          this.shootsToKillCoch[i] = parseInteger((String)shootsToKillCochQuery[i].evaluate(source, XPathConstants.STRING), 10);
        } 
      } catch (XPathExpressionException e) {
        ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading coch chase timings config: " + e.getMessage());
      }  
  }
  
  protected void setGameLevel(int gameLevel) {
    assert 0 <= gameLevel && this.secondsToCatchCoch.length > gameLevel;
    this.gameDifficultyLevel = gameLevel;
  }
}
