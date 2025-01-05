/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.articles;

import java.io.PrintStream;
import java.util.ArrayList;
import rnr.bigrace.articles.BigRaceAnnounceArticle;
import rnr.bigrace.articles.BigRaceAnnounceArticleSerialization;
import rnr.bigrace.articles.BigRaceSummaryArticle;
import rnr.bigrace.articles.BigRaceSummaryArticleSerialization;
import rnrscr.smi.Article;
import rnrscr.smi.Newspapers;
import xmlserialization.Helper;
import xmlutils.Node;
import xmlutils.NodeList;

public final class BigRaceArticlesSerialization {
    public static String getNodeName() {
        return "newspaper_bigrace";
    }

    public static void serializeXML(Newspapers newspapers, PrintStream stream) {
        Helper.openNode(stream, BigRaceArticlesSerialization.getNodeName());
        ArrayList<Article> articles = newspapers.getArticles();
        for (Article article : articles) {
            if (!(article instanceof BigRaceSummaryArticle)) continue;
            BigRaceSummaryArticle summaryArticle = (BigRaceSummaryArticle)article;
            BigRaceSummaryArticleSerialization.serializeXML(summaryArticle, stream);
        }
        for (Article article : articles) {
            if (!(article instanceof BigRaceAnnounceArticle)) continue;
            BigRaceAnnounceArticle announceArticle = (BigRaceAnnounceArticle)article;
            BigRaceAnnounceArticleSerialization.serializeXML(announceArticle, stream);
        }
        Helper.closeNode(stream, BigRaceArticlesSerialization.getNodeName());
    }

    public static void deserializeXML(Node node) {
        NodeList articlesNode = node.getNamedChildren(BigRaceArticlesSerialization.getNodeName());
        if (null != articlesNode && !articlesNode.isEmpty()) {
            for (Node articleNode : articlesNode) {
                NodeList summaryArticles = articleNode.getNamedChildren(BigRaceSummaryArticleSerialization.getNodeName());
                if (null == summaryArticles || summaryArticles.isEmpty()) continue;
                for (Node summaryArticle : summaryArticles) {
                    BigRaceSummaryArticleSerialization.deserializeXML(summaryArticle);
                }
            }
            for (Node articleNode : articlesNode) {
                NodeList announceArticles = articleNode.getNamedChildren(BigRaceAnnounceArticleSerialization.getNodeName());
                if (null == announceArticles || announceArticles.isEmpty()) continue;
                for (Node announceArticle : announceArticles) {
                    BigRaceAnnounceArticleSerialization.deserializeXML(announceArticle);
                }
            }
        }
    }
}

