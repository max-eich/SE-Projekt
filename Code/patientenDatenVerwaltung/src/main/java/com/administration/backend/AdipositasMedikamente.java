package com.administration.backend;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class AdipositasMedikamente
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Glukokortikoide;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Insulingabe;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Valproat;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean Phenothiazine;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String andere;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public AdipositasMedikamente(){
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
		if(Glukokortikoide == true)
			rueckgabe += "Glukokortikoide, ";
		if(Insulingabe == true)
			rueckgabe += "Insulingabe, ";
		if(Valproat == true)
			rueckgabe += "Valproat, ";
		if(Phenothiazine == true)
			rueckgabe += "Phenothiazine, ";

		if(rueckgabe.length() > 1) {
			rueckgabe = rueckgabe.substring(0, rueckgabe.length() - 2);
		}
		return rueckgabe;
	}
	
}

