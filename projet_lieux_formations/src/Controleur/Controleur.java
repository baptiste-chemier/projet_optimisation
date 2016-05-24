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
import javax.swing.JOptionPane;

/**
 *
 * @author Epulapp
 */
public class Controleur {
    
    protected List<Ville> listeVilles;
    protected List<Agence> listeAgences;
    
    public Controleur() {
        listeAgences = new ArrayList<>();
        listeVilles = new ArrayList<>();
        
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
                        int codePostal = Integer.parseInt(parts[2].replaceAll("\"", ""));
                        String longitude = parts[3].replaceAll("\"", "");
                        String latitude = parts[4].replaceAll("\"", "");

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
                        int codePostal = Integer.parseInt(parts[2].replaceAll("\"", ""));
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
    
}
