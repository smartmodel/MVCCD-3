package load;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import preferences.Preferences;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class XmlTreeModel  {

    /*
    public static  DefaultMutableTreeNode builtTreeNode(Node root) {
        DefaultMutableTreeNode dmtNode;
        dmtNode = new DefaultMutableTreeNode(root.getNodeName());
        System.out.println(root.getNodeName());
        NodeList nodeList = root.getChildNodes();
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);

            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasChildNodes()) {
                    dmtNode.add(builtTreeNode(tempNode));
                }
            }
        }
        return dmtNode;
    }

     */

    public static  DefaultMutableTreeNode builtTreeNode(Node root, MVCCDElement ancestor) {
        DefaultMutableTreeNode dmtNode = null;

        //dmtNode = new DefaultMutableTreeNode(root.getNodeName());
        MVCCDElement mvccdElement = MVCCDElementFactory.instance().createMVCCDElementFromXML(root.getNodeName(), ancestor);
        if (mvccdElement != null) {
            builtAttributes (root,mvccdElement);
            dmtNode = new DefaultMutableTreeNode(mvccdElement);
            NodeList nodeList = root.getChildNodes();
            for (int count = 0; count < nodeList.getLength(); count++) {
                Node childNode = nodeList.item(count);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (childNode.hasChildNodes()) {

                        //if (MVCCDElementFactory.instance().createMVCCDElementFromXML(childNode.getNodeName(), mvccdElement) != null) {
                        DefaultMutableTreeNode dmtNoteChild =  builtTreeNode(childNode, mvccdElement);
                        if (dmtNoteChild != null) {
                            dmtNode.add(dmtNoteChild);
                        //}
                        }
                    }
                }
            }
        }
        return dmtNode;
    }

    private static void builtAttributes(Node node, MVCCDElement mvccdElement){
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0 ; i < attributes.getLength(); i++){
            Node nodeAttribute = attributes.item(i);
            if (nodeAttribute.getNodeName().equals(Preferences.XML_ATTRIBUTE_NAME)) {
                mvccdElement.setName(nodeAttribute.getNodeValue());
            } else if (nodeAttribute.getNodeName().equals(Preferences.XML_ATTRIBUTE_SHORTNAME)) {
                if (mvccdElement instanceof MCDEntity) {
                    MCDEntity mcdEntity = (MCDEntity) mvccdElement;
                    mcdEntity.setShortName(nodeAttribute.getNodeValue());
                }
            } else {
                String message = MessagesBuilder.getMessagesProperty("load.xml.attribute.unknow", new String[] {
                        mvccdElement.getClass().getName(), mvccdElement.getName(), nodeAttribute.getNodeName() });
                MVCCDManager.instance().getConsole().printMessage(message);
            }
        }
    }
}