package load;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LoaderXML {
    private String folderPath = "C:/temp";
    private String fileName = "repository.xml";

    private DefaultTreeModel dtModel = null;

    public void load() {
        StringBuilder filePath = new StringBuilder(folderPath);
        filePath.append("/").append(fileName);

        Node rootXML = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(String.valueOf(filePath));
            rootXML = (Node) doc.getDocumentElement();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Can't parse file", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (rootXML != null) {
            DefaultMutableTreeNode treeNode = XmlTreeModel.builtTreeNode(rootXML, null);

            dtModel = new DefaultTreeModel(treeNode);
        }
    }

}
