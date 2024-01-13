/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;
import com.vaadin.flow.server.StreamResource;
import fr.insa.moly.objet.Atelier;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fr.insa.moly.GestionBDD.GestionBDD.listAtelier;
/**
 *
 * @author arnaud
 */


public class Entete extends MyHorizontalLayout {
    
    public VuePrincipale main;
    
    
    
    
    
    //Boutons
    
    private MenuItem menuItemCompte;
    
    
    
    
    
    
    private MenuBar menuBarG;
    private MenuBar menuBarD;
    private MenuBar menuBarM;
    private MenuBar menuFichier;
    private MenuItem menuItemMenu;
    
    
    
    //Pour le bouton de menu principal
    private SubMenu subMenuMenuPrincipal;
    private MenuItem menuItemPlan;
    private MenuItem menuItemMachine;
    private MenuItem menuItemProduit;
    private MenuItem menuItemTypeOperation;
    private MenuItem menuItemOperations;

    private MenuItem menuItemAtelier;
    private SubMenu subMenuAtelier;
    private MenuItem menuItemAjouterAtelier;
    private MenuItem menuItemDetailsAtelier;
    private MenuItem menuItemSupprimerAtelier;
    private MenuItem menuItemOperateurs;
    private MenuItem menuItemBrut;
    
    private SubMenu subMenuCompte;
    private MenuItem menuItemDeconnexion;
    private MenuItem menuItemInfosCompte;

    private MenuItem menuItemNotice;
    private MenuItem menuItemInfos;
    private ComboBox comboBoxAtelier;
    
    
    
    private ArrayList <String> listDesAteliers;
    
    public Entete(){
        this.comboBoxAtelier= new ComboBox<>();
    }
    
    
    
    
    public Entete(VuePrincipale main) throws SQLException {
        this.main = main;
        this.comboBoxAtelier= new ComboBox<>();
        
        

        this.addClassName("Custom-Entete");
        //this.getStyle().set("border", "1px solid #000000");
        this.getStyle().set("padding", "0px");
        this.getStyle().set("border-radius", "15px");
        this.getStyle().set("background-color","#EFEFEF");
        //this.getStyle().set("background-color","#D3D3D3");
        this.setMargin(true);


        this.setWidthFull();
        
        String couleur1 = new String("#38998C");
        String couleur2 = new String("#BDE767");
        String couleur3 = new String("#1F4C83");
        
        //Configuration de la menubar Menu
        this.menuBarG = new MenuBar();
        this.menuBarM = new MenuBar();
        this.menuBarD = new MenuBar();


        
        Icon menuIcon = new Icon(VaadinIcon.MENU);
        menuIcon.setSize("20px");
        menuIcon.setColor(couleur3);
        MyHorizontalLayout hlmenu = new MyHorizontalLayout();
        hlmenu.add(menuIcon,new H5("Menu"));
       
        hlmenu.setAlignItems(CENTER);
        //this.menuItemMenu= this.menuBarG.addItem(menuIcon,"Menu Principal");
        this.menuItemMenu= this.menuBarG.addItem(hlmenu);
        
        
        //On crée un sous menu du menu item du menu principal
        this.subMenuMenuPrincipal= menuItemMenu.getSubMenu();
        
        //On ajoute les menu item au sous menu du menuitem du menu principal
        this.menuItemPlan = this.subMenuMenuPrincipal.addItem("Plan");
        this.menuItemMachine = this.subMenuMenuPrincipal.addItem("Machines");
        this.menuItemProduit = this.subMenuMenuPrincipal.addItem("Produits");       
        this.menuItemBrut=this.subMenuMenuPrincipal.addItem("Bruts");
        this.menuItemOperations=this.subMenuMenuPrincipal.addItem("Operations");
        this.menuItemTypeOperation=this.subMenuMenuPrincipal.addItem("Types d'opérations");
        
        this.menuItemOperateurs=this.subMenuMenuPrincipal.addItem("Operateurs");

        
        
        this.menuItemAtelier=this.menuBarM.addItem(new H5("Atelier"));
        this.subMenuAtelier=menuItemAtelier.getSubMenu();
        
        
        this.menuItemAjouterAtelier=this.subMenuAtelier.addItem("Ajouter");
        this.menuItemDetailsAtelier=this.subMenuAtelier.addItem("Details");
        this.menuItemSupprimerAtelier=this.subMenuAtelier.addItem("Supprimer");
        
        this.comboBoxAtelier.setPlaceholder("Atelier");
        
        
        this.setAlignItems(CENTER);

        
        
        
        
        
        
        
 
        
        
        this.setComboBoxAtelier(listAtelier(this.main.getGestionBDD().conn));
        
        
        
        Icon compteIcon = new Icon(VaadinIcon.USER);
        compteIcon.setSize("20px");
        compteIcon.setColor(couleur3);
        MyHorizontalLayout hlcompte = new MyHorizontalLayout();
        hlcompte.add(compteIcon,new H5("Compte"));
        hlcompte.setAlignItems(CENTER);
        
        
        //this.menuItemMenu= this.menuBarG.addItem(hlmenu);
        
        this.menuItemCompte = this.menuBarD.addItem(hlcompte);
        
        this.subMenuCompte=menuItemCompte.getSubMenu();
        
        this.menuItemDeconnexion = this.subMenuCompte.addItem("Déconnexion");
        this.menuItemInfosCompte=this.subMenuCompte.addItem("Informations");
        
        
        
        Icon infosIcon = new Icon(VaadinIcon.INFO_CIRCLE);
        infosIcon.setSize("20px");
        infosIcon.setColor(couleur3);
        MyHorizontalLayout hlinfos = new MyHorizontalLayout();
        hlinfos.add(infosIcon,new H5("A propos"));
        hlinfos.setAlignItems(CENTER);
        
        Icon noticeIcon = new Icon(VaadinIcon.QUESTION);
        noticeIcon.setSize("20px");
        noticeIcon.setColor(couleur3);
        MyHorizontalLayout hlnotice = new MyHorizontalLayout();
        hlnotice.add(noticeIcon,new H5("Notice"));
        hlnotice.setAlignItems(CENTER);
        
        
        this.menuItemNotice= this.menuBarD.addItem(hlnotice);
        this.menuItemInfos= this.menuBarD.addItem(hlinfos);
        
        
        
        
        Div div0 = new Div();
        div0.setWidth("100px");
        Div div1 = new Div();
        div1.setWidth("300px");
        Div div2 = new Div();
        div2.setWidth("400px");
        Div div3 = new Div();
        div0.setWidth("1px");
        
        String imagePath = "logo.png";

        // Créer une ressource de flux pour l'image
        StreamResource resource = new StreamResource("logo.png", () ->
                getClass().getClassLoader().getResourceAsStream(imagePath));

        // Créer un composant Image avec la ressource
        Image image = new Image(resource, "Description de l'image");
        image.setWidth("60px");
        
        Span nomLogiciel = new Span("LEON");
        nomLogiciel.getElement().getStyle().set("font-family", "Nunito");
        nomLogiciel.getStyle().set("font-size", "25px");
        
        
        this.add(menuBarG,div1,comboBoxAtelier,menuBarM,div2,menuItemCompte,menuBarD,image,nomLogiciel);
    
        this.getThemeList().add("spacing-xl");
        
        
        

        
        
        
        //On renvoie l'événement vers le controleur de la vue principale
        menuItemDeconnexion.addClickListener(event -> {
            Notification.show("Deconnexion selectionnée !");
            this.main.getControleur().MenuItemDeconnexion();
        });
        
        
        
        menuItemInfosCompte.addClickListener(event -> {
            Notification.show("Infos compte selectionné !");
            try {
                this.main.getControleur().MenuItemInfosCompte();
            } catch (SQLException ex) {
                System.out.println("Entete : pas reussi à aller dans MenuIteminfoscompte");
            }
        });
        
        
        
        menuItemNotice.addClickListener(event -> {
            Notification.show("Ouverture du mode d'emploi");
            String cheminRelatif = "src/main/resources/FichierTest.pdf";
        
        try {
            // Utilisation de cmd pour ouvrir le fichier avec l'application par défaut
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "", cheminRelatif);
            processBuilder.start();
        } catch (IOException er) {
            er.printStackTrace();
        }
            
            
        });
        
        
        
        
        menuItemInfos.addClickListener(event -> {
            Notification.show("Ouverture de la section à propos");
            String cheminRelatif = "src/main/resources/LEON.pdf";
        
        try {
            // Utilisation de cmd pour ouvrir le fichier avec l'application par défaut
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "", cheminRelatif);
            processBuilder.start();
        } catch (IOException er) {
            er.printStackTrace();
        }
            
            
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
        
        menuItemOperations.addClickListener(event -> {
            Notification.show("Option operation sélectionnée !");
            try {
                this.main.getControleur().MenuItemOperations();
            } catch (SQLException ex) {
                System.out.println("Entete : erreur :  MenuItemOperations dans le controleur");
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
            try {
                this.main.getControleur().MenuItemOperateur();
            } catch (SQLException ex) {
                System.out.println("Entete : erreur :  MenuItemOperateur dans le controleur");
            }
            
            
        });
        
        
        menuItemBrut.addClickListener(event -> {
            Notification.show("Bruts sélectionnés !");
            
            try {
                this.main.getControleur().MenuItemBrut();
            } catch (SQLException ex) {
                System.out.println("Erreur controleur : menuItemBrut");
            }
            
            
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
        
            try {
                this.main.getControleur().MenuItemSupprimerAtelier();
            } catch (SQLException ex) {
                Logger.getLogger(Entete.class.getName()).log(Level.SEVERE, null, ex);
            }
                
 
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
        
        
        this.comboBoxAtelier.setItems(listNomAtelier);
        //System.out.println("Le comboBox est mis à jour (méthode setCombobox)");
        
    }
    
    
    

   
    
}
