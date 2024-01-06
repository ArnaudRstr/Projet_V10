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
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;
import static fr.insa.moly.GestionBDD.GestionBDD.listmachine;
import static fr.insa.moly.GestionBDD.GestionBDD.listtypeoperation;
import static fr.insa.moly.GestionBDD.GestionBDD.updateOperation;
import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Operateur;
import fr.insa.moly.objet.Operation;
import fr.insa.moly.objet.Produit;
import fr.insa.moly.objet.Typeoperation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 *
 * @author arnaud
 */
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
        System.out.println("Partie detail mise à jour");
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
        
        
        
        this.listboutons.add(new H2(" Détail"),div0,boutonSupprimer,boutonModifier,boutonEnregistrer);
        
        
        
        
        if (typeobjet =="machine"){
            
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
            
            
            ArrayList altypeop = new ArrayList();
            
            altypeop = listtypeoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
            
            ArrayList listajouter = new ArrayList();

            int index =0;
            while(index< altypeop.size()){
            
            //listajouter.add(((Typeoperation)altypeop.get(index)).getId()+" : "+ ((Typeoperation)altypeop.get(index)).getNom());
            listajouter.add(((Typeoperation)altypeop.get(index)).getNom());
            index++;
        }
            cbtypeoperation.setItems(listajouter);
            
            
            
            //Ici il faut encore faire en sorte d'afficher l'id + le nom du type d'opération et pas uniquement le type d'op pcq ça marche pas.
            
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
                    altypeop1 = listtypeoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
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
            
            if (nvstat=="Arrêt"){
                idnvstatut =0;
            }
            if (nvstat=="Marche"){
                idnvstatut =1;
            }
            if (nvstat=="Maintenance à prévoir"){
                idnvstatut =2;
            }
            

            
            
         //                                                                                                                                          Connection connect,int id,String nom,int idatelier,int idtypeoperation,String des, String marque,double puissance,int statut,double couthoraire,String localisation,double dimensionlargeur,double dimensionlongueur)   
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
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"machine",machinetemp.getNom(),machinetemp.getId());
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
        
        
        if (typeobjet == "produit"){
            contenu.add(listboutons);
            this.add(contenu);
            MyHorizontalLayout hlref = new MyHorizontalLayout();
            MyHorizontalLayout hlid = new MyHorizontalLayout();
            MyHorizontalLayout hldes = new MyHorizontalLayout();
            MyHorizontalLayout hlidbut = new MyHorizontalLayout();
            
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
            
            
            NumberField nfidbrut = new NumberField();
            nfidbrut.setLabel("Identifiant du brut");
            nfidbrut.setReadOnly(true);
            nfidbrut.setWidthFull();
            nfidbrut.setValue((double)Math.round(produittemp.getIdbrut()));
            contenu.add(nfidbrut);
            
            
            //On pourra encore ajouter le nom du brut
//            TextField tfnombrut=  new TextField();
//            tfref.setLabel("Nom du brut");
//            tfref.getStyle().set("font-size","40px");
//            tfref.setReadOnly(true);
//            
//            
//            tfref.setValue();
//            tfref.setWidthFull();
//            contenu.add(tfref);

            
            
            
            
            boutonModifier.addClickListener(event -> {
                    
            tfref.setReadOnly(false);
            nfidbrut.setReadOnly(false);
            tades.setReadOnly(false);
            
            
                        
            
            
            
            
            });
            
            boutonEnregistrer.addClickListener(event -> {
                    
            tfref.setReadOnly(true);
            nfidbrut.setReadOnly(true);
            tades.setReadOnly(true);
            
            
            
            
            
            System.out.println();
            System.out.println(produittemp.getId()+tfref.getValue()+tades.getValue()+(int)Math.round(nfidbrut.getValue()));
            
                try {
                    this.controleur.getVuePrincipale().getGestionBDD().updateProduit(this.controleur.getVuePrincipale().getGestionBDD().conn,produittemp.getId(),tfref.getValue(),tades.getValue(),(int)Math.round(nfidbrut.getValue()));
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
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"brut",produittemp.getRef(),produittemp.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //delete(this.controleur.getVuePrincipale().getGestionBDD().conn,produittemp.getnomtable(),produittemp.getId());
                try {
                    this.controleur.MenuItemProduit();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        }
        
        
        
        
         if (typeobjet == "brut"){
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
            
            
 
           
            
            //On pourra encore ajouter le nom du brut
//            TextField tfnombrut=  new TextField();
//            tfref.setLabel("Nom du brut");
//            tfref.getStyle().set("font-size","40px");
//            tfref.setReadOnly(true);
//            
//            
//            tfref.setValue();
//            tfref.setWidthFull();
//            contenu.add(tfref);

            
            
            
            
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
            
            
            Notification.show("La méthode d'enregistrement n'est pas encore faite");
            
            
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
                    
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"brut",bruttemp.getNom(),bruttemp.getId());

                    //delete(this.controleur.getVuePrincipale().getGestionBDD().conn,bruttemp.getnomtable(),bruttemp.getId());
                    // On fait apparaitre une fenetre supplémentaire
                } catch (SQLException ex) {
                    System.out.println("Erreur partie Detail");
                }
                
            });
            
        }
        
        if (typeobjet == "operation"){
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

            listtemp=listtypeoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);

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

            listmachinetemp=listmachine(this.controleur.getVuePrincipale().getGestionBDD().conn);

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
            System.out.println("IndexEspace1"+indexEspace1);
            if(indexEspace1==-1){
                
               idmachineselect = Integer.parseInt(idMachine);
            }
            
            else{
                
                String avantEspace1 = idMachine.substring(0,indexEspace1);
                
                idmachineselect = Integer.parseInt(avantEspace1);
                
            }
            
            System.out.println("id type op /"+idtypeoperationtemp+"/id machine /"+idmachineselect+"/");
            

            
            
          
            
            
            
            
            
            
            
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
                    
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"operation",operationtemp.getNom(),operationtemp.getId());

                    //delete(this.controleur.getVuePrincipale().getGestionBDD().conn,bruttemp.getnomtable(),bruttemp.getId());
                    // On fait apparaitre une fenetre supplémentaire
                } catch (SQLException ex) {
                    System.out.println("Erreur partie Detail");
                }
                
            });
            
            
            
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        if (typeobjet == "operateur"){
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
            final ArrayList typeop =operateurtemp.getListtypeoperation();
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
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

            contenu.add(cbbstatut);
            
            
            
            
            
            
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
            cbbstatut.setValue(idstatut);
            
           
            
            
            
            
            
            
            ComboBox cbbatelier = new ComboBox();
            ArrayList <Atelier> listTemp = new ArrayList();
            listTemp = listaltelier (this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            ArrayList <String> listNomAtelier = new ArrayList<>();

            int index =0;

            while (index<listTemp.size()){
                Atelier atelierTemp = (Atelier) listTemp.get(index);

                listNomAtelier.add(atelierTemp.getId()+" : "+atelierTemp.getNom());

                System.out.println(listNomAtelier.get(index));
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
            divtypeop.setHeight("350px");
            CheckboxGroup<Typeoperation> cbgtypeop = new CheckboxGroup<>();
            divtypeop.getStyle().set("overflow-y", "auto");
            cbgtypeop.setLabel("Type(s) d'opération(s)");
            ArrayList<Typeoperation> listtemptypop = listtypeoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);
      
            cbgtypeop.setItemLabelGenerator(
            Typeoperation -> Typeoperation.getNom());

            cbgtypeop.setItems(listtemptypop);
            cbgtypeop.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
            cbgtypeop.setReadOnly(true);
            divtypeop.add(cbgtypeop); 
            
            

//            List <Integer> listidselect=operateurtemp.getListtypeoperation();
//            
//            List<Typeoperation> selectedTypeoperations = (List<Typeoperation>) listtemptypop.stream()
//                    
//                    
//                .filter(typeoperation -> listidselect.contains(((Typeoperation)typeoperation).getId()))
//                .collect(Collectors.toList());
            
               ArrayList<Integer> listtypeoperation = operateurtemp.getListtypeoperation();
               
               List<Integer> listidselect =  operateurtemp.getListtypeoperation();
               
            System.out.println("Liste des types operations de l'operateur"+listtypeoperation);
            System.out.println("Le type de la liste est : " + listtypeoperation.getClass().getSimpleName());
            System.out.println("Le type d'un élément de liste est : " + listtypeoperation.get(0).getClass().getSimpleName());
            
        List<Typeoperation> selectedTypeoperations = listtemptypop.stream()
            .filter(typeoperation -> listidselect.contains((typeoperation).getId()))
            .collect(Collectors.toList()); 
            
        
        
            //cbgtypeop.select(selectedTypeoperations.toArray(new Typeoperation[0]));
            cbgtypeop.select(selectedTypeoperations);    
                
              contenu.add(cbgtypeop ); 
                
                
                
                
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
        
        
            if(stringstatut=="En congé"){
            idstatut2=0;
            }
            if(stringstatut=="En activité"){
            idstatut2=1;
            }
            if(stringstatut=="En formation"){
                idstatut2=2;
            }
            if(stringstatut=="Disponible"){
                idstatut2=3;
            }
            if(stringstatut=="Hors service"){
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
            
            
            ArrayList <Integer> listidtest = new ArrayList();
            
            listidtest.add(1);
            listidtest.add(13);
            
            
            Notification.show("La méthode d'enregistrement n'est pas encore faite");
            
            try {
                    //this.controleur.getVuePrincipale().getGestionBDD().updateOperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,operateurtemp.getId(),operateurtemp.getIdentifiant(),operateurtemp.getMotdepasse(),operateurtemp.getNom(),operateurtemp.getPrenom(),idateliertemp,idstatut,operateurtemp.getTel(),operateurtemp.getMail(),operateurtemp.getListtypeoperation());
              
            
                    this.controleur.getVuePrincipale().getGestionBDD().updateOperateur(this.controleur.getVuePrincipale().getGestionBDD().conn,id,identifiant,mdp,nom,prenom,idateliertemp,idstatutchoix,tel,mail,listidtest);

            
            } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                try {
                    this.controleur.MenuItemOperateur();
                } catch (SQLException ex) {
                    Logger.getLogger(PartieDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
//            
//            
            boutonSupprimer.addClickListener(event -> {
                
                
                
                try {
                    
                    FenetreAvertissementSuppression fenetreAvertissementSuppression = new FenetreAvertissementSuppression(this.controleur,"operateur",operateurtemp.getNom(),operateurtemp.getId());

                    //delete(this.controleur.getVuePrincipale().getGestionBDD().conn,bruttemp.getnomtable(),bruttemp.getId());
                    // On fait apparaitre une fenetre supplémentaire
                } catch (SQLException ex) {
                    System.out.println("Erreur partie Detail");
                }
                
            });
            
            
            
            
            
            
        }
    }
    
}
