/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.moly.GestionBDD.GestionBDD;
import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Machine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */
public class VuePlan extends MyVerticalLayout {
    
    private Atelier atelier;
    private int ordonnee;
    private int abscisse;
    private ArrayList<PlanButton> spaceMachineTaked;
    private ArrayList<PlanButton> spaceSelect;
    private ArrayList<Machine> listMachine;
    private Machine machinecbb;

    public Atelier getAtelier() {
        return atelier;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public ArrayList<PlanButton> getSpaceMachineTaked() {
        return spaceMachineTaked;
    }

    public ArrayList<PlanButton> getSpaceSelect() {
        return spaceSelect;
    }

    public ArrayList<Machine> getListMachine() {
        return listMachine;
    }

    public Machine getMachinecbb() {
        return machinecbb;
    }
    
    
    public VuePlan(Controleur controleur,int idatelier)throws SQLException {
        System.out.println("VuePlan");
        Atelier atelier = new Atelier(controleur.getVuePrincipale().getGestionBDD().conn, idatelier);
        System.out.println("Atelier trouvé");
        this.atelier = atelier;
        this.ordonnee = atelier.getLargeur();
        this.abscisse= atelier.getLongueur();
        this.spaceSelect= new ArrayList();
        this.spaceMachineTaked= new ArrayList();
        this.listMachine = GestionBDD.listMachineAtelier(controleur.getVuePrincipale().getGestionBDD().conn,atelier.getId());
        System.out.println("les attributs sont initialisés");
        
        //Combobox Machine
        ComboBox<Machine> cbbMachine = new ComboBox();
        cbbMachine.setItems(this.listMachine);
        cbbMachine.setItemLabelGenerator(Machine::getNom);
        cbbMachine.setReadOnly(false);
        cbbMachine.setLabel("Machine");
        this.machinecbb=cbbMachine.getValue();
        
        //Bouton valider
        Button valider = new Button("Valider");
        valider.addClickListener(even -> {
            try {
                GestionBDD.deletePlacementMachine(controleur.getVuePrincipale().getGestionBDD().conn, cbbMachine.getValue());
                for (int i = 0; i<spaceSelect.size();i++){
                    GestionBDD.addPlacementMachine(controleur.getVuePrincipale().getGestionBDD().conn, cbbMachine.getValue(), spaceSelect.get(i).getAbscisse(), spaceSelect.get(i).getOrdonnee());
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i=0; i<spaceSelect.size();i++){
                spaceMachineTaked.add(spaceSelect.get(i));
            }
            
            spaceSelect= new ArrayList();
            
            for (int i=0; i<spaceMachineTaked.size();i++){
                spaceMachineTaked.get(i).getStyle().set("background-color", "grey");
            }
            
        });
        
        
        
        //Colonne des boutons de base
        HorizontalLayout hltouls = new HorizontalLayout();
        hltouls.add(valider);
        hltouls.add(cbbMachine);
        
        //Plan
        VerticalLayout vlOrdonnee = new VerticalLayout();
       
        for (int i=0;i<this.ordonnee;i++){
            vlOrdonnee.add(new H2(String.valueOf(i)));
        }
        
        this.add(new H2("Plan de l'atelier"));
        
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(hltouls);
        hl.add(vlOrdonnee);
        for (int i=0;i<abscisse;i++ ){
            hl.add(createVerticalLayout(i));
        }
        hl.setSpacing(false);
        this.add(hl);
    }

    
    
    private String changeButtonColor(PlanButton button){
      // Obtenez la couleur actuelle du bouton
        String currentColor = button.getStyle().get("background-color");
        

        // Utilisez une instruction if pour vérifier la couleur actuelle et changer en conséquence
        if ("grey".equals(currentColor)){
            
        }
        else if ("red".equals(currentColor)) {
            // Si la couleur est rouge, changez-la en bleu
            button.getStyle().set("background-color", "white");
            this.spaceSelect.remove(button);
        } else {
            // Sinon, changez-la en rouge
            button.getStyle().set("background-color", "red");
            this.spaceSelect.add(button);
        }    
        return button.getStyle().get("background-color");
    }
    
    
    
    
    private VerticalLayout createVerticalLayout(int abscisse){
        
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(false);
        vl.setWidth("30px");
        vl.add(new H2(String.valueOf(abscisse)));
        for (int j=0;j<ordonnee;j++){
          
          vl.add(new PlanButton(abscisse,j,this.machinecbb));
        }
        return vl;
    }
    
    
    
    class PlanButton extends Button{
        private int abscisse;
        private int ordonnee;
        private String color;

        public PlanButton(int abscisse,int ordonnee,Machine cbbMachine){
            this.abscisse=abscisse;
            this.ordonnee=ordonnee;
            
            this.getStyle().setBackground("white");
            
            this.color=this.getStyle().get("background-color");
            this.setWidth("25px");
            this.setHeight("25px");
            this.addClickListener(even -> {

                this.color=changeButtonColor(this);
                System.out.println(this.abscisse + ","+this.ordonnee);
                 });
              }

        public int getAbscisse() {
            return abscisse;
        }

        public int getOrdonnee() {
            return ordonnee;
        }

        public String getColor() {
            return color;
        }
    }
}
