/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author arnaud
 */
public class VuePlan extends MyVerticalLayout {
    
    
    public VuePlan(){
        this.add(new H2("Plan de l'atelier"));
    
        int l=15;
        int L=10;
        List<Span> ordonnee = new ArrayList();
                
        Grid<Button> grid = new Grid<>();
        grid.addColumn(String::valueOf).setHeader("m");
        for (int i=0;i<L;i++){
            ordonnee.add(new Span(String.valueOf(i)));
            
        }
//        grid.setItems(ordonnee);
        
        List<Button> but = new ArrayList<>();
        for (int i=0;i<L;i++){
            but.add(createSquareButton("yap"));
        }
        grid.setItems(but);
        for (int i=0;i<l;i++){
        grid.addComponentColumn(item -> createSquareButton("hey")).setHeader(String.valueOf(i));
        }
        this.add(grid);




//        for (int i = 0; i < l; i++) {
//            for (int j = 0; j < 3; j++) {
//                Button button = createSquareButton();
//                int row = i;
//                int col = j;
//
//                // Ajouter un ClickListener pour changer la couleur du bouton
//                button.addClickListener(event -> changeButtonColor(button, row, col));
//
//                gridLayout.addComponent(button, col, row);
//            }
//        }
//
//        add(gridLayout);
//    }
//     private Button createSquareButton() {
//        Button button = new Button();
//        button.setWidth("100px");
//        button.setHeight("100px");
//        return button;
//    }
//
//    private void changeButtonColor(Button button, int row, int col) {
//        // Changer la couleur du bouton (vous pouvez personnaliser cela selon vos besoins)
//        button.getStyle().set("background-color", "blue");

// Créez une liste de données de démonstration
       
    }  
    
    private Button createSquareButton(String name) {
        Button button = new Button(name);
        button.getStyle().setBackground("blue");
        button.setWidth("100px");
        button.setHeight("100px");
        button.addClickListener(even -> {
            System.out.println("clique clique clique");
            changeButtonColor(button);
        });
        return button;
    }
    
    private void changeButtonColor(Button button){
      // Obtenez la couleur actuelle du bouton
        String currentColor = button.getStyle().get("background-color");

        // Utilisez une instruction if pour vérifier la couleur actuelle et changer en conséquence
        if ("red".equals(currentColor)) {
            // Si la couleur est rouge, changez-la en bleu
            button.getStyle().set("background-color", "blue");
        } else {
            // Sinon, changez-la en rouge
            button.getStyle().set("background-color", "red");
        }    }
}
