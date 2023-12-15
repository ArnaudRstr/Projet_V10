/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.moly.objet.Machine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    private Grid grid;
    
    private Controleur controleur;
    
    public PartiePrincipale(Controleur controleur, String objet) throws SQLException{

        this.controleur=controleur;
        this.objet=objet;
        
        this.menuBar=new MenuBar();
        this.menuItemAjouter=menuBar.addItem("Ajouter");
        this.add(menuBar);
        
        

        if (objet=="machine"){
            this.add(new H2("Machines"));
            ArrayList <Machine> machinesTemp = new ArrayList();
            System.out.println("Etat du controleur avant création de la machine:"+this.controleur.getEtat());
            machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listMachineAtelier(this.controleur.getVuePrincipale().getGestionBDD().conn,this.controleur.getEtat());
            
            //machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listmachine(this.controleur.getVuePrincipale().getGestionBDD().conn);

            int index =0;
            while (index<machinesTemp.size()){
                this.add(machinesTemp.get(index).getPannel());
                
                index++;
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
