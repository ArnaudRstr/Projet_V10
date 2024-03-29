/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import static fr.insa.moly.GestionBDD.GestionBDD.updateOperation;
import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Operateur;
import fr.insa.moly.objet.Operation;
import fr.insa.moly.objet.Produit;
import fr.insa.moly.objet.Typeoperation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fr.insa.moly.GestionBDD.GestionBDD.listAtelier;
import java.util.Set;
import static fr.insa.moly.GestionBDD.GestionBDD.listMachine;
import static fr.insa.moly.GestionBDD.GestionBDD.listOperation;
import static fr.insa.moly.GestionBDD.GestionBDD.listTypeOperation;


/**
 *
 * @author arnaud
 */

//Il s'agit ici de la partie droite du contenu, qui contient les différentes informations de détail. Elle est donc adaptée à chaque type d'objet.
public class PartieDetail extends MyVerticalLayout{
    
private Controleur controleur;
private MyVerticalLayout contenu; 
private MyHorizontalLayout listboutons;
private Button boutonModifier;
private Button boutonEnregistrer;
private Button boutonSupprimer;

    public PartieDetail(Controleur controleur){
        this.controleur=controleur;
        this.getStyle().set("padding", "1px");
        this.add(new H2(" Détail")); 
        this.add(new H4("Sélectionnez un élement pour en afficher les détails"));
    }
        
    public PartieDetail(Controleur controleur, String typeobjet , Object object ) throws SQLException{
        this.controleur=controleur;
        this.getStyle().set("padding", "0px");
        this.contenu = new MyVerticalLayout();
        contenu.getStyle().set("padding", "10px");
        contenu.setSpacing(false);
        this.listboutons= new MyHorizontalLayout();
        
        String couleur1 = new String("#38998C");
        String couleur2 = new String("#BDE767");
        String couleur3 = new String("#1F4C83");
        
        Icon iconenreg = new Icon("lumo","checkmark");
        iconenreg.setSize("30px");
        Icon iconmodif = new Icon("lumo","edit");
        iconmodif.setSize("30px");
        Icon iconsupp = new Icon(VaadinIcon.TRASH);
        iconsupp.setSize("30px");
        
        iconenreg.setColor(couleur1);
        iconmodif.setColor(couleur1);
        iconsupp.setColor(couleur1);
        
        this.boutonEnregistrer= new Button(iconenreg);
        this.boutonModifier =new Button(iconmodif);
        this.boutonSupprimer=new Button(iconsupp);
        
        
        Div div0 = new Div();
        div0.setWidth("200px");
     
        this.listboutons.add(new H2(" Détail"),boutonSupprimer,boutonModifier,boutonEnregistrer);
 
        if ("machine".equals(typeobjet)){           
            contenu.add(listboutons);
            Machine machinetemp = (Machine) object;
        
            TextField tfnom=  new TextField();
            tfnom.setLabel("Nom");
            tfnom.getStyle().set("font-size","40px");
            tfnom.setReadOnly(true);
            tfnom.setValue(machinetemp.getNom());
            tfnom.setWidthFull();
            contenu.add(tfnom);
            
            TextField tfmarque=  new TextField();
            tfmarque.setLabel("Marque");
            tfmarque.setReadOnly(true);
            tfmarque.setValue(machinetemp.getMarque());
            tfmarque.setWidthFull();
            contenu.add(tfmarque);
            
            NumberField nfid = new NumberField();
            nfid.setLabel("Identifiant");
            nfid.setReadOnly(true);
            nfid.setValue((double)Math.round(machinetemp.getId()));
            nfid.setWidthFull();
            contenu.add(nfid);
            
            TextArea tades= new TextArea();
            tades.setLabel("Description");
            tades.setReadOnly(true);
            tades.setValue(machinetemp.getDes());
            tades.setWidthFull();            
            contenu.add(tades);
     
            ArrayList <String> listStatuts = new ArrayList();
        
            listStatuts.add("Arrêt");
            listStatuts.add("Marche");
            listStatuts.add("Maintenance à prévoir");
        
            ComboBox cbbstatut = new ComboBox();
            cbbstatut.setItems(listStatuts);
            cbbstatut.setReadOnly(true);
            cbbstatut.setWidthFull();    
            cbbstatut.setLabel("Statut");
            cbbstatut.setValue(machinetemp.getStatutString());
            contenu.add(cbbstatut);

            ComboBox cbtypeoperation = new ComboBox();

            //On récupère les types d'opération pour remplir les combobox
            ArrayList altypeop = new ArrayList();
            altypeop = listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);           
            ArrayList listajouter = new ArrayList();

            int index =0;
            while(index< altypeop.size()){ 
            listajouter.add(((Typeoperation)altypeop.get(index)).getNom());
            index++;
        }
            cbtypeoperation.setItems(listajouter);

            //On recherche le type d'operation à partir de son id.
            int valide=0;
            int i =0;
            int id = machinetemp.getIdtypeoperation();
            while (valide==0){
                if (id == ((Typeoperation) altypeop.get(i)).getId()){
                    valide =1;
                    cbtypeoperation.setValue(((Typeoperation) altypeop.get(i)).getNom());

                }
                i++;                               
            }
            cbtypeoperation.setReadOnly(true);
            cbtypeoperation.setLabel("Type operation");
            cbtypeoperation.setWidthFull();
            contenu.add(cbtypeoperation);
            
            NumberField nfpuissance = new NumberField();
            nfpuissance.setLabel("Puissance (W)");
            nfpuissance.setReadOnly(true);
            nfpuissance.setWidthFull();
            nfpuissance.setValue(machinetemp.getPuissance());
            contenu.add(nfpuissance);
            
            NumberField nfcouthoraire = new NumberField();
            nfcouthoraire.setLabel("Cout horaire (€/h)");
            nfcouthoraire.setReadOnly(true);
            nfcouthoraire.setWidthFull();
            nfcouthoraire.setValue(machinetemp.getCouthoraire());
            contenu.add(nfcouthoraire);
            
            NumberField nfdimlarg = new NumberField();
            nfdimlarg.setLabel("Largeur (cm)");
            nfdimlarg.setReadOnly(true);
            nfdimlarg.setWidthFull();
            nfdimlarg.setValue(machinetemp.getDimensionlargeur());
            contenu.add(nfdimlarg);
            
            NumberField nfdimlong = new NumberField();
            nfdimlong.setLabel("Longueur (cm)");
            nfdimlong.setReadOnly(true);
            nfdimlong.setWidthFull();
            nfdimlong.setValue(machinetemp.getDimensionlongueur());

            contenu.add(nfdimlong);
            
            TextField tfloca=  new TextField();
            tfloca.setLabel("Localisation");
            tfloca.setReadOnly(true);
            tfloca.setValue(machinetemp.getLocalisation());
            tfloca.setWidthFull();
            contenu.add(tfloca);
            
            this.add(contenu);
                        
            boutonModifier.addClickListener(event -> {
                    
            tfnom.setReadOnly(false);
            tfmarque.setReadOnly(false);
            tades.setReadOnly(false);
            nfpuissance.setReadOnly(false);
            nfcouthoraire.setReadOnly(false);
            nfdimlarg.setReadOnly(false);
            nfdimlong.setReadOnly(false);
            cbtypeoperation.setReadOnly(false);
            cbbstatut.setReadOnly(false);
            tfloca.setReadOnly(false);
      
            });
            
            boutonEnregistrer.addClickListener(event -> {
                    
            tfnom.setReadOnly(true);
            tfmarque.setReadOnly(true);
            tades.setReadOnly(true);
            nfpuissance.setReadOnly(true);
            nfcouthoraire.setReadOnly(true);
            nfdimlarg.setReadOnly(true);
            nfdimlong.setReadOnly(true); 
            cbtypeoperation.setReadOnly(true);
            cbbstatut.setReadOnly(true);
            tfloca.setReadOnly(true);
            
            String nomtypeopchoix = (String) cbtypeoperation.getValue();
            //On recherche ensuite l'identifiant du type d'op à partir de son nom.             
            int index1 =0;
            int idtypeopchoix=-1;
            
            ArrayList altypeop1 = new ArrayList();           
                try {
                    altypeop1 = listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                           
            while(altypeop1.size()>index1){
               
                if(nomtypeopchoix.equals(((Typeoperation)altypeop1.get(index1)).getNom())){
                    
                    idtypeopchoix=((Typeoperation)altypeop1.get(index1)).getId();
                }
                index1++;
            }            
            //On récupère le nouveau statut           
            String nvstat = (String) cbbstatut.getValue();           
            int idnvstatut = -1000;
            
            if ("Arrêt".equals(nvstat)){
                idnvstatut =0;
            }
            if ("Marche".equals(nvstat)){
                idnvstatut =1;
            }
            if ("Maintenance à prévoir".equals(nvstat)){
                idnvstatut =2;
            }
        
            try {
                    this.controleur.getVuePrincipale().getGestionBDD().updateMachine(this.controleur.getVuePrincipale().getGestionBDD().conn,machinetemp.getId(),tfnom.getValue(),this.controleur.getEtatAtelier(),idtypeopchoix,tades.getValue(),tfmarque.getValue(),nfpuissance.getValue(),idnvstatut,nfcouthoraire.getValue(),tfloca.getValue(),nfdimlarg.getValue(),nfdimlong.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }    
                try {
                    this.controleur.MenuItemMachine();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            boutonSupprimer.addClickListener(event -> {
                
                try {
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"machine",machinetemp);

                    //FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"machine",machinetemp.getNom(),machinetemp.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                //delete(this.controleur.getVuePrincipale().getGestionBDD().conn,machinetemp.getnomtable(),machinetemp.getId());
                try {
                    this.controleur.MenuItemMachine();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        }
        

        
        if ("produit".equals(typeobjet)){
            contenu.add(listboutons);
            this.add(contenu);

            Produit produittemp = (Produit) object;
            
            TextField tfref=  new TextField();
            tfref.setLabel("Référence");
            tfref.getStyle().set("font-size","40px");
            tfref.setReadOnly(true);
            tfref.setValue(produittemp.getRef());
            tfref.setWidthFull();
            contenu.add(tfref);
            
            NumberField nfid = new NumberField();
            nfid.setLabel("Identifiant");
            nfid.setReadOnly(true);
            nfid.setWidthFull();
            nfid.setValue((double)Math.round(produittemp.getId()));
            contenu.add(nfid);

            TextArea tades= new TextArea();
            tades.setLabel("Description");
            tades.setReadOnly(true);
            tades.setWidthFull();
            tades.setValue(produittemp.getDes());
            tades.setWidthFull();
            contenu.add(tades);
            
  
            ComboBox<Brut> cbbbrut = new ComboBox();
            ArrayList <Brut> brutsbdd = this.controleur.getVuePrincipale().getGestionBDD().listBrut(this.controleur.getVuePrincipale().getGestionBDD().conn); 
            cbbbrut.setItems(brutsbdd);
            cbbbrut.setWidthFull();
            cbbbrut.setItemLabelGenerator(Brut::getNom);
            cbbbrut.setReadOnly(true);
            cbbbrut.setLabel("Brut");
            
            
            int j =0;
            while(j<brutsbdd.size()){
                
                if(((Brut)brutsbdd.get(j)).getId()==produittemp.getIdbrut())
                    cbbbrut.setValue(brutsbdd.get(j));

                                   
                j++;
            }
            //cbbbrut.setValue();
            contenu.add(cbbbrut);
   

            //On créé une opération vide qui permet de ne rien sélectionner.
            Operation opnulle = new Operation(-1,-1,"vide",-1,"nul",-1);

            //Ajout de la gamme => opérations
            
            ArrayList<Operation> listoptemp = new ArrayList();
            listoptemp.add(opnulle);
            
            ArrayList<Operation> listoptempbdd = listOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
            int i =0;
            while(i<listoptempbdd.size()){
                listoptemp.add(listoptempbdd.get(i));
                i++;
                
            }

            final ArrayList<Operation> listop = listoptemp;

            //On récupère la liste d'operation du produit
            ArrayList <Operation> listopactuelle = new ArrayList();
            listopactuelle = produittemp.getGamme().getList();
            ArrayList <Operation> listopselect = new ArrayList();   
            ArrayList <ComboBox> listcbb = new ArrayList();

            MyHorizontalLayout hlbouton = new MyHorizontalLayout();
            hlbouton.add(VaadinIcon.PLUS_CIRCLE.create(),new H5("Ajouter une operation"));
            Button baddop = new Button(hlbouton);
            baddop.setEnabled(false);
            Div div = new Div();
            div.setHeight("300px");
            div.setWidthFull();
            div.add(baddop);
            contenu.add(div);

            //On créé à nouveau la liste de combobox pour afficher les infos actuelles (avant modif)
              int index1=0;
      
            while(index1<listopactuelle.size()){
                
                ComboBox <Operation>cbbopn = new ComboBox<>();
                cbbopn.setItems(listop);
                cbbopn.setItemLabelGenerator(Operation::getNom);
                cbbopn.setLabel("Operation "+String.valueOf(index1+1));
                cbbopn.setWidth("80%");
                cbbopn.getStyle().set("margin-left", "auto");
                cbbopn.getStyle().set("margin-right", "auto");
                listcbb.add(cbbopn);       
                div.add(cbbopn);
                cbbopn.setValue(listopactuelle.get(index1));
       
                index1++;

            }           
 
            //Les cases vides ne peuvent pas être supprimées pendant la saisie, mais elles sont éliminées lors de l'enregistrement
            int index0=0;
            while(index0<listcbb.size()){
                
                if((listcbb.get(index0).getValue())== null){
                    listcbb.remove(index0);
                    index0--;      
                }          
                else{
        
                listcbb.get(index0).setReadOnly(true);
                }      
                index0++;
            }           

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
            
            boutonModifier.addClickListener(event -> {
                    
            tfref.setReadOnly(false);
            tades.setReadOnly(false);
            baddop.setEnabled(true);
            cbbbrut.setReadOnly(false);

            int index2=0;
            while(index2<listcbb.size()){
                listcbb.get(index2).setReadOnly(false);
                index2++;
            }           
    
            });
            
            boutonEnregistrer.addClickListener(event -> {
                
                int index2 =0;
            while(index2<listcbb.size()){
                listcbb.get(index2).setReadOnly(true);
                index2++;
            }     
   
            baddop.setEnabled(false);
            tfref.setReadOnly(true);
            tades.setReadOnly(true);
            int index =0;        
            while(index<listcbb.size()){
                
                if((listcbb.get(index).getValue())== null){
                    listcbb.remove(index);
                    index--;       
                }
                if("vide".equals(((Operation)(listcbb.get(index).getValue())).getNom())){
                    listcbb.remove(index);
                    index--;
                }
                else{  
                listopselect.add((Operation)(listcbb.get(index).getValue()));
                }
  
                index++;
      
            }

                try {
                    this.controleur.getVuePrincipale().getGestionBDD().updateProduit(this.controleur.getVuePrincipale().getGestionBDD().conn,produittemp.getId(),tfref.getValue(),tades.getValue(),(int)Math.round(((Brut)cbbbrut.getValue()).getId()),listopselect);
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    this.controleur.MenuItemProduit();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
     
            boutonSupprimer.addClickListener(event -> {
       
                try {
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"produit",produittemp);
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    this.controleur.MenuItemProduit();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        }
  
         if ("brut".equals(typeobjet)){
            contenu.add(listboutons);
            this.add(contenu);
            
            
            Brut bruttemp = (Brut) object;
            
            TextField tfnom=  new TextField();
            tfnom.setLabel("Nom");
            tfnom.getStyle().set("font-size","40px");
            tfnom.setReadOnly(true);
            tfnom.setValue(bruttemp.getNom());
            tfnom.setWidthFull();
            contenu.add(tfnom);
            
            TextField tfref=  new TextField();
            tfref.setLabel("Référence");
            tfref.setReadOnly(true);
            tfref.setValue(bruttemp.getRef());
            tfref.setWidthFull();
            contenu.add(tfref);
            
            NumberField nfid = new NumberField();
            nfid.setLabel("Identifiant");
            nfid.setReadOnly(true);
            nfid.setWidthFull();
            nfid.setValue((double)Math.round(bruttemp.getId()));
            contenu.add(nfid);
            
            TextField tfmatiere=  new TextField();
            tfmatiere.setLabel("Matière");
            tfmatiere.setReadOnly(true);
            tfmatiere.setValue(bruttemp.getMatiere());
            tfmatiere.setWidthFull();
            contenu.add(tfmatiere);
            
            NumberField nfstock = new NumberField();
            nfstock.setLabel("Stock");
            nfstock.setReadOnly(true);
            nfstock.setWidthFull();
            nfstock.setValue((double)Math.round(bruttemp.getStock()));
            contenu.add(nfstock);
            
            
            TextField tfdim=  new TextField();
            tfdim.setLabel("Dimension");
            tfdim.setReadOnly(true);
            tfdim.setValue(bruttemp.getDimension());
            tfdim.setWidthFull();
            contenu.add(tfdim);
            
            TextField tffourn=  new TextField();
            tffourn.setLabel("Fournisseur");
            tffourn.setReadOnly(true);
            tffourn.setValue(bruttemp.getFournisseur());
            tffourn.setWidthFull();
            contenu.add(tffourn);

            boutonModifier.addClickListener(event -> {
                    
            tfref.setReadOnly(false);
            tfnom.setReadOnly(false);
            tfdim.setReadOnly(false);
            tffourn.setReadOnly(false);
            tfmatiere.setReadOnly(false);
            nfstock.setReadOnly(false);

            });
            
            boutonEnregistrer.addClickListener(event -> {
                    
            tfref.setReadOnly(true);
            tfnom.setReadOnly(true);
            tfdim.setReadOnly(true);
            tffourn.setReadOnly(true);
            tfmatiere.setReadOnly(true);
            nfstock.setReadOnly(true);
   
            try {
                    this.controleur.getVuePrincipale().getGestionBDD().updateBrut(this.controleur.getVuePrincipale().getGestionBDD().conn,bruttemp.getId(),tfnom.getValue(),tfref.getValue(),tfmatiere.getValue(),(int)Math.round(nfstock.getValue()),tfdim.getValue(),tffourn.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                try {
                    this.controleur.MenuItemBrut();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
                     
            boutonSupprimer.addClickListener(event -> {
                
                
                
                try {
                    
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"brut",bruttemp);

                } catch (SQLException ex) {
                    System.out.println("Erreur partie Detail");
                }
                
            });
            
        }
        
        if ("operation".equals(typeobjet)){
            contenu.add(listboutons);
            this.add(contenu);

            Operation operationtemp = (Operation) object;
            
            TextField tfnom=  new TextField();
            tfnom.setLabel("Nom");
            tfnom.getStyle().set("font-size","40px");
            tfnom.setReadOnly(true);
            tfnom.setValue(operationtemp.getNom());
            tfnom.setWidthFull();
            contenu.add(tfnom);

            TextField tfoutil = new TextField("Outil");
            contenu.add(tfoutil);
            tfoutil.setWidthFull();
            tfoutil.setValue(operationtemp.getOutil());            
            
            NumberField nbid = new NumberField("Identifiant");
            nbid.setValue((double)operationtemp.getId());
            nbid.setWidthFull();           
            contenu.add(nbid);
                                   
            NumberField nbduree = new NumberField("Durée (s)");
            nbduree.setValue(operationtemp.getDuree());
            nbduree.setWidthFull();            
            contenu.add(nbduree);
                                                      
            ComboBox comboBoxTypeOperation = new ComboBox();           
            comboBoxTypeOperation.setWidthFull();
                                                          
            ArrayList<Typeoperation> listtemp = new ArrayList();

            listtemp=listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);

            ArrayList listajouter = new ArrayList();

            int index =0;
            while(index< listtemp.size()){

                listajouter.add(listtemp.get(index).getId()+" : "+listtemp.get(index).getNom());
                index++;
            }
            comboBoxTypeOperation.setItems(listajouter);
       
            comboBoxTypeOperation.setLabel("Type d'opération");
            comboBoxTypeOperation.setValue(operationtemp.getIdtypeoperation());
            contenu.add(comboBoxTypeOperation);
      
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
            comboBoxMachines.setValue(operationtemp.getIdmachine());
            contenu.add(comboBoxMachines);

            
            nbid.setReadOnly(true);
            tfnom.setReadOnly(true);
            tfoutil.setReadOnly(true);
            nbduree.setReadOnly(true);
            comboBoxTypeOperation.setReadOnly(true);
            comboBoxMachines.setReadOnly(true); 
            comboBoxMachines.setWidthFull();
            
            boutonModifier.addClickListener(event -> {
            tfnom.setReadOnly(false);
            tfoutil.setReadOnly(false);
            nbduree.setReadOnly(false);
            comboBoxTypeOperation.setReadOnly(false);
            comboBoxMachines.setReadOnly(false);          
            });
            
            boutonEnregistrer.addClickListener(event -> {             
            tfnom.setReadOnly(true);
            tfoutil.setReadOnly(true);
            nbduree.setReadOnly(true);
            comboBoxTypeOperation.setReadOnly(true);
            comboBoxMachines.setReadOnly(true);     
            int indexEspace =-1;
            int idtypeoperationtemp = -2;
          
            String idNom = String.valueOf(comboBoxTypeOperation.getValue());
            try {
                indexEspace = idNom.indexOf(' ');
            }
            catch (ArrayIndexOutOfBoundsException e){
                ;
            }
            
            if(indexEspace==-1){
                
               idtypeoperationtemp =  Integer.parseInt(idNom);
            }            
            else{
                
                String avantEspace = idNom.substring(0,indexEspace);       
                idtypeoperationtemp = Integer.parseInt(avantEspace);
                
            }
      
            int indexEspace1 =-1;
            int idmachineselect = -2;           
            String idMachine = String.valueOf(comboBoxMachines.getValue());
            try {
                indexEspace1 = idMachine.indexOf(' ');
            }
            catch (ArrayIndexOutOfBoundsException e1){
                ;
            }
            if(indexEspace1==-1){               
               idmachineselect = Integer.parseInt(idMachine);
            }           
            else{
                
                String avantEspace1 = idMachine.substring(0,indexEspace1);               
                idmachineselect = Integer.parseInt(avantEspace1);
                
            }

                try {
                    //updateOperation(Connection connect,int id, int idtypeoperation, String nom,double duree,String outil,int idmachine)
                    updateOperation(this.controleur.getVuePrincipale().getGestionBDD().conn,(int) Math.round(nbid.getValue()), idtypeoperationtemp,tfnom.getValue(),nbduree.getValue(),tfoutil.getValue(),idmachineselect);
                } catch (SQLException ex) {
                    System.out.println("Partie détail : erreur dans la modification de l'operation");
                }               
                try {
                    this.controleur.MenuItemOperations();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
     
            boutonSupprimer.addClickListener(event -> {
        
                try {
                    
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"operation",operationtemp);

                    //delete(this.controleur.getVuePrincipale().getGestionBDD().conn,bruttemp.getnomtable(),bruttemp.getId());
                    // On fait apparaitre une fenetre supplémentaire
                } catch (SQLException ex) {
                    System.out.println("Erreur partie Detail");
                }
                
            });
   
        }
        
        
        if ("operateur".equals(typeobjet)){
            contenu.add(listboutons);
            this.add(contenu);
            
            Operateur operateurtemp = (Operateur) object;
            
            final int id =operateurtemp.getId();
            final String identifiant = operateurtemp.getIdentifiant();
            final String mdp =operateurtemp.getMotdepasse();
            final String nom =operateurtemp.getNom();
            final String prenom =operateurtemp.getPrenom();
            
            final int tel = operateurtemp.getTel();
            final String mail = operateurtemp.getMail();       
            ArrayList <String> listStatuts = new ArrayList();
        
            listStatuts.add("Disponible");
            listStatuts.add("En activité");
            listStatuts.add("En formation");
            listStatuts.add("En congé");
            listStatuts.add("Hors service");
        
            ComboBox cbbstatut = new ComboBox();
            cbbstatut.setItems(listStatuts);
            cbbstatut.setReadOnly(true);
            cbbstatut.setWidthFull();
            cbbstatut.setLabel("Statut");

            Div div = new Div();
             div.setHeight("30px");
            Div div5 = new Div();
            div5.setHeight("20px");
            Div div4 = new Div();
            div4.setHeight("20px"); 
            
            H2 hnom = new H2(operateurtemp.getNom()+" "+operateurtemp.getPrenom());

            H3 ttel = new H3("Téléphone : +33 0"+String.valueOf(operateurtemp.getTel()));
            H3 tmail = new H3("Mail : "+String.valueOf(operateurtemp.getMail()));

            contenu.add(div,hnom,div4,ttel,div5,tmail,cbbstatut);

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
            cbbstatut.setValue(idstatut);
            
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
            cbbatelier.setReadOnly(true);
            cbbatelier.setWidthFull();
            cbbatelier.setLabel("Atelier");
            contenu.add(cbbatelier);
 
            cbbstatut.setValue(operateurtemp.getStatutString());
                try {
                    cbbatelier.setValue(operateurtemp.getAtelierString(this.controleur));
                } catch (SQLException ex) {
                    System.out.println("Pas reussi à récupérer l'atelier de l'opérateur");
                } 
                
            Div divtypeop = new Div();
            divtypeop.setHeight("500px");
            
            CheckboxGroup<Typeoperation> cbgtypeop = new CheckboxGroup<>();
            divtypeop.getStyle().set("overflow-y", "auto");
            cbgtypeop.setLabel("Type(s) d'opération(s)");
            ArrayList<Typeoperation> listtemptypop = listTypeOperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            cbgtypeop.setItemLabelGenerator(
            Typeoperation -> Typeoperation.getNom());

            cbgtypeop.setItems(listtemptypop);
            cbgtypeop.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
            cbgtypeop.setReadOnly(true);

            ArrayList<Typeoperation> listtypeoperationoperateur = operateurtemp.getListtypeoperation();

            cbgtypeop.setItems(listtemptypop);    

            for (int i=0;i<listtemptypop.size();i++){
                
                for(int j =0;j<listtypeoperationoperateur.size();j++){
                    
                    if(listtemptypop.get(i).getId()==listtypeoperationoperateur.get(j).getId()){
                        
                        cbgtypeop.select(listtemptypop.get(i));
                    }
                }
            }

            divtypeop.add(cbgtypeop); 
            contenu.add(divtypeop); 
   
            boutonModifier.addClickListener(event -> {
            cbbstatut.setReadOnly(false);
            cbbatelier.setReadOnly(false);
            cbgtypeop.setReadOnly(false);

            });
            
            boutonEnregistrer.addClickListener(event -> {
                    
            cbbstatut.setReadOnly(true);
            cbbatelier.setReadOnly(true);
            cbgtypeop.setReadOnly(true);
            
             String stringstatut =(String) cbbstatut.getValue();
            
            
            int idstatut2=-1;

            if("En congé".equals(stringstatut)){
            idstatut2=0;
            }
            if("En activité".equals(stringstatut)){
            idstatut2=1;
            }
            if("En formation".equals(stringstatut)){
                idstatut2=2;
            }
            if("Disponible".equals(stringstatut)){
                idstatut2=3;
            }
            if("Hors service".equals(stringstatut)){
                idstatut2=4;
            }
            
            final int idstatutchoix = idstatut2;

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
            try {
                    this.controleur.getVuePrincipale().getGestionBDD().updateOperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,id,identifiant,mdp,nom,prenom,idateliertemp,idstatutchoix,tel,mail,listtypeop);
     
            } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }    
                try {
                    this.controleur.MenuItemOperateur();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }         
            });
         
            boutonSupprimer.addClickListener(event -> {

                try {  
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"operateur",operateurtemp);
                } catch (SQLException ex) {
                    System.out.println("Erreur partie Detail");
                }      
            });        
        }
    }
    
}
