/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import static fr.insa.moly.GestionBDD.GestionBDD.delete;
import fr.insa.moly.objet.Atelier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fr.insa.moly.GestionBDD.GestionBDD.listAtelier;
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Operateur;
import fr.insa.moly.objet.Operation;
import fr.insa.moly.objet.Produit;

/**
 *
 * @author arnaud
 */
public class FenetreAvertissementSuppression extends Dialog {
    
    private Button boutonContinuer;
    private Button boutonAnnuler;
    private Button boutonFermer;
    private Button boutonEnregistrer;
    private MyVerticalLayout contenu;
    
    private Controleur controleur;

    //public FenetreAvertissementSuppression(Controleur controleur,String type,String text,int id) throws SQLException{
public FenetreAvertissementSuppression(Controleur controleur,String type,Object objet) throws SQLException{
    
    this.controleur=controleur;
    this.contenu=new MyVerticalLayout();
    this.boutonAnnuler =new Button("Annuler");
    this.boutonContinuer=new Button("Supprimer définitivement");
    this.boutonFermer = new Button(new Icon("lumo","cross"));
    this.boutonEnregistrer = new Button ("Ouvrir rapport de suppression");
    this.setHeaderTitle("Suppression");
    this.getHeader().add(boutonFermer);
    
    this.getFooter().add(boutonAnnuler,boutonContinuer);
    
    
    this.contenu.add(new H3("Etes-vous sûr de vouloir supprimer la sélection? "));
    MyHorizontalLayout hlselection = new MyHorizontalLayout();
    
    if (type == "atelier"){
        hlselection.add(((Atelier)objet).getNom());
        
        boutonEnregistrer.addClickListener(event -> {
        //Rapport de suppression
        Dialog rapportsupp = new Dialog();
        MyVerticalLayout contenurapport = new MyVerticalLayout();

        try {
            contenurapport.add();
            
            ArrayList<String> stringList = new ArrayList();
            stringList =((Atelier)objet).getGrandChildList(this.controleur.getVuePrincipale().getGestionBDD().conn);
            stringList.forEach(element -> contenurapport.add(new Text(element)));
            
            rapportsupp.open();
            
        } catch (SQLException ex) {
            System.out.println("L'ouverture du rapport a échoué");
        }

       });
        
    }
    if (type == "machine"){
        hlselection.add(((Machine)objet).getNom());
    }
    if (type == "produit"){
        hlselection.add(((Produit)objet).getRef());
    }
    if (type == "brut"){
        hlselection.add(((Brut)objet).getNom());
    }
    if (type == "operateur"){
        hlselection.add(((Operateur)objet).getNom());
    }
    if (type == "operation"){
        hlselection.add(((Operation)objet).getNom());
    }
    
    
    
    
    //hlselection.add(new Text(text));
    hlselection.setWidthFull();
    hlselection.getStyle().set("border", "1px solid #000000");
    this.contenu.add(hlselection);
    
    MyHorizontalLayout hlenregistrer = new MyHorizontalLayout();
    hlenregistrer.setWidthFull();
    hlenregistrer.getStyle().set("align-items", "center");
    //hlenregistrer.add(new Text("Enregistrer le rapport de suppression"));
    hlenregistrer.add(boutonEnregistrer);
    this.contenu.setSpacing(true);
    this.contenu.add(hlenregistrer);
    hlenregistrer.setAlignItems(FlexComponent.Alignment.CENTER);
    this.contenu.setAlignItems(FlexComponent.Alignment.CENTER);
    this.add(contenu);
    
    this.open();
    
    
    boutonFermer.addClickListener(event -> {
           this.close();   
       });
    boutonAnnuler.addClickListener(event -> {
           this.close();   
       });
    
    
    
    
    
    
        boutonContinuer.addClickListener(event -> {
           this.close(); 
           
           
        if (type == "atelier"){
            
        
        try {
            
            
            
            delete(this.controleur.getVuePrincipale().getGestionBDD().conn,type,((Atelier)objet).getId());
            System.out.println("suppression devrait être effectuée : id  : "+((Atelier)objet).getId());

        } catch (SQLException ex) {
                   System.out.println("erreur dans la suppression de l'atelier");
        }      
        
        try { //On reactualise le combobox
            this.controleur.getVuePrincipale().getEntete().setComboBoxAtelier(listAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn));
        } catch (SQLException ex) {
            Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        }
        
        if (type=="brut"){
            
               try {
                   delete(this.controleur.getVuePrincipale().getGestionBDD().conn,type,((Brut)objet).getId());
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
               }
               try {
                   this.controleur.MenuItemBrut();
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
        }
        
        if (type=="produit"){
            
               try {
                   delete(this.controleur.getVuePrincipale().getGestionBDD().conn,type,((Produit)objet).getId());
               } catch (SQLException ex) {
                   System.out.println("erreur dans la suppression du produit");
               }
               try {
                   this.controleur.MenuItemProduit();
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
        }
        if (type=="machine"){
            try {
                   delete(this.controleur.getVuePrincipale().getGestionBDD().conn,type,((Machine)objet).getId());
               } catch (SQLException ex) {
                   System.out.println("erreur dans la suppression de la machine");
               }
               try {
                   this.controleur.MenuItemMachine();
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
            
        }

        if (type=="operation"){
            try {
                   delete(this.controleur.getVuePrincipale().getGestionBDD().conn,type,((Operation)objet).getId());
               } catch (SQLException ex) {
                   System.out.println("erreur dans la suppression de l'operation");
               }
               try {
                   this.controleur.MenuItemOperations();
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
            
        }
        
        if (type=="operateur"){
            try {
                   delete(this.controleur.getVuePrincipale().getGestionBDD().conn,type,((Operateur)objet).getId());
               } catch (SQLException ex) {
                   System.out.println("erreur dans la suppression de l'operateur");
               }
               try {
                   this.controleur.MenuItemOperateur();
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
            
        }

       });
        
        
        
    
}
}
