/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Node;
import rnr.bigrace.articles.BigRaceArticlesSerialization;
import rnrcore.XmlSerializable;
import rnrscr.smi.Article;
import rnrscr.smi.Newspapers;
import rnrscr.smi.TenderInformation;
import xmlserialization.Helper;
import xmlserialization.TenderArticleSerialization;
import xmlutils.NodeList;

public class NewspaperSerialization
implements XmlSerializable {
    private static NewspaperSerialization instance = new NewspaperSerialization();

    public static NewspaperSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return NewspaperSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        NewspaperSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        NewspaperSerialization.serializeXML(Newspapers.getInstance(), stream);
    }

    public static String getNodeName() {
        return "newspaper";
    }

    public static void serializeXML(Newspapers value, PrintStream stream) {
        Helper.openNode(stream, NewspaperSerialization.getNodeName());
        ArrayList<Article> articles = value.getArticles();
        for (Article singleArticle : articles) {
            if (!(singleArticle instanceof TenderInformation)) continue;
            TenderInformation tender = (TenderInformation)singleArticle;
            TenderArticleSerialization.serializeXML(tender, stream);
        }
        BigRaceArticlesSerialization.serializeXML(value, stream);
        Helper.closeNode(stream, NewspaperSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList tenders = node.getNamedChildren(TenderArticleSerialization.getNodeName());
        if (null != tenders && !tenders.isEmpty()) {
            for (xmlutils.Node singleTender : tenders) {
                TenderArticleSerialization.deserializeXML(singleTender);
            }
        }
        BigRaceArticlesSerialization.deserializeXML(node);
    }
}

