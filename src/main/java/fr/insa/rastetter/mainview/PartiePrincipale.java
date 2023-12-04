/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 *
 * @author arnaud
 */
public class PartiePrincipale extends MyVerticalLayout {

    
    public Paragraph paragraph;
    
    public PartiePrincipale(){

        this.paragraph= new Paragraph("ici on aura la liste des machines");
        this.add(new H2("ça c'est la partie principale"));
        
        
        this.add(paragraph);
    }
    
    
    
    public void setParagraph(Paragraph paragraph){
        this.paragraph=paragraph;
    }
    

}
