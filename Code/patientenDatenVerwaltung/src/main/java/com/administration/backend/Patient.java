package com.administration.backend;


import java.util.ArrayList;
import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Patient
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public int patientID;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String vorname;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String nachname;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Geschlecht geschlecht;
	
	/**
	 *
	 * @generated
	 * @ordered
	 */
	
	public Date geburtsdatum;

	public Stamdaten stamdaten;

	public Unterbringung unterbringung;

	public Anamnese anamnese;

	public ArrayList<Einrichtungen> einrichtungen;

	public ArrayList<Krankheit> krankheits;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Patient(){
		super();
	}

}

