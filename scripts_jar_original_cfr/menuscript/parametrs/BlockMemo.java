/*
 * Decompiled with CFR 0.151.
 */
package menuscript.parametrs;

import menuscript.parametrs.ParametrsBlock;

public class BlockMemo
extends ParametrsBlock {
    private ParametrsBlock memo = null;

    public BlockMemo(ParametrsBlock memo) {
        this.memo = memo.makeMemo();
    }

    public void recordChanges(ParametrsBlock tampl) {
        tampl.recordMemoChanges(this.memo);
    }

    public void restore(ParametrsBlock tampl) {
        tampl.restoreMemo(this.memo);
    }

    public void restoreChanges(ParametrsBlock tampl) {
        tampl.restoreMemoChanges(this.memo);
    }
}

