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
    private ArrayList<Operation> listOperation;
    private int idproduit;

    public Gamme(ArrayList<Operation> list,int idproduit) {
        this.listOperation = list;
        this.idproduit = idproduit;
    }
    
    public Gamme(Connection connect,int idproduit) throws SQLException {
        this.listOperation = GestionBDD.listgammeproduit(connect, idproduit);
        this.idproduit = idproduit;
    }
    

    public ArrayList<Operation> getList() {
        return listOperation;
    }

    public int getIdproduit() {
        return idproduit;
    }

    public void setList(ArrayList<Operation> list) {
        this.listOperation = list;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }
    public String getString(){
        String tab= "Identifiant produit: "+this.idproduit+ " Gamme: ";
        for (int i=0;i>this.listOperation.size();i++){
        tab = tab +","+this.listOperation.get(i);
        }
        return tab;
    }
    public String getnomtable(){
      return   "gamme";
    }
}
