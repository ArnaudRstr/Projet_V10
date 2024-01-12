/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.moly.objet.Atelier;
import java.awt.GridLayout;

/**
 *
 * @author molys
 */
public class Plan extends VerticalLayout {
    private Atelier atelier;
    
//    public Plan() {
//        int l=10;
//        int L=10;
//        GridLayout gridLayout = new GridLayout(l,L);
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
//    }
}
