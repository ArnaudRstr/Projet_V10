/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.notification.Notification;
import fr.insa.moly.GestionBDD.GestionBDD;
import fr.insa.moly.objet.Atelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;

import static fr.insa.rastetter.mainview.MyHorizontalLayout.CSS_COLOR;
import java.sql.SQLException;

/**
 *
 * @author arnaud
 */




public class Controleur {
    
    private VuePrincipale main;
    
    
    
    public Controleur(VuePrincipale main){
        this.main = main;
               
    }
    
        
    
    //On gère les événements pour les boutons et différents composants
    
    
    public void boutonConnect(){
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
    
    
    public void MenuItemAjouterAtelier() throws SQLException{
        Notification.show("Ajouter Atelier via controleur");
        //this.main.getGestionBDD().addatelier(this.main.getGestionBDD().conn,"Menuiserie","Description vide",12,20);
        System.out.println("l'atelier devrait être créé");
        Atelier atelierSelect = (Atelier) listaltelier(this.main.getGestionBDD().conn).get(0);
        System.out.println(atelierSelect.getNom());
        System.out.println("les ateliers devraient être affichés");
    }
    
    
}
