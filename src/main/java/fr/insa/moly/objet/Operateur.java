/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import fr.insa.moly.GestionBDD.GestionBDD;
import static fr.insa.moly.GestionBDD.GestionBDD.listaltelier;
import fr.insa.rastetter.mainview.Controleur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author molys
 */
public class Operateur {
    private final int id;
    private String identifiant;
    private String motdepasse;
    private String nom;
    private String prenom;
    private int idatelier;
    private int statut;
    private int tel;
    private String mail;
    private ArrayList<Typeoperation> listtypeoperation;

    public Operateur(int id, String identifiant, String motdepasse, String nom, String prenom, int idatelier, int statut, int tel, String mail,ArrayList<Typeoperation> listtypeoperation) {
        this.id = id;
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.nom = nom;
        this.prenom = prenom;
        this.idatelier = idatelier;
        this.statut = statut;
        this.tel = tel;
        this.mail = mail;
        this.listtypeoperation = listtypeoperation;
    }

    public Operateur(Connection connect,int id)throws SQLException {
       this.id=id;
        try {
        connect.setAutoCommit(false);

        String sql = "select * from operateur WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet resultat = pst.executeQuery();
            while (resultat.next()!= false){
                this.identifiant = resultat.getString("identifiant");
                this.motdepasse = resultat.getString("motdepasse");
                this.nom = resultat.getString("nom");
                this.prenom = resultat.getString("prenom");
                this.idatelier = resultat.getInt("idatelier");
                this.statut = resultat.getInt("statut");
                this.tel = resultat.getInt("tel");
                this.mail = resultat.getString("mail");
                
            }
        } catch (SQLException ex) {
            connect.rollback();
            System.out.println("Rollback. Erreur : " + ex.getMessage());
            throw ex;
        }
        
        this.listtypeoperation = GestionBDD.listgammeproduit(connect,id);
        
        
        
        
        
    } finally {
        try {
            if (connect != null) {
                connect.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la gestion des ressources : " + ex.getMessage());
        }
    }
   }
    
    public int getId() {
        return id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getIdatelier() {
        return idatelier;
    }

    public int getStatut() {
        return statut;
    }

    public String getStatutString(){
        String stringstatut = "";
        
        
        if(statut==0){
            stringstatut="En congé";
        }
        if(statut==1){
            stringstatut="En activité";
        }
        if(statut==2){
            stringstatut="En formation";
        }
        if(statut==3){
            stringstatut="Disponible";
        }
        if(statut==4){
            stringstatut="Hors service";
        }
        
  
        return stringstatut;
    }
    
    
    
    public String getAtelierString(Controleur controleur) throws SQLException{
        String stringatelier = "";
        
        ArrayList <Atelier> listateliertemp = new ArrayList();
        listateliertemp = listaltelier (controleur.getVuePrincipale().getGestionBDD().conn);
        int index =0;
        boolean valid =false;
        while (index<listateliertemp.size()||valid==false){
            Atelier atelierTemp = (Atelier) listateliertemp.get(index);
            
            
            if (this.idatelier==atelierTemp.getId()){
                stringatelier = atelierTemp.getId()+" : "+atelierTemp.getNom();
                valid=true;
            }
            index++;
        }
        
        
        
        
        
        
        
        return stringatelier;
    }
    
    public int getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<Typeoperation> getListtypeoperation() {

        return listtypeoperation;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setIdatelier(int idatelier) {
        this.idatelier = idatelier;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }
    
    
    
    public void setStatutString(String statut){
        
        int idstatut=-1;
        
        
        if(statut=="En congé"){
            idstatut=0;
        }
        if(statut=="En activité"){
            idstatut=1;
        }
        if(statut=="En formation"){
            idstatut=2;
        }
        if(statut=="Disponible"){
            idstatut=3;
        }
        if(statut=="Hors service"){
            idstatut=4;
        }
        
        
        
        
        
        this.statut=idstatut;
        
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setListoperation(ArrayList<Typeoperation> listtypeoperation) {
        this.listtypeoperation = listtypeoperation;
    }
    public String getString(){
        String tab = "Identifiant: "+this.id + " Nom: "+ this.nom+ " Prénom: "+ this.prenom+" Identifiant Atelier: "+this.idatelier+" Téléphone: "+this.tel+ " Mail: "+this.mail;
        return tab;
    }
    public String getnomtable(){
      return   "operateur";
    }
    
     public ArrayList getGrandChildList(Connection connect)throws SQLException{
        ArrayList<String> listIdGrandChild = new ArrayList();
        listIdGrandChild.add("Rapport de suppression, en supprimant :");
        listIdGrandChild.add(this.getString());
        
        return listIdGrandChild;
    }
}
