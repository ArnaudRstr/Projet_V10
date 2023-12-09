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
public class Produit {
    private final int id;
    private String ref;
    private String des;
    private int idbrut;

    public Produit(int id, String ref, String des, int idbrut) {
        this.id = id;
        this.ref = ref;
        this.des = des;
        this.idbrut = idbrut;
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

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setIdbrut(int idbrut) {
        this.idbrut = idbrut;
    }
    
    public String getnomtable(){
      return   "produit";
    }
    
}
