package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.CodeApplException;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import window.editor.mcd.operation.parameter.ParameterEditor;

import java.util.ArrayList;

public class MCDParameterService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;

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


    public static ArrayList<MCDAttribute> createTargetsAttributesUnique(MCDEntity mcdEntity) {
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
                if ( ! (c1 || c2 || c3 || c4 || c5 )){
                    resultat.add(attributePotential);
                }
            }
        }
        return resultat;
    }


    public static ArrayList<MCDAttribute> createTargetsAttributesOptionnalUnique(MCDEntity mcdEntity) {
        ArrayList<MCDAttribute> resultat = new  ArrayList<MCDAttribute>();
        ArrayList<MCDAttribute> targetsPotential = createTargetsAttributesUnique(mcdEntity);
        for (MVCCDElement targetPotential : targetsPotential) {
            if (targetPotential instanceof MCDAttribute) {
                MCDAttribute attributePotential = (MCDAttribute) targetPotential;
                if (! attributePotential.isMandatory() ) {
                    resultat.add(attributePotential);
                }
            }
        }
        return resultat;
    }



    public static ArrayList<MCDAssEnd> createTargetsNonExisting(ArrayList <MCDAssEnd> mcdAssEnds, ArrayList<MCDParameter> parameters) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : mcdAssEnds){
            if (!existTargetInParameters(parameters, mcdAssEnd)){
                resultat.add(mcdAssEnd);
            }
        }
        return resultat;
    }

    //#MAJ 2021-05-18 ParameterInputContent (Inversion)
    /*
    public static Collection<? extends MVCCDElement> createTargetsAssEndsNoIdAndNoNN(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        // no Id
        ArrayList<MCDAssEnd> mcdAssEndChilds = mcdEntity.getAssEndsNoIdChild();
        mcdAssEndChilds.removeAll(mcdEntity.getAssEndsAssNNChild());

        // Extrémité inverse comme résultat
        for (MCDAssEnd mcdAssEndChild : mcdAssEndChilds) {
            resultat.add(mcdAssEndChild.getMCDAssEndOpposite());
        }

        return resultat;
    }


    public static Collection<? extends MVCCDElement> createTargetsAssEndsIdAndNN(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        ArrayList<MCDAssEnd> mcdAssEndChilds = mcdEntity.getAssEndsIdAndNNChild();

        // Extrémité inverse comme résultat
        for ( MCDAssEnd mcdAssEndChild : mcdAssEndChilds ){
            // Si n'existe pas déjà...
            resultat.add (mcdAssEndChild.getMCDAssEndOpposite());
        }

        return resultat;
    }

     */

    public static ArrayList<MCDAttribute> createTargetsAttributesNID(MCDEntity mcdEntity) {
        ArrayList<MCDAttribute> resultat = new  ArrayList<MCDAttribute>();
        ArrayList<MCDAttribute> targetsPotential = createTargetsAttributesUnique(mcdEntity);
        for (MVCCDElement targetPotential : targetsPotential) {
            if (targetPotential instanceof MCDAttribute) {
                MCDAttribute attributePotential = (MCDAttribute) targetPotential;
                MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(attributePotential.getDatatypeLienProg());
                //#MAJ 2021-06-18D MCDDatatype autorisé pour NID (Généralisation)
                /*
                MCDDatatype token = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TOKEN_LIENPROG);
                MCDDatatype positiveInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG);
                boolean c1 = mcdDatatype.isSelfOrDescendantOf(token) || mcdDatatype.isSelfOrDescendantOf(positiveInteger);
                */
                // Il doit y avoir un attribut obligatoire mais, d'autres optionnels
                //boolean c2 = attributePotential.isMandatory();
                if (mcdDatatype.isAuthorizedForNID()) {
                        resultat.add(attributePotential);
                }
            }
        }
        return resultat;
    }


    public static IMCDParameter getTargetByTypeAndNameTarget(MCDEntity mcdEntity, String type, String nameTarget) {
        if ( type.equals(MCDAttribute.CLASSSHORTNAMEUI)) {
            // Attributs
            for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()) {
                if (mcdAttribute.getNameTarget().equals(nameTarget)) {
                    return mcdAttribute;
                }
            }
        }
        if ( type.equals(MCDAssEnd.CLASSSHORTNAMEUI)) {
            // Extrémités d'association
            // Sans entité associative
            for (MCDAssEnd mcdAssEnd : mcdEntity.getMCDAssEnds()) {
                //#MAJ 2021-05-19 Affinement MCDUnicity

                //if (mcdAssEnd.getMCDAssEndOpposite().getNameTree().equals(nameTree)) {
                //if (mcdAssEnd.getNameTarget().equals(nameTarget)) {
                if (mcdAssEnd.getMCDAssEndOpposite().getNameTarget().equals(nameTarget)) {
                        //return mcdAssEnd.getMCDAssEndOpposite();
                    return mcdAssEnd.getMCDAssEndOpposite();
                }
            }
            // Avec entité associative
            for (MCDLinkEnd mcdLinkEnd : mcdEntity.getMCDLinkEnds()) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdLinkEnd.getMcdLink().getEndAssociation().getParent().getParent();
                if (mcdAssociation.getFrom().getNameTarget().equals(nameTarget)) {
                    return mcdAssociation.getFrom();
                }
                if (mcdAssociation.getTo().getNameTarget().equals(nameTarget)) {
                    return mcdAssociation.getTo();
                }
            }
        }

        throw new CodeApplException("La méthode getTargetByTypeAndName ne trouve pas de cible de type "+
                type + " et de nom " + nameTarget + " pour l'entité " + mcdEntity.getName());
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

    // Les MCDAssEnd en haut par rapport à l'ordre de saisie des paramètres
    // Les MCDAttribute ensuite par rapport à l'ordre de saisie en tant qu'attribut d'entité
    // Les paramètres sans target en bas par rapport à l'ordre de saisie
    public static int compareToDefault(MCDParameter courant, MCDParameter other) {

        //TODO-1 Code à metttre en place lorsque l'écran de saisie sera adaptà au tri ci-desssos
        /*
        MCDAttribute courantAttribute = courant.getMCDAttribute();
        MCDAttribute otherAttribute = other.getMCDAttribute();
        MCDAssEnd courantAssEnd = courant.getMCDAssEnd();
        MCDAssEnd otherAssEnd = other.getMCDAssEnd();
        IMCDParameter courantTarget = courant.getTarget();
        IMCDParameter otherTarget = other.getTarget();
        if ( (courantAssEnd != null) && (otherAssEnd != null)){
            return courant.compareToOrder(other) ;
        } else if ( (courantAttribute != null) && (otherAttribute != null)){
            return courantAttribute.compareToOrder(otherAttribute) ;
        } else if ( (courantTarget == null) && (otherTarget == null)){
            return courant.compareToOrder(other) ;
        } else if (courantAssEnd != null) {
            return HAUT;
        } else if (otherAssEnd != null) {
            return BAS ;
        } else if (courantAttribute != null) {
            return HAUT;
        } else if (otherAttribute != null) {
            return BAS ;
        } else {
            return COMPARE_DEFAULT;
        }

         */

        // En attendant
        return courant.compareToOrder(other) ;
    }
}
