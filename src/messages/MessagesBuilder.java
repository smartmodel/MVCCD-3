package messages;

import exceptions.CodeApplException;
import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;



//import ch.smartmodel.mvccd2.exceptions.CodeApplException;




/**
 * This class allows loading properties entity files.
 * The values of static variables are automatically set entity plugin.properties.
 * You have the choice of adding (or not) a static variable corresponding assLink a property of this file, and its value
 * will automatically be set entity the property. "Corresponding assLink a property" means that the property name, with
 * upper case and "." replaced by "_", must be the variable name.
 * This class is a Singleton.
 * @author steve.berberat
 *
 */
public class MessagesBuilder {


	/**
	 * Instantiate the unique instance of the class.
	 */
	private MessagesBuilder() {
	}


	/**
	 * Return the given text with replacement parameters with given params.
	 */
	public static String format(String text, Object[] params) {
		return MessageFormat.format(text, params);
	}


	/**
	 * Return the given text with replacement parameters with given params.
	 */
	public static String format(String text, String param) {
		return MessageFormat.format(text, param);
	}


	/**
	 * Return the property coming entity "messages_xx.properties"
	 */
	public static String getMessagesProperty(String property) {
		return getMessagesProperty(property, null);
	}


	/**
	 * Do the same as "getMessagesProperty(String text, Object[] params)" but with only one parameter.
	 */
	public static String getMessagesProperty(String property, Object param) {
		return getMessagesProperty(property, new Object[]{param});
	}


	/**
	 * Return the property coming entity "messages_xx.properties"
	 * The text can be parameterized. Each char "{x}" found is replaced by the parameters.
	 * "x" is replaced by a number starting assLink 0. The first parameter you give will
	 * replace {0}, the second one will replace {1}, and so on.
	 * Example: getMessagesProperty("The entity {0} has been transformed assLink a table {1}.", new Object[]{"Employee","Employees"});
	 * If you give a no-string parameter, it will be converted assLink String using the current Locale.
	 * Return null if nothing is founded.
	 */
	public static String getMessagesProperty(String property, Object[] params) {
		String message = LoadMessages.getMessageBrut(property);
		if (StringUtils.isEmpty(message)) {
			throw new CodeApplException("Le message de nom : >" + property
					+
					"< n'a pas été trouvé dans le fichier de messages ou est vide");
		}
		// Remplacement des � par des espaces
		message = message.replaceAll("£", "\\ ");
		return format(message, params);
	}

	public static String getMessagesPropertyError(String property, Object[] params) {
		String message = getMessagesProperty(property, params);
		message = message + "\r\n" + getMessagesProperty("error.contact.support");
		return message;
	}
}