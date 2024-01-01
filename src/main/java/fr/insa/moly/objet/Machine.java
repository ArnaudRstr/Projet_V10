/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextArea;
import fr.insa.moly.GestionBDD.GestionBDD;
import fr.insa.rastetter.mainview.MyHorizontalLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author molys
 */
public class Machine {
    private final int id;
    private String nom;
    private int idatelier;
    private int idtypeoperation;
    private String des;
    private String marque;
    private double puissance;
    private int statut;
    private double couthoraire;
    private String localisation;
    private double dimensionlargeur;
    private double dimensionlongueur;
    
    private MyHorizontalLayout pannel;
    private Span spanStatut;
    
    
    public Machine(int id, String nom, int idatelier, int idtypeoperation, String des, String marque, double puissance, int statut, double couthoraire, String localisation, double dimensionlargeur, double dimensionlongueur) {
        
        this.id = id;
        this.nom = nom;
        this.idatelier = idatelier;
        this.idtypeoperation = idtypeoperation;
        this.des = des;
        this.marque = marque;
        this.puissance = puissance;
        this.statut = statut;
        this.couthoraire = couthoraire;
        this.localisation = localisation;
        this.dimensionlargeur = dimensionlargeur;
        this.dimensionlongueur = dimensionlongueur;
        
        this.pannel= new MyHorizontalLayout();
        this.pannel.setWidthFull();
        this.pannel.getStyle().set("border", "1px solid #000000");
        this.pannel.getStyle().set("border-radius", "7px");
        this.pannel.getStyle().set("padding", "4px");
        
        
        Text nomaffiche= new Text(nom);
        Text idaffiche = new Text(String.valueOf(id));
        Text marqueaffiche = new Text(marque);
        
        
        
        this.spanStatut=new Span();
        this.spanStatut.setText("statut");
        //this.spanStatut.getElement().getStyle().set("margin-left", "auto");
        this.spanStatut.getElement().getThemeList().add("badge success primary");
        this.pannel.add(spanStatut,nomaffiche);
    }

   public Machine(Connection connect,int id)throws SQLException {
       this.id=id;
        try {
        connect.setAutoCommit(false);

        String sql = "select * from machine WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet resultat = pst.executeQuery();
            while (resultat.next()!= false){
                this.nom = resultat.getString("nom");
                this.idatelier = resultat.getInt("idatelier");
                this.idtypeoperation = resultat.getInt("idtypeoperation");
                this.des = resultat.getString("des");
                this.marque = resultat.getString("marque");
                this.puissance = resultat.getDouble("puissance");
                this.statut = resultat.getInt("statut");
                this.couthoraire = resultat.getDouble("couthoraire");
                this.localisation = resultat.getString("localisation");
                this.dimensionlargeur = resultat.getDouble("dimensionlargeur");
                this.dimensionlongueur = resultat.getDouble("dimensionlongueur");
            }
        } catch (SQLException ex) {
            connect.rollback();
            System.out.println("Rollback. Erreur : " + ex.getMessage());
            throw ex;
        }
    } finally {
        try {
            if (connect != null) {
                connect.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la gestion des ressources : " + ex.getMessage());
        }
    }
        
        this.pannel= new MyHorizontalLayout();
        this.pannel.setWidthFull();
        this.pannel.getStyle().set("border", "1px solid #000000");
        this.pannel.getStyle().set("border-radius", "7px");
        this.pannel.getStyle().set("padding", "4px");
        
        
        Text nomaffiche= new Text(nom);
        Text idaffiche = new Text(String.valueOf(id));
        Text marqueaffiche = new Text(marque);
        
        
        
        this.spanStatut=new Span();
        this.spanStatut.setText("statut");
        //this.spanStatut.getElement().getStyle().set("margin-left", "auto");
        this.spanStatut.getElement().getThemeList().add("badge success primary");
        this.pannel.add(spanStatut,nomaffiche);
   }
    
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getIdatelier() {
        return idatelier;
    }

    public int getIdtypeoperation() {
        return idtypeoperation;
    }

    public String getDes() {
        return des;
    }

    public String getMarque() {
        return marque;
    }

    public double getPuissance() {
        return puissance;
    }

    public int getStatut() {
        return statut;
    }

    public double getCouthoraire() {
        return couthoraire;
    }

    public String getLocalisation() {
        return localisation;
    }

    public double getDimensionlargeur() {
        return dimensionlargeur;
    }

    public double getDimensionlongueur() {
        return dimensionlongueur;
        
    }
    
    public MyHorizontalLayout getPannel(){
        return this.pannel;
    }
    
    public Span getSpanStatut(){
        return this.spanStatut;
    }
    
    public String getSpanText(){
        return this.spanStatut.getText();
    }
    
    public void setSpanStatut(Span statut){
        this.spanStatut=statut;
    }
    

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setIdatelier(int idatelier) {
        this.idatelier = idatelier;
    }

    public void setIdtypeoperation(int idtypeoperation) {
        this.idtypeoperation = idtypeoperation;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public void setCouthoraire(double couthoraire) {
        this.couthoraire = couthoraire;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setDimensionlargeur(double dimensionlargeur) {
        this.dimensionlargeur = dimensionlargeur;
    }

    public void setDimensionlongueur(double dimensionlongueur) {
        this.dimensionlongueur = dimensionlongueur;
    }
    
    public String getString(){
        String tab = "Identifiant: "+this.id + " Nom: "+ this.nom+" Description: "+this.des+" Marque: "+this.marque+" Indentifiant Atelier:"+this.idatelier+ " Identifiant du type d'opération: "+ this.idtypeoperation+" Coût horaire: "+this.couthoraire+" Puissance: "+this.puissance+" Statut: "+this.statut+" Localisation: "+this.localisation+" Longueur x Largeur: "+this.dimensionlongueur+" x "+this.dimensionlargeur;
        return tab;
    }
    public String getnomtable(){
      return   "machine";
    }
    
    public ArrayList getOperationchild(Connection connect)throws SQLException{
        ArrayList<Integer> listidoperationchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"operation");       
        return listidoperationchild;
    }
}
