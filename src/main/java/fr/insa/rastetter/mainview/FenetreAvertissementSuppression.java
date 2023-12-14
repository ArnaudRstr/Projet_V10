/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

/**
 *
 * @author arnaud
 */
public class FenetreAvertissementSuppression extends Dialog {
    
    private Button boutonContinuer;
    private Button boutonAnnuler;
    private MyVerticalLayout contenu;
    private MenuBar menuBar;
    private Controleur controleur;

    
public FenetreAvertissementSuppression(String text){
    
    this.contenu=new MyVerticalLayout();
    this.boutonAnnuler =new Button("Annuler");
    this.boutonContinuer=new Button("Supprimer définitivement");
    this.menuBar=new MenuBar();
    
    this.menuBar.getElement().getThemeList().add("borderless");
    this.menuBar.getElement().getThemeList().add("padding");
    
    this.menuBar.addItem(boutonAnnuler);
    this.menuBar.addItem(boutonContinuer);
    this.contenu.add(new H3("Etes-vous sûr de vouloir supprimer la sélection? : "+ text), menuBar);
    this.contenu.setAlignItems(FlexComponent.Alignment.CENTER);
    this.add(contenu);
    
    this.open();
    
    boutonAnnuler.addClickListener(event -> {
           this.close();   
       });
    
    boutonContinuer.addClickListener(event -> {
           this.close(); 
           
           //Ici il faut mettre la suppresion
       });
    
    
}
}
