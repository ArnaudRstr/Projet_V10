/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.GestionBDD;

/**
 *
 * @author molys
 */
public class Verification {
    
     // Méthode pour vérifier la longueur d'une chaîne
    private static boolean ValidVarchar(String value, int maxLength) {
        return value.length() <= maxLength;
    }
    
    public String intertextvalidVarchar(String categorie,String value, int maxLength){
        int rep =1;
        String newvalue = value;
        while(rep==1){
            if (ValidVarchar(newvalue,maxLength)==false){
                System.out.println("attention vous dépassez la taille requie, 0=abandoner ou 1=réessayer");
                rep=Lire.i();
               if (rep==1){
                   System.out.println(categorie);
                   newvalue=Lire.S();
                   }
               }
        }
        return newvalue;
    }
}
