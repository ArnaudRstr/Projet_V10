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
public class Atelier {
    
    private final int id; 
    private String nom;
    private String des;
    private int largeur;
    private int longueur;

   public Atelier(int id, String nom, String des, int largeur, int longueur){
       this.id=id;
       this.nom=nom;
       this.des=des;
       this.largeur=largeur;
       this.longueur=longueur;
   }
 
   public Atelier(Connection connect,int id)throws SQLException {
       this.id=id;
        try {
        connect.setAutoCommit(false);

        String sql = "select * from atelier WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet resultat = pst.executeQuery();
            while (resultat.next()!= false){
                this.nom=resultat.getString("nom");
                this.des=resultat.getString("des");
                this.largeur=resultat.getInt("dimensionlargeur");
                this.longueur=resultat.getInt("dimensionlongueur");
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
   }
   
    public String getNom() {
        return nom;
    }

    public String getDes() {
        return des;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }
    
    public String getString(){
        String tab = "Identifiant: "+this.id + " Nom: "+ this.nom+ " Description: "+ this.des;
        return tab;
    }
    public String getnomtable(){
      return   "atelier";
    }

    public int getLargeur() {
        return largeur;
    }

    public int getLongueur() {
        return longueur;
    }
    
    
   
    public ArrayList getMachinechild(Connection connect)throws SQLException{
        ArrayList<Integer> listidchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"machine");       
        return listidchild;
    }
    
    public ArrayList getOperateurchild(Connection connect)throws SQLException{
        ArrayList<Integer> listidchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"operateur");       
        return listidchild;
    }
    
    public ArrayList getProduitchild(Connection connect)throws SQLException{
        ArrayList<Integer> listidchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"produit");       
        return listidchild;
    }
    
    public ArrayList getGrandChildList(Connection connect)throws SQLException{
        ArrayList<String> listIdGrandChild = new ArrayList();
        listIdGrandChild.add("Rapport de suppression, en supprimant :");
        listIdGrandChild.add(this.getString());
        
        ArrayList<Integer> listMachine = this.getMachinechild(connect);
        for(int i=0; i<listMachine.size();i++){
            Machine ma= new Machine(connect,listMachine.get(i));
            ArrayList<String> machinechild =ma.getGrandChildList(connect);
            
            
            for(int k=0;k<machinechild.size();k++){
               listIdGrandChild.add(machinechild.get(k));
            }
        }
        
        ArrayList<Integer> listOperateur = this.getOperateurchild(connect);
        for(int i=0; i<listOperateur.size();i++){
            Operateur op= new Operateur(connect,listOperateur.get(i));
            listIdGrandChild.add(op.getString());
        }
        
        return listIdGrandChild;
    }
}
