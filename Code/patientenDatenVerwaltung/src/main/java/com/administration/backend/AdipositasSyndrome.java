package com.administration.backend;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 *
 * @generated
 */

public class AdipositasSyndrome {
    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public boolean Laurence_Moon_Bardet_Biedel;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public boolean Prader_Willi;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public boolean Simpson_Golabi_Behmel;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public boolean Sotos;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public boolean Trisomie_21;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public boolean Weaver;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public String andere;

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     */
    public AdipositasSyndrome() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!--  end-user-doc  -->
     *
     * @generated
     * @ordered
     */

    public String toString() {
        String rueckgabe = "";
        if (Laurence_Moon_Bardet_Biedel == true)
            rueckgabe += "Laurence-Moon-Bardet-Biedel, ";
        if (Prader_Willi == true)
            rueckgabe += "Prader-Willi, ";
        if (Simpson_Golabi_Behmel == true)
            rueckgabe += "Simpson-Golabi-Behmel, ";
        if (Sotos == true)
            rueckgabe += "Sotos, ";
        if (Trisomie_21 == true)
            rueckgabe += "Trisomie-21, ";
        if (Weaver == true)
            rueckgabe += "Weaver, ";

        if (rueckgabe.length() > 1) {
            rueckgabe = rueckgabe.substring(0, rueckgabe.length() - 2);
        }
        return rueckgabe;
    }

}

