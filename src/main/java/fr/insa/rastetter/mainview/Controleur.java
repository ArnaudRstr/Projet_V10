/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
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
    private int etatAtelier;
    private String etatFenetre;
    
    private FenetreEntreeDonnees fenetreEntreeDonnees;
    
    
    
    
    public Controleur(VuePrincipale main,int etat,String etatFenetre){
        this.main = main;
        this.etatAtelier = etat;
        this.etatFenetre = etatFenetre;
               
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
        this.etatFenetre= "machine";
        
        
        
        
        Notification.show("machines via controleur");
        
        
    }
    
    public void MenuItemProduit() {
        Notification.show("Produits via controleur");
    }
    
    
    
    
    public void MenuItemDetailsAtelier() throws SQLException{
        
        
        MyVerticalLayout contenu = new MyVerticalLayout();
        NumberField id = new NumberField();
        TextField nom = new TextField();
        TextArea des = new TextArea();
        
        
        NumberField dimLong = new NumberField();
        NumberField dimLarg = new NumberField();
        Dialog fenetre = new Dialog();
        
        Button boutonModifNom = new Button(new Icon("lumo","edit"));
        Button boutonModifDes = new Button(new Icon("lumo","edit"));

        MyHorizontalLayout hlnom = new MyHorizontalLayout();
        MyHorizontalLayout hldes = new MyHorizontalLayout();
        
        
        nom.setLabel("Nom de l'atelier");
        des.setLabel("Description");
        id.setLabel("Identifiant");
        
        
        nom.setReadOnly(true);
        des.setReadOnly(true);
        id.setReadOnly(true);
                
        
        //Il faut encore ajouter les placeHolder et les boutons pour activer les zones.

        ArrayList<Atelier> listTemp =  new ArrayList();
       
        listTemp=listaltelier(this.getVuePrincipale().getGestionBDD().conn);
        
        int index =0;
        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);

            if(atelierTemp.getId()==this.etatAtelier){
                
                nom.setValue(atelierTemp.getNom());
                id.setValue((double)Math.round(atelierTemp.getId()));
                des.setValue(atelierTemp.getDes());
                
                hlnom.add(nom,boutonModifNom);
                //hlnom.add(boutonModifNom);
                
                hldes.add(des,boutonModifDes);
                
                
                hlnom.setAlignItems(FlexComponent.Alignment.END);
                hldes.setAlignItems(FlexComponent.Alignment.END);
                
                contenu.add(hlnom);
                contenu.add(id);
                contenu.add(hldes);
                fenetre.add(contenu);
                fenetre.setHeaderTitle("Détails de l'atelier");
                Button boutonFermer = new Button(new Icon("lumo","cross"));
                fenetre.getHeader().add(boutonFermer);
                fenetre.open();
                
                boutonFermer.addClickListener(event -> {
                    fenetre.close();
        });
                
                
                
                
                
                
                
                
            }
            
            
            index++;
        }
        
        
        
        
        
        
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
    
    
    
    
    
    

    
  
    
    
    public void OuvrirFenetreEntree(String objet){
        
        
        
        if (objet=="atelier"){
            

        }
        
            
        
    }
    
    
    
    
    public void CreationObjet(String objet) throws SQLException{
        
        
        
        
        if(objet =="atelier"){
            
            
            System.out.println("la fenetre va être créée");
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"atelier");
            
            System.out.println("La fenetre devrait être crée");
            
            
            
            
            
//            ArrayList donneesText = new ArrayList();
//            ArrayList<Double> donneesNum = new ArrayList();
//
//            donneesText = this.fenetreEntreeDonnees.getDonneesText();
//            donneesNum = this.fenetreEntreeDonnees.getDonneesNum();
//            System.out.println("données avant création " + this.fenetreEntreeDonnees.getDonneesText()+this.fenetreEntreeDonnees.getDonneesNum());
            
            //this.main.getGestionBDD().addatelier(this.main.getGestionBDD().conn,(String) donneesText.get(0),(String) donneesText.get(1),(int)Math.round(donneesNum.get(0)),(int) Math.round(donneesNum.get(1)));
        //System.out.println("l'atelier devrait être créé");
        
        
        
        //Penser à enlever les arrondis si on change le type de la dimension de l'atelier
        System.out.println("Controleur : essai de mise à jour du combobox");
        //this.main.getEntete().setComboBoxAtelier(listaltelier(this.main.getGestionBDD().conn));
         
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
    
    
    public int getEtatAtelier(){
        
        return this.etatAtelier;
    }
    
        public String getEtatFenetre(){
        
        return this.etatFenetre;
    }
        
        
    public void setEtatAtelier(int i){
        this.etatAtelier = i; //l'etat correspond à l'identifiant de l'atelier sélectionné. 
    
  
    }
    
    public void setEtatFenetre(String i ){
        this.etatFenetre=i;
    }
    
}
