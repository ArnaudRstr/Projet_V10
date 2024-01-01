/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.GestionBDD;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author molys
 */
public class RapportSuppr {

    private final String cheminDossier;
    private final ArrayList<String> list;
    private String nomFichier;
    
    // Constructeur avec tous préexistants
    public RapportSuppr(String cheminDossier,ArrayList<String> list,String nomFichier){
        this.list=list;
        this.cheminDossier=cheminDossier;
        this.nomFichier=nomFichier;
    }
    // Constructeur avec fenêtre pour choix du dossier
    public RapportSuppr(ArrayList<String> list,String nomFichier) {
        this.list = list;
        this.cheminDossier = choisirDossier();
    }

    private String choisirDossier() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Sélectionner le dossier pour le rapport de suppression");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            return selectedFolder.getAbsolutePath() + "/";
        } else {
            System.out.println("Aucun dossier sélectionné. Utilisation du dossier par défaut.");
            return System.getProperty("user.dir") + "/";  // Utiliser le dossier de travail par défaut
        }
    }

    

    public void creeFichier() {
        String cheminFichier=this.cheminDossier + this.nomFichier;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (int i=0;i<list.size();i++){
            writer.write(list.get(i)+"\n");
            
        }
            System.out.println("Contenu écrit dans le fichier avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    public void supprimerFichier() {
        String cheminFichier=this.cheminDossier + this.nomFichier;
        File fichier = new File(cheminFichier);
        if (fichier.exists()) {
            if (fichier.delete()) {
                System.out.println("Fichier supprimé avec succès !");
            } else {
                System.err.println("Erreur lors de la suppression du fichier.");
            }
        } else {
            System.out.println("Le fichier n'existe pas.");
        }
    }

    
}


