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
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import static fr.insa.moly.GestionBDD.GestionBDD.listmachine;
import static fr.insa.moly.GestionBDD.GestionBDD.listtypeoperation;
import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Typeoperation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static fr.insa.moly.GestionBDD.GestionBDD.listAtelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listoperation;
import fr.insa.moly.objet.Operation;

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
                
                //this.controleur.MenuItemMachine();
                //this.controleur.getEtatFenetre()

                //this.controleur.CreationObjet("atelier");
            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            try {
                this.controleur.getVuePrincipale().getEntete().setComboBoxAtelier(listAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn));
            } catch (SQLException ex) {
                System.out.println("Impossible de mettre à jour le combobox");
            }
                System.out.println("Combobox mis à jour (via Fenetre)");
            
           
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
            this.setWidth("40vw");



            this.contenuVL.add(nom,tfref, des,nbidbrut);
            this.add(contenuVL);
            
            
            //On récupère la liste de toutes les operations possibles pour remplir le choix des combobox
            final ArrayList<Operation> listop = listoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);

            ArrayList <Operation> listopselect = new ArrayList();
            
            ArrayList <ComboBox> listcbb = new ArrayList();
            
            
            
            
            MyHorizontalLayout hl1 = new MyHorizontalLayout();
            ComboBox <Operation>cbbop1 = new ComboBox<>();
            cbbop1.setItems(listop);
            cbbop1.setItemLabelGenerator(Operation::getNom);
            cbbop1.setLabel("Operation 1");
            cbbop1.setWidth("80%");
            cbbop1.getStyle().set("margin-left", "auto");
            cbbop1.getStyle().set("margin-right", "auto");
            
            MyHorizontalLayout hlbouton = new MyHorizontalLayout();
            hlbouton.add(VaadinIcon.PLUS_CIRCLE.create(),new H5("Ajouter une operation"));

            Button baddop = new Button(hlbouton);
            
            this.contenuVL.add(baddop);
            Div div = new Div();
            div.setHeight("300px");
            div.setWidthFull();
            listcbb.add(cbbop1);
            
            div.add(cbbop1);
            this.contenuVL.add(div);
            
            this.open();
            
            baddop.addClickListener(event -> {
                
                ComboBox <Operation>cbbopn = new ComboBox<>();
                cbbopn.setItems(listop);
                cbbopn.setItemLabelGenerator(Operation::getNom);
                cbbopn.setLabel("Operation "+String.valueOf(listcbb.size()+1));
                cbbopn.setWidth("80%");
                cbbopn.getStyle().set("margin-left", "auto");
                cbbopn.getStyle().set("margin-right", "auto");
                listcbb.add(cbbopn);
                
                div.add(cbbopn);
                
                
            });
            
            
            
            
            
           
           
           
           
            boutonFermer.addClickListener(event -> {
            this.close();   
        });

         boutonAnnuler.addClickListener(event -> {
            this.close();   
        });
        
        boutonEnregistrer.addClickListener(event -> {
            System.out.println("1");
            int index =0;
            while(index<listcbb.size()){
                
                if((listcbb.get(index).getValue())== null){
                    listcbb.remove(index);
                    index--;
                    
                }
                
                else{
                System.out.println(index+1);
                //On ajoutera ici l'operation à la listopselect
                
                System.out.println(((Operation)(listcbb.get(index).getValue())).getNom());
                listopselect.add((Operation)(listcbb.get(index).getValue()));
                }
                
                
                index++;
                
                
                
            }
            
            this.close();
            
            
            System.out.println(listopselect);
               try {
                   this.controleur.getVuePrincipale().getGestionBDD().addproduit(this.controleur.getVuePrincipale().getGestionBDD().conn,tfref.getValue(),des.getValue(),(int) Math.round(nbidbrut.getValue()),listopselect);
                   
                   
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
            NumberField nbtel = new NumberField("Téléphone (+33 0)");

            
            ArrayList <String> listStatuts = new ArrayList();
        
            listStatuts.add("Disponible");
            listStatuts.add("En activité");
            listStatuts.add("En formation");
            listStatuts.add("En congé");
            listStatuts.add("Hors service");
        
            ComboBox cbbstatut = new ComboBox();
            cbbstatut.setItems(listStatuts);
            //cbbstatut.setWidthFull();
            cbbstatut.setLabel("Statut");


            
            ComboBox cbbatelier = new ComboBox();
            ArrayList <Atelier> listTemp = new ArrayList();
            listTemp = listAtelier (this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            ArrayList <String> listNomAtelier = new ArrayList<>();

            int index =0;

            while (index<listTemp.size()){
                Atelier atelierTemp = (Atelier) listTemp.get(index);

                listNomAtelier.add(atelierTemp.getId()+" : "+atelierTemp.getNom());

                System.out.println(listNomAtelier.get(index));
                index++;
            }
            cbbatelier.setItems(listNomAtelier); 
            //cbbatelier.setWidthFull();
            cbbatelier.setLabel("Atelier");
            
            
            
            
            
            Div divtypeop = new Div();
            divtypeop.setHeight("350px");
            CheckboxGroup<Typeoperation> cbgtypeop = new CheckboxGroup<>();
            divtypeop.getStyle().set("overflow-y", "auto");
            cbgtypeop.setLabel("Type(s) d'opération(s)");
            ArrayList listtemp = listtypeoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            cbgtypeop.setItemLabelGenerator(
            Typeoperation -> Typeoperation.getNom());

            cbgtypeop.setItems(listtemp);
            cbgtypeop.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

            divtypeop.add(cbgtypeop);
            

            
            
            
            MyVerticalLayout colonne1 = new MyVerticalLayout();
            MyVerticalLayout colonne2 = new MyVerticalLayout();
            
            
            colonne1.add(tfprenom);
            colonne1.add(nom);
            colonne1.add(nbtel);
            colonne1.add(tfmail);
            colonne1.add(tfidentifiant);
            colonne1.add(tfmdp);
   
            colonne2.add(cbbstatut);
            colonne2.add(cbbatelier);
            colonne2.add(divtypeop);
            colonne2.setAlignItems(Alignment.CENTER);
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
            
            String statut = (String) cbbstatut.getValue();
            int idstatut=-1;
        
        
            if(statut=="En congé"){
            idstatut=0;
            }
            if(statut=="En activité"){
            idstatut=1;
            }
            if(statut=="En formation"){
                idstatut=2;
            }
            if(statut=="Disponible"){
                idstatut=3;
            }
            if(statut=="Hors service"){
                idstatut=4;
            }
            
            
            int indexEspace =-1;
            int idateliertemp = -2;

            String NomAtelier = String.valueOf(cbbatelier.getValue());
            try {
                indexEspace = NomAtelier.indexOf(' ');
            }
            catch (ArrayIndexOutOfBoundsException e){
                ;
            }
            if(indexEspace==-1){
                
               idateliertemp =  Integer.parseInt(NomAtelier);
            }
            else{
                
                String avantEspace = NomAtelier.substring(0,indexEspace);
                
                idateliertemp = Integer.parseInt(avantEspace);   
            }
            
           final int idatelier =idateliertemp; 
            
            
            
           
           Set<Typeoperation> selectedItems = cbgtypeop.getSelectedItems();
            ArrayList<Typeoperation> listtypeop = new ArrayList<>(selectedItems);

           
           List<Integer> selected = selectedItems.stream()
            .map(Typeoperation::getId) 
            .collect(Collectors.toList());
           
           
           System.out.println(listtypeop);
           
           
           String typesOperations = listtypeop.stream()
            .map(Typeoperation::getNom) // Supposons que Typeoperation a une méthode getNom()
            .collect(Collectors.joining(", "));

            System.out.println("Types d'opérations : " + typesOperations);
           
           
           
           
            
            
            this.close();
               try {
                   
                   //(Connection connect,String identifiant, String motdepasse,String nom,String prenom,int idatelier,int statut, int tel, String mail,ArrayList<Integer> listtypeoperation)
                   this.controleur.getVuePrincipale().getGestionBDD().addoperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,tfidentifiant.getValue(),tfmdp.getValue(),nom.getValue(),tfprenom.getValue(),idateliertemp,idstatut,(int) Math.round(nbtel.getValue()),tfmail.getValue(),listtypeop);
                   
                   //this.controleur.getVuePrincipale().getGestionBDD().addoperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,"identifiant","mdp","martin","martine",2,0,1,"mail",listidtypeop);

                   
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout de l'opérateur");
               }
                try {
                    this.controleur.MenuItemOperateur();
                } catch (SQLException ex) {
                    System.out.println("Erreur Fenetre entrée de donnée : pas reussi à actualiser la page");
                }
                   
                   System.out.println("L'operateur devrait être créé");
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
                    System.out.println("addop fait");
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
