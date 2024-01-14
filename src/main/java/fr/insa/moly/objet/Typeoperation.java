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
public class Typeoperation {

    private final int id;
    private String nom;

    public Typeoperation(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Typeoperation(Connection connect, int id) throws SQLException {
        this.id = id;
        try {
            connect.setAutoCommit(false);

            String sql = "select * from typeoperation WHERE id=?";
            try (PreparedStatement pst = connect.prepareStatement(sql)) {
                pst.setInt(1, id);
                ResultSet resultat = pst.executeQuery();
                while (resultat.next() != false) {
                    this.nom = resultat.getString("nom");
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

    public String getString() {
        String tab = "identifiant: " + this.id + " Nom: " + this.nom;
        return tab;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getnomtable() {
        return "typeoperation";
    }

    public ArrayList getOperationchild(Connection connect) throws SQLException {
        ArrayList<Integer> listidchild = GestionBDD.listChild(connect, this.getnomtable(), this.id, "operation");
        return listidchild;
    }

    public ArrayList getMachinechild(Connection connect) throws SQLException {
        ArrayList<Integer> listidchild = GestionBDD.listChild(connect, this.getnomtable(), this.id, "machine");
        return listidchild;
    }

    public ArrayList getOperateurchild(Connection connect) throws SQLException {
        ArrayList<Integer> listidchild = GestionBDD.listChildRealiseOperateur(connect, this.id);
        return listidchild;
    }

    public ArrayList getGrandChildList(Connection connect) throws SQLException {
        ArrayList<String> listIdGrandChild = new ArrayList();
        listIdGrandChild.add("Rapport de suppression, en supprimant :");
        listIdGrandChild.add(this.getString());

        ArrayList<Integer> listOperation = this.getOperationchild(connect);
        listIdGrandChild.add("Opérations supprimées :");
        for (int i = 0; i < listOperation.size(); i++) {
            Operation op = new Operation(connect, listOperation.get(i));
            listIdGrandChild.add(op.getString());
            listIdGrandChild.add("Cette opération sera enlevé de la gamme de Ces produits :");
            ArrayList<Integer> listIdProduit = GestionBDD.listGammeOperation(connect, this.id);
            for (int j = 0; j < listIdProduit.size(); j++) {
                Produit prod = new Produit(connect, listIdProduit.get(j));
                listIdGrandChild.add(prod.getString());
            }
        }

        ArrayList<Integer> listMachine = this.getMachinechild(connect);
        for (int i = 0; i < listMachine.size(); i++) {
            Machine ma = new Machine(connect, listMachine.get(i));
            ArrayList<String> machinechild = ma.getGrandChildList(connect);

            for (int k = 0; k < machinechild.size(); k++) {
                listIdGrandChild.add(machinechild.get(k));
            }
        }

        ArrayList<Integer> listOperateur = this.getOperateurchild(connect);
        for (int i = 0; i < listOperateur.size(); i++) {
            Operateur op = new Operateur(connect, listOperateur.get(i));
            listIdGrandChild.add(op.getString());
        }

        return listIdGrandChild;
    }
}
