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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.moly.objet.Machine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arnaud
 */
public class PartiePrincipale extends MyVerticalLayout {

    private MenuBar menuBar;
    private MenuItem menuItemAjouter;
    
    public Paragraph paragraph;
    private Grid grid;
    
    private Controleur controleur;
    
    public PartiePrincipale(Controleur controleur) throws SQLException{

        this.controleur=controleur;
        this.menuBar=new MenuBar();
        this.menuItemAjouter=menuBar.addItem("Ajouter");
        
        this.paragraph= new Paragraph("ici on aura la liste des machines");
        this.add(new H2("ça c'est la partie principale"));
        
        
        this.controleur.getVuePrincipale().getGestionBDD().addmachine(this.controleur.getVuePrincipale().getGestionBDD().conn,"Presse",1,1,"presse hydraulique","NoName",3000,0,12,"Strasbourg",50,223);
        
        ArrayList <Machine> machinesTemp = new ArrayList();
        machinesTemp = this.controleur.getVuePrincipale().getGestionBDD().listmachine(this.controleur.getVuePrincipale().getGestionBDD().conn);
        
        
        
        this.add(paragraph);        
        
        this.add(machinesTemp.get(0).getPannel());

        List<String> listTest = new ArrayList<>();
        listTest.add("1");
        listTest.add("2");
        
        this.grid=new Grid(String.class);
        this.grid.setItems(listTest);
        this.add(grid);
    }
    
    
    
    public void setParagraph(Paragraph paragraph){
        this.paragraph=paragraph;
    }
    

}
