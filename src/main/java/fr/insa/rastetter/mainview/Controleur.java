/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import static fr.insa.moly.GestionBDD.GestionBDD.addtypeoperation;
import fr.insa.moly.objet.Atelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listtypeoperation;
import fr.insa.moly.objet.Typeoperation;
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


//Le controleur fait le lien entre les différentes classes
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

    public void boutonConnect(VuePrincipale main) throws SQLException{
        MenuItemMachine();
        this.main=main;
        this.main.setEntete(new Entete(main));
        this.etatAtelier=-1;
        MenuItemMachine();
        
    }

    //Retour sur la vue initiale
    public void MenuItemDeconnexion() {
        this.main.setMainContent(new VueInitialeConnection(this.main,this));
        this.main.entete.removeAll();
        this.main.setEntete(new Entete());
   
    }
    
    
    public void MenuItemInfosCompte() throws SQLException {
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
    }

    public void MenuItemMachine() throws SQLException {
        this.fenetrePartagee =new FenetrePartagee(this, "machine");        
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "machine";
    }
    
    public void MenuItemProduit() throws SQLException {
        this.fenetrePartagee =new FenetrePartagee(this,"produit");   
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "produit";
    }
    
    
    public void MenuItemOperations() throws SQLException {       
        this.fenetrePartagee =new FenetrePartagee(this,"operation");
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

            if (nomtype.isEmpty()){
                Notification.show("Ajoutez un nom");
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
                    boutonsupprimer.setEnabled(true);
                    
                    final int intselect = event5.getItem().getId();
                    Typeoperation typopsupp = event5.getItem();
                    
                    
                    //Ouvre la fenetre supplémentaire : confirmation de suppression
                    boutonsupprimer.addClickListener(event2 -> {
                    fenetre.close();    
                        
                        try {
                            FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this,"typeoperation",typopsupp);
                        } catch (SQLException ex) {
                            Logger.getLogger(Controleur.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        grid.deselectAll();

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
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "brut";
    
    }
    
    
    
    public void MenuItemOperateur() throws SQLException{
        this.fenetrePartagee =new FenetrePartagee(this,"operateur");                
        this.main.setMainContent(this.fenetrePartagee);
        this.etatFenetre= "operateur";
    }

    public void MenuItemDetailsAtelier() throws SQLException{
        
        
        MyVerticalLayout contenu = new MyVerticalLayout();
        NumberField id = new NumberField("Identifiant");
        TextField nom = new TextField("Nom de l'atelier");
        TextArea des = new TextArea("Description");
        NumberField nfdimlarg = new NumberField("Largeur (cm)");
        NumberField nfdimlong = new NumberField("Longueur (cm)");

        Dialog fenetre = new Dialog();
        
        Button boutonModifNom = new Button(new Icon("lumo","edit"));

        
        nom.setReadOnly(true);
        des.setReadOnly(true);
        id.setReadOnly(true);
        nfdimlarg.setReadOnly(true);
        nfdimlong.setReadOnly(true);    
 
        ArrayList<Atelier> listTemp =  new ArrayList();
       
        listTemp=listAtelier(this.getVuePrincipale().getGestionBDD().conn);
        
        int index =0;
        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);

            if(atelierTemp.getId()==this.etatAtelier){
                
                nom.setValue(atelierTemp.getNom());
                id.setValue((double)Math.round(atelierTemp.getId()));
                des.setValue(atelierTemp.getDes());
                nom.setReadOnly(true);
                id.setReadOnly(true);
                des.setReadOnly(true);
                nfdimlarg.setReadOnly(true);
                nfdimlong.setReadOnly(true);
                
                contenu.add(boutonModifNom,nom,id,des,nfdimlong,nfdimlarg);
                
                fenetre.add(contenu);
                
                fenetre.setHeaderTitle("Détails de l'atelier");
                Button boutonFermer = new Button(new Icon("lumo","cross"));
                Button boutonEnregistrer = new Button("Enregistrer");

                fenetre.getHeader().add(boutonFermer);
                fenetre.getFooter().add(boutonEnregistrer);
                fenetre.open();
                
                boutonFermer.addClickListener(event -> {
                    fenetre.close();
        });
                boutonModifNom.addClickListener(event -> {
                nom.setReadOnly(false);
                des.setReadOnly(false);
                nfdimlarg.setReadOnly(false);
                nfdimlong.setReadOnly(false);
    
                    
        });
                boutonEnregistrer.addClickListener(event -> {
                nom.setReadOnly(true);
                des.setReadOnly(true);
                nfdimlarg.setReadOnly(true);
                nfdimlong.setReadOnly(true);
                fenetre.close();
   
                    try {
                        this.getVuePrincipale().getGestionBDD().updateAtelier(this.getVuePrincipale().getGestionBDD().conn,atelierTemp.getId(), nom.getValue(),  des.getValue(), 100/*(int) Math.round(nfdimlarg.getValue())*/, 100/*(int) Math.round(nfdimlong.getValue())*/);
                    } catch (SQLException ex) {
                        Logger.getLogger(Controleur.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
        });
    
                
            }
            
            
            index++;
        }
    
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
        boolean valide = false;
        Atelier atelierselect = new Atelier(-1, "vide", "vide");
        while (index<listTemp.size()&& valide == false){
            Atelier atelierTemp = (Atelier) listTemp.get(index);

            if(atelierTemp.getId()==this.etatAtelier){
                
                nom = atelierTemp.getNom();
                atelierselect = atelierTemp;              
            }
            index++;        
        }
        FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this,"atelier",atelierselect);                       
    }
    }

    public void CreationObjet(String objet) throws SQLException{

        
        if("atelier".equals(objet)){
  
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"atelier");

        int index =0;
        ArrayList<Atelier> listTemp= listAtelier(this.main.getGestionBDD().conn);

        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);
            index++;
        }  
        }
  
        if("machine".equals(objet)){

            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"machine");                       
            this.MenuItemMachine();
        }
        
        if("produit".equals(objet)){
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"produit");        
            this.MenuItemProduit();
        }
     
        if("brut".equals(objet)){
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"brut"); 
            this.MenuItemBrut();
        }
        
        if("operation".equals(objet)){
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"operation");          
            this.MenuItemOperations();
        }

        if("operateur".equals(objet)){
            this.fenetreEntreeDonnees = new FenetreEntreeDonnees(this,"operateur");    
            this.MenuItemOperateur();
        }

    }
 
    
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
