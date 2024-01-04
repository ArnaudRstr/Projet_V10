/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listmachine;
import static fr.insa.moly.GestionBDD.GestionBDD.listtypeoperation;
import fr.insa.moly.objet.Machine;
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
        this.boutonAnnuler=new Button(new H5("Annuler"));
        this.boutonEnregistrer=new Button(new H5("Enregistrer"));
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

        
        
        ArrayList <String> listStatuts = new ArrayList();
        
        listStatuts.add("Arret");
        listStatuts.add("Marche");
        listStatuts.add("Maintenance à prévoir");
        
        ComboBox cbbstatut = new ComboBox();
        cbbstatut.setItems(listStatuts);
        cbbstatut.setLabel("Statut");
        
        
        
        
        
        

        
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
        
        
        MyVerticalLayout colonne1 = new MyVerticalLayout();
        MyVerticalLayout colonne2 = new MyVerticalLayout();
        colonne1.add(nom);
        colonne1.add(des);
        colonne1.add(marque);
        //colonne1.add(idTypeOperation);
        colonne1.add(comboBoxTypeOperation);
        colonne1.add(puissance);
        colonne2.add(coutHoraire);
        colonne2.add(statut);
        colonne2.add(cbbstatut);
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
            
//            
            
            String idNom = (String) comboBoxTypeOperation.getValue();
            int indexEspace = idNom.indexOf(' ');
            String avantEspace = idNom.substring(0,indexEspace);
            
            int idtypeoperationtemp = Integer.parseInt(avantEspace);
       
            String stattemp= (String) cbbstatut.getValue();
            int statutTemp =-1;
            
            if(stattemp=="Arret"){
                statutTemp=0;
            }
            if(stattemp=="Marche"){
                statutTemp=1;
            }
            if(stattemp=="Maintenance à prévoir"){
                statutTemp=2;
            }
            
            
            
//            
            this.close();

            try {
                this.controleur.getVuePrincipale().getGestionBDD().addmachine(this.controleur.getVuePrincipale().getGestionBDD().conn,nom.getValue(),this.controleur.getEtatAtelier(),idtypeoperationtemp,des.getValue(),marque.getValue(),puissance.getValue(),statutTemp,coutHoraire.getValue(),localisation.getValue(),dimLargeur.getValue(),dimLongueur.getValue());
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
       
       
       if (objet=="brut"){
           
           System.out.println("Creation de brut");
           
            this.setHeaderTitle("Ajouter un brut");

            this.nom = new TextField("Nom");
            //this.des = new TextArea("Description");
            TextField tfmat = new TextField("Matière");
            TextField tfref = new TextField("Référence");
            NumberField nbidbrut = new NumberField("Identifiant du brut");
            NumberField nbstock = new NumberField("Nombre en stock");
            TextField tfdim = new TextField("Dimensions");
            TextField tffournisseur = new TextField("Fournisseur");



            this.contenuVL.add(nom,tfref,nbstock,tffournisseur,tfmat,tfdim);
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
                   this.controleur.getVuePrincipale().getGestionBDD().addbrut(this.controleur.getVuePrincipale().getGestionBDD().conn,this.nom.getValue(), tfref.getValue(),tfmat.getValue(),(int) Math.round(nbstock.getValue()),tfdim.getValue(),tffournisseur.getValue());
                   this.controleur.MenuItemBrut();
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout du brut");
               }
            
            
            
        });
        
       }
       

        
        
        
        if (objet=="operateur"){
           
           
            this.setHeaderTitle("Ajouter un opérateur");

            this.nom = new TextField("Nom");
            //this.des = new TextArea("Description");
            TextField tfprenom = new TextField("Prénom");
            
            TextField tfmdp = new TextField("Mot de passe");
            TextField tfmail = new TextField("Adresse mail");
            TextField tfidentifiant = new TextField("Identifiant");
            NumberField nbidatelier = new NumberField("Identifiant de l'atelier");
            NumberField nbstatut = new NumberField("Statut");
            NumberField nbtel = new NumberField("Téléphone");

            MyVerticalLayout colonne1 = new MyVerticalLayout();
            MyVerticalLayout colonne2 = new MyVerticalLayout();
            colonne1.add(nom);
            colonne1.add(tfprenom);
            colonne1.add(nbtel);
            colonne1.add(tfmail);
            colonne1.add(tfidentifiant);
            colonne1.add(tfmdp);
   
            colonne2.add(nbidatelier);
            colonne2.add(nbstatut);

            this.contenuHL.add(colonne1,colonne2);
            this.contenuVL.add(this.contenuHL);
            
            this.add(contenuVL);
            this.open();

            
            
            ArrayList listidtypeop = new ArrayList();
            listidtypeop.add(1);
            
            
            boutonFermer.addClickListener(event -> {
            this.close();   
        });

         boutonAnnuler.addClickListener(event -> {
            this.close();   
        });
        
        boutonEnregistrer.addClickListener(event -> {
            
            
            this.close();
               try {
                   
                   //(Connection connect,String identifiant, String motdepasse,String nom,String prenom,int idatelier,int statut, int tel, String mail,ArrayList<Integer> listtypeoperation)
                   //this.controleur.getVuePrincipale().getGestionBDD().addoperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,tfidentifiant.getValue(),tfmdp.getValue(),nom.getValue(),tfprenom.getValue(),(int) Math.round(nbidatelier.getValue()),(int) Math.round(nbstatut.getValue()),(int) Math.round(nbtel.getValue()),tfmail.getValue(),listidtypeop);
                   
                   //this.controleur.getVuePrincipale().getGestionBDD().addoperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,"identifiant","mdp","martin","martine",2,0,1,"mail",listidtypeop);

                   this.controleur.MenuItemOperateur();
                   
                   System.out.println("L'operateur devrait être créé");
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout de l'opérateur");
               }
  
        });
           
           
           
       }
        
        
        if (objet=="operation"){
           
           
            this.setHeaderTitle("Ajouter une operation");

            this.nom = new TextField("Nom");
            TextField tfoutil = new TextField("Outil");
            
            
            NumberField nbidmachine = new NumberField("Identifiant de la machine");
            NumberField nbduree = new NumberField("Durée (s)");
            NumberField nbidtypop = new NumberField("Identifiant du type operation");
           
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
            
            
            
            
            ComboBox comboBoxMachines = new ComboBox();
            comboBoxMachines.setLabel("Machine");
            ArrayList<Machine> listmachinetemp = new ArrayList();

            listmachinetemp=listmachine(this.controleur.getVuePrincipale().getGestionBDD().conn);

            ArrayList listmachineajouter = new ArrayList();

            int index1 =0;
            while(index1< listmachinetemp.size()){

                listmachineajouter.add(listmachinetemp.get(index1).getId()+" : "+listmachinetemp.get(index1).getNom()+" "+listmachinetemp.get(index1).getMarque());
                index1++;
            }
            comboBoxMachines.setItems(listmachineajouter);
            
            
            
            nom.setWidthFull();
            tfoutil.setWidthFull();
            nbidmachine.setWidthFull();
            nbduree.setWidthFull();
            nbidtypop.setWidthFull();
            comboBoxMachines.setWidthFull();
            comboBoxTypeOperation.setWidthFull();
            
            
            
            

            contenuVL.add(nom,nbduree,comboBoxMachines,tfoutil,comboBoxTypeOperation);
            
            this.add(contenuVL);
            this.setWidth("30vw");
            this.open();

            
    
            
            
            
            boutonFermer.addClickListener(event -> {
            this.close();   
        });

         boutonAnnuler.addClickListener(event -> {
            this.close();   
        });
        
        boutonEnregistrer.addClickListener(event -> {
            
            
            String idNom = (String) comboBoxTypeOperation.getValue();
            int indexEspace = idNom.indexOf(' ');
            String avantEspace = idNom.substring(0,indexEspace);
            
            int idtypeoperationtemp = Integer.parseInt(avantEspace);
            
            
            
            
            
            String idmachine = (String) comboBoxMachines.getValue();
            int indexEspace1 = idmachine.indexOf(' ');
            String avantEspace1 = idmachine.substring(0,indexEspace1);
            
            int idmachineselect = Integer.parseInt(avantEspace1);
            
            
            
            
            
            
            this.close();
            
            
               try {
                   this.controleur.getVuePrincipale().getGestionBDD().addoperation(this.controleur.getVuePrincipale().getGestionBDD().conn,idtypeoperationtemp,nom.getValue(),nbduree.getValue(),tfoutil.getValue(),idmachineselect);

                   this.controleur.MenuItemOperations();
                   
                   System.out.println("L'operation devrait être créé");
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout de l'opération");
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
