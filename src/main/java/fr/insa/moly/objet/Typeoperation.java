/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import fr.insa.moly.GestionBDD.GestionBDD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author molys
 */
public class Typeoperation {
    private final int id;
    private String nom;

    public Typeoperation(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
public String getString(){
        String tab = "identifiant: "+this.id + " Nom: "+ this.nom;
        return tab;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getnomtable(){
      return   "typeoperation";
    }
    
    public ArrayList getOperationchild(Connection connect)throws SQLException{
        ArrayList<Integer> listidchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"operation");       
        return listidchild;
    }
    
    public ArrayList getMachinechild(Connection connect)throws SQLException{
        ArrayList<Integer> listidchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"machine");       
        return listidchild;
    }
    
    public ArrayList getRealiseOOchild(Connection connect)throws SQLException{
        ArrayList<Integer> listidchild = GestionBDD.listchild(connect,this.getnomtable(),this.id,"realiseOO");       
        return listidchild;
    }
}
