package project;

import exceptions.CodeApplException;
import main.MVCCDElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import project.Project;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class LoaderSerializable {
   
    private ObjectInputStream reader ;
    private Project project = null;

    public Project load(File file)  {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (fileInputStream != null) {
                reader = new ObjectInputStream(fileInputStream);
                boolean eof = false;
                while (!eof) {
                    try {
                        MVCCDElement mvccdElement = (MVCCDElement) reader.readObject();
                        if (mvccdElement instanceof Project) {
                            project = (Project) mvccdElement;
                        }
                    } catch (EOFException e) {
                        eof = true;
                    }
                }
            }
        } catch (Exception e){
            throw (new CodeApplException(e));	// L'erreur est renvoyée
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
        return project;
    }

}
