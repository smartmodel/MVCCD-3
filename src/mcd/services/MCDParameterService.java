package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.CodeApplException;
import m.MElement;
import m.services.MElementService;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.Trace;
import window.editor.mcd.operation.parameter.ParameterEditor;

import java.util.ArrayList;
import java.util.Collection;

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
                MCDDatatype token = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TOKEN_LIENPROG);
                MCDDatatype positiveInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG);
                boolean c1 = mcdDatatype.isSelfOrDescendantOf(token) || mcdDatatype.isSelfOrDescendantOf(positiveInteger);
                // Il doit y avoir un attribut obligatoire mais, d'autres optionnels
                //boolean c2 = attributePotential.isMandatory();
                if (c1 ) {
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

 }
