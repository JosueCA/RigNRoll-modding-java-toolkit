/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.IcontaktCB;

public class CBContact
implements IcontaktCB {
    private String modelName;
    private String firstName;
    private String lastName;
    private String nickName;

    CBContact(String modelName, String firstName, String lastName, String nickName) {
        this.modelName = modelName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
    }

    public String gModelname() {
        return this.modelName;
    }

    public String gFirstName() {
        return this.firstName;
    }

    public String gLastName() {
        return this.lastName;
    }

    public String gNickName() {
        return this.nickName;
    }
}

