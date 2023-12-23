/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.moly.objet.Machine;

/**
 *
 * @author arnaud
 */
public class PartieDetail extends MyVerticalLayout{
    
private Controleur controleur;

    public PartieDetail(Controleur controleur){
        this.controleur=controleur;
        
        this.add(new H2("Partie de détail")); 
    }
        
    public PartieDetail(Controleur controleur, String typeobjet , Object object ){
        this.controleur=controleur;

        System.out.println("Partie detail mise à jour");
        
        if (typeobjet =="machine"){
            
            MyVerticalLayout contenu = new MyVerticalLayout();
            
            MyHorizontalLayout hlnom = new MyHorizontalLayout();
            MyHorizontalLayout hlid = new MyHorizontalLayout();
            MyHorizontalLayout hldes = new MyHorizontalLayout();
            MyHorizontalLayout hlmarque = new MyHorizontalLayout();
            MyHorizontalLayout hltypeop = new MyHorizontalLayout();
            MyHorizontalLayout hlpuissance = new MyHorizontalLayout();
            MyHorizontalLayout hlcouthoraire = new MyHorizontalLayout();
            MyHorizontalLayout hldimlarg = new MyHorizontalLayout();
            MyHorizontalLayout hldimlong = new MyHorizontalLayout();   
            //AVEC TOUS LES PETITS BOUTONS
//            TextField tfnom=  new TextField();
//            tfnom.setLabel("Nom de la machine");
//            Button bmnom = new Button(new Icon("lumo","edit"));
//            hlnom.add(tfnom,bmnom);
//            contenu.add(hlnom);
//            
//            TextField tfmarque=  new TextField();
//            tfmarque.setLabel("Marque");
//            Button bmmarque = new Button(new Icon("lumo","edit"));
//            hlmarque.add(tfmarque,bmmarque);
//            contenu.add(hlmarque);
//            
//            NumberField nfid = new NumberField();
//            nfid.setLabel("Identifiant");
//            hlid.add(nfid);
//            contenu.add(hlid);
//            
//            TextArea tades= new TextArea();
//            tades.setLabel("Description");
//            Button bmdes = new Button(new Icon("lumo","edit"));
//            hldes.add(tades,bmdes);
//            contenu.add(hldes);
//            
//            
//            
//            NumberField nfpuissance = new NumberField();
//            nfpuissance.setLabel("Puissance (W)");
//            Button bmpuissance = new Button(new Icon("lumo","edit"));
//            hlpuissance.add(nfpuissance,bmpuissance);
//            contenu.add(hlpuissance);
//            
//            NumberField nfcouthoraire = new NumberField();
//            nfcouthoraire.setLabel("Cout horaire (€/h)");
//            Button bmcouthoraire = new Button(new Icon("lumo","edit"));
//            hlcouthoraire.add(nfcouthoraire,bmcouthoraire);
//            contenu.add(hlcouthoraire);
//            
//            NumberField nfdimlarg = new NumberField();
//            nfdimlarg.setLabel("Largeur (cm)");
//            Button bmdimlarg = new Button(new Icon("lumo","edit"));
//            hldimlarg.add(nfdimlarg,bmdimlarg);
//            contenu.add(hldimlarg);
//            
//            NumberField nfdimlong = new NumberField();
//            nfdimlong.setLabel("Longueur (cm)");
//
//            Button bmdimlong = new Button(new Icon("lumo","edit"));
//            hldimlong.add(nfdimlong,bmdimlong);
//            contenu.add(hldimlong);

            MyHorizontalLayout listboutons = new MyHorizontalLayout();
            
            Button boutonModifier = new Button(new Icon("lumo","edit"));
            listboutons.add(boutonModifier);
            Button boutonEnregistrer = new Button(new Icon("lumo","checkmark"));
            listboutons.add(boutonEnregistrer);
            contenu.add(listboutons);

            Machine machinetemp = (Machine) object;
            
            TextField tfnom=  new TextField();
            tfnom.setLabel("Nom");
            tfnom.setReadOnly(true);
            tfnom.setValue(machinetemp.getNom());
            tfnom.setWidthFull();
            contenu.add(tfnom);
            
            TextField tfmarque=  new TextField();
            tfmarque.setLabel("Marque");
            tfmarque.setReadOnly(true);
            tfmarque.setValue(machinetemp.getMarque());
            contenu.add(tfmarque);
            
            NumberField nfid = new NumberField();
            nfid.setLabel("Identifiant");
            nfid.setReadOnly(true);
            nfid.setValue((double)Math.round(machinetemp.getId()));
            contenu.add(nfid);
            
            TextArea tades= new TextArea();
            tades.setLabel("Description");
            tades.setReadOnly(true);
            tades.setValue(machinetemp.getDes());
            tades.setWidthFull();
            contenu.add(tades);
            
            
            
            NumberField nfpuissance = new NumberField();
            nfpuissance.setLabel("Puissance (W)");
            nfpuissance.setReadOnly(true);
            nfpuissance.setValue(machinetemp.getPuissance());
            contenu.add(nfpuissance);
            
            NumberField nfcouthoraire = new NumberField();
            nfcouthoraire.setLabel("Cout horaire (€/h)");
            nfcouthoraire.setReadOnly(true);
            nfcouthoraire.setValue(machinetemp.getCouthoraire());
            contenu.add(nfcouthoraire);
            
            NumberField nfdimlarg = new NumberField();
            nfdimlarg.setLabel("Largeur (cm)");
            nfdimlarg.setReadOnly(true);
            nfdimlarg.setValue(machinetemp.getDimensionlargeur());
            contenu.add(nfdimlarg);
            
            NumberField nfdimlong = new NumberField();
            nfdimlong.setLabel("Longueur (cm)");
            nfdimlong.setReadOnly(true);
            nfdimlong.setValue(machinetemp.getDimensionlongueur());

            contenu.add(nfdimlong);
            
            this.add(contenu);
            
            
            boutonModifier.addClickListener(event -> {
                    
            tfnom.setReadOnly(false);
            tfmarque.setReadOnly(false);
            tades.setReadOnly(false);
            nfpuissance.setReadOnly(false);
            nfcouthoraire.setReadOnly(false);
            nfdimlarg.setReadOnly(false);
            nfdimlong.setReadOnly(false);
            
                        
            
            
            
            
            });
            
            boutonEnregistrer.addClickListener(event -> {
                    
            tfnom.setReadOnly(true);
            tfmarque.setReadOnly(true);
            tades.setReadOnly(true);
            nfpuissance.setReadOnly(true);
            nfcouthoraire.setReadOnly(true);
            nfdimlarg.setReadOnly(true);
            nfdimlong.setReadOnly(true); 
            
            Notification.show("La méthode d'enregistrement n'est pas encore faite");
            
            });
            
        }
        
        
        
        
    }
    
/*   
    public void setParagraph(Paragraph paragraph){
            this.paragraph = paragraph;
                    }

    void setText(String text) {
        this.text = text;
    }*/
}
