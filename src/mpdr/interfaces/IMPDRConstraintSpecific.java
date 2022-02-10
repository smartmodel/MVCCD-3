package mpdr.interfaces;

import mpdr.MPDRConstraintSpecificRole;

// Contrainte créée spécifiquement pour le MPDR comme un index
public interface IMPDRConstraintSpecific {


    public MPDRConstraintSpecificRole getRole();
    public void setRole(MPDRConstraintSpecificRole role);

}
