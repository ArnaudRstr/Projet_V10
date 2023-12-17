/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;

/**
 *
 * @author arnaud
 */
public class PartieDetail extends MyVerticalLayout{
    
private Controleur controleur;
    
    public PartieDetail(Controleur controleur){
        this.controleur=controleur;
        this.add(new H2("Partie de détail"));
        this.add(new H3("Ici on aura le détail de l'objet sélectionné à gauche"));

        
        
    }
    
/*   
    public void setParagraph(Paragraph paragraph){
            this.paragraph = paragraph;
                    }

    void setText(String text) {
        this.text = text;
    }*/
}
