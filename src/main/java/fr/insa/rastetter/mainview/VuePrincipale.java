package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.moly.GestionBDD.GestionBDD;
import java.sql.Connection;
import java.sql.SQLException;



// La vue principale est la fenetre entière représentant toute l'interface graphique
@PageTitle("Main")
@Route(value = "")
public class VuePrincipale extends MyVerticalLayout {

    private Controleur controleur;
    public Connection connect;
    
    public GestionBDD gestionnaire;
    
    
    
    
    
    
    public Entete entete; //Bandeau supérieur où l'on a tout le menu
    public MyVerticalLayout mainContent;
    
    
    public VuePrincipale() throws SQLException {
        
        this.gestionnaire = new GestionBDD();
        
        //this.mainContent = new MyVerticalLayout();
        this.controleur = new Controleur(this,-1,"initial");
        this.mainContent = new MyVerticalLayout();
        this.mainContent.setWidthFull();
        this.mainContent.setHeightFull();
        this.getStyle().set("padding", "0px");
        //this.mainContent.getStyle().set("border", "1px solid #000000");
        this.getStyle().set("background-color","#FFFFFF");
        this.mainContent.getStyle().set("border", "0px solid #000000");
        this.mainContent.getStyle().set("border-radius", "10px");
        this.mainContent.getStyle().set("padding", "0px");
        this.mainContent.setMargin(false);


        
        
        //this.entete = new MyHorizontalLayout();
        
        this.entete=new Entete();
        this.entete.setWidthFull();

        this.add(this.entete);
        this.add(this.mainContent);
        
        
        
        
        
        
        this.setMainContent(new VueInitialeConnection(this,this.controleur));
        
        
        
        
        //this.setEntete(new Entete(this));
        //this.add(this.entete);

        
        
        
        
        
    }
    public void setEntete(Entete entete1){
        this.entete.removeAll();
        this.entete.add(entete1);

    }
    
 

    public void setMainContent(MyVerticalLayout vlo){
        this.mainContent.removeAll();
        this.mainContent.add(vlo);
        
    }
    public MyVerticalLayout getMainContent(){
        return this.mainContent;
    }
    
    public Entete getEntete(){
        return this.entete;
    }

    public Controleur getControleur() {
        return this.controleur;
    }
    
    public GestionBDD getGestionBDD(){
        return this.gestionnaire;
        
    }

   

}
