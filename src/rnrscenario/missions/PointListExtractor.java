/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.w3c.dom.Node;
import scenarioXml.XmlFilter;
import scenarioXml.XmlNodeDataProcessor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class PointListExtractor
implements XmlNodeDataProcessor {
    private Set<String> commonPoints = new HashSet<String>();
    private Set<String> finishPoints = new HashSet<String>();

    PointListExtractor(Node root) {
        assert (null != root) : "root must be non-null reference";
        new XmlFilter(root.getChildNodes()).visitAllNodes("point", this, null);
    }

    Collection<String> getFinishPoints() {
        return Collections.unmodifiableSet(this.finishPoints);
    }

    Collection<String> getCommonPoints() {
        return Collections.unmodifiableSet(this.commonPoints);
    }

    Collection<String> getPoints() {
        LinkedList<String> result = new LinkedList<String>(this.commonPoints);
        result.addAll(this.finishPoints);
        return result;
    }

    // @Override
    public void process(Node target, Object param) {
        Node pointName = target.getAttributes().getNamedItem("name");
        if (XmlFilter.textContentExists(pointName)) {
            if (null != target.getAttributes().getNamedItem("finish")) {
                this.finishPoints.add(pointName.getTextContent());
            } else {
                this.commonPoints.add(pointName.getTextContent());
            }
        }
    }
}

