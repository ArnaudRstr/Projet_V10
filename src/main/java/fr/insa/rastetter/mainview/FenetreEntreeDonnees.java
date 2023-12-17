/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;
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
    private TextArea des;
    private NumberField dimLargeur;
    private NumberField dimLongueur;
    private MenuBar menuBar;
    
    private Button boutonFermer;
    
    
    private Controleur controleur;
    
    
    
    public FenetreEntreeDonnees(Controleur controleur, String objet){
        this.controleur=controleur;
        this.contenu= new MyVerticalLayout();
        this.boutonAnnuler=new Button("Annuler");
        this.boutonEnregistrer=new Button("Enregistrer");
        this.donneesText= new ArrayList();
        this.donneesNum = new ArrayList();
        this.menuBar=new MenuBar();
        this.boutonFermer = new Button(new Icon("lumo","cross"));

       
       
       
       
      
       //Pour un atelier
    if (objet =="atelier"){
        
        this.nom = new TextField("Nom");
        this.des = new TextArea("Description");
        this.dimLongueur = new NumberField("Longueur (cm)");
        this.dimLargeur = new NumberField("Largeur (cm)");
        
        this.setHeaderTitle("Ajouter un atelier");
        this.getHeader().add(boutonFermer);

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
        boutonFermer.addClickListener(event -> {
           this.close();   
       });

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
                this.controleur.getVuePrincipale().getGestionBDD().addatelier(this.controleur.getVuePrincipale().getGestionBDD().conn,this.nom.getValue(),this.des.getValue(),(int)Math.round(this.dimLongueur.getValue()),(int) Math.round(this.dimLargeur.getValue()));
                System.out.println("l'atelier devrait être affiché (via Fenetre)");
                this.controleur.getVuePrincipale().getEntete().setComboBoxAtelier(listaltelier(this.controleur.getVuePrincipale().getGestionBDD().conn));
                System.out.println("Combobox mis à jour (via Fenetre)");
                //this.controleur.MenuItemMachine();
                //this.controleur.getEtatFenetre()

                //this.controleur.CreationObjet("atelier");
            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
        });
       
       
       
       
       
    }
    
    
    if (objet =="machine"){
        
        this.nom = new TextField("Nom");
        this.des = new TextArea("Description");
        this.dimLongueur = new NumberField("Longueur (cm)");
        this.dimLargeur = new NumberField("Largeur (cm)");

        NumberField idTypeOperation = new NumberField("Id type operation (int)");
        NumberField puissance = new NumberField("Puissance (W)");
        NumberField coutHoraire = new NumberField("Coût horaire (€)");
        TextField marque = new TextField("Marque");
        TextField localisation = new TextField("Localisation (String)");
        NumberField statut = new NumberField("Statut (int)");


        
        
        
        
        this.setHeaderTitle("Ajouter une machine");
        this.getHeader().add(boutonFermer);
        
        this.contenu.add(nom);
        this.contenu.add(des);
        this.contenu.add(marque);
        this.contenu.add(idTypeOperation);
        this.contenu.add(puissance);
        this.contenu.add(coutHoraire);
        this.contenu.add(statut);
        this.contenu.add(dimLongueur);
        this.contenu.add(dimLargeur);
        this.contenu.add(localisation);
        
        
        this.menuBar.addItem(boutonAnnuler);
        this.menuBar.addItem(boutonEnregistrer);
        this.contenu.add(menuBar);
        this.contenu.setAlignItems(CENTER);
        
        this.add(contenu);
        this.open();
        boutonFermer.addClickListener(event -> {
           this.close();   
       });
        
        boutonAnnuler.addClickListener(event -> {
           this.close();   
       });
        
       boutonEnregistrer.addClickListener(event -> {
            //Il faut encore faire un contrôle de saisie ici
            
            String nomTemp = nom.getValue();
            String desTemp = des.getValue();
            String marqueTemp= marque.getValue();
            int idtypeoperationtemp = (int)Math.round(idTypeOperation.getValue());
            double puissancetemp = puissance.getValue();
            double couthorairetemp= coutHoraire.getValue();
            double dimLongtemp= dimLongueur.getValue();
            double dimLargtemp =dimLargeur.getValue();
            String locatemp=localisation.getValue();
            int statuttemp= (int) Math.round(statut.getValue());
            this.close();

            try {
                this.controleur.getVuePrincipale().getGestionBDD().addmachine(this.controleur.getVuePrincipale().getGestionBDD().conn,nomTemp,this.controleur.getEtatAtelier(),idtypeoperationtemp,desTemp,marqueTemp,puissancetemp,statuttemp,couthorairetemp,locatemp,dimLargtemp,dimLongtemp);
                this.controleur.MenuItemMachine();

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
