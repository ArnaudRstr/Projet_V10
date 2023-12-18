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
public class Operateur {
    private final int id;
    private String identifiant;
    private String motdepasse;
    private String nom;
    private String prenom;
    private int idatelier;
    private int statut;
    private int tel;
    private String mail;
    private ArrayList<Integer> listtypeoperation;

    public Operateur(int id, String identifiant, String motdepasse, String nom, String prenom, int idatelier, int statut, int tel, String mail,ArrayList<Integer> listtypeoperation) {
        this.id = id;
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.nom = nom;
        this.prenom = prenom;
        this.idatelier = idatelier;
        this.statut = statut;
        this.tel = tel;
        this.mail = mail;
        this.listtypeoperation = listtypeoperation;
    }

    public int getId() {
        return id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getIdatelier() {
        return idatelier;
    }

    public int getStatut() {
        return statut;
    }

    public int getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<Integer> getListtypeoperation() {
        return listtypeoperation;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setIdatelier(int idatelier) {
        this.idatelier = idatelier;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setListoperation(ArrayList<Integer> listtypeoperation) {
        this.listtypeoperation = listtypeoperation;
    }
    
    public String getnomtable(){
      return   "operateur";
    }
    
}
