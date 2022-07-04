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
	
	public int patientID=0;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String vorname="";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String nachname="";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Geschlecht geschlecht=Geschlecht.d;
	
	/**
	 *
	 * @generated
	 * @ordered
	 */
	
	public Date geburtsdatum=new Date();

	public Stamdaten stamdaten=new Stamdaten();

	public Unterbringung unterbringung=new Unterbringung();

	public Anamnese anamnese=new Anamnese();

	public ArrayList<Einrichtungen> einrichtungen=new ArrayList<>();

	public ArrayList<Krankheit> krankheits = new ArrayList<>();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Patient(){
		super();
	}

}

