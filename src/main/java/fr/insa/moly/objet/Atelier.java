/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

import java.util.ArrayList;

/**
 *
 * @author molys
 */
public class Atelier {
    
    private final int id; 
    private String nom;
    private String des; 

   public Atelier(int id, String nom, String des){
       this.id=id;
       this.nom=nom;
       this.des=des;
       
       
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
   
    public String getnomtable(){
      return   "atelier";
    }
   
}
