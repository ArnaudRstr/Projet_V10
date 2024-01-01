/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import java.sql.Connection;
import java.util.ArrayList;
import fr.insa.moly.GestionBDD.GestionBDD;
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
        ArrayList<Integer> listidproduitchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"produit");       
        return listidproduitchild;
    }
    
    public ArrayList getGrandChildList(Connection connect)throws SQLException{
        ArrayList<String> listIdGrandChild = new ArrayList();
        
        
        return listIdGrandChild;
    }
}
