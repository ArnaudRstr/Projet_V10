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
public class Gamme {
    private ArrayList<Integer> list;
    private int idproduit;

    public Gamme(ArrayList<Integer> list,int idproduit) {
        this.list = list;
        this.idproduit = idproduit;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public int getIdproduit() {
        return idproduit;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }
    
    public String getnomtable(){
      return   "gamme";
    }
}