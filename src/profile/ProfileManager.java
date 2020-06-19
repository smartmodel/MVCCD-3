package profile;

import console.Console;
import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import preferences.Preferences;
import preferences.PreferencesLoader;
import preferences.PreferencesManager;
import preferences.PreferencesSaver;
import project.Project;
import project.ProjectsRecents;
import repository.Repository;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileManager {

    private static ProfileManager instance;

    public static synchronized ProfileManager instance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public ArrayList<String> filesProfile() {
        ArrayList<String> resultat = new ArrayList<String>();
        File directoryProfile = new File(Preferences.DIRECTORY_PROFILE_NAME);
        String filesProfile[] = directoryProfile.list();
        if (filesProfile.length > 0) {
            for (int i = 0; i < filesProfile.length; i++) {
                if (UtilFiles.isFileProfil(filesProfile[i])) {
                    resultat.add(filesProfile[i]);
                }
            }
        }
        return resultat;
    }

    public Preferences loadFileProfile(String profileFileName) {
        Preferences profilePref = null;
        if (profileFileName != null) {
            try {
                PreferencesLoader loader = new PreferencesLoader();
                profilePref = loader.load(new File(profileFileName));
            } catch (FileNotFoundException e) {
                // Pas de profile
                String message = "Le fichier n'est pas trouvé!";
                DialogMessage.showError(MVCCDManager.instance().getMvccdWindow(), message);
            }
        }
        return profilePref;
    }

    public Preferences loadFileProfileXML(String profileFileName) {
        Preferences profilePref = new Preferences(null, null);
        if (profileFileName != null) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File(Preferences.DIRECTORY_PROFILE_NAME + Preferences.SYSTEM_FILE_SEPARATOR + profileFileName));

                Element racine = document.getDocumentElement();
                Element preferences = (Element) racine.getElementsByTagName("Preferences").item(0);

                NodeList preferencesChild = preferences.getChildNodes();
                for (int i = 0; i < preferencesChild.getLength(); i++) {
                    Node child = preferencesChild.item(i);
                    if (child instanceof Element) {
                        if (child.getNodeName().equals("Mcd_journalization")) {
                            Element mcdJournalization = (Element) child;
                            profilePref.setMCD_JOURNALIZATION(Boolean.valueOf(mcdJournalization.getTextContent()));
                        }
                        if (child.getNodeName().equals("Mcd_Journalization_exception")) {
                            Element mcdJournalizationException = (Element) child;
                            profilePref.setMCD_JOURNALIZATION_EXCEPTION(Boolean.valueOf(mcdJournalizationException.getTextContent()));
                        }
                        if (child.getNodeName().equals("Mcd_audit")) {
                            Element mcdAudit = (Element) child;
                            profilePref.setMCD_AUDIT(Boolean.valueOf(mcdAudit.getTextContent()));

                        }
                        if (child.getNodeName().equals("Mcd_audit_Exception")) {
                            Element mcdAuditException = (Element) child;
                            profilePref.setMCD_AUDIT_EXCEPTION(Boolean.valueOf(mcdAuditException.getTextContent()));

                        }
                        if (child.getNodeName().equals("Mcd_aid_data_type_lien_prog")) {
                            Element mcdAidDataTypeLienProg = (Element) child;
                            profilePref.setMCD_AID_DATATYPE_LIENPROG(mcdAidDataTypeLienProg.getTextContent());

                        }
                        if (child.getNodeName().equals("Mcd_data_type_number_size_mode")) {
                            Element mcdDataTypeNumberSizemode = (Element) child;
                            profilePref.setMCDDATATYPE_NUMBER_SIZE_MODE(mcdDataTypeNumberSizemode.getTextContent());

                        }
                        if (child.getNodeName().equals("Mcd_aid_ind_column_name")) {
                            Element mcdAidIndColumnName = (Element) child;
                            profilePref.setMCD_AID_IND_COLUMN_NAME(mcdAidIndColumnName.getTextContent());

                        }
                        if (child.getNodeName().equals("Mcd_aid_dep_column_name")) {
                            Element mcdAidDepColumnName = (Element) child;
                            profilePref.setMCD_AID_DEP_COLUMN_NAME(mcdAidDepColumnName.getTextContent());


                        }
                        if (child.getNodeName().equals("Mcd_aid_with_dep")) {
                            Element mcdAidWithDep = (Element) child;
                            profilePref.setMCD_AID_WITH_DEP(Boolean.valueOf(mcdAidWithDep.getTextContent()));

                        }
                        if (child.getNodeName().equals("Mcd_tree_naming_association")) {
                            Element mcdTreeNamingAssociation = (Element) child;
                            profilePref.setMCD_TREE_NAMING_ASSOCIATION(mcdTreeNamingAssociation.getTextContent());


                        }
                        if (child.getNodeName().equals("Mcd_mode_naming_long_name")) {
                            Element mcdModeNamingLongName = (Element) child;
                            profilePref.setMCD_MODE_NAMING_LONG_NAME(mcdModeNamingLongName.getTextContent());


                        }
                        if (child.getNodeName().equals("Mcd_mode_naming_attribute_short_name")) {
                            Element mcdModeNamingAttributeShortName = (Element) child;
                            profilePref.setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(mcdModeNamingAttributeShortName.getTextContent());

                        }
                    }
                }

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return profilePref;
    }
}
