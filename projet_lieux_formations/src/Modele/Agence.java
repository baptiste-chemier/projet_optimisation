/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

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
    
    public Agence(String id, String nom, String codePostal, String longitude, String latitude, int nbPersonne) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nbPersonne = nbPersonne;
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
}
