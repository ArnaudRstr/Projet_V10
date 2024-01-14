/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.moly.GestionBDD.GestionBDD;
import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Machine;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private ArrayList<PlanButton> listButton;
    private Controleur controleur;
    private ComboBox<Machine> cbbMachine;

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

    public VuePlan(Controleur controleur, int idatelier) throws SQLException {

        this.listButton = new ArrayList();
        this.controleur = controleur;
        //System.out.println("VuePlan");
        Atelier atelier = new Atelier(controleur.getVuePrincipale().getGestionBDD().conn, idatelier);
        //System.out.println("Atelier trouvé");
        this.atelier = atelier;
        this.ordonnee = atelier.getLargeur();
        this.abscisse = atelier.getLongueur();
        this.spaceSelect = new ArrayList();
        this.spaceMachineTaked = new ArrayList();
        this.listMachine = GestionBDD.listMachineAtelier(controleur.getVuePrincipale().getGestionBDD().conn, atelier.getId());

        //Combobox Machine
        this.cbbMachine = new ComboBox();
        cbbMachine.setItems(this.listMachine);
        cbbMachine.setItemLabelGenerator(Machine::getNom);
        cbbMachine.setReadOnly(false);
        cbbMachine.setLabel("Machine");
        cbbMachine.addValueChangeListener(even -> {

            this.machinecbb = cbbMachine.getValue();

            try {
                this.listMachine = GestionBDD.listMachineAtelier(controleur.getVuePrincipale().getGestionBDD().conn, atelier.getId());
            } catch (SQLException ex) {
                Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < this.listButton.size(); i++) {
                try {
                    setColorPlanButton(this.listButton.get(i), this.listMachine, false);
                } catch (SQLException ex) {
                    Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Bouton valider
        Button valider = new Button("Valider");
        valider.addClickListener(even -> {
            System.out.println(this.spaceSelect.size());
            try {
                GestionBDD.deletePlacementMachine(controleur.getVuePrincipale().getGestionBDD().conn, this.cbbMachine.getValue());
                for (int i = 0; i < this.spaceSelect.size(); i++) {
                    GestionBDD.addPlacementMachine(controleur.getVuePrincipale().getGestionBDD().conn, this.cbbMachine.getValue(), spaceSelect.get(i).getAbscisse(), spaceSelect.get(i).getOrdonnee());
                }

            } catch (SQLException ex) {
                Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                this.machinecbb = new Machine(controleur.getVuePrincipale().getGestionBDD().conn, this.machinecbb.getId());
            } catch (SQLException ex) {
                Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.spaceSelect = new ArrayList();
            System.out.println("spaceselect vide " + this.spaceSelect.size());

            try {
                this.listMachine = GestionBDD.listMachineAtelier(controleur.getVuePrincipale().getGestionBDD().conn, atelier.getId());
            } catch (SQLException ex) {
                Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < this.listButton.size(); i++) {
                try {
                    setColorPlanButton(this.listButton.get(i), this.listMachine, false);
                } catch (SQLException ex) {
                    Logger.getLogger(VuePlan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.cbbMachine.setItems(this.listMachine);
        });

        //Colonne des boutons de base
        HorizontalLayout hltouls = new HorizontalLayout();
        hltouls.add(valider);
        hltouls.add(cbbMachine);

        //Plan
        VerticalLayout vlOrdonnee = new VerticalLayout();

        for (int i = 0; i < this.ordonnee; i++) {
            vlOrdonnee.add(new H2(String.valueOf(i)));
        }

        this.add(new H2("Plan de l'atelier"));

        HorizontalLayout hl = new HorizontalLayout();
        hl.add(hltouls);
        hl.add(vlOrdonnee);
        for (int i = 0; i < abscisse; i++) {
            hl.add(createVerticalLayout(i));
        }
        hl.setSpacing(false);
        this.add(hl);

    }

    //gestion du changement de couleur au clique
    private String changeButtonColor(PlanButton button) {
        String currentColor = button.getStyle().get("background-color");

        if (!"grey".equals(currentColor)) {

            if ("red".equals(currentColor)) {
                // Si la couleur est rouge, changez-la en bleu
                button.getStyle().set("background-color", "white");
                this.spaceSelect.remove(button);
            } else {
                // Sinon, changez-la en rouge
                button.getStyle().set("background-color", "red");
                this.spaceSelect.add(button);
            }
        }
        return button.getStyle().get("background-color");
    }

    //gestion de la couleur du bouton selon s'il est rattaché à une machine
    private void setColorPlanButton(PlanButton button, ArrayList<Machine> machineAtelier, boolean initialisation) throws SQLException {

        //ArrayList<Machine> machineAtelier = GestionBDD.listMachineAtelier(controleur.getVuePrincipale().getGestionBDD().conn,this.atelier.getId());
        for (int i = 0; i < machineAtelier.size(); i++) {
            if (machineAtelier.get(i).getCoordonnee().length != 0) {
                for (int j = 0; j < machineAtelier.get(i).getCoordonnee()[0].length; j++) {
                    if (button.getAbscisse() == machineAtelier.get(i).getCoordonnee()[0][j] && button.getOrdonnee() == machineAtelier.get(i).getCoordonnee()[1][j]) {

                        button.getStyle().set("background-color", "grey");
                        button.setStatut(1);
                        this.spaceMachineTaked.add(button);
                    }
                }
            }

        }

        if (initialisation == false) {
            if (this.machinecbb.getCoordonnee().length != 0) {
                for (int i = 0; i < this.machinecbb.getCoordonnee()[0].length; i++) {
                    if (button.getAbscisse() == this.machinecbb.getCoordonnee()[0][i] && button.getOrdonnee() == this.machinecbb.getCoordonnee()[1][i]) {
                        button.getStyle().set("background-color", "red");
                        System.out.println("correspondance !!!!!!!!!!");
                        button.setStatut(2);
                        this.spaceSelect.add(button);
                    }
                }
            }
        }
    }

    //Colonne de button pour le plan
    private VerticalLayout createVerticalLayout(int abscisse) {

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(false);
        vl.setWidth("55px");
        vl.add(new H2(String.valueOf(abscisse)));
        for (int j = 0; j < ordonnee; j++) {
            PlanButton pb = new PlanButton(abscisse, j, this.cbbMachine.getValue());
            this.listButton.add(pb);
            vl.add(pb);
        }
        return vl;
    }

    //Buttons formaté pour le plan
    class PlanButton extends Button {

        private int abscisse;
        private int ordonnee;
        private String color;
        private int statut;

        public PlanButton(int abscisse, int ordonnee, Machine cbbMachine) {
            this.statut = 0;
            this.abscisse = abscisse;
            this.ordonnee = ordonnee;
            this.addThemeVariants(ButtonVariant.LUMO_SMALL);
            this.getStyle().setBackground("white");

            this.color = this.getStyle().get("background-color");
            this.setWidth("50px");
            this.setHeight("50px");
            this.addClickListener(even -> {

                this.color = changeButtonColor(this);
                System.out.println(this.abscisse + "," + this.ordonnee);
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

        public int getStatut() {
            return statut;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setStatut(int statut) {
            this.statut = statut;
        }

    }
}
