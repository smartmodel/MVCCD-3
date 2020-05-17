package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import window.editor.operation.parameter.ParameterEditor;

import java.util.ArrayList;

public class MCDParameterService {

    public static ArrayList<String> checkTarget(String selectedItem, int scope, IMCDParameter iMCDParameterTarget) {
        ArrayList<String> messages = new ArrayList<String>();
        if (StringUtils.isNotEmpty(selectedItem) && (iMCDParameterTarget == null)){
            messages.add(MessagesBuilder.getMessagesProperty( "editor.unique.parameter.target.unknow.error"));
        }
        if ( scope == ParameterEditor.UNIQUE ){
            if (StringUtils.isEmpty(selectedItem)){
                messages.add(0, MessagesBuilder.getMessagesProperty( "editor.unique.parameter.target.mandatory.error"));
            }
        }
        if ( scope == ParameterEditor.NID ){
                if (StringUtils.isEmpty(selectedItem)){
                    messages.add(0,MessagesBuilder.getMessagesProperty( "editor.nid.parameter.target.mandatory.error"));
                }

        }
        return messages;
    }


    public static ArrayList<MCDAttribute> createTargetsAttributesUnique(MCDEntity mcdEntity) {
        ArrayList<MCDAttribute> resultat = new ArrayList<MCDAttribute>();
        ArrayList<MVCCDElement> targetsPotential = MVCCDElementConvert.to(mcdEntity.getMcdAttributes());
        for (MVCCDElement targetPotential : targetsPotential){
            if (targetPotential instanceof MCDAttribute){
                MCDAttribute attributePotential = (MCDAttribute) targetPotential;
                boolean c1 = attributePotential.isAid();
                boolean c2 = attributePotential.isList();
                boolean c3 = attributePotential.isDerived();
                boolean c4 = attributePotential.getDatatypeLienProg() == null;
                boolean c5 = true;
                if ( ! c4){
                    MCDDatatype mcdDatatype= MDDatatypeService.getMCDDatatypeByLienProg(attributePotential.getDatatypeLienProg());
                    MCDDatatype token = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TOKEN_LIENPROG);
                    MCDDatatype integer = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_INTEGER_LIENPROG);
                    MCDDatatype temporal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TEMPORAL_LIENPROG);
                    c5 = ! ((mcdDatatype.isSelfOrDescendantOf(token) ||
                            mcdDatatype.isSelfOrDescendantOf(integer)||
                            mcdDatatype.isDescendantOf(temporal)));
                }
                if ( ! (c1 || c2 || c3 || c4 || c5)){
                    resultat.add(attributePotential);
                }
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAttribute> createTargetsAttributesNID(MCDEntity mcdEntity) {
        ArrayList<MCDAttribute> resultat = new  ArrayList<MCDAttribute>();
        ArrayList<MCDAttribute> targetsPotential = createTargetsAttributesUnique(mcdEntity);
        for (MVCCDElement targetPotential : targetsPotential) {
            if (targetPotential instanceof MCDAttribute) {
                MCDAttribute attributePotential = (MCDAttribute) targetPotential;
                MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(attributePotential.getDatatypeLienProg());
                MCDDatatype token = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TOKEN_LIENPROG);
                MCDDatatype positiveInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG);
                boolean c1 = mcdDatatype.isSelfOrDescendantOf(token) || mcdDatatype.isSelfOrDescendantOf(positiveInteger);
                if (c1) {
                    resultat.add(attributePotential);
                }
            }
        }
        return resultat;
    }

    public static IMCDParameter getTargetByTypeAndName(MCDEntity mcdEntity, String type, String name){
        if ( type.equals(MCDAttribute.CLASSSHORTNAMEUI)){
            for ( MCDAttribute mcdAttribute : mcdEntity.getMcdAttributes()){
                if (mcdAttribute.getName().equals(name)){
                    return mcdAttribute;
                }
            }
        }
        throw new CodeApplException("La méthode getTargetByTypeAndName ne trouve pas de cible de type "+
                type + " et de nom " + name + "pour l'entité " + mcdEntity.getName());
    }


}
