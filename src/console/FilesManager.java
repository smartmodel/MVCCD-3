package console;

import utilities.Trace;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class FilesManager {

    private static FilesManager instance;

    /**
     * Instantiate the unique instance of the class.
     */
    private FilesManager(){
    }

    /**
     * Get the unique instance of the class.
     * @return
     */
    public static synchronized FilesManager instance(){
        if(instance == null){
            instance = new FilesManager();
        }
        return instance;
    }



    /**
     * This method will add the text you give in the file.
     * If the folder don't exist, it will be created.
     * If the file don't exist yet, it will be created.
     * @param folderPath must be the absolute folder of the file. It must not contain the file name.
     * @param fileName
     * @param text
     * @throws Throwable
     */

    public void addLineToFile(String folderPath, String fileName, String text) throws Throwable{
        StringBuilder filePath = new StringBuilder(folderPath);
        FileWriter writer = null;
        try {
            new File(folderPath).mkdirs(); //Create folder if necessary
            filePath.append("/").append(fileName);
            //Setting true allow assLink write at the end of the file.
            //TODO-PAS Voir avec Steve
            writer = new FileWriter(filePath.toString(), StandardCharsets.UTF_8, true);
            writer.write(text + System.lineSeparator());
            //writer.write(text);
            //writer.write(text,0,text.length());
            //La ligne ci-dessous provoque une erreur (Intéressant pour les tests)
            //writer.write(System.getProperty(System.lineSeparator())); //New line
        } catch (Throwable ex) {
            throw ex;	// L'erreur est renvoyée
        } finally{
            if(writer != null){
                try {
                    writer.close();
                } catch (Exception e) {}
            }
        }

    }

}
