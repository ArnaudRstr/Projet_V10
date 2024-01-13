/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import fr.insa.moly.GestionBDD.GestionBDD;
import static fr.insa.moly.GestionBDD.GestionBDD.listproduit;
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Operateur;
import fr.insa.moly.objet.Operation;
import fr.insa.moly.objet.Produit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */

//Cette classe est principalement la partie gauche du SplitLayout. On y affiche le tableaux des objets dans la base de données
//Lorsqu'une ligne du tableau est sélectionnée, on affiche la partie détail.
public class PartiePrincipale extends MyVerticalLayout {

    private MenuBar menuBar;
    private MenuItem menuItemAjouter;
    private String objet;
    public Paragraph paragraph;
    private Grid<Machine> gridMachines;
    private Grid<Produit> gridProduits;
    private Grid<Brut> gridBruts;
    private Grid<Operateur> gridOperateurs;
    private Grid<Operation> gridOperations;
    private MyHorizontalLayout hltitre;
    private Controleur controleur;
    
    public PartiePrincipale(Controleur controleur, String objet) throws SQLException{

        this.controleur=controleur;
        this.objet=objet;
        this.hltitre=new MyHorizontalLayout();
        this.getStyle().set("padding", "0px");

        this.menuBar=new MenuBar();
        this.menuItemAjouter=menuBar.addItem(new H5("Ajouter"));
        this.add(menuBar);
        this.menuItemAjouter.setEnabled(false);

        if ("machine".equals(objet)){
            this.hltitre.add(new H2("Machines"),menuBar);
            this.add(hltitre);
            ArrayList <Machine> machinesTemp = new ArrayList();
            machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listMachineAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn,this.controleur.getEtatAtelier());
            int index =0;
            if (machinesTemp.size()==0 && this.controleur.getEtatAtelier()==-1){
                this.add(new H3("Sélectionnez tout d'abord un atelier"));
            }
            else if (machinesTemp.size()==0){
                
                this.add(new H3("Il n'y a pas de machines dans cet atelier"));
                this.menuItemAjouter.setEnabled(true);
            }
            else {
                this.menuItemAjouter.setEnabled(true);
                Text nbelements = new Text(machinesTemp.size()+" machine(s) dans cet atelier");
                this.add(nbelements); 
                this.gridMachines= new Grid<>();
                gridMachines.addColumn(Machine::getId).setHeader(new H5("Identifiant"))
                       .setComparator(Comparator.comparingInt(Machine::getId));

                gridMachines.addColumn(Machine::getNom).setHeader(new H5("Nom"))
                        .setComparator(Comparator.comparing(Machine::getNom, Comparator.naturalOrder()));
                
                gridMachines.addColumn(Machine::getMarque).setHeader(new H5("Marque"))
                         .setComparator(Comparator.comparing(Machine::getMarque, Comparator.naturalOrder()));
                gridMachines.addColumn(Machine::getStatutString).setHeader(new H5("Statut"))
                         .setComparator(Comparator.comparing(Machine::getStatutString, Comparator.naturalOrder()));
                gridMachines.setItems(machinesTemp);
                this.add(gridMachines);
                gridMachines.addItemClickListener(event -> {

                Machine machineTemp = event.getItem();

                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"machine",machineTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });   
            }   
        this.menuItemAjouter.addClickListener(event -> {
                try {
                    this.controleur.CreationObjet("machine");
                } catch (SQLException ex) {
                    Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                }

                    });
            

        }
        
        if ("produit".equals(objet)){
            
            this.hltitre.add(new H2("Produits"),menuBar);
            this.add(hltitre);
            this.menuItemAjouter.setEnabled(true);
            ArrayList <Produit> produitsTemp = new ArrayList();
            produitsTemp = listproduit(this.controleur.getVuePrincipale().getGestionBDD().conn);

            int index =0;
            if (produitsTemp.size()==0){
                this.add(new H3("Il n'y a pas de produits à fabriquer"));
            }
            else{
                Text nbelements = new Text(produitsTemp.size()+" produit(s)");
                this.add(nbelements);
                this.gridProduits= new Grid<>();
                gridProduits.addColumn(Produit::getRef).setHeader("Référence")
                        .setComparator(Comparator.comparing(Produit::getRef, Comparator.naturalOrder()));
                gridProduits.addColumn(Produit::getIdbrut).setHeader("Brut")
                        .setComparator(Comparator.comparingInt(Produit::getIdbrut));
                gridProduits.addColumn(Produit::getDes).setHeader("Description");
                gridProduits.setItems(produitsTemp);
                this.add(gridProduits);
                gridProduits.addItemClickListener(event -> {                   
                Produit produitTemp = event.getItem();

                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"produit",produitTemp));
                    } catch (SQLException ex) {
                        System.out.println("Erreur Partie principale : Produits");
                    }      
                });
                
            }
            this.menuItemAjouter.addClickListener(event -> {
                try {
                    this.controleur.CreationObjet("produit");
                } catch (SQLException ex) {
                    Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        }

        if ("brut".equals(objet)){     
            this.hltitre.add(new H2("Bruts"),menuBar);
            this.add(hltitre);
            this.menuItemAjouter.setEnabled(true);
            ArrayList <Brut> brutstemp = new ArrayList();
            brutstemp = this.controleur.getVuePrincipale().getGestionBDD().listBrut(this.controleur.getVuePrincipale().getGestionBDD().conn);
            int index =0;        
            if (brutstemp.size()==0){
                
                this.add(new H3("Il n'y a pas de bruts "));
                
            }           
            else{
                Text nbelements = new Text(brutstemp.size()+" brut(s)");
                this.add(nbelements);
                this.gridBruts= new Grid<>();
                gridBruts.addColumn(Brut::getNom).setHeader(new H5("Nom"))
                        .setComparator(Comparator.comparing(Brut::getNom, Comparator.naturalOrder()));
                gridBruts.addColumn(Brut::getRef).setHeader(new H5("Référence"));
                gridBruts.addColumn(Brut::getFournisseur).setHeader(new H5("Fournisseur"))
                        .setComparator(Comparator.comparing(Brut::getFournisseur, Comparator.naturalOrder()));
                gridBruts.addColumn(Brut::getStock).setHeader(new H5("En stock"))
                        .setComparator(Comparator.comparingInt(Brut::getStock));
                gridBruts.setItems(brutstemp);
                this.add(gridBruts);               
                gridBruts.addItemClickListener(event -> {                  
                Brut brutTemp = event.getItem();             
                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"brut",brutTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
             
            }        
            this.menuItemAjouter.addClickListener(event -> {
                try {
                    this.controleur.CreationObjet("brut");
                } catch (SQLException ex) {
                    System.out.println("Erreur : partie principale : Pas reussi à créer le brut");
                }
            });            
        }    
        if ("operateur".equals(objet)){
            
            this.hltitre.add(new H2("Operateurs"),menuBar);
            this.add(hltitre);
            this.menuItemAjouter.setEnabled(true);    
            ArrayList <Operateur> operateurstemp = new ArrayList();
            operateurstemp = GestionBDD.listoperateur(this.controleur.getVuePrincipale().getGestionBDD().conn);
     
            int index =0; 
            if (operateurstemp.size()==0){       
                this.add(new H3("Il n'y a pas d'operateurs "));         
            }          
            else{                
                Text nbelements = new Text(operateurstemp.size()+" operateur(s)");
                this.add(nbelements);
                this.gridOperateurs= new Grid<>();

                gridOperateurs.setItems(operateurstemp);
                gridOperateurs.addColumn(Operateur::getId).setHeader(new H5("Identifiant"))
                        .setComparator(Comparator.comparingInt(Operateur::getId));
                gridOperateurs.addColumn(Operateur::getNom).setHeader(new H5("Nom"));
                gridOperateurs.addColumn(Operateur::getPrenom).setHeader(new H5("Prénom"));
   
                gridOperateurs.addColumn(operateur -> {
                try {        
                    return operateur.getAtelierString(this.controleur);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
                }).setHeader(new H5("Atelier"))
                        .setComparator(Comparator.comparingInt(Operateur::getIdatelier));
              
                gridOperateurs.addColumn(Operateur::getStatutString).setHeader(new H5("Statut"))
                        .setComparator(Comparator.comparing(Operateur::getStatutString, Comparator.naturalOrder()));

                this.add(gridOperateurs);
                
                gridOperateurs.addItemClickListener(event -> {             
                Operateur opTemp = event.getItem();
                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"operateur",opTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }              
                });
    
            this.menuItemAjouter.addClickListener(event -> {
                try {
                    this.controleur.CreationObjet("operateur");
                } catch (SQLException ex) {
                    System.out.println("Erreur : partie principale : Pas reussi à créer l'operateur");
                }
            });            
        }
    
        } 

        if ("operation".equals(objet)){
            this.hltitre.add(new H2("Opérations"),menuBar);
            this.add(hltitre);
            this.menuItemAjouter.setEnabled(true);
            ArrayList <Operation> operationstemp = new ArrayList();
            operationstemp = this.controleur.getVuePrincipale().getGestionBDD().listoperation(this.controleur.getVuePrincipale().getGestionBDD().conn);

            int index =0;
            if (operationstemp.size()==0){    
                this.add(new H3("Il n'y a pas d'operations "));       
            }
            
            else{
                Text nbelements = new Text(operationstemp.size()+" opération(s)");
                this.add(nbelements);
                this.gridOperations= new Grid<>();
                gridOperations.setItems(operationstemp);
                gridOperations.addColumn(Operation::getId).setHeader(new H5("Identifiant"))
                        .setComparator(Comparator.comparingInt(Operation::getId));
                gridOperations.addColumn(Operation::getNom).setHeader(new H5("Nom"))
                        .setComparator(Comparator.comparing(Operation::getNom, Comparator.naturalOrder()));
                
                gridOperations.addColumn(Operation::getIdmachine).setHeader(new H5("Identifiant de la machine"))
                        .setComparator(Comparator.comparingInt(Operation::getIdmachine));
                gridOperations.addColumn(Operation::getOutil).setHeader(new H5("Outil"))
                        .setComparator(Comparator.comparing(Operation::getOutil, Comparator.naturalOrder()));
                this.add(gridOperations);
                
                gridOperations.addItemClickListener(event -> {  
                Operation operationTemp = event.getItem();

                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"operation",operationTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }      
                });
      
            }
            
            this.menuItemAjouter.addClickListener(event -> {
                try {
                    this.controleur.CreationObjet("operation");
                } catch (SQLException ex) {
                    System.out.println("Erreur : partie principale : Pas reussi à créer l'operation");
                }
            });       
        }

    }

    public void setParagraph(Paragraph paragraph){
        this.paragraph=paragraph;
    }

}
