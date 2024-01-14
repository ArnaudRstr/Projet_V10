/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import java.sql.Connection;
import java.util.ArrayList;
import fr.insa.moly.GestionBDD.GestionBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author molys
 */
public class Brut {
    private final int id;
    private String nom;
    private String ref;
    private String matiere;
    private int stock;
    private String dimension;
    private String fournisseur;

    public Brut(int id , String nom, String ref, String matiere, int stock, String dimension, String fournisseur) {
        this.id = id;
        this.nom = nom;
        this.ref = ref;
        this.matiere = matiere;
        this.stock = stock;
        this.dimension = dimension;
        this.fournisseur = fournisseur;
    }
    
     public Brut(Connection connect,int id)throws SQLException {
       this.id=id;
        try {
        connect.setAutoCommit(false);

        String sql = "select * from brut WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet resultat = pst.executeQuery();
            while (resultat.next()!= false){
                this.nom=resultat.getString("nom");
                this.ref = resultat.getString("ref");
                this.matiere = resultat.getString("matiere");
                this.stock = resultat.getInt("stock");
                this.dimension = resultat.getString("dimension");
                this.fournisseur = resultat.getString("fournisseur");
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

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getRef() {
        return ref;
    }

    public String getMatiere() {
        return matiere;
    }

    public int getStock() {
        return stock;
    }

    public String getDimension() {
        return dimension;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    
    
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }
    public String getString(){
        String tab = "Identifiant: "+this.id + " Nom: "+ this.nom+ " Réference: "+ this.ref+" Matière: "+this.matiere+" Dimention: "+this.dimension +" Stock: "+ this.stock +" Fournisseur: "+ this.fournisseur;
        return tab;
    }
    public String getnomtable(){
      return   "brut";
    }
    
    public ArrayList getProduitchild(Connection connect)throws SQLException{
        ArrayList<Integer> listidproduitchild = GestionBDD.listChild(connect,this.getnomtable(),this.id,"produit");       
        return listidproduitchild;
    }
    
    public ArrayList getGrandChildList(Connection connect)throws SQLException{
        
        ArrayList<String> listIdGrandChild = new ArrayList();
        ArrayList<Integer> listidproduitchild =this.getProduitchild(connect);
        listIdGrandChild.add("Rapport de suppression, en supprimant :");
        listIdGrandChild.add(this.getString());
        listIdGrandChild.add("Produits Supprimés");
        for(int i=0;i<listidproduitchild.size();i++){
            Produit prod = new Produit(connect,listidproduitchild.get(i));
            listIdGrandChild.add(prod.getString());
        }
        
        
        return listIdGrandChild;
    }
}
