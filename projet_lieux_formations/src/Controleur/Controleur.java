/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Agence;
import Modele.Solution;
import Modele.Ville;
import Outils.Outils;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import projet_lieux_formations.Vue;

/**
 *
 * @author Epulapp
 */
public class Controleur {
    
    private List<Ville> listeVilles;
    private List<Agence> listeAgences;
    private Solution solution;
    
    private final List<Ville> listeVilleCloseWithoutAgence;
    private final List<Agence> listeAgenceWithoutLF;
    
    private final Vue vue;
    
    
    public Controleur(Vue vue) {
        listeAgences = new ArrayList<>();
        listeVilles = new ArrayList<>();
        listeVilleCloseWithoutAgence = new ArrayList<>();
        listeAgenceWithoutLF = new ArrayList<>();
        solution = new Solution();
        this.vue = vue;
        
        
    }
    public void importerVille(File file) {
        try {
            InputStream flux = new FileInputStream(file.getAbsolutePath());
            InputStreamReader lecture = new InputStreamReader(flux);
            try (BufferedReader buff = new BufferedReader(lecture)) {
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
                            
                            //System.out.println(id + " " + nom + " " + codePostal + " " + longitude + " " + latitude);
                            Ville ville = new Ville(id, nom, codePostal, longitude, latitude);
                            listeVilles.add(ville);
                            Ville ville2 = new Ville(id, nom, codePostal, longitude, latitude);
                            getListeVilleCloseWithoutAgence().add(ville2);
                        }
                    }
                    //System.out.println(ligne);
                    i++;
                }
            }
            JOptionPane.showMessageDialog(null, "Import terminé", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | HeadlessException e) {
            System.out.println(e.toString());
            //Boîte du message d'erreur
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'import", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void importerAgence(File file) {
        try {
            InputStream flux = new FileInputStream(file.getAbsolutePath());
            InputStreamReader lecture = new InputStreamReader(flux);
            try (BufferedReader buff = new BufferedReader(lecture)) {
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
                            Agence a = new Agence (id, nom, codePostal, longitude, latitude, nbPersonne);
                            listeAgenceWithoutLF.add(a);
                        }
                    }
                    //System.out.println(ligne);
                    i++;
                }
            }
            JOptionPane.showMessageDialog(null, "Import terminé", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | NumberFormatException | HeadlessException e) {
            System.out.println(e.toString());
            //Boîte du message d'erreur
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'import", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initialisation() 
    {
        listeAgences = cloneListeAgence(listeAgenceWithoutLF);
        listeVilles = cloneListVille(listeVilleCloseWithoutAgence);
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
            double distance = Outils.getDistance(listeAgences.get(i).getLatitude(), listeAgences.get(i).getLongitude(), listeVilles.get(listeAgences.get(i).getIndexOfLieuFormation()).getLatitude(), listeVilles.get(listeAgences.get(i).getIndexOfLieuFormation()).getLongitude());
            
            System.out.println("Agence: " + listeAgences.get(i).getNom() + " | Lieu de formation associé: " + listeVilles.get(listeAgences.get(i).getIndexOfLieuFormation()).getNom() + " | Distance: " + distance);
        }
        double priceTranport = getPriceTransport();
        double priceOpen = getPriceLieuxOpen();
        double totalPrice = priceOpen + priceTranport;
        
        this.getSolution().setTotalPrice(totalPrice);
        this.getSolution().setListeAgences(listeAgences);
        this.getSolution().setListeLieuxFormation(getLieuxFormation());
        this.getSolution().setListeVilles(listeVilles);
        
    }
    
    public double getPriceTransport() {
        double price = 0;
        for (Agence listeAgence : listeAgences) {
            Ville lieuFormation = listeVilles.get(listeAgence.getIndexOfLieuFormation());
            double distance = 2*Outils.getDistance(listeAgence.getLatitude(), listeAgence.getLongitude(), lieuFormation.getLatitude(), lieuFormation.getLongitude()); //On gère l'aller retour
            price += 0.4 * listeAgence.getNbPersonne() * distance;
        }
        return price;
    }
    
    public double getPriceTransportFromSolution(List <Agence> liste) {
        double price = 0;
        for (Agence listeAgence : liste) {
            Ville lieuFormation = listeVilles.get(listeAgence.getIndexOfLieuFormation());
            double distance = 2 * Outils.getDistance(listeAgence.getLatitude(), listeAgence.getLongitude(), lieuFormation.getLatitude(), lieuFormation.getLongitude()); //On gère l'aller retour
            price += 0.4 * listeAgence.getNbPersonne() * distance;
        }
        return price;
    }
    
    public double getPriceLieuxOpen() {
        double price = 0;
        for (Ville listeVille : listeVilles) {
            if (listeVille.getIsOpen()) {
                price += 3000;
            }
        }
        return price;
    }
    
    public double getTotalPrice(List<Agence> liste) {
        double price = 0;
        for (Agence listeAgence : liste) {
            Ville lieuFormation = listeVilles.get(listeAgence.getIndexOfLieuFormation());
            double distance = 2 * Outils.getDistance(listeAgence.getLatitude(), listeAgence.getLongitude(), lieuFormation.getLatitude(), lieuFormation.getLongitude()); //On gère l'aller retour
            price += 0.4 * listeAgence.getNbPersonne() * distance;
        }
        
        for (Ville listeVille : listeVilles) {
            if (listeVille.getIsOpen()) {
                price += 3000;
            }
        }
        
        
        return price;
    }
    
    public Solution genererVoisinage() {      
        
        double bestVoisin = 1000000000;
        int indice;
        Random rand;
        int choice;
  
        Solution meilleurVoisin = new Solution();
        
        int indiceVille = -1;
        int indiceAgence = -1;
        boolean change = false;
        List<Ville> lFTmp = new ArrayList<>();
        lFTmp.addAll(getListeVilles());

        List<Agence> agenceTmp = new ArrayList<>();
        agenceTmp.addAll(getSolution().getListeAgences());
        
        for (int i = 0; i < listeAgences.size(); i++) {
            double price = this.getSolution().getTotalPrice();

            
            rand = new Random();
            
            choice = rand.nextInt(10);
            if (choice >8) {
                indice = rand.nextInt(lFTmp.size() - 1); //indice du nouveau lieu de formation
            } else {
                int random = rand.nextInt(agenceTmp.size() - 1); //indice du nouveau lieu de formation
                indice = agenceTmp.get(random).getIndexOfLieuFormation();
            }
            boolean traite = false;
            
            while (!traite) {
                if (lFTmp.get(indice).getNbPlacesRestantes() >= agenceTmp.get(i).getNbPersonne() && lFTmp.get(indice).getNbPlacesRestantes() <= 60 && indice != agenceTmp.get(i).getIndexOfLastLieuFormation()) {

                    int lastIndexLf = agenceTmp.get(i).getIndexOfLieuFormation(); //Récupération de l'ancien index du Lf
                             
                    if (lFTmp.get(indice).getIsOpen() == false) {
                        price +=3000;
                    }
                    
                    double distance = 2* Outils.getDistance(agenceTmp.get(i).getLatitude(), agenceTmp.get(i).getLongitude(), lFTmp.get(indice).getLatitude(), lFTmp.get(indice).getLongitude());
                    price += 0.4 * agenceTmp.get(i).getNbPersonne() * distance;    
                    
                    double distanceOld = 2* Outils.getDistance(agenceTmp.get(i).getLatitude(), agenceTmp.get(i).getLongitude(), lFTmp.get(lastIndexLf).getLatitude(), lFTmp.get(lastIndexLf).getLongitude());
                    price -= 0.4 * agenceTmp.get(i).getNbPersonne() * distanceOld;
                    
                    if ((lFTmp.get(indice).getNbPlacesRestantes() + agenceTmp.get(i).getNbPersonne()) == 60) {
                        price -= 3000;
                    }
                    
                    traite = true;
                    
                } else {
                    rand = new Random();

                    choice = rand.nextInt(2);
                    if (choice == 0) {
                        indice = rand.nextInt(lFTmp.size() - 1); //indice du nouveau lieu de formation
                    } else {
                        int random = rand.nextInt(agenceTmp.size() - 1); //indice du nouveau lieu de formation
                        indice = agenceTmp.get(random).getIndexOfLieuFormation();
                    }
                }   
            }
            
            if (price < bestVoisin) {
                change = true;
                bestVoisin = price;
                indiceVille = indice;
                indiceAgence = i;
                meilleurVoisin.setListeAgences(agenceTmp);
                meilleurVoisin.setTotalPrice(price);
            }
        }
        
        if( change == true) {
            int lastIndexLf = agenceTmp.get(indiceAgence).getIndexOfLieuFormation(); //Récupération de l'ancien index du Lf
            agenceTmp.get(indiceAgence).setIdLieuFormation(lFTmp.get(indiceVille).getId()); //Set de l'ID
            agenceTmp.get(indiceAgence).setIndexOfLieuFormation(indiceVille); //Set de l'Index
            agenceTmp.get(indiceAgence).setIndexOfLastLieuFormation(lastIndexLf); //Ancien index.

            int capaciteRestante = lFTmp.get(indiceVille).getNbPlacesRestantes();
            lFTmp.get(indiceVille).setNbPlacesRestantes(capaciteRestante - agenceTmp.get(indiceAgence).getNbPersonne());

            if (lFTmp.get(indiceVille).getIsOpen() == false) {
                lFTmp.get(indiceVille).setIsOpen(true);
            }
            int capaciteRestanteOldLf = lFTmp.get(lastIndexLf).getNbPlacesRestantes();
            lFTmp.get(lastIndexLf).setNbPlacesRestantes(capaciteRestanteOldLf + agenceTmp.get(indiceAgence).getNbPersonne());

            if (lFTmp.get(indiceVille).getNbPlacesRestantes() == 60) {
                lFTmp.get(indiceVille).setIsOpen(false);
            }
        }
        
        return meilleurVoisin;
    }
    
    public void startTabou(int nbIte) {
        
        initialisation();
        long debut = System.currentTimeMillis();
        //Initialisation
        genererSolutionInitiale();        

        for (int i = 0; i<nbIte; i++) {
            Solution s = genererVoisinage();
            List<Ville> villeCLose = new ArrayList<>();
            villeCLose.addAll(cloneListVille(getListeVilleCloseWithoutAgence()));
            
            
            if (!control()) {
                System.err.println("Erreur");
            } 
            if (s.getTotalPrice() < solution.getTotalPrice()) {
                majListeFormation(s.getListeAgences(), villeCLose);
                solution = cloneSolution(s);
            }
        }  
        
        long end = System.currentTimeMillis();
        
        System.out.println(        String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(end-debut),
                TimeUnit.MILLISECONDS.toSeconds(end-debut)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end-debut))
        ));
        vue.afficheInfos();
    }
    
    public List<Ville> getLieuxFormation() {
        List<Ville> lieuFormation = new ArrayList();
        for (Ville listeVille : listeVilles) {
            if (listeVille.getIsOpen() && !lieuFormation.contains(listeVille)) {
                lieuFormation.add(listeVille);
            }
        }
        return lieuFormation;
    }
    
    public List<Ville> getLieuxFormation(List<Ville> listeLieuFormation) {
        List<Ville> lieuFormation = new ArrayList();
        for (Ville listeVille : listeLieuFormation) {
            if (listeVille.getIsOpen()) {
                lieuFormation.add(listeVille);
            }
        }
        return lieuFormation;
    }
    
    public void majListeFormation(List<Agence> listeAgenceAttachees, List<Ville> allVilleClose) {


        for (Agence agence : listeAgenceAttachees) {
            allVilleClose.get(agence.getIndexOfLieuFormation()).setIsOpen(true);
            int capaciteRestante = allVilleClose.get(agence.getIndexOfLieuFormation()).getNbPlacesRestantes();
            allVilleClose.get(agence.getIndexOfLieuFormation()).setNbPlacesRestantes(capaciteRestante - agence.getNbPersonne());
            allVilleClose.get(agence.getIndexOfLieuFormation()).getListeAgences().add(agence);
        }
        
        this.setListeVilles(allVilleClose);
    }
    
    public boolean control(){
        for (Agence listeAgence : listeAgences) {
            if (listeAgence.getIndexOfLieuFormation() < 0) {
                return false;
            }
        }
        return true;
    }
    
    public int getNbParticipantFromLF(Ville v) {
        int nbPersonne = 0;
        for (int i=0; i< v.getListeAgences().size(); i++) {
            nbPersonne += v.getListeAgences().get(i).getNbPersonne();
        }
        
        return nbPersonne;
    }

    /**
     * @return the solution
     */
    public Solution getSolution() {
        return solution;
    }

    /**
     * @param listeVilles the listeVilles to set
     */
    public void setListeVilles(List<Ville> listeVilles) {
        this.listeVilles = listeVilles;
    }
    
    public List<Ville> getLieuFormationFromAgence(List<Agence> listeAgence) 
    {
        List<Ville> lieuFormation = new ArrayList();
        for (Agence listeAgence1 : listeAgence) {
            listeVilles.get(listeAgence1.getIndexOfLieuFormation()).setIsOpen(true);
            if (!lieuFormation.contains(listeVilles.get(listeAgence1.getIndexOfLieuFormation()))) {
                lieuFormation.add(listeVilles.get(listeAgence1.getIndexOfLieuFormation()));
            }
        }
        return lieuFormation;
    }

    /**
     * @return the listeVilleCloseWithoutAgence
     */
    public List<Ville> getListeVilleCloseWithoutAgence() {
        return listeVilleCloseWithoutAgence;
    }
    
    public List<Ville> closeAllVille(List<Ville> listeVilleToClose) 
    {
        for (Ville listeVilleToClose1 : listeVilleToClose) {
            listeVilleToClose1.setIsOpen(false); //Set close
            for (int j = 0; j < listeVilleToClose1.getListeAgences().size(); j++) {
                listeVilleToClose1.getListeAgences().remove(j); //On supprime toutes les agences liées à cette ville
            }
        }
        return listeVilleToClose;
    }

    public List<Ville> cloneListVille(List<Ville> lV) 
    {
        List<Ville> listeReturn = new ArrayList<>();

        for (Ville v : lV) {
            Ville v2 = new Ville(v.getId(), v.getNom(), v.getCodePostal(), v.getLongitude(), v.getLatitude());
            v2.setIsOpen(v.getIsOpen());
            v2.setNbPlacesRestantes(v.getNbPlacesRestantes());
            listeReturn.add(v2);
        }
        return listeReturn;
    }
    
    public List<Agence> cloneListeAgence(List<Agence> lA)
    {
        List<Agence> listeReturn = new ArrayList<>();
        
        for (Agence a : lA) {
            Agence a2 = new Agence(a.getId(), a.getNom(), a.getCodePostal(), a.getLongitude(), a.getLatitude(), a.getNbPersonne());
            a2.setIndexOfLieuFormation(a.getIndexOfLieuFormation());
            listeReturn.add(a2);
        }
        return listeReturn;
    }
    
    public Solution cloneSolution(Solution s) 
    {
        Solution solutionRetour = new Solution();
        solutionRetour.setListeAgences(s.getListeAgences());
        solutionRetour.setListeVilles(this.getListeVilles());
        solutionRetour.setTotalPrice(s.getTotalPrice());
        
        return solutionRetour;
    }
    
}
