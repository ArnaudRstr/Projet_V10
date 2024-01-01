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
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Produit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */
public class PartiePrincipale extends MyVerticalLayout {

    private MenuBar menuBar;
    private MenuItem menuItemAjouter;
    private String objet;
    public Paragraph paragraph;
    private Grid<Machine> gridMachines;
    private Grid<Produit> gridProduits;
    private Grid<Brut> gridBruts;
    private MyHorizontalLayout hltitre;

    private Controleur controleur;
    
    public PartiePrincipale(Controleur controleur, String objet) throws SQLException{

        this.controleur=controleur;
        this.objet=objet;
        this.getStyle().set("padding", "0px");
        this.hltitre=new MyHorizontalLayout();
        
        this.menuBar=new MenuBar();
        this.menuItemAjouter=menuBar.addItem("Ajouter");
        this.add(menuBar);
        
        this.menuItemAjouter.setEnabled(false);


        if (objet=="machine"){
            this.hltitre.add(new H2("Machines"),menuBar);
            this.add(hltitre);
            //this.add(new H2("Machines"));
            ArrayList <Machine> machinesTemp = new ArrayList();
            System.out.println("Etat du controleur avant création de la machine:"+this.controleur.getEtatAtelier());
            machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listMachineAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn,this.controleur.getEtatAtelier());
            
            
            //machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listmachine(this.controleur.getVuePrincipale().getGestionBDD().conn);

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
                Text nbelements = new Text(machinesTemp.size()+" machines dans cet atelier");
                this.add(nbelements);
                
                this.gridMachines= new Grid<>();
                gridMachines.addColumn(Machine::getNom).setHeader(new H5("Nom"));
                
                gridMachines.addColumn(Machine::getMarque).setHeader(new H5("Marque"));
                //gridMachines.addColumn(Machine::getStatut).setHeader("Statut");
                gridMachines.addColumn(Machine::getStatutString).setHeader(new H5("Statut"));
                gridMachines.setItems(machinesTemp);
                this.add(gridMachines);
                
                gridMachines.addItemClickListener(event -> {
                    Notification.show("Ligne sélectionnée"+event.getItem().getId());
                   
                MyVerticalLayout test = new MyVerticalLayout();
                test.add(new Text("TEST"));
                Machine machineTemp = event.getItem();
                
                
                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"machine",machineTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
                
            }
            
            
         
            
        this.menuItemAjouter.addClickListener(event -> {
            Notification.show("Option Plan sélectionnée !");
                try {
                    this.controleur.CreationObjet("machine");
                } catch (SQLException ex) {
                    Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
                    });
            

        }
        
        if (objet=="produit"){
            
            this.hltitre.add(new H2("Produits"),menuBar);
            this.add(hltitre);
            this.menuItemAjouter.setEnabled(true);
            //this.add(new H2("Produits"));
            ArrayList <Produit> produitsTemp = new ArrayList();
            System.out.println("Etat du controleur avant création de la machine:"+this.controleur.getEtatAtelier());
            produitsTemp = this.controleur.getVuePrincipale().getGestionBDD().listproduit(this.controleur.getVuePrincipale().getGestionBDD().conn);
            
            
            //machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listmachine(this.controleur.getVuePrincipale().getGestionBDD().conn);

            int index =0;
            
            if (produitsTemp.size()==0){
                
                this.add(new H3("Il n'y a pas de produits à fabriquer"));
            }
            
            else{
                Text nbelements = new Text(produitsTemp.size()+" produits");
                this.add(nbelements);
                this.gridProduits= new Grid<>();
                gridProduits.addColumn(Produit::getRef).setHeader("Référence");
                gridProduits.setItems(produitsTemp);
                this.add(gridProduits);
                gridProduits.addItemClickListener(event -> {
                    Notification.show("Ligne sélectionnée"+event.getItem().getId());
                   
                Produit produitTemp = event.getItem();
                
                
                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"produit",produitTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
            }
            
            this.menuItemAjouter.addClickListener(event -> {
            Notification.show("Option produit sélectionnée !");
                try {
                    this.controleur.CreationObjet("produit");
                } catch (SQLException ex) {
                    Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        }
        
        
        
        if (objet=="brut"){
            
            this.hltitre.add(new H2("Bruts"),menuBar);
            this.add(hltitre);
            this.menuItemAjouter.setEnabled(true);
            //this.add(new H2("Bruts"));
            ArrayList <Brut> brutstemp = new ArrayList();
            System.out.println("Etat du controleur avant création de la machine:"+this.controleur.getEtatAtelier());
            brutstemp = this.controleur.getVuePrincipale().getGestionBDD().listbrut(this.controleur.getVuePrincipale().getGestionBDD().conn);
            
            
            
            
            
            
            

            int index =0;
            
            if (brutstemp.size()==0){
                
                this.add(new H3("Il n'y a pas de bruts "));
                
            }
            
            else{
                Text nbelements = new Text(brutstemp.size()+" bruts");
                this.add(nbelements);
                this.gridBruts= new Grid<>();
                gridBruts.addColumn(Brut::getNom).setHeader(new H5("Nom"));
                gridBruts.addColumn(Brut::getRef).setHeader(new H5("Référence"));
                gridBruts.addColumn(Brut::getStock).setHeader(new H5("En stock"));

                gridBruts.setItems(brutstemp);
                this.add(gridBruts);
                
                gridBruts.addItemClickListener(event -> {
                    Notification.show("Ligne sélectionnée"+event.getItem().getId());
                   
                Brut brutTemp = event.getItem();
                
                
                    try {
                        this.controleur.getFenetrePartagee().setPartD(new PartieDetail(this.controleur,"brut",brutTemp));
                    } catch (SQLException ex) {
                        Logger.getLogger(PartiePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
                
            }
            
            this.menuItemAjouter.addClickListener(event -> {
            Notification.show("Option brut sélectionnée !");
                try {
                    this.controleur.CreationObjet("brut");
                } catch (SQLException ex) {
                    System.out.println("Erreur : partie principale : Pas reussi à créer le brut");
                }
            });
            
        }
        

        
        
        
        
                

//        List<String> listTest = new ArrayList<>();
//        listTest.add("1");
//        listTest.add("2");
//        
//        this.grid=new Grid(String.class);
//        this.grid.setItems(listTest);
//        this.add(grid);
    }
    
    
    
    public void setParagraph(Paragraph paragraph){
        this.paragraph=paragraph;
    }
    
    
    

}
