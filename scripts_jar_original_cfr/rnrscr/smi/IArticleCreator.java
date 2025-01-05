/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import java.io.Serializable;
import rnrscr.IMissionInformation;
import rnrscr.smi.Article;

public interface IArticleCreator
extends Serializable {
    public Article create(String var1, IMissionInformation var2);
}

