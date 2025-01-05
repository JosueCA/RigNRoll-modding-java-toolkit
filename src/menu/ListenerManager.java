// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

import java.util.HashMap;
import java.util.Vector;
import menu.SelectCb;

public class ListenerManager {
    public static final int INIT_MENU = 103;
    public static final int AFTER_INIT_MENU = 104;
    public static final int EXIT_MENU = 105;
    private static HashMap m_map;

    public static void AddListener(int event, SelectCb cb) {
        Integer i;
        Vector<SelectCb> vec;
        if (m_map == null) {
            m_map = new HashMap();
        }
        if ((vec = (Vector<SelectCb>)m_map.get(i = new Integer(event))) == null) {
            vec = new Vector<SelectCb>();
            m_map.put(i, vec);
        }
        vec.add(cb);
    }

    public static void TriggerEvent(int event) {
        Vector vec;
        if (event == 103 && m_map == null) {
            m_map = new HashMap();
        }
        if (null != m_map && (vec = (Vector)m_map.get(event)) != null) {
            for (int i = 0; i < vec.size(); ++i) {
                SelectCb cb = (SelectCb)vec.get(i);
                cb.OnSelect(event, null);
            }
        }
        if (event == 105) {
            m_map = null;
        }
    }
}
