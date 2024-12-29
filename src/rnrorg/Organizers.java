// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import rnrcore.eng;
import rnrorg.IStoreorgelement;
import rnrorg.Scorgelement;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */

public class Organizers {
    private static Organizers ourInstance = new Organizers();
    private ArrayList<Pair<Pattern, String>> organizerNamePatterns = new ArrayList();
    private HashMap<String, IStoreorgelement> organizerElements = new HashMap();
    private HashMap<String, IStoreorgelement> organizerElementsSpecial = new HashMap();

    public static Organizers getInstance() {
        return ourInstance;
    }

    private Organizers() {
    }

    public static void deinit() {
        ourInstance = new Organizers();
    }

    public IStoreorgelement get(String organizerName) {
        IStoreorgelement result = this.organizerElements.get(organizerName);
        if (null == result) {
            return this.organizerElementsSpecial.get(organizerName);
        }
        return result;
    }

    public void add(String organizerName, IStoreorgelement org) {
        this.organizerElements.put(organizerName, org);
    }

    public void addSpecial(String organizerName, IStoreorgelement org) {
        this.organizerElementsSpecial.put(organizerName, org);
    }

    public IStoreorgelement getPatterned(String organizerName) {
        if (null != organizerName) {
            for (Pair<Pattern, String> pair : this.organizerNamePatterns) {
                if (!pair.getFirst().matcher(organizerName).matches()) continue;
                return this.get(pair.getSecond());
            }
        }
        return null;
    }

    public void add(String name, Pattern namePattern) {
        if (null != name && null != namePattern) {
            this.organizerNamePatterns.add(new Pair<Pattern, String>(namePattern, name));
        }
    }

    public HashMap<String, IStoreorgelement> getOrganizerElementsSpecial() {
        return this.organizerElementsSpecial;
    }

    public void setOrganizerElementsSpecial(HashMap<String, IStoreorgelement> organizerElementsSpecial) {
        this.organizerElementsSpecial = organizerElementsSpecial;
    }

    public IStoreorgelement submitRestoredOrgElement(Scorgelement element) {
        IStoreorgelement result = null;
        result = element.getType().isSpecialType() ? this.organizerElementsSpecial.get(element.getName()) : this.organizerElements.get(element.getName());
        if (null == result) {
            eng.err("ERRORR. Organizers on submitRestoredOrgElement cannot find named element " + element.getName());
            eng.fatal("Save incompatibility.");
            return element;
        }
        if (!(result instanceof Scorgelement)) {
            eng.err("ERRORR. Organizers on submitRestoredOrgElement found named element " + element.getName() + " that is not instance of Scorgelement.");
            eng.fatal("Save incompatibility.");
            return element;
        }
        Scorgelement orgElement = (Scorgelement)result;
        orgElement.submitLoadedOrgNode(element);
        return orgElement;
    }
}
