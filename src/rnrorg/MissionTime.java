package rnrorg;

// RICK: before modifying this file
// take a look at the 'eco.pkqt'
public class MissionTime {
  private double time_coef = 1.0D;
  
  public MissionTime(double coef) {
    this.time_coef = coef;
  }
  
  public MissionTime() {}
  
  public void makeUrgent() {
    this.time_coef = 0.7D;
  }
  
  public void setCoef(double time) {
    this.time_coef = time;
  }
  
  public double getCoef() {
    return this.time_coef;
  }
}
