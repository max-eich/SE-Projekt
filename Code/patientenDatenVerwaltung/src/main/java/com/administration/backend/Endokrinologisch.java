package com.administration.backend;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Endokrinologisch
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean AdipositasBeiHypothyreose;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Cushing_Syndrom;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean genetisch_bedingter_Leptinmangel;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Kraniopharyngeom;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Leptirezeptormutation;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean MC4_Rezeptormutationen;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Morbus_Cushing;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean POMC_Mutationen;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean primärerHyperinsulinismusWiedemann_Beckwith;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean STH_Mangel;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Endokrinologisch(){
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String toString() {
		String rueckgabe = "";
		if (AdipositasBeiHypothyreose == true)
			rueckgabe += "Adipositas bei Hypothyreose, ";
		if (Cushing_Syndrom == true)
			rueckgabe += "Cushing-Syndrom, ";
		if (genetisch_bedingter_Leptinmangel == true)
			rueckgabe += "genetisch-bedingter Leptinmangel, ";
		if (Kraniopharyngeom == true)
			rueckgabe += "Kraniopharyngeom, ";
		if (Leptirezeptormutation == true)
			rueckgabe += "Leptirezeptormutation, ";
		if (MC4_Rezeptormutationen == true)
			rueckgabe += "MC4-Rezeptormutationen, ";
		if (Morbus_Cushing == true)
			rueckgabe += "Morbus-Cushing, ";
		if (POMC_Mutationen == true)
			rueckgabe += "POMC-Mutationen, ";
		if (primärerHyperinsulinismusWiedemann_Beckwith == true)
			rueckgabe += "primärer Hyperinsulinismus Wiedemann-Beckwith, ";
		if (STH_Mangel == true)
			rueckgabe += "STH-Mangel, ";

		if (rueckgabe.length() > 1) {
			rueckgabe = rueckgabe.substring(0, rueckgabe.length() - 2);
		}
		return rueckgabe;
	}
	
}

