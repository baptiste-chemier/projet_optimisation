/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Outils.Outils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Epulapp
 */
public class Ville {
    private String id;
    private String nom;
    private String codePostal;
    private String longitude;
    private String latitude;
    
    private int nbPlacesRestantes;   
    private List<Agence> listeAgences;
    private boolean isOpen;

    public Ville(String id, String nom, String codePostal, String longitude, String latitude) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
        this.longitude = longitude;
        this.latitude = latitude;
        
        this.nbPlacesRestantes = 60;
        this.isOpen = false;
        this.listeAgences = new ArrayList<Agence>();
                
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal the codePostal to set
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the nbPlacesRestantes
     */
    public int getNbPlacesRestantes() {
        return nbPlacesRestantes;
    }

    /**
     * @param nbPlacesRestantes the nbPlacesRestantes to set
     */
    public void setNbPlacesRestantes(int nbPlacesRestantes) {
        this.nbPlacesRestantes = nbPlacesRestantes;
    }

    /**
     * @return the listeAgences
     */
    public List<Agence> getListeAgences() {
        return listeAgences;
    }

    /**
     * @return the isOpen
     */
    public boolean getIsOpen() {
        return isOpen;
    }

    /**
     * @param isChosen the isOpen to set
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    public int getNbPersonnePresente() 
    {
        int nbPersonne = 0;
        for (int i = 0; i< listeAgences.size(); i++ ) {
            nbPersonne += listeAgences.get(i).getNbPersonne();
        }
       return nbPersonne;
    }
    
    public int calculPlaceRestante() 
    {
        int nbPlacesRestantes = getNbPlacesRestantes();
        int nbPersonnesPresentes = getNbPersonnePresente();
        int nbPlacesRestantesCalc = 60 - nbPersonnesPresentes;
        
        if (nbPlacesRestantes == nbPlacesRestantesCalc) {
            return nbPlacesRestantesCalc;
        } else {
            return 0;
        }        
    }
    
    public double getPriceForLF() 
    {
        double price = 0;
        if (this.getIsOpen() == true) {
            price += 3000;
        }
        
        for (int i = 0; i< listeAgences.size(); i++ ) {
            double distance = 2* Outils.getDistance(listeAgences.get(i).getLatitude(), listeAgences.get(i).getLongitude(), getLatitude(), getLongitude());
            price += 0.4 * listeAgences.get(i).getNbPersonne() * distance;
        }
        
        return Math.round(price);
    }
    
    
    

}
