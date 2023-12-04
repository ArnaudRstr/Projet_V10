package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



// La vue principale est la fenetre entière représentant toute l'interface graphique
@PageTitle("Main")
@Route(value = "")
public class VuePrincipale extends MyVerticalLayout {

    private TextField name;
    private Button sayHello;
    
    
    
    //private MyVerticalLayout mainContent; //Partie inférieure, dans laquelle on aura plus tard plusieurs onglets ou fenetres
    public MyHorizontalLayout entete; //Bandeau supérieur où l'on a tout le menu
    public MyVerticalLayout mainContent;
    
    
    public VuePrincipale() {
        /*name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        /*setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        
        add(name, sayHello);*/
        
        //this.mainContent = new MyVerticalLayout();
        this.mainContent = new VuePlan();
        this.mainContent.setWidthFull();
        this.mainContent.setHeightFull();
        this.getStyle().set("border", "1px solid #000000");
        this.getStyle().set("padding", "10px");
        this.getStyle().set("border-radius", "5px");
        this.mainContent.getStyle().set("border", "1px solid #000000");
        
        this.entete = new MyHorizontalLayout();
        this.entete.setWidthFull();

        this.add(this.entete);
        this.add(this.mainContent);
        
        
        
        
        this.setMainContent(new VueInitialeConnection(this));
        
        
        
        
        //this.setEntete(new Entete(this));

        
        
        
        
        
    }
    public void setEntete(MyHorizontalLayout vlo){
        this.entete.removeAll();
        this.entete.add(vlo);
        
    }

    public void setMainContent(MyVerticalLayout vlo){
        this.mainContent.removeAll();
        this.mainContent.add(vlo);
        
    }
    public MyVerticalLayout getMainContent(){
        return this.mainContent;
    }
    
    public MyHorizontalLayout getEntete(){
        return this.entete;
    }
    
    

   

}
