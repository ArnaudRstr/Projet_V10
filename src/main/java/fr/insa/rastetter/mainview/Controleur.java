/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import static fr.insa.moly.GestionBDD.GestionBDD.addtypeoperation;
import static fr.insa.moly.GestionBDD.GestionBDD.delete;
import fr.insa.moly.objet.Atelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listtypeoperation;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Typeoperation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fr.insa.moly.GestionBDD.GestionBDD.listAtelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listoperateur;
import fr.insa.moly.objet.Operateur;

/**
 *
 * @author arnaud
 */




public class Controleur {
    
    private VuePrincipale main;
    private int etatAtelier;
    private String etatFenetre;
    
    
    private String identifiantutilisateur;
    
    private FenetrePartagee fenetrePartagee;
    
    private FenetreEntreeDonnees fenetreEntreeDonnees;
    
    
    
    
    public Controleur(VuePrincipale main,int etat,String etatFenetre) throws SQLException{
        this.main = main;
        this.etatAtelier = etat;
        this.etatFenetre = etatFenetre;
        this.fenetrePartagee=new FenetrePartagee(this);
        this.identifiantutilisateur="";
        
               
    }
    
        
    
    //On gère les événements pour les boutons et différents composants
    
    
    public void boutonConnect() throws SQLException{
        //this.main.setMainContent(new VuePlan());
        MenuItemMachine();
        this.main.setEntete(new Entete(this.main));
        
    }
    
    
    public void MenuItemDeconnexion() {
        Notification.show("Deconnexion via controleur");
        
        
        
        
        //ICI il faudra déconnecter l'utilisateur
        
        this.main.setMainContent(new VueInitialeConnection(this.main,this));
        this.main.entete.removeAll();
        this.main.setEntete(new Entete());
        
        
    }
    
    
    public void MenuItemInfosCompte() throws SQLException {
        Notification.show("Deconnexion via controleur");
        
        Dialog dcompte = new Dialog();
        MyVerticalLayout vlcontenu = new MyVerticalLayout();
        vlcontenu.setWidthFull();
        Button boutonEnregistrer=new Button(new H5("Enregistrer"));
        Button boutonFermer = new Button(new Icon("lumo","cross"));
        dcompte.getFooter().add(boutonEnregistrer);
        
        dcompte.setWidth("30vw");
        
        //On récupèrera les infos de la personne connectée ici
        ArrayList <Operateur> listoperateurs = new ArrayList();
        
        listoperateurs = listoperateur (this.main.getGestionBDD().getConn());
        
        //On recherche la personne grâce à l'identifiant (attribut du controleur)
        int i =0;
        boolean valide = false;
        
        Operateur operateurconnecte = new Operateur();
        while(i<listoperateurs.size()||valide == false){
            if(listoperateurs.get(i).getIdentifiant().equals(this.identifiantutilisateur)){
                operateurconnecte= (Operateur) listoperateurs.get(i);
                valide = true;
            }
            i++;
            
        }
        
        
        
        
        this.identifiantutilisateur=operateurconnecte.getIdentifiant();
        
        TextField tfnom = new TextField("Nom");
        tfnom.setReadOnly(true);
        tfnom.setWidthFull();
        tfnom.setValue(operateurconnecte.getNom());
        TextField tfprenom = new TextField("Prénom");
        tfprenom.setReadOnly(true);
        tfprenom.setValue(operateurconnecte.getPrenom());
        tfprenom.setWidthFull();
        TextField tfmdp = new TextField("Mot de passe");
        tfmdp.setReadOnly(true);
        tfmdp.setValue(operateurconnecte.getMotdepasse());
        tfmdp.setWidthFull();
        TextField tfmail = new TextField("Adresse mail");
        tfmail.setReadOnly(true);
        tfmail.setValue(operateurconnecte.getMail());
        tfmail.setWidthFull();
        TextField tfidentifiant = new TextField("Identifiant");
        tfidentifiant.setReadOnly(true);
        tfidentifiant.setWidthFull();
        tfidentifiant.setValue(operateurconnecte.getIdentifiant());
        NumberField nftel=new NumberField("Téléphone");
        nftel.setReadOnly(true);
        nftel.setWidthFull();
        nftel.setValue((double)operateurconnecte.getTel());
        
        
        
        
        String couleur1 = new String("#38998C");
        String couleur2 = new String("#BDE767");
        String couleur3 = new String("#1F4C83");
        
        
        Icon iconenreg = new Icon("lumo","checkmark");
        Icon iconmodif = new Icon("lumo","edit");
        Icon iconsupp = new Icon(VaadinIcon.TRASH);
        
        iconenreg.setColor(couleur1);
        iconmodif.setColor(couleur1);
        iconsupp.setColor(couleur1);
        
         
        
        Button boutonModifier =new Button(new H5("Modifier"));
        
        
        dcompte.setHeaderTitle("Informations du compte");
        dcompte.getHeader().add(boutonFermer);
        vlcontenu.add(boutonModifier,tfnom,tfprenom,nftel,tfmail,tfidentifiant,tfmdp);
        vlcontenu.setSpacing(false);
        dcompte.add(vlcontenu);
        dcompte.open();

        final int id=operateurconnecte.getId();
        final int idateliertemp = operateurconnecte.getIdatelier();
        final int idstatutchoix = operateurconnecte.getStatut();
        final ArrayList <Typeoperation> listtypeop = operateurconnecte.getListtypeoperation();
        
        
        boutonModifier.addClickListener(event -> {
            tfnom.setReadOnly(false);
            tfprenom.setReadOnly(false);
            tfmdp.setReadOnly(false);
            tfmail.setReadOnly(false);
            tfidentifiant.setReadOnly(false);  
            nftel.setReadOnly(false);
                
            });
        
        boutonFermer.addClickListener(event -> {
            dcompte.close();
            tfnom.setReadOnly(true);
            tfprenom.setReadOnly(true);
            tfmdp.setReadOnly(true);
            tfmail.setReadOnly(true);
            tfidentifiant.setReadOnly(true);  
            nftel.setReadOnly(true);
                
            });
        
        
        boutonEnregistrer.addClickListener(event -> {
            tfnom.setReadOnly(true);
            tfprenom.setReadOnly(true);
            tfmdp.setReadOnly(true);
            tfmail.setReadOnly(true);
            tfidentifiant.setReadOnly(true);  
            nftel.setReadOnly(true);
            
            
                
            try {
                this.getVuePrincipale().getGestionBDD().updateOperateur(this.getVuePrincipale().getGestionBDD().conn,id,tfidentifiant.getValue(),tfmdp.getValue(),tfnom.getValue(),tfprenom.getValue(),idateliertemp,idstatutchoix,(int) Math.round(nftel.getValue()),tfmail.getValue(),listtypeop);
            } catch (SQLException ex) {
                System.out.println("Pas reussi à modifier l'operateur");
            }
            
                        
            
            dcompte.close();
            });
        
    }
    
    
    public void MenuItemPlan() {
        this.main.setMainContent(new VuePlan());
        Notification.show("plan via controleur");
    }

    public void MenuItemMachine() throws SQLException {
        this.fenetrePartagee =new FenetrePartagee(this, "machine");
        //this.main.setMainContent(new FenetrePartagee(this, "machine"));
        
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "machine";
        
        
        
        
        Notification.show("machines via controleur");
        
        
    }
    
    public void MenuItemProduit() throws SQLException {
        Notification.show("Produits via controleur");
        
        this.fenetrePartagee =new FenetrePartagee(this,"produit");
        //this.main.setMainContent(new FenetrePartagee(this, "machine"));
        
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "produit";
        
        
        
    }
    
    
    public void MenuItemOperations() throws SQLException {
        Notification.show("Operations via controleur");
        
        this.fenetrePartagee =new FenetrePartagee(this,"operation");
        //this.main.setMainContent(new FenetrePartagee(this, "machine"));
        
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "operation";
        
        
        
    }
    
    
    
    
    public void MenuItemTypeOperation() throws SQLException{
        Dialog fenetre = new Dialog();
        MyVerticalLayout contenu = new MyVerticalLayout();
        Grid<Typeoperation> grid = new Grid<>();
        fenetre.setModal(false);
        fenetre.setDraggable(true);
        fenetre.setWidth("40vw");
        fenetre.getElement().getThemeList().add("dialogResizable");
        Button boutonajouter = new Button("Ajouter");
        Button boutonsupprimer = new Button("Supprimer");
        boutonsupprimer.setEnabled(false);
        MyHorizontalLayout hlmodif = new MyHorizontalLayout();
        hlmodif.setWidthFull();
        TextField entreetype = new TextField();
        entreetype.setPlaceholder("Nom du type d'operation à ajouter");
        entreetype.setWidthFull();
        
        hlmodif.add(entreetype,boutonajouter,boutonsupprimer);
        contenu.add(hlmodif);
        
        
        if (listtypeoperation(this.getVuePrincipale().getGestionBDD().conn).size()==0){
            contenu.add(new Text("Il n'y a pas de type d'opération"));
            System.out.println("Pas de types d'iopération");
        }
        
        
        else{
          ArrayList<Typeoperation> listtemp = new ArrayList();
          listtemp=listtypeoperation(this.getVuePrincipale().getGestionBDD().conn);
          grid.addColumn(Typeoperation::getNom).setHeader("Nom");
          grid.addColumn(Typeoperation::getId).setHeader("Identifiant");
          grid.setItems(listtemp);
          contenu.add(grid);
             
        }
        fenetre.add(contenu);
        Button boutonFermer = new Button(new Icon("lumo","cross"));
        fenetre.getHeader().add(boutonFermer);
        fenetre.setHeaderTitle("Types d'opération");
        fenetre.setResizable(true);
        fenetre.open(); 
        
        
        boutonajouter.addClickListener(event4 -> {
            
            String nomtype = entreetype.getValue();
            System.out.println("Bouton Ajouté activé");

            if (nomtype.isEmpty()){
                Notification.show("Ajoutez un nom");
                System.out.println("Ajoutez un nom");
            }
            else  {
                
            try {
                addtypeoperation(this.getVuePrincipale().getGestionBDD().conn,nomtype);
            } catch (SQLException ex) {
                System.out.println("Controleur : Erreur lors de l'ajout du type d'opération");
            }
            try {
                grid.setItems(listtypeoperation(this.getVuePrincipale().getGestionBDD().conn));
            } catch (SQLException ex) {
                System.out.println("Controleur : Erreur lors de la mise à jour du type d'opération");
            }
     
            }
      
        });
        
        
       
        
        grid.addItemClickListener(event5 -> {
                    Notification.show("Ligne sélectionnée"+event5.getItem().getId());
                    System.out.println("Id à supprimer "+event5.getItem().getId());
                    boutonsupprimer.setEnabled(true);
                    
                    final int intselect = event5.getItem().getId();
                    Typeoperation typopsupp = event5.getItem();
                    
                    
                    //Ouvre la fenetre supplémentaire : confirmation de suppression
                    boutonsupprimer.addClickListener(event2 -> {
                        
                        
                        grid.deselectAll();
                        System.out.println("Id à supprimer ( 2ème boucle) "+intselect);
                        grid.deselectAll();
                        Dialog dsuppression = new Dialog();
                        dsuppression.setHeaderTitle("Confirmer la suppression");
                        MyVerticalLayout vlsuppression = new MyVerticalLayout();
                        vlsuppression.add(new Text("Etes-vous sûr de vouloir supprimer les éléments suivants?"));
                        vlsuppression.add();
                        vlsuppression.add(new Text("Liste des éléments qui seront supprimés"));
                        
                        //Il faudra ajouter tout ce qui est supprimé
                        
                        
                        
                        dsuppression.add(vlsuppression);
                        Button boutonFermer2 = new Button(new Icon("lumo","cross"));
                        dsuppression.getHeader().add(boutonFermer2);
                        Button boutonSupp = new Button("Supprimer");
                        Button boutonAnnuler = new Button("Annuler");
                        dsuppression.open();

                        
                        
                        
                        
                        dsuppression.getFooter().add(boutonAnnuler,boutonSupp);                     

                        
                        boutonSupp.addClickListener(event3 -> {
                            dsuppression.close();
                            
                            try {
                                System.out.println("Essai de suppression : id = "+intselect);

                                delete(this.getVuePrincipale().getGestionBDD().conn,typopsupp.getnomtable(),intselect);
                                grid.setItems(listtypeoperation(this.getVuePrincipale().getGestionBDD().conn));
                                boutonSupp.setEnabled(true);
                            } catch (SQLException ex) {
                                System.out.println("Pas reussi à supprimer le type d'operation");
                            }
                                dsuppression.close();

                            
                        });
                        
                  
                        boutonAnnuler.addClickListener(event3 -> {
                            dsuppression.close();
                        });

                        boutonFermer2.addClickListener(event3 -> {
                            dsuppression.close();
                        });
                             
                        

                    //System.out.println("Id à supprimer "+event.getItem().getId()+" fenetre fermée avec bouton supprimer");
                    
                    
                 
                });
                    
                event5=null;
                return;
                
                
                

       
                
                });
        
        
        
        
        
        boutonFermer.addClickListener(event -> {
            fenetre.close();
        });
        
        
        
    }
    
    public void MenuItemBrut() throws SQLException{
        this.fenetrePartagee =new FenetrePartagee(this,"brut");
        //this.main.setMainContent(new FenetrePartagee(this, "machine"));
        
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "brut";
        
        
        
    }
    
    
    
    public void MenuItemOperateur() throws SQLException{
        System.out.println(" Arrivé dans MenuItemOperateur");
        this.fenetrePartagee =new FenetrePartagee(this,"operateur");
        System.out.println(" pas de probleme pour fenetre partagee");
        
        //this.main.setMainContent(new FenetrePartagee(this, "machine"));
        
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "operateur";
        
        
 
        
        //this.main.setMainContent(new PartiePrincipale(this,"operateur")); 

        System.out.println(" Reparti dans MenuItemOperateur");

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
       
        listTemp=listAtelier(this.getVuePrincipale().getGestionBDD().conn);
        
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
    
    
    public void MenuItemSupprimerAtelier() throws SQLException{
        
        
        
        
        if(this.etatAtelier==-1){
            Notification.show("Séléctionnez un atelier");
        }
        else {
        

          
            ArrayList<Atelier> listTemp =  new ArrayList();
       
        listTemp=listAtelier(this.getVuePrincipale().getGestionBDD().conn);
        String nom = new String();
        int index =0;
        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);

            if(atelierTemp.getId()==this.etatAtelier){
                
                nom = atelierTemp.getNom();
                 
                
            }
            
            
            index++;        

        }
        Atelier atelierselect = (Atelier) listTemp.get(this.etatAtelier);
        //On affiche une fenêtre d'avertissement
        
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this,"atelier",atelierselect);

                        
    }
        
        
        
//        System.out.println("les ateliers devraient être affichés");
//        this.getVuePrincipale().getEntete().setComboBoxAtelier(listaltelier(this.getVuePrincipale().getGestionBDD().conn));
        
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
        //System.out.println("Controleur : essai de mise à jour du combobox");
        //this.main.getEntete().setComboBoxAtelier(listaltelier(this.main.getGestionBDD().conn));
         
        System.out.println("Combobox mis à jour");
        int index =0;
        ArrayList<Atelier> listTemp= listAtelier(this.main.getGestionBDD().conn);
        
        
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
        
        if(objet =="produit"){
        
            System.out.println("la fenetre va être créée");
            
            
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"produit");
            System.out.println("La fenetre devrait être crée");
            
            
            this.MenuItemProduit();
        }
        
        
        if(objet =="brut"){
        
            System.out.println("la fenetre va être créée");
            
            
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"brut");
            System.out.println("La fenetre devrait être crée");
            
            this.MenuItemBrut();
        }
        
        
        
        if(objet =="operation"){
        
            System.out.println("la fenetre va être créée");
            
            
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"operation");
            System.out.println("La fenetre devrait être crée");
            
            this.MenuItemOperations();
        }
        
        
        
        if(objet =="operateur"){
        
            System.out.println("la fenetre va être créée");
            
            
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"operateur");
            System.out.println("La fenetre devrait être crée");
            
            this.MenuItemOperateur();
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
       
    public FenetrePartagee getFenetrePartagee(){
        return this.fenetrePartagee;
    }
    
        
    public void setEtatAtelier(int i){
        this.etatAtelier = i; //l'etat correspond à l'identifiant de l'atelier sélectionné. 
    
  
    }
    
    public void setEtatFenetre(String i ){
        this.etatFenetre=i;
    }
    
    
    public String getIdentifiantUtilisateur(){
        return this.identifiantutilisateur;
    }
    
    public void setIdentifiantUtilisateur(String identifiant){
        this.identifiantutilisateur=identifiant;
    }
}
