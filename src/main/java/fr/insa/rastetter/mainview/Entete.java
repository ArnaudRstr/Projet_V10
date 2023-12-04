/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
/**
 *
 * @author arnaud
 */
public class Entete extends MyHorizontalLayout {
    
    public VuePrincipale main;
    
    
    
    private Button CompteUtilisateur;
    private MenuBar menuBar = new MenuBar();
    private MenuBar menuFichier = new MenuBar();
    
    
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
        MenuItem menu = menuBar.addItem("Menu", e -> {
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
        
        
        
        
    
        //menuBar.addItem(menu);
        this.add(menuBar);
        this.add(menuFichier);
        
        this.add(new H1("ça c'est l'entête"));

        
        CompteUtilisateur = new Button("Compte");
        this.add(CompteUtilisateur);
        
        //this.add(new Paragraph("merci de vous connecter"));
        
}
    
    

    
}
