/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Epulapp
 */
public class Solution {
    
    private List<Ville> listeLieuxFormation;
    private List<Ville> listeVilles;
    private double totalPrice;
    private List<Agence> listeAgences;


    /**
     * @return the listeLieuxFormation
     */
    public List<Ville> getListeLieuxFormation() {
        return listeLieuxFormation;
    }

    /**
     * @param listeLieuxFormation the listeLieuxFormation to set
     */
    public void setListeLieuxFormation(List<Ville> listeLieuxFormation) {
        this.listeLieuxFormation = listeLieuxFormation;
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the listeAgences
     */
    public List<Agence> getListeAgences() {
        return listeAgences;
    }

    /**
     * @param listeAgences the listeAgences to set
     */
    public void setListeAgences(List<Agence> listeAgences) {
        this.listeAgences = listeAgences;
    }


    /**
     * @return the listeVilles
     */
    public List<Ville> getListeVilles() {
        return listeVilles;
    }

    /**
     * @param listeVilles the listeVilles to set
     */
    public void setListeVilles(List<Ville> listeVilles) {
        this.listeVilles = listeVilles;
    }
    
    public List<Ville> controlOpen() {
        List<Ville> lieuFormation = new ArrayList();
        for (Ville listeVille : listeVilles) {
            if (listeVille.getIsOpen()) {
                lieuFormation.add(listeVille);
            }
        }
        return lieuFormation;
    }
    
    public double getPrice()
    {
        double price = 0;
        for (int i = 0; i < listeVilles.size(); i++ ) {
            price += listeVilles.get(i).getPriceForLF();
        }
        
        return Math.round(price);
    }
        
    
}
