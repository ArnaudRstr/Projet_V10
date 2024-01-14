/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
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
import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Typeoperation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static fr.insa.moly.GestionBDD.GestionBDD.listAtelier;
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Operation;
import static fr.insa.moly.GestionBDD.GestionBDD.listMachine;
import static fr.insa.moly.GestionBDD.GestionBDD.listOperation;
import static fr.insa.moly.GestionBDD.GestionBDD.listTypeOperation;

/**
 *
 * @author arnaud
 */


//Gère l'entrée de donnée lors de la création d'un objet
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

       
       
       
      //Cette classe créé une fenêtre de dialogue d'entrée de donnée. 
      //Ces fenêtres sont toutes différentes en fonction de l'objet que l'on souhaite ajouter.
      //Dans chaque cas, on configure d'abord la fenêtre de dialogue, puis lors de clic sur les boutons on récupère les informations néssaires à la création de l'objet.
      
      
       //Pour un atelier
    if ("atelier".equals(objet)){
        
        this.nom = new TextField("Nom");
        nom.setWidthFull();
        this.des = new TextArea("Description");
        des.setWidthFull();
        this.dimLongueur = new NumberField("Longueur (m)");
        dimLongueur.setWidthFull();
        this.dimLargeur = new NumberField("Largeur (m)");
        dimLargeur.setWidthFull();
        this.setHeaderTitle("Ajouter un atelier");
        this.contenuVL.add(nom,des,dimLongueur,dimLargeur);
        this.contenuVL.setWidthFull();
        this.add(contenuVL);
        this.setWidth("35vw");
        this.contenuVL.setAlignItems(CENTER);
        this.open();

        //Evénements sur les boutons
        boutonFermer.addClickListener(event -> {
           this.close();   
       });

        boutonAnnuler.addClickListener(event -> {
           this.close();   
       });
       
   
        boutonEnregistrer.addClickListener(event -> {
   
            this.close();
            Notification.show("Données enregistrées");
            try {
                this.controleur.getVuePrincipale().getGestionBDD().addAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn,this.nom.getValue(),this.des.getValue(),(int)Math.round(this.dimLongueur.getValue()),(int) Math.round(this.dimLargeur.getValue()));       
            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                this.controleur.getVuePrincipale().getEntete().setComboBoxAtelier(listAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn));
            } catch (SQLException ex) {
                System.out.println("Impossible de mettre à jour le combobox");
            }

            //On remet à jour la vue 
            try {
                this.controleur.boutonConnect(this.controleur.getVuePrincipale());
            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });
       

    }
    
    if ("machine".equals(objet)){
        
        this.nom = new TextField("Nom");
        this.des = new TextArea("Description");
        this.dimLongueur = new NumberField("Longueur (cm)");
        this.dimLargeur = new NumberField("Largeur (cm)");
        NumberField idTypeOperation = new NumberField("Id type operation (int)");
        NumberField puissance = new NumberField("Puissance (W)");
        NumberField coutHoraire = new NumberField("Coût horaire (€)");
        TextField marque = new TextField("Marque");
        TextField localisation = new TextField("Localisation (String)");
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
        
        listtemp=listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
        
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
        colonne1.add(nom,des,marque,comboBoxTypeOperation,puissance);
        colonne2.add(coutHoraire,cbbstatut,dimLongueur,dimLargeur,localisation);
    
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
                   
            this.close();

            try {
                this.controleur.getVuePrincipale().getGestionBDD().addMachine(this.controleur.getVuePrincipale().getGestionBDD().conn,nom.getValue(),this.controleur.getEtatAtelier(),idtypeoperationtemp,des.getValue(),marque.getValue(),puissance.getValue(),statutTemp,coutHoraire.getValue(),localisation.getValue(),dimLargeur.getValue(),dimLongueur.getValue());

            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            //On affiche à nouveau la vue pour reactualiser
            try {
                this.controleur.MenuItemMachine();
            } catch (SQLException ex) {
                Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
           
    }
       
       
       
    
    
    
       if ("produit".equals(objet)){

            this.setHeaderTitle("Ajouter un produit");

            this.des = new TextArea("Description");
            this.des.setWidthFull();
            TextField tfref = new TextField("Référence");
            tfref.setWidthFull();
            ComboBox<Brut> cbbbrut = new ComboBox();
            ArrayList <Brut> brutsbdd = this.controleur.getVuePrincipale().getGestionBDD().listBrut(this.controleur.getVuePrincipale().getGestionBDD().conn); 
            cbbbrut.setItems(brutsbdd);
            cbbbrut.setWidthFull();
            cbbbrut.setLabel("Brut");
            cbbbrut.setItemLabelGenerator(Brut::getNom);
            this.setWidth("35vw");
            this.contenuVL.add(tfref, des,cbbbrut);
            this.add(contenuVL);

            //On récupère la liste de toutes les operations possibles pour remplir le choix des combobox
            final ArrayList<Operation> listop = listOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);

            ArrayList <Operation> listopselect = new ArrayList();
            
            ArrayList <ComboBox> listcbb = new ArrayList();
            
            MyHorizontalLayout hlbouton = new MyHorizontalLayout();
            hlbouton.add(VaadinIcon.PLUS_CIRCLE.create(),new H5("Ajouter une operation"));
            Button baddop = new Button(hlbouton);
            this.contenuVL.add(baddop);
            Div div = new Div();
            div.setHeight("300px");
            div.setWidthFull();
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
            int index =0;
            while(index<listcbb.size()){
                
                if((listcbb.get(index).getValue())== null){
                    listcbb.remove(index);
                    index--;                  
                }
                
                else{
                listopselect.add((Operation)(listcbb.get(index).getValue()));
                }                          
                index++;                                                
            }           
            this.close();
             
               try {
                   this.controleur.getVuePrincipale().getGestionBDD().addProduit(this.controleur.getVuePrincipale().getGestionBDD().conn,tfref.getValue(),des.getValue(),(int) Math.round(((Brut)cbbbrut.getValue()).getId()),listopselect);
         
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout du produit");
               }

               try {
                   this.controleur.MenuItemProduit();
               } catch (SQLException ex) {
                   Logger.getLogger(FenetreEntreeDonnees.class.getName()).log(Level.SEVERE, null, ex);
               }          
        });        
       }
       
       
       if ("brut".equals(objet)){
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
                   this.controleur.getVuePrincipale().getGestionBDD().addBrut(this.controleur.getVuePrincipale().getGestionBDD().conn,this.nom.getValue(), tfref.getValue(),tfmat.getValue(),(int) Math.round(nbstock.getValue()),tfdim.getValue(),tffournisseur.getValue());
                   this.controleur.MenuItemBrut();
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout du brut");
               }
    
        });
        
       }
          
        if ("operateur".equals(objet)){
           
            this.setHeaderTitle("Ajouter un opérateur");

            this.nom = new TextField("Nom");
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
            cbbstatut.setLabel("Statut");
 
            ComboBox cbbatelier = new ComboBox();
            ArrayList <Atelier> listTemp = new ArrayList();
            listTemp = listAtelier (this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            ArrayList <String> listNomAtelier = new ArrayList<>();

            int index =0;

            while (index<listTemp.size()){
                Atelier atelierTemp = (Atelier) listTemp.get(index);

                listNomAtelier.add(atelierTemp.getId()+" : "+atelierTemp.getNom());

                index++;
            }
            cbbatelier.setItems(listNomAtelier); 
            cbbatelier.setLabel("Atelier");

            Div divtypeop = new Div();
            divtypeop.setHeight("350px");
            CheckboxGroup<Typeoperation> cbgtypeop = new CheckboxGroup<>();
            divtypeop.getStyle().set("overflow-y", "auto");
            cbgtypeop.setLabel("Type(s) d'opération(s)");
            ArrayList listtemp = listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            cbgtypeop.setItemLabelGenerator(
            Typeoperation -> Typeoperation.getNom());

            cbgtypeop.setItems(listtemp);
            cbgtypeop.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

            divtypeop.add(cbgtypeop);
            
            MyVerticalLayout colonne1 = new MyVerticalLayout();
            MyVerticalLayout colonne2 = new MyVerticalLayout();
          
            colonne1.add(tfprenom,nom,nbtel,tfmail,tfidentifiant,tfmdp);

            colonne2.add(cbbstatut,cbbatelier,divtypeop);        
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
  
            if("En congé".equals(statut)){
            idstatut=0;
            }
            if("En activité".equals(statut)){
            idstatut=1;
            }
            if("En formation".equals(statut)){
                idstatut=2;
            }
            if("Disponible".equals(statut)){
                idstatut=3;
            }
            if("Hors service".equals(statut)){
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
       
       
           String typesOperations = listtypeop.stream()
            .map(Typeoperation::getNom)
            .collect(Collectors.joining(", "));
           

            this.close();
               try {
                   this.controleur.getVuePrincipale().getGestionBDD().addOperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,tfidentifiant.getValue(),tfmdp.getValue(),nom.getValue(),tfprenom.getValue(),idateliertemp,idstatut,(int) Math.round(nbtel.getValue()),tfmail.getValue(),listtypeop);
               
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout de l'opérateur");
               }
                try {
                    this.controleur.MenuItemOperateur();
                } catch (SQLException ex) {
                    System.out.println("Erreur Fenetre entrée de donnée : pas reussi à actualiser la page");
                }
        });
     
       }
             
        if ("operation".equals(objet)){
           
           
            this.setHeaderTitle("Ajouter une operation");
            this.nom = new TextField("Nom");
            TextField tfoutil = new TextField("Outil");

            NumberField nbidmachine = new NumberField("Identifiant de la machine");
            NumberField nbduree = new NumberField("Durée (s)");
            NumberField nbidtypop = new NumberField("Identifiant du type operation");
            ComboBox comboBoxTypeOperation = new ComboBox();
            comboBoxTypeOperation.setLabel("Type d'opération");

            
            ArrayList<Typeoperation> listtemp = new ArrayList();
            listtemp=listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
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
            listmachinetemp=listMachine(this.controleur.getVuePrincipale().getGestionBDD().conn);
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
                   this.controleur.getVuePrincipale().getGestionBDD().addOperation(this.controleur.getVuePrincipale().getGestionBDD().conn,idtypeoperationtemp,nom.getValue(),nbduree.getValue(),tfoutil.getValue(),idmachineselect);
               } catch (SQLException ex) {
                   System.out.print("Fenêtre entrée de donnée : erreur lors de l'ajout de l'opération");
               }
   
                try {
                    this.controleur.MenuItemOperations();
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
