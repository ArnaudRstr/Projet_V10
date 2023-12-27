/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
import static fr.insa.moly.GestionBDD.GestionBDD.listtypeoperation;
import fr.insa.moly.objet.Typeoperation;
import java.sql.Connection;
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

    private MyVerticalLayout contenuVL;
    private MyHorizontalLayout contenuHL;
    
    
    private ArrayList donneesText;
    private ArrayList<Double> donneesNum;
    
    
    private TextField nom;
    private TextArea des;
    private NumberField dimLargeur;
    private NumberField dimLongueur;
    private MenuBar menuBar;
    
    private Button boutonFermer;
    
    
    private Controleur controleur;
    
    
    
    public FenetreEntreeDonnees(Controleur controleur, String objet) throws SQLException{
        this.controleur=controleur;
        this.contenuVL= new MyVerticalLayout();
        this.contenuHL= new MyHorizontalLayout();
        this.boutonAnnuler=new Button("Annuler");
        this.boutonEnregistrer=new Button("Enregistrer");
        this.donneesText= new ArrayList();
        this.donneesNum = new ArrayList();
        this.menuBar=new MenuBar();
        this.boutonFermer = new Button(new Icon("lumo","cross"));

        this.getFooter().add(boutonAnnuler,boutonEnregistrer);
        this.getHeader().add(boutonFermer);

       
       
       
      
       //Pour un atelier
    if (objet =="atelier"){
        
        this.nom = new TextField("Nom");
        this.des = new TextArea("Description");
        this.dimLongueur = new NumberField("Longueur (cm)");
        this.dimLargeur = new NumberField("Largeur (cm)");
        
        this.setHeaderTitle("Ajouter un atelier");

        this.contenuVL.add(nom);
        this.contenuVL.add(des);
        this.contenuVL.add(dimLongueur);
        this.contenuVL.add(dimLargeur);
        

        this.add(contenuVL);
       
        this.contenuVL.setAlignItems(CENTER);
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

        
        
        MyVerticalLayout colonne1 = new MyVerticalLayout();
        MyVerticalLayout colonne2 = new MyVerticalLayout();

        
        ComboBox comboBoxTypeOperation = new ComboBox();
        comboBoxTypeOperation.setLabel("Type d'opération");
        
        ArrayList<Typeoperation> listtemp = new ArrayList();
        
        listtemp=listtypeoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
        
        ArrayList listajouter = new ArrayList();

        int index =0;
        while(index< listtemp.size()){
            
            listajouter.add(listtemp.get(index).getId()+" : "+listtemp.get(index).getNom());
            index++;
        }
        
        comboBoxTypeOperation.setItems(listajouter);
        
        
        
        this.setHeaderTitle("Ajouter une machine");
        
        colonne1.add(nom);
        colonne1.add(des);
        colonne1.add(marque);
        //colonne1.add(idTypeOperation);
        colonne1.add(comboBoxTypeOperation);
        colonne1.add(puissance);
        colonne2.add(coutHoraire);
        colonne2.add(statut);
        colonne2.add(dimLongueur);
        colonne2.add(dimLargeur);
        colonne2.add(localisation);
        
        this.contenuHL.add(colonne1,colonne2);
        this.contenuVL.add(this.contenuHL);
        

        this.contenuVL.setAlignItems(CENTER);
        
        this.add(contenuVL);
        
        
        
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
            //int idtypeoperationtemp = (int)Math.round(idTypeOperation.getValue());
            
            String idNom = (String) comboBoxTypeOperation.getValue();
            int indexEspace = idNom.indexOf(' ');
            String avantEspace = idNom.substring(0,indexEspace);
            int idtypeoperationtemp = Integer.parseInt(avantEspace);
       
            
            
            
            
            
            double puissancetemp = puissance.getValue();        //PAS BESOIN DE TOUT çA, ON PEUT METTRE DANS LA METHODE DE CREATION DIRECTEMENT (RESTE A FAIRE)
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
       
       
       
       if (objet=="produit"){
           
           System.out.println("Creation de produit");
           
            this.setHeaderTitle("Ajouter un produit");

            this.nom = new TextField("Nom");
            this.des = new TextArea("Description");
            TextField tfref = new TextField("Référence");
            NumberField nbidbrut = new NumberField("Identifiant du brut");
            



            this.contenuVL.add(nom,tfref, des,nbidbrut);
            this.add(contenuVL);
            
            this.open();
           
           
           
           
            boutonFermer.addClickListener(event -> {
            this.close();   
        });

         boutonAnnuler.addClickListener(event -> {
            this.close();   
        });
        
        boutonEnregistrer.addClickListener(event -> {
            
            
            this.close();
               try {
                   this.controleur.getVuePrincipale().getGestionBDD().addproduit(this.controleur.getVuePrincipale().getGestionBDD().conn,tfref.getValue(),des.getValue(),(int) Math.round(nbidbrut.getValue()));
                   this.controleur.MenuItemProduit();
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout du produit");
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
