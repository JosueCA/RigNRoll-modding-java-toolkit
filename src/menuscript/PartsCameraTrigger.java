/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Item;
import menu.SelectCb;
import menu.TableNode;
import menu.TruckView;

public class PartsCameraTrigger
implements SelectCb {
    SelectCb m_nextcb;
    TruckView m_truckview;

    public PartsCameraTrigger(SelectCb nextcb, TruckView truckview) {
        this.m_truckview = truckview;
        this.m_nextcb = nextcb;
    }

    public void OnSelect(int state, Object sender) {
        switch (state) {
            case 100: {
                break;
            }
            case 101: {
                break;
            }
            case 102: {
                TableNode node = (TableNode)sender;
                Item item = (Item)node.item;
                this.m_truckview.SetCameraSwitch(item.id);
            }
        }
        if (this.m_nextcb != null) {
            this.m_nextcb.OnSelect(state, sender);
        }
    }
}

