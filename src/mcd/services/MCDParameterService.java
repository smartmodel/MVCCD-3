package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.CodeApplException;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.MCDParameter;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import window.editor.operation.parameter.ParameterEditor;

import java.util.ArrayList;

public class MCDParameterService {

    public static ArrayList<String> checkTarget(IMCDParameter iMCDParameterTarget,
                                                String selectedItem,
                                                int scope) {
        ArrayList<String> messages = new ArrayList<String>();

        //TODO-2 A terme (Opérations de service le nom d'un paramètre pourra être saisi!
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
        if (messages.size() == 0){

        }
        return messages;
    }


    public static ArrayList<MCDAttribute> createTargetsAttributesUnique(MCDEntity mcdEntity, ArrayList<MCDParameter> parameters) {
        ArrayList<MCDAttribute> resultat = new ArrayList<MCDAttribute>();
        ArrayList<MVCCDElement> targetsPotential = MVCCDElementConvert.to(mcdEntity.getMCDAttributes());
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
                boolean c6 = existTargetInParameters( parameters, attributePotential);
                if ( ! (c1 || c2 || c3 || c4 || c5 || c6)){
                    resultat.add(attributePotential);
                }
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAttribute> createTargetsAttributesNID(MCDEntity mcdEntity, ArrayList<MCDParameter> parameters) {
        ArrayList<MCDAttribute> resultat = new  ArrayList<MCDAttribute>();
        ArrayList<MCDAttribute> targetsPotential = createTargetsAttributesUnique(mcdEntity, parameters);
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
            for ( MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
                if (mcdAttribute.getName().equals(name)){
                    return mcdAttribute;
                }
            }
        }
        throw new CodeApplException("La méthode getTargetByTypeAndName ne trouve pas de cible de type "+
                type + " et de nom " + name + "pour l'entité " + mcdEntity.getName());
    }

    public static boolean existTargetInParameters(ArrayList<MCDParameter> parameters,
                                                  MElement target){
        for (MCDParameter parameter : parameters) {
            if (parameter.getTarget() != null) {
                if (parameter.getTarget() == target) {
                    return true;
                }
            }
        }
        return false;
    }

}
