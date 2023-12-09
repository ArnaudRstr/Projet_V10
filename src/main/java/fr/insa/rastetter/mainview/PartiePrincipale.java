/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arnaud
 */
public class PartiePrincipale extends MyVerticalLayout {

    
    public Paragraph paragraph;
    private Grid grid;
    public PartiePrincipale(){

        this.paragraph= new Paragraph("ici on aura la liste des machines");
        this.add(new H2("ça c'est la partie principale"));
        
        
        this.add(paragraph);
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
