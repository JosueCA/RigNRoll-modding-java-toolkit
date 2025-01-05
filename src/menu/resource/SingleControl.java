/*
 * Decompiled with CFR 0.151.
 */
package menu.resource;

import menu.resource.IMenuResources;

class SingleControl
implements IMenuResources {
    private String filename;
    private String groupname;
    private String controlname;

    public SingleControl(String filename, String groupname, String controlname) {
        this.filename = filename;
        this.groupname = groupname;
        this.controlname = controlname;
    }

    public long[] loadResource(long menuDescriptor) {
        return null;
    }
}

