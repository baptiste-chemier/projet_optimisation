/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Outils.Outils;

/**
 *
 * @author Epulapp
 */
public class Agence {
    private String id;
    private String nom;
    private String codePostal;
    private String longitude;
    private String latitude;
    private int nbPersonne;
    
    private String idLieuFormation;
    private int indexOfLieuFormation; //Sauvegarde de l'index afin de gagner du temps d'exécution lors de la recherche du lieu de formation
    private int indexOfLastLieuFormation; //Sauvegarde de l'index de l'ancien lieu de formation dans le cas d'un changement.
    
    
    public Agence(String id, String nom, String codePostal, String longitude, String latitude, int nbPersonne) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nbPersonne = nbPersonne;
        this.indexOfLieuFormation = -1;
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
     * @return the nbPersonne
     */
    public int getNbPersonne() {
        return nbPersonne;
    }

    /**
     * @param nbPersonne the nbPersonne to set
     */
    public void setNbPersonne(int nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    /**
     * @return the idLieuFormation
     */
    public String getIdLieuFormation() {
        return idLieuFormation;
    }

    /**
     * @param idLieuFormation the idLieuFormation to set
     */
    public void setIdLieuFormation(String idLieuFormation) {
        this.idLieuFormation = idLieuFormation;
    }

    /**
     * @return the indexOfLieuFormation
     */
    public int getIndexOfLieuFormation() {
        return indexOfLieuFormation;
    }

    /**
     * @param indexOfLieuFormation the indexOfLieuFormation to set
     */
    public void setIndexOfLieuFormation(int indexOfLieuFormation) {
        this.indexOfLieuFormation = indexOfLieuFormation;
    }

    /**
     * @return the indexOfLastLieuFormation
     */
    public int getIndexOfLastLieuFormation() {
        return indexOfLastLieuFormation;
    }

    /**
     * @param indexOfLastLieuFormation the indexOfLastLieuFormation to set
     */
    public void setIndexOfLastLieuFormation(int indexOfLastLieuFormation) {
        this.indexOfLastLieuFormation = indexOfLastLieuFormation;
    }
    
    public double getPriceForTransportForAgenceToLF(Ville lF)
    {
        double price = 0;
        double distance = 2 * Outils.getDistance(getLatitude(), getLongitude(), lF.getLatitude(), lF.getLongitude());
        price += 0.4 *getNbPersonne() * distance;
        
        return price;
    }
}
