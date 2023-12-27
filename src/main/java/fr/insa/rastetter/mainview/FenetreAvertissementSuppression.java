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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */
public class FenetreAvertissementSuppression extends Dialog {
    
    private Button boutonContinuer;
    private Button boutonAnnuler;
    private Button boutonFermer;
    private MyVerticalLayout contenu;
    private Controleur controleur;

    
public FenetreAvertissementSuppression(Controleur controleur,String text,int id){
    
    this.controleur=controleur;
    this.contenu=new MyVerticalLayout();
    this.boutonAnnuler =new Button("Annuler");
    this.boutonContinuer=new Button("Supprimer définitivement");
    this.boutonFermer = new Button(new Icon("lumo","cross"));

    this.setHeaderTitle("Suppression");
    this.getHeader().add(boutonFermer);
    
    this.getFooter().add(boutonAnnuler,boutonContinuer);
    
    
    this.contenu.add(new H3("Etes-vous sûr de vouloir supprimer la sélection? "));
    this.contenu.add(new Text(text));
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
           
           
        try {
            
            
            
            delete(this.controleur.getVuePrincipale().getGestionBDD().conn,"Atelier",id);
            System.out.println("suppression devrait être effectuée : id atelier : "+id);

        } catch (SQLException ex) {
            Logger.getLogger(FenetreAvertissementSuppression.class.getName()).log(Level.SEVERE, null, ex);
        }
       });
        
      
    
    
}
}
