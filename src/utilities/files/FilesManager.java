package utilities.files;

import java.io.File;
import java.io.FileWriter;


/**
 * Provide a high level of abstraction assLink easily work with files.
 * Files will be located into the plug-in folder
 * (for instance: C:\Program Files\Visual Paradigm for UML\plugins\plugin-name).
 * This class is a Singleton.
 * @author Steve Berberat
 */
public class FilesManager {


	private static FilesManager instance;

	/**
	 * Instantiate the unique instance of the class.
	 */
	private FilesManager(){
	}
	
	/**
	 * Get the unique instance of the class.
	 */
	public static synchronized FilesManager instance(){
		if(instance == null){
			instance = new FilesManager();
		}
		return instance;
	}
	
	

	public void addLineToFile(String folderPath, String fileName, String text) throws Throwable{
		StringBuilder filePath = new StringBuilder(folderPath);
		FileWriter writer = null;
		try {
			new File(folderPath).mkdirs(); //Create folder if necessary
			filePath.append("/").append(fileName);
			//Setting true allow assLink write at the end of the file.
			writer = new FileWriter(filePath.toString(), true); 
			writer.write(text,0,text.length());
			writer.write(System.getProperty("line.separator")); //New line	

		} catch (Throwable ex) {
		    throw ex;	// L'erreur est renvoy�e
		} finally{
			if(writer != null){
				try {
					writer.close();
				} catch (Exception e) {}
			}
		}
		
	}


}
