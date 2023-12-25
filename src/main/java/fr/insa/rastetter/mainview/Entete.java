/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;
import fr.insa.moly.objet.Atelier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author arnaud
 */


public class Entete extends MyHorizontalLayout {
    
    public VuePrincipale main;
    
    
    
    
    
    //Boutons
    private Button boutonCompteUtilisateur;
    
    
    
    
    
    
    
    
    private MenuBar menuBarG;
    private MenuBar menuBarD;
    private MenuBar menuFichier;
    private MenuItem menuItemMenu;
    
    
    
    //Pour le bouton de menu principal
    private SubMenu subMenuMenuPrincipal;
    private MenuItem menuItemPlan;
    private MenuItem menuItemMachine;
    private MenuItem menuItemProduit;
    
    private MenuItem menuItemAtelier;
    private SubMenu subMenuAtelier;
    private MenuItem menuItemAjouterAtelier;
    private MenuItem menuItemDetailsAtelier;
    private MenuItem menuItemSupprimerAtelier;
    private MenuItem menuItemTypeOperation;
    private MenuItem menuItemOperateurs;
    
    
    private ComboBox comboBoxAtelier;
    
    
    
    private ArrayList <String> listDesAteliers;
    
    public Entete(){
        
    }
    
    
    
    
    public Entete(VuePrincipale main) throws SQLException {
        this.main = main;
        
        this.addClassName("Custom-Entete");
        this.getStyle().set("border", "1px solid #000000");
        this.getStyle().set("padding", "1px");
        this.getStyle().set("border-radius", "7px");
        this.getStyle().set("background-color","#F8F8FF");


        this.setWidthFull();
        
        
        
        
        
        //Configuration de la menubar Menu
        this.menuBarG = new MenuBar();
        this.menuBarD = new MenuBar();

        
                
        this.menuItemMenu= this.menuBarG.addItem("Menu Principal");
        
        //On crée un sous menu du menu item du menu principal
        this.subMenuMenuPrincipal= menuItemMenu.getSubMenu();
        
        //On ajoute les menu item au sous menu du menuitem du menu principal
        this.menuItemPlan = this.subMenuMenuPrincipal.addItem("Plan");
        this.menuItemMachine = this.subMenuMenuPrincipal.addItem("Machines");
        this.menuItemProduit = this.subMenuMenuPrincipal.addItem("Produits");
        this.menuItemTypeOperation=this.subMenuMenuPrincipal.addItem("Types d'opérations");
        this.menuItemOperateurs=this.subMenuMenuPrincipal.addItem("Operateurs");

        
        this.menuItemAtelier=this.menuBarD.addItem("Atelier");
        this.subMenuAtelier=menuItemAtelier.getSubMenu();
        
        
        this.menuItemAjouterAtelier=this.subMenuAtelier.addItem("Ajouter");
        this.menuItemDetailsAtelier=this.subMenuAtelier.addItem("Details");
        this.menuItemSupprimerAtelier=this.subMenuAtelier.addItem("Supprimer");
        
        this.comboBoxAtelier= new ComboBox<>();
        this.comboBoxAtelier.setHelperText("Atelier");
        
        
        this.setAlignItems(CENTER);

        
        
        
        
        
        
        
 
        
        
        this.setComboBoxAtelier(listaltelier(this.main.getGestionBDD().conn));
        
        
        

        

        
        
        this.boutonCompteUtilisateur = new Button("Compte");
        
        
        
        this.add(menuBarG,comboBoxAtelier,menuBarD,boutonCompteUtilisateur);
    
        this.getThemeList().add("spacing-xl");
        
        
        

        
        
        
        //On renvoie l'événement vers le controleur de la vue principale
        boutonCompteUtilisateur.addClickListener(event -> {
            Notification.show("Option Compte sélectionnée !");
            this.main.getControleur().boutonCompte();
        });
        
        
        
        
        menuItemPlan.addClickListener(event -> {
            Notification.show("Option Plan sélectionnée !");
            this.main.getControleur().MenuItemPlan();
            this.main.getControleur().setEtatFenetre("plan");
        });
        
        
        menuItemMachine.addClickListener(event -> {
            Notification.show("Option machine sélectionnée !");
            try {
                this.main.getControleur().MenuItemMachine();
                this.main.getControleur().setEtatFenetre("machine");
            } catch (SQLException ex) {
                
                Logger.getLogger(Entete.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        
        menuItemProduit.addClickListener(event -> {
            Notification.show("Option produit sélectionnée !");
            try {
                this.main.getControleur().MenuItemProduit();
            } catch (SQLException ex) {
                System.out.println("Entete : erreur :  MenuItemProduit dans le controleur");
            }
            
            
            
            
            
        });
        
        
        
        menuItemTypeOperation.addClickListener(event -> {
            Notification.show("Option type operation sélectionnée !");
//            try {
//                //On essaie d'ajouter un type d'opération
//                this.main.getGestionBDD().addtypeoperation(this.main.getGestionBDD().conn,"test2");
//                System.out.println("Type opération ajouté");
//            } catch (SQLException ex) {
//                System.out.println("Type opération pas ajouté");
//            }



            try {
                this.main.getControleur().MenuItemTypeOperation();
            } catch (SQLException ex) {
                System.out.println("Pas reussi à execuer la méthode du controleur menuItemTypeOperation");
            }
        });
        
        menuItemOperateurs.addClickListener(event -> {
            Notification.show("Operateurs sélectionnés !");
            
            
            
        });
        
        
        
        
        
        
        menuItemAjouterAtelier.addClickListener(event -> {
         
        Notification.show("bouton ajouter Atelier sélectionné !");

            try {
                this.main.getControleur().CreationObjet("atelier");
            } catch (SQLException ex) {
                Logger.getLogger(Entete.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        //Il faut remettre à jour le bouton
        
//        try {
//
//               this.setComboBoxAtelier(listaltelier(this.main.getGestionBDD().conn));
//               
//                
//                System.out.println("Combo box Atelier mis à jour");
//            } catch (SQLException ex) {
//                Logger.getLogger(Entete.class.getName()).log(Level.SEVERE, null, ex);   
//            }
//            
//             
     });
        
        
        menuItemDetailsAtelier.addClickListener(event -> {
         
        Notification.show("bouton details Atelier sélectionné !");
        
            try {
                this.main.getControleur().MenuItemDetailsAtelier();
            } catch (SQLException ex) {
                Logger.getLogger(Entete.class.getName()).log(Level.SEVERE, null, ex);
            }
                
 
        });
        
        
        
        menuItemSupprimerAtelier.addClickListener(event -> {
         
        Notification.show("bouton Supprimer Atelier sélectionné !");
        
        this.main.getControleur().MenuItemSupprimerAtelier();
                
 
        });
        
        
        comboBoxAtelier.addValueChangeListener(event -> {
            
            //on cherche à récupérer l'identifiant de l'atelier, on fait une méthode générale dans le cas où l'on peu avoir plusieurs ateliers (+ de 9)
            
            //System.out.println("Evénement Combo box déclenché");
            
            System.out.println("1er event value: " +event.getValue()+" (Entete evenement du combobox");
            
            
            
            if (event.getValue()==null){
                
            }
            else if (event.getValue()!=null){
                
                String idNom = (String) event.getValue();
                
                
            int indexEspace = idNom.indexOf(' ');
            String avantEspace = idNom.substring(0,indexEspace);
            int idAtelier = Integer.parseInt(avantEspace);
            //System.out.println("idAtelier "+idAtelier);
            
            // On renvoie la valeur de l'atelier au contrôleur pour mettre à jour dans quel atelier on se trouve
            
            this.main.getControleur().setEtatAtelier(idAtelier);
            
            
            System.out.println("Etat du controleur mis à jour : "+idAtelier);
            
            
            //Ici on met à jour la fenetre
                try {
                    if (this.main.getControleur().getEtatFenetre() =="machine"){
                        this.main.getControleur().MenuItemMachine();

                    }
                    
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Entete.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        });

}
    
    
            
            
            

    
    
    
    public void setComboBoxAtelier(ArrayList<Atelier> listTemp){
        
        ArrayList <String> listNomAtelier = new ArrayList<>();

        
        
        int index =0;
        
        while (index<listTemp.size()){
            Atelier atelierTemp = (Atelier) listTemp.get(index);
            
            listNomAtelier.add(atelierTemp.getId()+" : "+atelierTemp.getNom());
            
            System.out.println(listNomAtelier.get(index));
            index++;
        }
        
        //System.out.println("arrivé dans la méthode set comboboxAtelier");
        //System.out.println("liste ateliers à mettre dans combobox: "+listNomAtelier);
        
        
        comboBoxAtelier.setItems(listNomAtelier);
        //System.out.println("Le comboBox est mis à jour (méthode setCombobox)");
        
    }
    
    
    

   
    
}
