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
public class Gamme {
    private ArrayList<Integer> list;
    private int idproduit;

    public Gamme(ArrayList<Integer> list,int idproduit) {
        this.list = list;
        this.idproduit = idproduit;
    }
    
    public Gamme(Connection connect,int idproduit) throws SQLException {
        this.list = GestionBDD.listgammeproduit(connect, idproduit);
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
    public String getString(){
        String tab= "Identifiant produit: "+this.idproduit+ " Gamme: ";
        for (int i=0;i>this.list.size();i++){
        tab = tab +","+this.list.get(i);
        }
        return tab;
    }
    public String getnomtable(){
      return   "gamme";
    }
}
