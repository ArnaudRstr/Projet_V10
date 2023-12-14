/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */
public class FenetreEntreeDonnees extends Dialog{
    
    
    
    
    private Button boutonEnregistrer;
    private Button boutonAnnuler;

    private MyVerticalLayout contenu;
    
    
    
    private ArrayList donneesText;
    private ArrayList<Double> donneesNum;
    
    
    private TextField nom;
    private TextField des;
    private NumberField dimLargeur;
    private NumberField dimLongueur;
    private MenuBar menuBar;
    
    
    
    private Controleur controleur;
    
    
    
    public FenetreEntreeDonnees(Controleur controleur, String objet){
        this.controleur=controleur;
        this.contenu= new MyVerticalLayout();
        this.boutonAnnuler=new Button("Annuler");
        this.boutonEnregistrer=new Button("Enregistrer");
        this.donneesText= new ArrayList();
        this.donneesNum = new ArrayList();
        this.menuBar=new MenuBar();
       
       
       
       
       
      
       //Pour un atelier
    if (objet =="atelier"){
        this.nom = new TextField("Nom");
        this.des = new TextField("Description");
        this.dimLongueur = new NumberField("Longueur");
        this.dimLargeur = new NumberField("Largeur");
        
        
        this.contenu.add(new H2("Ajouter un atelier"));
        this.contenu.add(nom);
        this.contenu.add(des);
        this.contenu.add(dimLongueur);
        this.contenu.add(dimLargeur);
        
        this.menuBar.addItem(boutonAnnuler);
        this.menuBar.addItem(boutonEnregistrer);
        this.contenu.add(menuBar);
        this.add(contenu);
       
        this.contenu.setAlignItems(CENTER);
        this.open();
       

        boutonAnnuler.addClickListener(event -> {
           this.close();   
       });
       
       
       
       
        boutonEnregistrer.addClickListener(event -> {
            //Il faut encore faire un contrôle de saisie ici
            
            this.donneesText.add((String) this.nom.getValue());
            this.donneesText.add((String) this.des.getValue());
            this.donneesNum.add(this.dimLongueur.getValue());
            this.donneesNum.add(this.dimLargeur.getValue());   
            this.close();
            
            Notification.show("Données enregistrées");
            try {
                this.controleur.CreationObjet("atelier");
            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
       
       
       
       
    }
       
        
        

    
       
       
       
       
    
    
    
    
    
   
       

       
       
       
       
       
       
        
    }
    
    
    public ArrayList getDonneesText(){
        
        return this.donneesText;
    }
    
    public ArrayList getDonneesNum(){
        
        return this.donneesNum;
    }
    public void OuvrirFenetre(){
        this.open();
        
        
        
    }
   
    
    
    
}
