/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import menu.Cmenu_TTI;
import menu.ITableNodeVisitor;
import menu.menues;

public class Helper {
    private static Stack<Integer> animation_ids = null;
    private static int dimention_min = 0;
    private static int dimention_max = 2;
    private static int animation_hide_show_controls = -1;
    private static final ArrayList<ControlShow> toShowHide = new ArrayList();
    private static final String METH_ANIMATE_HIDESHOW = "onHideShow";

    private static int numVisibleNodes_children(Cmenu_TTI menu) {
        if (null == menu || !menu.toshow) {
            return 0;
        }
        int ch_show = 0;
        if (menu.showCH) {
            Iterator iter = menu.children.iterator();
            while (iter.hasNext()) {
                ch_show += Helper.numVisibleNodes_children((Cmenu_TTI)iter.next());
            }
        }
        return 1 + ch_show;
    }

    public static int numVisibleNodes(Cmenu_TTI menu) {
        if (null == menu || !menu.toshow) {
            return 0;
        }
        int ch_show = 0;
        Iterator iter = menu.children.iterator();
        while (iter.hasNext()) {
            ch_show += Helper.numVisibleNodes_children((Cmenu_TTI)iter.next());
        }
        return ch_show;
    }

    private static boolean setNumVisibleNodeOnTop_children(Cmenu_TTI menu, int depth) {
        if (null == menu || !menu.toshow) {
            return false;
        }
        if (depth == 0) {
            menu.ontop = true;
            return true;
        }
        menu.ontop = false;
        if (menu.showCH) {
            Iterator iter = menu.children.iterator();
            while (iter.hasNext()) {
                if (!Helper.setNumVisibleNodeOnTop_children((Cmenu_TTI)iter.next(), --depth)) continue;
                return true;
            }
        }
        return false;
    }

    public static void setNumVisibleNodeOnTop(Cmenu_TTI menu, int depth) {
        if (null == menu || !menu.toshow) {
            return;
        }
        Iterator iter = menu.children.iterator();
        while (iter.hasNext()) {
            if (!Helper.setNumVisibleNodeOnTop_children((Cmenu_TTI)iter.next(), --depth)) continue;
            return;
        }
    }

    public static boolean tell0Line_children(Cmenu_TTI menu, Integer current_depth) {
        if (null == menu || !menu.toshow) {
            return false;
        }
        if (menu.ontop) {
            return true;
        }
        if (menu.showCH) {
            Iterator iter = menu.children.iterator();
            while (iter.hasNext()) {
                current_depth = new Integer(current_depth + 1);
                if (!Helper.tell0Line_children((Cmenu_TTI)iter.next(), current_depth)) continue;
                return true;
            }
        }
        return false;
    }

    public static int tell0Line(Cmenu_TTI menu) {
        if (null == menu || !menu.toshow) {
            return 0;
        }
        Integer i = new Integer(0);
        Iterator iter = menu.children.iterator();
        while (iter.hasNext()) {
            if (Helper.tell0Line_children((Cmenu_TTI)iter.next(), i)) {
                return i;
            }
            i = new Integer(i + 1);
        }
        return 0;
    }

    public static boolean tellItemLine_children(Cmenu_TTI menu, Cmenu_TTI item, Integer current_depth) {
        if (null == menu || !menu.toshow) {
            return false;
        }
        if (menu.equals(item)) {
            return true;
        }
        if (menu.showCH) {
            Iterator iter = menu.children.iterator();
            while (iter.hasNext()) {
                current_depth = new Integer(current_depth + 1);
                if (!Helper.tellItemLine_children((Cmenu_TTI)iter.next(), item, current_depth)) continue;
                return true;
            }
        }
        return false;
    }

    public static int tellItemLine(Cmenu_TTI root, Cmenu_TTI item) {
        Integer i = new Integer(0);
        Iterator iter = root.children.iterator();
        while (iter.hasNext()) {
            if (Helper.tellItemLine_children((Cmenu_TTI)iter.next(), item, i)) {
                return i;
            }
            i = new Integer(i + 1);
        }
        return i;
    }

    public static int tellLine(Cmenu_TTI root, Cmenu_TTI item) {
        return Helper.tellItemLine(root, item) - Helper.tell0Line(root);
    }

    public static Cmenu_TTI findInTree(Cmenu_TTI root, Object obj) {
        if (root == null || obj == null) {
            return null;
        }
        if (root.item != null && root.item.equals(obj)) {
            return root;
        }
        for (Cmenu_TTI next_one : root.children) {
            Cmenu_TTI res = Helper.findInTree(next_one, obj);
            if (res == null) continue;
            return res;
        }
        return null;
    }

    public static void traverseTree(Cmenu_TTI root, ITableNodeVisitor visitor) {
        if (root == null || visitor == null) {
            return;
        }
        visitor.visitNode(root);
        Iterator iter = root.children.iterator();
        while (iter.hasNext()) {
            Helper.traverseTree((Cmenu_TTI)iter.next(), visitor);
        }
    }

    private static boolean traverseTree_if(Cmenu_TTI root, Cmenu_TTI begin, Cmenu_TTI end, ITableNodeVisitor visitor, BoolProxy started) {
        if (!started.get() && root.equals(begin)) {
            started.set(true);
        }
        if (started.get()) {
            visitor.visitNode(root);
        }
        if (root.equals(end)) {
            return false;
        }
        Iterator iter = root.children.iterator();
        while (iter.hasNext()) {
            if (Helper.traverseTree_if((Cmenu_TTI)iter.next(), begin, end, visitor, started)) continue;
            return false;
        }
        return true;
    }

    public static void traverseTree(Cmenu_TTI root, Cmenu_TTI begin, Cmenu_TTI end, ITableNodeVisitor visitor) {
        if (root == null || begin == null || visitor == null) {
            return;
        }
        Helper.traverseTree_if(root, begin, end, visitor, new BoolProxy(false));
    }

    public static String convertMoney(int summ) {
        int count_spasec;
        if (summ == 0) {
            return "0";
        }
        if (summ < 0) {
            summ *= -1;
        }
        String res = "";
        if (count_spasec == 0) {
            res = "" + summ;
            return res;
        }
        boolean first_time = true;
        for (count_spasec = (int)Math.floor(Math.floor(Math.log10(summ)) / 3.0); count_spasec >= 0; --count_spasec) {
            double rem = (double)summ / 1000.0 - Math.floor((double)summ / 1000.0);
            long irem = Math.round(rem *= 1000.0);
            if (irem == 0L) {
                irem = 1000L;
            }
            String medres = "";
            if (irem >= 1000L) {
                medres = count_spasec == 0 ? (first_time ? "0" : res) : (first_time ? "000" : "000 " + res);
            } else if (irem >= 100L) {
                medres = first_time ? "" + irem : irem + " " + res;
            } else if (irem < 100L && irem >= 10L) {
                medres = count_spasec == 0 ? (first_time ? "" + irem : irem + " " + res) : (first_time ? "0" + irem : "0" + irem + " " + res);
            } else if (irem < 10L) {
                medres = count_spasec == 0 ? (first_time ? "" + irem : irem + " " + res) : (first_time ? "00" + irem : "00" + irem + " " + res);
            }
            res = medres;
            first_time = false;
            summ = (int)Math.floor((double)summ / 1000.0);
        }
        return res;
    }

    public static final int getUniqueAnimationID() {
        block3: {
            block2: {
                if (animation_ids != null) break block2;
                animation_ids = new Stack();
                for (int i = 1 << dimention_min; i < 1 << dimention_max; ++i) {
                    animation_ids.push(i);
                }
                break block3;
            }
            if (!animation_ids.isEmpty()) break block3;
            dimention_min = dimention_max;
            for (int i = 1 << dimention_min; i < 1 << (dimention_max += 2); ++i) {
                animation_ids.push(i);
            }
        }
        return animation_ids.pop();
    }

    public static final void returnUniqueAnimationID(int value) {
        Iterator i$ = animation_ids.iterator();
        while (i$.hasNext()) {
            int i = (Integer)i$.next();
            if (i != value) continue;
            return;
        }
        animation_ids.push(value);
    }

    public static void setControlShow(long control, boolean show) {
        toShowHide.add(new ControlShow(control, show));
        if (-1 == animation_hide_show_controls) {
            animation_hide_show_controls = Helper.getUniqueAnimationID();
            menues.SetScriptObjectAnimation(0L, animation_hide_show_controls, new Helper(), METH_ANIMATE_HIDESHOW);
        }
    }

    public void onHideShow(long control, double time) {
        for (ControlShow item : toShowHide) {
            menues.SetShowField(item.control, item.show);
        }
        toShowHide.clear();
        menues.StopScriptAnimation(animation_hide_show_controls);
        animation_hide_show_controls = -1;
    }

    static class ControlShow {
        long control;
        boolean show;

        ControlShow(long control, boolean show) {
            this.control = control;
            this.show = show;
        }
    }

    private static class BoolProxy {
        private boolean val;

        BoolProxy(boolean value) {
            this.set(value);
        }

        void set(boolean value) {
            this.val = value;
        }

        boolean get() {
            return this.val;
        }
    }
}

