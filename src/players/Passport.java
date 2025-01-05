/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.io.Serializable;
import menu.JavaEvents;
import players.IdentiteNames;
import rnrcore.eng;

public class Passport
implements Serializable {
    static final long serialVersionUID = 1L;
    protected String firstName;
    protected String lastName;
    protected String nickName;
    protected String modelName;
    private String m_identitie;

    public Passport(String identitie) {
        if (null == identitie) {
            return;
        }
        this.m_identitie = identitie;
        IdentiteNames info = new IdentiteNames(identitie);
        if (!eng.noNative) {
            JavaEvents.SendEvent(57, 1, info);
        }
        this.firstName = info.firstName;
        this.lastName = info.lastName;
        this.nickName = info.nickName;
        this.modelName = info.modelName;
    }

    public String getM_identitie() {
        return this.m_identitie;
    }

    public void setM_identitie(String m_identitie) {
        this.m_identitie = m_identitie;
    }
}

