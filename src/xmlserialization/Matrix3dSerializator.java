/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import rnrcore.matrixJ;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.Vector3dSerializator;
import xmlutils.Node;

public class Matrix3dSerializator {
    public static String getNodeName() {
        return "matrix3d";
    }

    public static void serializeXML(matrixJ value, PrintStream stream) {
        Helper.openNode(stream, "matrix3d");
        if (null == value.v0) {
            Log.error("ERRORR. Matrix3dSerializator. serializeXML null==value.v0.");
        } else {
            Helper.openNode(stream, "v0");
            Vector3dSerializator.serializeXML(value.v0, stream);
            Helper.closeNode(stream, "v0");
        }
        if (null == value.v1) {
            Log.error("ERRORR. Matrix3dSerializator. serializeXML null==value.v1.");
        } else {
            Helper.openNode(stream, "v1");
            Vector3dSerializator.serializeXML(value.v1, stream);
            Helper.closeNode(stream, "v1");
        }
        if (null == value.v2) {
            Log.error("ERRORR. Matrix3dSerializator. serializeXML null==value.v2.");
        } else {
            Helper.openNode(stream, "v2");
            Vector3dSerializator.serializeXML(value.v2, stream);
            Helper.closeNode(stream, "v2");
        }
        Helper.closeNode(stream, "matrix3d");
    }

    public static matrixJ deserializeXML(Node node) {
        Node vectorNode;
        Node v0Node = node.getNamedChild("v0");
        Node v1Node = node.getNamedChild("v1");
        Node v2Node = node.getNamedChild("v2");
        matrixJ M = new matrixJ();
        if (null == v0Node) {
            Log.error("ERRORR. Matrix3dSerializator. deserializeXML. No node with name v0");
        } else {
            vectorNode = v0Node.getNamedChild(Vector3dSerializator.getNodeName());
            if (null != vectorNode) {
                M.v0 = Vector3dSerializator.deserializeXML(vectorNode);
            } else {
                Log.error("ERRORR. Matrix3dSerializator. deserializeXML. Node with name v0 has no child node with name " + Vector3dSerializator.getNodeName());
            }
        }
        if (null == v1Node) {
            Log.error("ERRORR. Matrix3dSerializator. deserializeXML. No node with name v1");
        } else {
            vectorNode = v1Node.getNamedChild(Vector3dSerializator.getNodeName());
            if (null != vectorNode) {
                M.v1 = Vector3dSerializator.deserializeXML(vectorNode);
            } else {
                Log.error("ERRORR. Matrix3dSerializator. deserializeXML. Node with name v1 has no child node with name " + Vector3dSerializator.getNodeName());
            }
        }
        if (null == v2Node) {
            Log.error("ERRORR. Matrix3dSerializator. deserializeXML. No node with name v2");
        } else {
            vectorNode = v2Node.getNamedChild(Vector3dSerializator.getNodeName());
            if (null != vectorNode) {
                M.v2 = Vector3dSerializator.deserializeXML(vectorNode);
            } else {
                Log.error("ERRORR. Matrix3dSerializator. deserializeXML. Node with name v2 has no child node with name " + Vector3dSerializator.getNodeName());
            }
        }
        return M;
    }
}

