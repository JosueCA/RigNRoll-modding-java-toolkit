/*
 * Decompiled with CFR 0.151.
 */
package menu.resource;

import java.util.HashMap;
import menu.resource.ControlGroup;
import menu.resource.IMenuResources;
import menu.resource.SingleControl;

public class MenuLoadPack {
    private HashMap<String, IMenuResources> resources = new HashMap();

    public void add(String id, String filename, String groupname) {
        this.resources.put(id, new ControlGroup(filename, groupname));
    }

    public void add(String id, String filename, String groupname, String contolname) {
        this.resources.put(id, new SingleControl(filename, groupname, contolname));
    }

    public IMenuResources getResource(String id) {
        return this.resources.get(id);
    }
}

