/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */
public class VueInitialeConnection extends MyVerticalLayout{
    
    public VuePrincipale main;

    
    private Button connectButton;
    
    public VueInitialeConnection(VuePrincipale main){
        this.main = main;
        this.add(new H2("Veuillez vous connecter"));
        this.connectButton= new Button("se connecter");
        this.add(this.connectButton);
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        this.connectButton.addClickListener(e -> {
        this.main.setMainContent(new VuePlan());
            try {
                this.main.setEntete(new Entete(this.main));
            } catch (SQLException ex) {
                Logger.getLogger(VueInitialeConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    Notification.show("connection cliqué !");
    });
        
            

    
    }
}
