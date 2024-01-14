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
public class Operation {

    private final int id;
    private int idtypeoperation;
    private String nom;
    private double duree;
    private String outil;
    private int idmachine;

    public Operation(int id, int idtypeoperation, String nom, double duree, String outil, int idmachine) {
        this.id = id;
        this.idtypeoperation = idtypeoperation;
        this.nom = nom;
        this.duree = duree;
        this.outil = outil;
        this.idmachine = idmachine;
    }

    public Operation(Connection connect, int id) throws SQLException {
        this.id = id;
        try {
            connect.setAutoCommit(false);

            String sql = "select * from operation WHERE id=?";
            try (PreparedStatement pst = connect.prepareStatement(sql)) {
                pst.setInt(1, id);
                ResultSet resultat = pst.executeQuery();
                while (resultat.next() != false) {
                    this.idtypeoperation = resultat.getInt("idtypeoperation");
                    this.nom = resultat.getString("nom");
                    this.duree = resultat.getDouble("duree");
                    this.outil = resultat.getString("outil");
                    this.idmachine = resultat.getInt("idmachine");
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

    public int getIdtypeoperation() {
        return idtypeoperation;
    }

    public String getNom() {
        return nom;
    }

    public double getDuree() {
        return duree;
    }

    public String getOutil() {
        return outil;
    }

    public int getIdmachine() {
        return idmachine;
    }

    public void setIdtypeoperation(int idtypeoperation) {
        this.idtypeoperation = idtypeoperation;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public void setOutil(String outil) {
        this.outil = outil;
    }

    public void setIdmachine(int idmachine) {
        this.idmachine = idmachine;
    }

    public String getString() {
        String tab = "identifiant: " + this.id + " Nom: " + this.nom + " Identifiant du type d'opération: " + this.idtypeoperation + " Outils: " + this.outil + " Durée: " + this.duree;
        return tab;
    }

    public String getnomtable() {
        return "operation";
    }

    public ArrayList getGrandChildList(Connection connect) throws SQLException {
        ArrayList<String> listIdGrandChild = new ArrayList();
        listIdGrandChild.add("Rapport de suppression, en supprimant :");
        listIdGrandChild.add(this.getString());

        listIdGrandChild.add("Cette opération sera enlevé de la gamme de Ces produits :");
        ArrayList<Integer> listIdProduit = GestionBDD.listGammeOperation(connect, this.id);
        for (int j = 0; j < listIdProduit.size(); j++) {
            Produit prod = new Produit(connect, listIdProduit.get(j));
            listIdGrandChild.add(prod.getString());
        }
        return listIdGrandChild;
    }
}
