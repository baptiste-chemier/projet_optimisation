/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Agence;
import Modele.Ville;
import java.io.BufferedReader;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Epulapp
 */
public class Controleur {
    
    private List<Ville> listeVilles;
    private List<Agence> listeAgences;
    
    private List<Ville> listeVilleSelectionnees;
    
    public Controleur() {
        listeAgences = new ArrayList<>();
        listeVilles = new ArrayList<>();
        listeVilleSelectionnees = new ArrayList<>();
        
    }
    public void importerVille(File file) {
        try {
            InputStream flux = new FileInputStream(file.getAbsolutePath());
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            int i = 0;
            while ((ligne = buff.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 5) {
                    if (i != 0) {
                        String id = parts[0].replaceAll("\"", "");
                        String nom = parts[1].replaceAll("\"", "");
                        String codePostal = parts[2].replaceAll("\"", "");
                        String longitude = parts[3].replaceAll("\"", "");
                        String latitude = parts[4].replaceAll("\"", "");
                        
                        System.out.println(id + " " + nom + " " + codePostal + " " + longitude + " " + latitude);
                        Ville ville = new Ville(id, nom, codePostal, longitude, latitude);
                        listeVilles.add(ville);
                    }
                }
                //System.out.println(ligne);
                i++;
            }
            buff.close();
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(null, "Import terminé", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e.toString());
            //Boîte du message d'erreur
            JOptionPane jop3 = new JOptionPane();
            jop3.showMessageDialog(null, "Une erreur est survenue lors de l'import", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void importerAgence(File file) {
        try {
            InputStream flux = new FileInputStream(file.getAbsolutePath());
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            int i =0;
            while ((ligne = buff.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 6) {
                    if (i != 0) {
                        String id = parts[0].replaceAll("\"", "");
                        String nom = parts[1].replaceAll("\"", "");
                        String codePostal = parts[2].replaceAll("\"", "");
                        String longitude = parts[3].replaceAll("\"", "");
                        String latitude = parts[4].replaceAll("\"", "");
                        int nbPersonne = Integer.parseInt(parts[5].replaceAll("\"", ""));

                        Agence agence = new Agence(id, nom, codePostal, longitude, latitude, nbPersonne);
                        listeAgences.add(agence);
                    }
                }
                //System.out.println(ligne);
                i++;
            }
            buff.close();
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(null, "Import terminé", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e.toString());
            //Boîte du message d'erreur
            JOptionPane jop3 = new JOptionPane();
            jop3.showMessageDialog(null, "Une erreur est survenue lors de l'import", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @return the listeVilles
     */
    public List<Ville> getListeVilles() {
        return listeVilles;
    }

    /**
     * @return the listeAgences
     */
    public List<Agence> getListeAgences() {
        return listeAgences;
    }
    
    public double getDistance(String latitude1, String longitude1, String latitude2, String longitude2) {
        double lat1, lat2, long1, long2, radLat1, radLat2, theta, radTheta, dist;
        
        lat1 = Double.parseDouble(latitude1);
        long1 = Double.parseDouble(longitude1);
        lat2 = Double.parseDouble(latitude2);
        long2 = Double.parseDouble(longitude2);
        
        radLat1 = Math.toRadians(lat1);
        radLat2 = Math.toRadians(lat2);
        
        if (long2 < long1) {
            theta = long1 - long2;
        } else {
            theta = long2 - long1;
        }
        
        radTheta = Math.toRadians(theta);
        dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
        dist = Math.acos(dist);
        dist = dist * 180/(Math.PI);
        dist = dist * 60 * 1.1515;
        
        return dist * 1.609344;
    }

    /**
     * @return the listeVilleSelectionnees
     */
    public List<Ville> getListeVilleSelectionnees() {
        return listeVilleSelectionnees;
    }
    
    public void genererSolutionInitiale() {
        for (int i = 0; i< listeAgences.size(); i++){
            Random rand = new Random();
            int indice = rand.nextInt(listeVilles.size()-1);
            
            int nbPlaceRestante = listeVilles.get(indice).getNbPlacesRestantes();
            if (nbPlaceRestante >= listeAgences.get(i).getNbPersonne()) {
                listeAgences.get(i).setIdLieuFormation(listeVilles.get(indice).getId()); //Set de l'id du lieu de formation à l'agence
                listeAgences.get(i).setIndexOfLieuFormation(indice); //Set de l'indice du lieu de formation dans l'agence
                listeVilles.get(indice).getListeAgences().add(listeAgences.get(i)); //Ajout de l'agence comme agence du lieu de formation

                listeVilles.get(indice).setIsOpen(true); //On ouvre le lieu de formation

                int nbPlaceRestanteNew = nbPlaceRestante - listeAgences.get(i).getNbPersonne();
                listeVilles.get(indice).setNbPlacesRestantes(nbPlaceRestanteNew); //gestion du nombre de places restantes
            }
            double distance = getDistance(listeAgences.get(i).getLatitude(), listeAgences.get(i).getLongitude(), listeVilles.get(listeAgences.get(i).getIndexOfLieuFormation()).getLatitude(), listeVilles.get(listeAgences.get(i).getIndexOfLieuFormation()).getLongitude());
            
            System.out.println("Agence: " + listeAgences.get(i).getNom() + " | Lieu de formation associé: " + listeVilles.get(listeAgences.get(i).getIndexOfLieuFormation()).getNom() + " | Distance: " + distance);
        }
        double priceTranport = getPriceTransport();
        double priceOpen = getPriceLieuxOpen();
        double priceTotal = priceOpen + priceTranport;
        
        System.out.println("Cout de transport total : " + priceTranport);
        System.out.println("Cout d'ouverture des lieux: " + priceOpen);
        System.out.println("Cout total: " + priceTotal);
        
    }
    
    public double getPriceTransport() {
        double price = 0;
        for (Agence listeAgence : listeAgences) {
            Ville lieuFormation = listeVilles.get(listeAgence.getIndexOfLieuFormation());
            double distance = 2*getDistance(listeAgence.getLatitude(), listeAgence.getLongitude(), lieuFormation.getLatitude(), lieuFormation.getLongitude()); //On gère l'aller retour
            price += 0.4 * listeAgence.getNbPersonne() * distance;
        }
        return price;
    }
    
    public double getPriceLieuxOpen() {
        double price = 0;
        for (int i =0; i< listeVilles.size(); i++) {
            if (listeVilles.get(i).getIsOpen()) {
                price += 3000;
            }
        }
        return price;
    }
    
    public void startTabou() {
        
        //Initialisation
        genererSolutionInitiale();
    }
    
}
