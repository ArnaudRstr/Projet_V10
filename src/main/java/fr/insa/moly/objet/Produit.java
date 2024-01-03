/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import fr.insa.moly.GestionBDD.GestionBDD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author molys
 */
public class Produit {
    private final int id;
    private String ref;
    private String des;
    private int idbrut;
    private Gamme gamme ;

    public Produit(Connection connect, int id, String ref, String des, int idbrut) throws SQLException {
        this.id = id;
        this.ref = ref;
        this.des = des;
        this.idbrut = idbrut;
        this.gamme= new Gamme(connect, id);
        
    }
    
    public Produit(Connection connect,int id)throws SQLException {
       this.id=id;
        try {
        connect.setAutoCommit(false);

        String sql = "select * from produit WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet resultat = pst.executeQuery();
            while (resultat.next()!= false){
                this.ref = resultat.getString("ref");
                this.des = resultat.getString("des");
                this.idbrut = resultat.getInt("idbrut");
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
    this.gamme= new Gamme(connect, id);
   }

    public int getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public String getDes() {
        return des;
    }

    public int getIdbrut() {
        return idbrut;
    }

    public Gamme getGamme() {
        return gamme;
    }
    

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setIdbrut(int idbrut) {
        this.idbrut = idbrut;
    }
    
    
    public String getString(){
        String tab = "identifiant: "+this.id + " Reférence: "+ this.ref+ " Description: "+ this.des+" Identifiant Brut: "+this.idbrut;
        return tab;
    }
    public String getnomtable(){
      return   "produit";
    }
   
}
