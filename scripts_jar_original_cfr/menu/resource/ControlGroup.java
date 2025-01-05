/*
 * Decompiled with CFR 0.151.
 */
package menu.resource;

import menu.resource.IMenuResources;

class ControlGroup
implements IMenuResources {
    private String filename;
    private String groupname;

    public ControlGroup(String filename, String groupname) {
        this.filename = filename;
        this.groupname = groupname;
    }

    public long[] loadResource(long menuDescriptor) {
        return null;
    }
}

