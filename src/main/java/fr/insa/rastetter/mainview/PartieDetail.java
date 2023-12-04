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
    

    
    public PartieDetail(){
        this.add(new H2("ça c'est la partie de détail"));
        this.add(new H3("Ici on aura le détail"));

    }
    
/*   
    public void setParagraph(Paragraph paragraph){
            this.paragraph = paragraph;
                    }

    void setText(String text) {
        this.text = text;
    }*/
}
