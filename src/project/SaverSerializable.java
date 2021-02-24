package project;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import utilities.window.DialogMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaverSerializable {
    private ObjectOutputStream writer ;

    public void save(File file) {


        try {
            writer = new ObjectOutputStream(new FileOutputStream(file));
            saveNode(MVCCDManager.instance().getRepository().getNodeProject(), 0);

            // Quittance de fin
            String message = MessagesBuilder.getMessagesProperty ("project.saved",
                    new String[] {MVCCDManager.instance().getProject().getName(), file.getPath() });
            DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(),message);

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
            if (node.getUserObject() instanceof MVCCDElement) {
                MVCCDElement mcdElement= (MVCCDElement) node.getUserObject();
                writer.writeObject(mcdElement);
                if (node.getChildCount() > 0){
                    int levelChild = level + 1 ;
                    for (int i=0 ; i< node.getChildCount(); i++){
                        saveNode((DefaultMutableTreeNode) node.getChildAt(i), levelChild);
                    }
                }
            }
        } catch(Exception e){
            throw (new CodeApplException(e));	// L'erreur est renvoyée
        }
    }



}
