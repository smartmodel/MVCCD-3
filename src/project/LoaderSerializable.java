package project;

import exceptions.CodeApplException;
import main.MVCCDElement;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

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
            //TODO-0 Traiter correctement l'erreur ... Attendre la sauvegarde XML
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
