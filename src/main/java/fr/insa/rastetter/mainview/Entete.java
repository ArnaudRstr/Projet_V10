/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import java.sql.Connection;
/**
 *
 * @author arnaud
 */
public class Entete extends MyHorizontalLayout {
    
    public VuePrincipale main;
    
    
    
    
    
    //Boutons
    private Button boutonCompteUtilisateur;
    
    
    
    
    
    
    
    
    private MenuBar menuBar;
    private MenuBar menuFichier;
    private MenuItem menuItemMenu;
    
    
    
    //Pour le bouton de menu principal
    private SubMenu subMenuMenuPrincipal;
    private MenuItem menuItemPlan;
    private MenuItem menuItemMachine;
    private MenuItem menuItemProduit;
    
    private MenuItem menuItemAtelier;
    private SubMenu subMenuAtelier;
    private MenuItem menuItemAjouterAtelier;
    
    
    public Entete(VuePrincipale main) {
        this.main = main;
        
        this.addClassName("Custom-Entete");
        //this.getStyle().set("border", "1px solid #000000");
        this.getStyle().set("padding", "10px");
        this.getStyle().set("border-radius", "7px");
        this.getStyle().set("background-color","#F8E0E0");

        this.getStyle().set("border", "1px solid #000000");
        this.getStyle().set("padding", "10px");
        this.getStyle().set("border-radius", "0px");

        this.setWidthFull();
        
        
        
        //Configuration de la menubar Menu
        this.menuBar = new MenuBar();
        this.add(this.menuBar);
        
                
        this.menuItemMenu= this.menuBar.addItem("Menu Principal");
        
        //On crée un sous menu du menu item du menu principal
        this.subMenuMenuPrincipal= menuItemMenu.getSubMenu();
        
        //On ajoute les menu item au sous menu du menuitem du menu principal
        this.menuItemPlan = this.subMenuMenuPrincipal.addItem("Plan");
        this.menuItemMachine = this.subMenuMenuPrincipal.addItem("Machines");
        this.menuItemProduit = this.subMenuMenuPrincipal.addItem("Produits");

        
        this.menuItemAtelier=this.menuBar.addItem("Atelier");
        this.subMenuAtelier=menuItemAtelier.getSubMenu();
        
        
        this.menuItemAjouterAtelier=this.subMenuAtelier.addItem("Ajouter");
        
        
        
        
        
        
        
        
        
        
     /*           addItem("Menu", e -> {
    // Code à exécuter lors du clic sur le MenuItem
    Notification.show("MenuItem cliqué !");
    });
        
     
     
     
        SubMenu Plan = menu.getSubMenu();
        Plan.addItem("Plan", e -> {
    
    this.main.setMainContent(new VuePlan());
    Notification.show("Plan cliqué !");
    
    
    });
        
        
        SubMenu Machine = menu.getSubMenu();
        Machine.addItem("Machine", e -> {
    this.main.setMainContent(new FenetrePartagee());
    
    Notification.show("machine cliqué !");
    });
        SubMenu Produit = menu.getSubMenu();
        Machine.addItem("Produit", e -> {
    // Code à exécuter lors du clic sur le MenuItem
    Notification.show("Produit cliqué !");
    });
        
       
        
        
        
        
        //Configuration de la menubar Fichier

    MenuItem fichier = menuFichier.addItem("Fichier", e -> {
    // Code à exécuter lors du clic sur le MenuItem
    Notification.show("MenuItem cliqué !");
    });

        SubMenu modifier = fichier.getSubMenu();
        modifier.addItem("Modifier", e -> {
    
    Notification.show("modifier");
 
    });
    
    SubMenu supprimer = fichier.getSubMenu();
        modifier.addItem("Supprimer", e -> {
    
    Notification.show("Supprimer");
 
    });
    */
        
        
        
    
        //menuBar.addItem(menu);
        //this.add(menuBar);
        //this.add(menuFichier);
        
        this.add(new H1("ça c'est l'entête"));

        
        //CompteUtilisateur = new Button("Compte");
        //this.add(CompteUtilisateur);
        
        //this.add(new Paragraph("merci de vous connecter"));
        
        
        
        
        //On crée et on ajoute les boutons
        this.boutonCompteUtilisateur = new Button("Compte");
        this.add(boutonCompteUtilisateur);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //On renvoie l'événement vers le controleur de la vue principale
        boutonCompteUtilisateur.addClickListener(event -> {
            Notification.show("Option Compte sélectionnée !");
            this.main.getControleur().boutonCompte();
        });
        
        
        
        
        menuItemPlan.addClickListener(event -> {
            Notification.show("Option Plan sélectionnée !");
            this.main.getControleur().MenuItemPlan();
        });
        
        
        menuItemMachine.addClickListener(event -> {
            Notification.show("Option machine sélectionnée !");
            this.main.getControleur().MenuItemMachine();
        });
        
        
        
        menuItemProduit.addClickListener(event -> {
            Notification.show("Option produit sélectionnée !");
            this.main.getControleur().MenuItemProduit();
        });
        
        
        menuItemAjouterAtelier.addClickListener(event -> {
            Notification.show("Option produit sélectionnée !");
            this.main.getControleur().MenuItemAjouterAtelier();
            
            
            
            
        });

}
    
    

    
}
