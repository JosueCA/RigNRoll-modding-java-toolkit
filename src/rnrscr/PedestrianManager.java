package rnrscr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import menu.JavaEvents;
import players.Crew;
import players.IdentiteNames;
import rickroll.RickRollConfig;
import rickroll.log.RickRollLog;
import rnrcore.SCRperson;

public class PedestrianManager {
  private static final String MODEL_TO_CHOOSE = null;
  
  static class ModelUsage {
    ArrayList<SCRperson> persons = new ArrayList<SCRperson>();
  }
  
  private static PedestrianManager instance = null;
  
  private HashMap<String, ModelUsage> model_usages = new HashMap<String, ModelUsage>();
  
  // With value of 10, a lot more than 10 are created
  // Put a low value???
  private int population = 0;
  
  private int nom_models = 0;
  
  public static PedestrianManager getInstance() {
    if (instance == null)
      instance = new PedestrianManager(); 
    
    if(RickRollConfig.ENABLE_PEDESTRIANS) {
    	instance.population = 10;
    }
    return instance;
  }
  
  public static void deinit() {
    instance = null;
  }
  
  private void renewModelNames() {
    ArrayList<String> names = Crew.getInstance().getPoolNames();
    Iterator<String> iter = names.iterator();
    while (iter.hasNext()) {
      String name = iter.next();
      IdentiteNames info = new IdentiteNames(name);
      JavaEvents.SendEvent(57, 1, info);
      if (null != MODEL_TO_CHOOSE && MODEL_TO_CHOOSE.compareTo(info.modelName) != 0)
        continue; 
      if (!this.model_usages.containsKey(info.modelName))
        this.model_usages.put(info.modelName, new ModelUsage()); 
    } 
  }
  
  private String getModelName() {
    Set<Map.Entry<String, ModelUsage>> models = this.model_usages.entrySet();
    if (models.isEmpty())
      return null; 
    Iterator<Map.Entry<String, ModelUsage>> iter = models.iterator();
    Map.Entry<String, ModelUsage> lowest = null;
    int num_lowest = 10000000;
    while (iter.hasNext()) {
      Map.Entry<String, ModelUsage> entry = iter.next();
      ModelUsage item = entry.getValue();
      if (lowest == null || item.persons.size() < num_lowest) {
        lowest = entry;
        num_lowest = item.persons.size();
        if (num_lowest == 0)
          break; 
      } 
    } 
    if (lowest == null)
      return null; 
    return lowest.getKey();
  }
  
  private void addmodel(String model_name, SCRperson person) {
    ModelUsage usage = null;
    if (this.model_usages.containsKey(model_name)) {
      usage = this.model_usages.get(model_name);
    } else {
      usage = new ModelUsage();
      this.model_usages.put(model_name, usage);
    } 
    usage.persons.add(person);
    this.nom_models++;
  }
  
  private boolean createPedestrian() {
    String model_name = getModelName();
    if (null == model_name)
      return false; 
    SCRperson person = SFpedestrians.createPedestrian(model_name);
    addmodel(model_name, person);
    return true;
  }
  
  private void removePedestrian(int num_to_remove) {
    Collection<ModelUsage> models = this.model_usages.values();
    if (models.isEmpty())
      return; 
    int removed = 0;
    while (num_to_remove != removed) {
      Iterator<ModelUsage> iter = models.iterator();
      ModelUsage biggest = null;
      int num_biggest = 0;
      while (iter.hasNext()) {
        ModelUsage item = iter.next();
        if (biggest == null || item.persons.size() > num_biggest) {
          biggest = item;
          num_biggest = item.persons.size();
        } 
      } 
      if (biggest == null || biggest.persons.isEmpty())
        break; 
      SCRperson person_to_remove = biggest.persons.remove(0);
      person_to_remove.delete();
      removed++;
      this.nom_models--;
    } 
  }
  
  public void removeNamedModel(String name) {
    ModelUsage models = this.model_usages.get(name);
    if (models.persons.isEmpty())
      return; 
    for (SCRperson pers : models.persons) {
      pers.delete();
      this.nom_models--;
    } 
    models.persons.clear();
  }
  
  public void setPopulation(int num) {
	  if(RickRollConfig.ENABLE_PEDESTRIANS) {
		  this.population = num;
	  } else {
		  this.population = 0;
	  }
  }
  
  public void process() {
    renewModelNames();
    if(RickRollConfig.ENABLE_PEDESTRIANS) {
	    if (this.population > this.nom_models) {
	      while (this.population > this.nom_models) {
	    	  createPedestrian();
	      }
	    } else if (this.population < this.nom_models) {
	      removePedestrian(this.nom_models - this.population);
	    } 
    }
  }
}
