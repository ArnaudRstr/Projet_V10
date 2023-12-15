/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.notification.Notification;
import fr.insa.moly.objet.Atelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author arnaud
 */




public class Controleur {
    
    private VuePrincipale main;
    private int etat;
    
    private FenetreEntreeDonnees fenetreEntreeDonnees;
    
    
    
    
    public Controleur(VuePrincipale main,int etat){
        this.main = main;
        this.etat = etat;
               
    }
    
        
    
    //On gère les événements pour les boutons et différents composants
    
    
    public void boutonConnect() throws SQLException{
        this.main.setMainContent(new VuePlan());
        this.main.setEntete(new Entete(this.main));
        
    }
    
    
    public void boutonCompte() {
        Notification.show("Compte via controleur");
        System.out.println("compte via controleur ");        
    }
    
    public void MenuItemPlan() {
        this.main.setMainContent(new VuePlan());
        Notification.show("plan via controleur");
    }

    public void MenuItemMachine() throws SQLException {
        
        this.main.setMainContent(new FenetrePartagee(this, "machine"));
        
        
        
        
        
        Notification.show("machines via controleur");
        
        
    }
    
    public void MenuItemProduit() {
        Notification.show("Produits via controleur");
    }
    
    
    
    
    public void MenuItemDetailsAtelier(){
        
        //A compléter
        
        
    }
    
    
    public void MenuItemSupprimerAtelier(){
        
        //On affiche une fenêtre d'avertissement
        new FenetreAvertissementSuppression("Atelier");
        
        
        
    }
    
    
    public void MenuItemAjouterMachine(){
        
        
    }

    
    
    //Plus besoin de cette méthode normalement
    /*
    public void MenuItemAjouterAtelier() throws SQLException, InterruptedException{
        Notification.show("Ajouter Atelier via controleur");
        //On recueuille les données nécessaires grâce à la fenêtre d'entrée de données
        //On ajoute l'atelier avec ce que l'on a comme données
        this.main.getGestionBDD().addatelier(this.main.getGestionBDD().conn,"test 11","Description",14,25);
        System.out.println("l'atelier devrait être créé");
        int index =0;
        ArrayList<Atelier> listTemp= listaltelier(this.main.getGestionBDD().conn);

        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);
            System.out.println(atelierTemp.getId()+" : "+atelierTemp.getNom());
            index++;
        }    
        System.out.println("les ateliers devraient être affichés");  
    }
    */
    
    
    
    
    
    
    public void setEtat(int i){
    this.etat = i; //l'etat correspond à l'identifiant de l'atelier sélectionné. 
    
  
    }
    
  
    
    
    public void OuvrirFenetreEntree(String objet){
        
        
        
        if (objet=="atelier"){
            

        }
        
            
        
    }
    
    
    
    
    public void CreationObjet(String objet) throws SQLException{
        
        
        
        
        if(objet =="atelier"){
            
            
            System.out.println("la fenetre va être créée");
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"atelier");
            System.out.println("La fenetre devrait être crée");
            
            
            ArrayList donneesText = new ArrayList();
            ArrayList<Double> donneesNum = new ArrayList();

            donneesText = this.fenetreEntreeDonnees.getDonneesText();
            donneesNum = this.fenetreEntreeDonnees.getDonneesNum();
            System.out.println("données avant création " + this.fenetreEntreeDonnees.getDonneesText()+this.fenetreEntreeDonnees.getDonneesNum());
            
            this.main.getGestionBDD().addatelier(this.main.getGestionBDD().conn,(String) donneesText.get(0),(String) donneesText.get(1),(int)Math.round(donneesNum.get(0)),(int) Math.round(donneesNum.get(1)));
        System.out.println("l'atelier devrait être créé");
        
        //Penser à enlever les arrondis si on change le type de la dimension de l'atelier
           
        this.main.getEntete().setComboBoxAtelier(listaltelier(this.main.getGestionBDD().conn));
         
        System.out.println("Combobox mis à jour");
        int index =0;
        ArrayList<Atelier> listTemp= listaltelier(this.main.getGestionBDD().conn);
        
        
        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);
            System.out.println(atelierTemp.getId()+" : "+atelierTemp.getNom());
            index++;
        }
        
        System.out.println("les ateliers devraient être affichés");
            
        }
        
        
        if(objet =="machine"){
        
            System.out.println("la fenetre va être créée");
            
            
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"machine");
            System.out.println("La fenetre devrait être crée");
            
            
            this.MenuItemMachine();
        }
        
        
        
        
        
        
    }
    
    
    
    
    //Aucun atelier sélectionné : etat = -1
    //Atelier 1 selectionné : etat 1 etc...
    
    public VuePrincipale getVuePrincipale(){
        
        return this.main;
    }
    
    
    public int getEtat(){
        
        return this.etat;
    }
    
}
