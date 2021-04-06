package project;

import exceptions.TransformMCDException;
import main.MVCCDElement;
import main.MVCCDManager;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaverSerializable {
    private ObjectOutputStream writer ;

    public void save(File file) {


        try {
            writer = new ObjectOutputStream(new FileOutputStream(file));
            saveNode(MVCCDManager.instance().getRepository().getNodeProject(), 0);

        } catch (Exception  ex) {
                throw(new TransformMCDException(ex));	// L'erreur est renvoyée
        } finally{
                if(writer != null){
                    try {
                        writer.close();
                    } catch (Exception e) {}
                }
        }
    }

    /**
     * Parcourt récursivement le noeud de l'arbre passé en paramètre. À chaque noeud, l'objet attaché(par exemple, une
     * entité MCD) est sérialisé dans le fichier définit dans le writer de cette classe. Le noeud en lui-même est aussi
     * sauvegardé.
     * Ainsi, tous les noeuds existants dans l'arbre donné sont sauvegardé dans le fichier sérialisé.
     * @param node Noeud racine à partir duquel la sérialisation est faite.
     * @param level Indique à quel niveau le noeud à partir duquel faire le traitement de sauvegarde se trouve par
     *              rapport à l'arbre complet. Si l'arbre complet est envoyé pour sauvegarde (par exemple le noeud qui
     *              représente le projet), alors le level doit être définit à 0.
     */
    private void saveNode(DefaultMutableTreeNode node, int level) {
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
            throw (new TransformMCDException(e));	// L'erreur est renvoyée
        }
    }



}
