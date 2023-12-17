/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.objet;

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

    public Operation(int id, int idtypeoperation, String nom, double duree, String outil,int idmachine) {
        this.id = id;
        this.idtypeoperation = idtypeoperation;
        this.nom = nom;
        this.duree = duree;
        this.outil = outil;
        this.idmachine = idmachine;
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
    
    public String getnomtable(){
      return   "operation";
    }
}
