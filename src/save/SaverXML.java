package save;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDManager;
import preferences.Preferences;
import project.Project;
import repository.RepositoryService;
import utilities.window.DialogMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaverXML {
    private String folderPath = "C:/temp";
    private String fileName = "repository.xml";
    private FileWriter writer ;
    String lineSeparator = System.getProperty("line.separator");
    String tabulator = "\t";

    public void save() {


        try {
            // Crée le répertoire s'il n'existe pas
            new File(folderPath).mkdirs();

            // Crée le fichier en écriture
            StringBuilder filePath = new StringBuilder(folderPath);
            filePath.append("/").append(fileName);
            //filePath.append(System.getProperty("path.separator")).append(fileName);
            writer = new FileWriter(filePath.toString());

            // Entête
            writer.write(Preferences.XML_ENTETE_FICHIER + lineSeparator);

            // Parcours de l'arbre du référentiel
            saveNode(MVCCDManager.instance().getRepository().getRootNode(), 0);

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
            if (node.getUserObject() instanceof MVCCDElement) {
                MVCCDElement mcdElement = (MVCCDElement) node.getUserObject();
                writer.write(tabsLevel(level) + mcdElement.baliseXMLBegin() + lineSeparator);
                if (node.getChildCount() > 0){
                    int levelChild = level + 1 ;
                    for (int i=0 ; i< node.getChildCount(); i++){
                        saveNode((DefaultMutableTreeNode) node.getChildAt(i), levelChild);
                    }
                }
                writer.write(tabsLevel(level) + mcdElement.baliseXMLEnd() + lineSeparator);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Erreur IO");
            throw (new CodeApplException(e));	// L'erreur est renvoyée
        }
    }

    private String tabsLevel(int level) {
        String resultat = "";
        if (level > 0){
            for (int i=1 ; i <= level; i++){
                resultat = resultat + tabulator;
            }
        }
        return resultat;
    }

}
