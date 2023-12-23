/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.splitlayout.SplitLayout;
import java.sql.SQLException;

/**
 *
 * @author arnaud
 */


public class FenetrePartagee extends MyVerticalLayout {
    
    
    private Controleur controleur;
    private String objet;
    public SplitLayout splitLayout;
    public MyVerticalLayout partG;
    public MyVerticalLayout partD;
    
    
    //On pourrait faire en sorte de pouvoir afficher un plan de l'atelier
    public FenetrePartagee(Controleur controleur) throws SQLException{
        this.controleur=controleur;
        this.objet=objet;
        this.partD= new MyVerticalLayout();
        this.partG=new MyVerticalLayout();
        
        
        
        
    }
    
    public FenetrePartagee(Controleur controleur, String objet) throws SQLException{
        this.controleur=controleur;
        this.objet=objet;
        
        this.partG=new PartiePrincipale(this.controleur, objet);
        
        
        
        this.partD=new PartieDetail(this.controleur);

        this.splitLayout= new SplitLayout(partG,partD);
        add(this.splitLayout);
        
        
        
        
        this.splitLayout.setWidthFull();
        this.splitLayout.setHeightFull();
        this.splitLayout.setSplitterPosition(70);

    }
    
    
    
   public void setPartG(MyVerticalLayout partg){
       this.partG.removeAll();
       this.partG.add(partg);
   }
    
   public MyVerticalLayout getPartG(){
       return this.partG;
    }
   
   public void setPartD(MyVerticalLayout partd){
       this.partD.removeAll();
       this.partD.add(partd);

       //this.partD=partd;

   }
    
   public MyVerticalLayout getPartD(){
       return this.partD;
    }

}
