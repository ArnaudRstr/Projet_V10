/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rastetter.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.server.StreamResource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fr.insa.moly.GestionBDD.GestionBDD.connectionutilisateur;


/**
 *
 * @author arnaud
 */
public class VueInitialeConnection extends MyVerticalLayout{
    
    public VuePrincipale main;
    public MyHorizontalLayout HLBoutons;
    public MyVerticalLayout VLcontenu;
    public Controleur controleur;
    
    private Button connectButton;
    
    public VueInitialeConnection(VuePrincipale main, Controleur controleur){
        
        this.main = main;
        this.controleur = controleur;
        this.getStyle().set("padding", "0px");
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        this.setMargin(false);
        this.setWidthFull();
        this.setHeightFull();
        this.getStyle().set("padding", "0px");
 
        this.HLBoutons=new MyHorizontalLayout();
        
        this.VLcontenu = new MyVerticalLayout();
        this.add(VLcontenu);
        this.add(HLBoutons);
        this.VLcontenu.setAlignItems(FlexComponent.Alignment.CENTER);

        this.HLBoutons.getStyle().set("padding", "0px");
        this.HLBoutons.setMargin(false);
        this.HLBoutons.setWidthFull();
        this.HLBoutons.getStyle().set("padding", "0px");
        
        Button boutonInfoLogiciel = new Button(new H5("A propos du logiciel"));
        Button boutonNotice = new Button(new H5("Notice d'utilisation"));

        this.HLBoutons.add(boutonNotice,boutonInfoLogiciel);
        this.HLBoutons.setJustifyContentMode(JustifyContentMode.END);
        

        //Ajout de l'image (placée dans resources)
        String imagePath = "logo.png";
        StreamResource resource = new StreamResource("logo.png", () ->
                getClass().getClassLoader().getResourceAsStream(imagePath));
        // Créer un composant Image avec la ressource
        Image image = new Image(resource, "Description de l'image");
        image.setWidth("200px");
        this.VLcontenu.add(image);

        Span nomLogiciel = new Span("LEON");
        nomLogiciel.getElement().getStyle().set("font-family", "Nunito");
        nomLogiciel.getStyle().set("font-size", "80px");
        this.VLcontenu.add(nomLogiciel);

        
        //Ajout du composant Login 

        LoginI18n i18n  = LoginI18n.createDefault();
        LoginI18n.Form i18nForm  = i18n .getForm();
        i18nForm .setTitle("Connexion"); 
        i18nForm .setUsername("Nom d'utilisateur");
        i18nForm .setPassword("Mot de passe");
        i18nForm .setSubmit("Se connecter");
        i18nForm .setForgotPassword("");
        i18n .setForm(i18nForm );
        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Nom d'utilisateur ou mot de passe incorrect");
        i18nErrorMessage.setMessage(
                "Vérifiez que le nom d'utilisateur et le mot de passe sont corrects et réessayez.");
        i18n.setErrorMessage(i18nErrorMessage);
        i18n.setAdditionalInformation("Si vous rencontrez des problèmes de connexion, contactez l'admin sidonie.moly@leon.fr ou arnaud.rastetter@leon.fr ");   

        LoginForm loginForm = new LoginForm();
        loginForm.setI18n(i18n);
        loginForm.setEnabled(true);

        //On crée un VL pour le login (car le composant login change de taille selon les actions, et décale donc tout le reste)
        MyVerticalLayout vlLogin = new MyVerticalLayout(); 
        vlLogin.setHeight("500px");
        vlLogin.add(loginForm);
        //vlLogin.getStyle().set("border", "1px solid #000000");
        vlLogin.setAlignItems(FlexComponent.Alignment.CENTER);
        vlLogin.getStyle().set("padding", "0px");

        //CONNEXION ICI
        loginForm.addLoginListener(event -> {
            
            String login = event.getUsername();
            String mdp = event.getPassword();
 
            
            
            
            //Pour se connecter sans login valide
//            try {
//                this.main.getControleur().boutonConnect(this.main);
//            } catch (SQLException ex) {
//                Logger.getLogger(VueInitialeConnection.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            
            //Pour se connecter avec le login et le mdp nécessaire
            try {
                //On va tester de se connecter ici
                if (connectionutilisateur(this.controleur.getVuePrincipale().getGestionBDD().getConn(),login,mdp)==true){
                    try {
                        this.main.getControleur().boutonConnect(this.main);
                    } catch (SQLException ex) {
                        System.out.println("impossible de se connecter");
                    }
                    this.controleur.setIdentifiantUtilisateur(login);       
                }
                else{
                    Notification.show("Connection impossible");
                    loginForm.setError(true);
                    loginForm.setEnabled(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(VueInitialeConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        

        this.VLcontenu.add(vlLogin);    
        
        //Gestion des événements pour les 2 .pdf.
        boutonInfoLogiciel.addClickListener(e -> {
        String cheminRelatif = "src/main/resources/LEON.pdf";
        try {
            // Utilisation de cmd pour ouvrir le fichier avec l'application par défaut
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "", cheminRelatif);
            processBuilder.start();
        } catch (IOException er) {
            er.printStackTrace();
        }
    });
  
        boutonNotice.addClickListener(e -> {
        String cheminRelatif = "src/main/resources/Notice d'utilisation.pdf";
        
        try {
            // Utilisation de cmd pour ouvrir le fichier avec l'application par défaut
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "", cheminRelatif);
            processBuilder.start();
        } catch (IOException er) {
            er.printStackTrace();
        }
    });
    }
}
