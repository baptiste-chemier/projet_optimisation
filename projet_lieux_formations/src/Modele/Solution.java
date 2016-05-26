/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Epulapp
 */
public class Solution {
    
    private List<Ville> listeLieuxFormation;
    private double totalPrice;
    private List<Agence> listeAgences;
    private LinkedList<Agence> listeTabou;
    public int sizeTabou = 1;

    public Solution() {
        // On sauvegarde dans cette liste les agences ayant un indexOfLastLieuFormation non nul ce qui signifie
        // que l'on ne peut pas retournerà l'ancienne solution
        listeTabou = new LinkedList<>();
    }
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
     * @return the listeTabou
     */
    public List<Agence> getListeTabou() {
        return listeTabou;
    }


    public void addToListeTabou(Agence agence) {
        //Si la taille du tableau est >= à la taille de la liste tabou, on supprime le premier élément et on ajoute l'agence à la fin.
        if (listeTabou.size() >= sizeTabou) {
            listeTabou.removeFirst();
        }
        listeTabou.addLast(agence);
    }
    
}
