package project;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementSerializable;
import main.MVCCDManager;
import preferences.Preferences;
import utilities.window.DialogMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;

public class SaverSerializable {
    private ObjectOutputStream writer ;

    public void save(File file) {


        try {
            writer = new ObjectOutputStream(new FileOutputStream(file));
            saveNode(MVCCDManager.instance().getRepository().getNodeProject(), 0);

            // Quittance de fin
            DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(),"Le projet a été sauvegardé");
        } catch (Exception  ex) {
                throw(new CodeApplException(ex));	// L'erreur est renvoyée
        } finally{
                if(writer != null){
                    try {
                        writer.close();
                    } catch (Exception e) {}
                }
        }
    }

    private void saveNode(DefaultMutableTreeNode node, int level) throws IOException {
        try{
            if (node.getUserObject() instanceof MVCCDElementSerializable) {
                MVCCDElementSerializable  mcdElementSerializable= (MVCCDElementSerializable) node.getUserObject();
                writer.writeObject(mcdElementSerializable);
                System.out.print(mcdElementSerializable.getName() + "   " );
                if (mcdElementSerializable.getParent() != null){
                    System.out.print(mcdElementSerializable.getParent().getName());
                }
                System.out.println("");
                if (node.getChildCount() > 0){
                    int levelChild = level + 1 ;
                    for (int i=0 ; i< node.getChildCount(); i++){
                        saveNode((DefaultMutableTreeNode) node.getChildAt(i), levelChild);
                    }
                }
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Erreur IO");
            throw (new CodeApplException(e));	// L'erreur est renvoyée
        }
    }



}
