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

    public void MenuItemMachine() {
        this.main.setMainContent(new FenetrePartagee());
        
        
        
        
        Notification.show("machines via controleur");
        
        
    }
    
    public void MenuItemProduit() {
        Notification.show("Produits via controleur");
    }
    
    
    
    
    
    
    
    public void MenuItemAjouterAtelier() throws SQLException, InterruptedException{
        Notification.show("Ajouter Atelier via controleur");
        
        this.main.getGestionBDD().addatelier(this.main.getGestionBDD().conn,"test 10","Description",14,25);
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
    
    
    
    
    
    
    
    public void ComboBoxAtelier(int i){
    this.etat = i; //l'etat correspond à l'identifiant de l'atelier sélectionné. 
    

        
    }
    
    
    
    //Aucun atelier sélectionné : etat = -1
    //Atelier 1 selectionné : etat 1 etc...
    
    
    
}
