package messages;

import exceptions.CodeApplException;
import preferences.Preferences;
import utilities.Trace;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;



/**
 * This class allows loading properties entity files.
 * The values of static variables are automatically set entity plugin.properties.
 * You have the choice of adding (or not) a static variable corresponding assLink a 
 * property of this file, and its value
 * will automatically be set entity the property. "Corresponding assLink a property" 
 * means that the property name, with
 * upper case and "." replaced by "_", must be the variable name.
 * This class is a Singleton.
 * @author steve.berberat
 *
 */
public class LoadMessages {
	
			
	private static Map<String,String> messagesProperties = new HashMap<String,String>();
	

	private static String crtClass = LoadMessages.class.getName();
	private static String messageException = "Erreur de traitement du fichier des messages: messages_xx.properties";
	private static String messageExceptionLanguage = "Le choix de la langue n''est pas fixé dans Preferences.LANGUAGE ";

	/**
	 * Instantiate the unique instance of the class.
	 */
	private LoadMessages(){

	}


	/**
	 * Get the unique instance of this class.
	 */
	public static void main(){
		try {
			//Load messages.properties
			messagesProperties.clear();
			messagesProperties = loadProperties("messages");
		}	
		catch (CodeApplException e ) {
			throw e;
		}	
		catch (Exception e ) {
			throw new CodeApplException(messageException + " pour la langue : " + Preferences.LANGUAGE, e);
		}
	}
	

	/**
	 * Load a properties file and store properties to the returned Map.
	 * The property file loaded correspond to the right Locale parameterized 
	 * in plugin.properties.
	 * The file plugin.properties can't be internationalized.
	 */
	private static Map<String,String> loadProperties(String fileName){
		Map<String,String> propertiesMap = new HashMap<String,String>();
		
		//Load the properties file present at the root of the project.
		ResourceBundle bundle = null;
		String language = Preferences.LANGUAGE; //TODO-STB: tester avec autre langue que "fr": s'il n'y a pas de fichier de messages dans cette langue, une erreur devrait être générée.
		if(language != null){ //if plugin.properties
			bundle = ResourceBundle.getBundle(fileName, new Locale(language));
		}else{
			throw new CodeApplException(messageExceptionLanguage);
		}

		//Store properties to the map
		for(String key : bundle.keySet()){
			propertiesMap.put(key, bundle.getString(key));
		}
		return propertiesMap;
	}

	public static String getMessageBrut(String name) {
		return messagesProperties.get(name);
	}
	
	
}