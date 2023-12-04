/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.GestionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


        

/**
 *
 * @author molys
 */

public class GestionBDD {

   
    
    Connection conn ;//Attribut de la connexion
    //Consturcteur
    public GestionBDD(Connection conn) {
        this.conn = conn;
    }
    
    public static Connection connectGeneralMySQL(String host,
            int port, String database,
            String user, String pass)
            throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }
    
    public static Connection connectSurServeurM3() throws SQLException {
        return connectGeneralMySQL("92.222.25.165", 3306,
                "m3_smoly01", "m3_smoly01",
                "09975930");
    }
    
    public static void Initialisation() {
        try (Connection connect = connectSurServeurM3()) {
            
            System.out.println("connecté");
            GestionBDD gestionnaire = new GestionBDD(connect);
            System.out.println("Voulez vous commencer avec les tests de base ?");
            System.out.println("oui=0, non=1");
            System.out.println();
            int r =Lire.i();
            if (r==0)
            { 
               gestionnaire.deleteBDDTest();
               gestionnaire.creatBDDTest();
            }  
            int out= 0;
            while(out==0){
            int i = 1;
            System.out.println(i+" ajouter un atelier ");
            i= i+1;
            System.out.println(i+" Supprimer un atelier");
            i= i+1;
            System.out.println(i+" Modifier un atelier");
            
            i= i+1;
            System.out.println(i+" ajouter une machine ");
            i= i+1;
            System.out.println(i+" Supprimer une machine");
            i= i+1;
            System.out.println(i+" Modifier une machine");
            
            i= i+1;
            System.out.println(i+" ajouter une opération ");
            i= i+1;
            System.out.println(i+" Supprimer une opération");
            i= i+1;
            System.out.println(i+" Modifier une opération");
            
            int rep = Lire.i();
            int j= 1;
            if (rep==j++){
                System.out.println("La réference:");
                String ref= Lire.S();
                System.out.println("description:");
                String des= Lire.S();
            }
            else if (rep== j++){
                System.out.println("La réference:");
                String ref= Lire.S();
                System.out.println("description:");
                String des= Lire.S();
                System.out.println("la puissance:");
                Double puissance = Lire.d();
                
            }
            else if(rep==j++){
                
            }
            else if(rep==j++){
                
            }
            else if(rep==j++){
               int idtype = GestionBDD.askidtype(connect);
                int idproduit= GestionBDD.askidproduit(connect);
               
            }
            System.out.println("Voulez-vous continuer ? oui=0 non=1");
            out=Lire.i();
            }     
            }
           
catch (SQLException ex) {
            throw new Error("Connection impossible", ex);
        }
    }

   
public void deleteBDDTest()throws SQLException {
        this.conn.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        
 //On supprime tout les clés étrangères
        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate("ALTER TABLE machine DROP CONSTRAINT fk_machine_idatelier, DROP CONSTRAINT fk_machine_idtypeoperation");
            System.out.println("Contraintes supprimées machine");
        } catch (SQLException ex) {
            System.out.println("Contraintes non supprimées machine");
        }

        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate("ALTER TABLE operateur DROP CONSTRAINT fk_operateur_idatelier");
            System.out.println("Contraintes supprimées operateur");
        } catch (SQLException ex) {
            System.out.println("Contraintes non supprimées operateur");
        }

        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate("ALTER TABLE operation DROP CONSTRAINT fk_operation_idtypeoperation");
            System.out.println("Contraintes supprimées operation");
        } catch (SQLException ex) {
            System.out.println("Contraintes non supprimées operation");
        } 
        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate("ALTER TABLE ordre DROP CONSTRAINT fk_ordre_idopapres, DROP CONSTRAINT fk_ordre_idopavant,DROP CONSTRAINT fk_ordre_idproduit");
            System.out.println("Contraintes supprimées ordre");
        } catch (SQLException ex) {
            System.out.println("Contraintes non supprimées ordre");
        } 
        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate("ALTER TABLE produit DROP CONSTRAINT fk_produit_idbrut");
            System.out.println("Contraintes supprimées produit");
        } catch (SQLException ex) {
            System.out.println("Contraintes non supprimées produit");
        } 
        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate("ALTER TABLE realiseoo DROP CONSTRAINT fk_realiseoo_idoperateur, DROP CONSTRAINT fk_realiseoo_idoperation");
            System.out.println("Contraintes supprimées realiseoo");
        } catch (SQLException ex) {
            System.out.println("Contraintes non supprimées realiseoo");
        } 
                    
  //On supprime toutes les tables                    
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table realiseoo ;
                        """
                );
                        System.out.println("realiseoo dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table  produit;
                        """
                );
                        System.out.println("produit dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table ordre;
                        """
                );
                        System.out.println("ordre dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table operation;
                        """
                );
                        System.out.println("operation dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table operateur;
                        """
                );
                        System.out.println("operateur dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table machine;
                        """
                );
                        System.out.println("machine dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table atelier;
                        """
                );
                        System.out.println("atelier dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table brut;
                        """
                );
                        System.out.println("brut dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    drop table typeoperation;
                        """
                );
                        System.out.println("typeoperation dropped");

                } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("nothing");
                }
        //test du commit
        try { // creation d'un requete 

            //System.out.println("l'ordre fonctionne") ;
            this.conn.commit(); // valide le refresh
            //System.out.println("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            this.conn.rollback();
            throw ex;
        } finally {
            this.conn.setAutoCommit(true);// on remet le refresh automatique
        }
    }
    
public void creatBDDTest()throws SQLException {
        this.conn.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
 //On crée les tables
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `atelier` (
                          `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          `nom` varchar(20) NOT NULL,
                          `des` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `dimensionlargeur` int NOT NULL,
                          `dimensionlongueur` int NOT NULL
                        )
                        """
                );
                        System.out.println("atelier created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `brut` (
                          `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          `nom` varchar(20) NOT NULL,
                          `ref` varchar(20) NOT NULL,
                          `matiere` varchar(50) NOT NULL,
                          `stock` int NOT NULL,
                          `dimension` text NOT NULL,
                          `fournisseur` varchar(50) NOT NULL
                        )
                        """
                );
                        System.out.println("brut created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `machine` (
                         `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         `nom` varchar(20) NOT NULL,
                         `idatelier` int NOT NULL,
                         `idtypeoperation` int NOT NULL,
                         `des` text NOT NULL,
                         `marque` varchar(20) NOT NULL,
                         `puissance` double NOT NULL,
                         `statut` int NOT NULL,
                         `couthoraire` double NOT NULL,
                         `localisation` varchar(100) NOT NULL,
                         `dimensionlargeur` double NOT NULL,
                         `dimensionlongueur` double NOT NULL
                       )
                        """
                );
                        System.out.println("machine created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `operateur` (
                         `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         `identifiant` varchar(20) NOT NULL,
                         `motdepasse` varchar(20) NOT NULL,
                         `nom` varchar(20) NOT NULL,
                         `prenom` varchar(20) NOT NULL,
                         `idatelier` int NOT NULL,
                         `statut` int NOT NULL,
                         `tel` int NOT NULL,
                         `mail` varchar(20) NOT NULL
                       )                        """
                );
                        System.out.println("operateur created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `operation` (
                         `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         `idtypeoperation` int NOT NULL,
                         `nom` varchar(20) NOT NULL,
                         `duree` double NOT NULL,
                         `outil` varchar(50) NOT NULL
                       )
                        """
                );
                        System.out.println("operation created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `ordre` (
                         `idopavant` int NOT NULL,
                         `idopapres` int NOT NULL,
                         `idproduit` int NOT NULL
                       )
                        """
                );
                        System.out.println("ordre created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `produit` (
                         `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         `ref` varchar(20) NOT NULL,
                         `des` text NOT NULL,
                         `idbrut` int NOT NULL
                       )
                        """
                );
                        System.out.println("produit created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `realiseoo` (
                         `idoperateur` int NOT NULL,
                         `idoperation` int NOT NULL
                       )
                        """
                );
                        System.out.println("realiseoo created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    CREATE TABLE `typeoperation` (
                         `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         `nom` varchar(20) NOT NULL
                       )
                        """
                );
                        System.out.println("typeoperation created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
//On ajoute les clés étrangères
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    ALTER TABLE `machine`
                            ADD CONSTRAINT `fk_machine_idatelier` FOREIGN KEY (`idatelier`) REFERENCES `atelier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                            ADD CONSTRAINT `fk_machine_idtypeoperation` FOREIGN KEY (`idtypeoperation`) REFERENCES `typeoperation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
                        """
                );
                        System.out.println("Contraint machine created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    ALTER TABLE `operateur`
                            ADD CONSTRAINT `fk_operateur_idatelier` FOREIGN KEY (`idatelier`) REFERENCES `atelier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
                        """
                );
                        System.out.println("Contraint operateur created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                     ALTER TABLE `operation`
                            ADD CONSTRAINT `fk_operation_idtypeoperation` FOREIGN KEY (`idtypeoperation`) REFERENCES `typeoperation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
                        """
                );
                        System.out.println("Contraint  operation created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
         try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                     ALTER TABLE `ordre`
                           ADD CONSTRAINT `fk_ordre_idopapres` FOREIGN KEY (`idopapres`) REFERENCES `operation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           ADD CONSTRAINT `fk_ordre_idopavant` FOREIGN KEY (`idopavant`) REFERENCES `operation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           ADD CONSTRAINT `fk_ordre_idproduit` FOREIGN KEY (`idproduit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
                    """
                );
                        System.out.println("Contraint ordre created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
          try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                     ALTER TABLE `produit`
                            ADD CONSTRAINT `fk_produit_idbrut` FOREIGN KEY (`idbrut`) REFERENCES `brut` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
                    """
                );
                        System.out.println("Contraint produit created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
           try ( Statement st = this.conn.createStatement()){
                st.executeUpdate(
                        """
                    ALTER TABLE `realiseoo`
                                ADD CONSTRAINT `fk_realiseoo_idoperateur` FOREIGN KEY (`idoperateur`) REFERENCES `operateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                ADD CONSTRAINT `fk_realiseoo_idoperation` FOREIGN KEY (`idoperation`) REFERENCES `operation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
                        """
                );
                        System.out.println("Contraint realiseoo created");

                } catch (SQLException ex) {
                System.out.println("nothing");
                }
        
//On ajoute les éléments de base
//try (Statement st = this.conn.createStatement()) {
//    st.executeUpdate(
//        """
//        INSERT INTO `machine` (`id`, `ref`, `des`, `puissance`) VALUES 
//        (1, 'F01', 'rapide', 20), 
//        (2, 'F02', 'lente', 10);
//        """
//    );
//    System.out.println("1 add");
//} catch (SQLException ex) {
//    System.out.println("nothing");
//}
//try (Statement st = this.conn.createStatement()) {
//    st.executeUpdate(
//        """
//        INSERT INTO `typeoperation` (`id`, `nom`,`des`) VALUES
//        (1, 'fraisage', 'outils carbure'),
//        (2, 'polissage', 'Non compatible au pvc');
//        """
//    );
//    System.out.println("2 add");
//} catch (SQLException ex) {
//    System.out.println("nothing");
//}
//try (Statement st = this.conn.createStatement()) {
//    st.executeUpdate(
//        """
//        INSERT INTO `produit` (`id`,`nom`, `ref`, `des`) VALUES
//        (1,'jante', 'T23', 'jante'),
//        (2, 'roulement', 'P12', 'roulement'),
//        (3, 'moyeu', 'T03', 'moyeu');
//        """
//    );
//    System.out.println("3 add");
//} catch (SQLException ex) {
//    System.out.println("nothing");
//}
//try (Statement st = this.conn.createStatement()) {
//    st.executeUpdate(
//        """
//        INSERT INTO `operation` (`id`, `idtype`, `idproduit`) VALUES 
//        (1, 1, 1), 
//        (2, 1, 1),
//        (3, 2, 1), 
//        (4, 2, 2), 
//        (5, 1, 2),
//        (6, 1, 2), 
//        (7, 2, 2),
//        (8, 2, 3);
//        """
//    );
//    System.out.println("4 add");
//} catch (SQLException ex) {
//    System.out.println("nothing");
//    ex.printStackTrace();
//    System.out.println("An exception occurred: " + ex.getMessage());
//}
//
//
//try (Statement st = this.conn.createStatement()) {
//    st.executeUpdate(
//        """
//        INSERT INTO `precedenceoperation` (`opavant`, `opapres`) VALUES
//        (1, 3),
//        (2, 3),
//        (4, 5),
//        (4, 6),
//        (5, 7),
//        (6, 7);
//        """
//    );
//    System.out.println("5 add");
//} catch (SQLException ex) {
//    System.out.println("nothing");
//    System.out.println("An exception occurred: " + ex.getMessage());
//}
//
//try (Statement st = this.conn.createStatement()) {
//    st.executeUpdate(
//        """
//        INSERT INTO `realise` (`idmachine`, `idtypeoperation`, `duree`) VALUES
//        (1, 1, 20),
//        (1, 2, 30),
//        (2, 1, 5);
//        """
//    );
//    System.out.println("6 add");
//} catch (SQLException ex) {
//    System.out.println("nothing");
//}
//


//test du commit
try {
    //System.out.println("l'ordre fonctionne");
    this.conn.commit();
    //System.out.println("le refresh fonctionne");
} catch (SQLException ex) {
    this.conn.rollback();
    //System.out.println("rollback");
    throw ex;
} finally {
    this.conn.setAutoCommit(true);
}
}

public static void addatelier (Connection connect,String nom, String des, int dimensionlargeur, int dimensionlongueur)throws SQLException {
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from atelier where nom= ? and des=? and dimensionlargeur=? and dimensionlongueur=?")) {
            cherchedouble.setString(1, nom);
            cherchedouble.setString(2, des);
            cherchedouble.setDouble(3, dimensionlargeur);
            cherchedouble.setDouble(4, dimensionlongueur);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `atelier` (`nom`, `des`, `dimensionlargeur`,`dimensionlongueur`) VALUES (?,?,?,?);"

            )){
                    pst.setString(1, nom);
                    pst.setString(2, des);
                    pst.setDouble(3, dimensionlargeur);
                    pst.setDouble(4, dimensionlongueur);
                    pst.executeUpdate();
                    System.out.println("atelier add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        }
}

public static void addmachine(Connection connect,String nom,int idatelier,int idtypeatelier,String des, String marque,double puissance,int statut,double couthoraire,String localisation,double dimensionlargeur,double dimensionlongueur)throws SQLException{
    
     connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from machine where nom=? and idatelier=? and idtypeatelier=? and des=? and marque=? and puissance=? and statut=? and couthoraire=? and localisation=? and dimensionlargeur=? and dimensionlongueur")) {
            cherchedouble.setString(1, nom);
            cherchedouble.setInt(2, idatelier);
            cherchedouble.setInt(3, idtypeatelier);
            cherchedouble.setString(4, des);
            cherchedouble.setString(5, marque);
            cherchedouble.setDouble(6, puissance);
            cherchedouble.setInt(7, statut);
            cherchedouble.setDouble(8, couthoraire);
            cherchedouble.setString(9, localisation);
            cherchedouble.setDouble(10, dimensionlargeur);
            cherchedouble.setDouble(11, dimensionlongueur);
            
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `machine` (nom,idatelier,idtypeatelier,des,marque,puissance,statut,couthoraire,localisation,dimensionlargeur,dimensionlongueur) VALUES (?,?,?,?,?,?,?,?,?,?,?);"

            )){
                    pst.setString(1, nom);
                    pst.setInt(2, idatelier);
                    pst.setInt(3, idtypeatelier);
                    pst.setString(4, des);
                    pst.setString(5, marque);
                    pst.setDouble(6, puissance);
                    pst.setInt(7, statut);
                    pst.setDouble(8, couthoraire);
                    pst.setString(9, localisation);
                    pst.setDouble(10, dimensionlargeur);
                    pst.setDouble(11, dimensionlongueur);
                    pst.executeUpdate();
                    System.out.println("machine add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
}
}

public static void addbrut(Connection connect,String nom, String ref,String matiere,int stock,String dimension,String fournisseur)throws SQLException {
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from brut where nom=? and ref=? and matiere=? and stock=? and dimension=? and fournisseur=?")) {
            cherchedouble.setString(1, nom);
            cherchedouble.setString(2, ref);
            cherchedouble.setString(3, matiere);
            cherchedouble.setInt(4, stock);
            cherchedouble.setString(5, dimension);
            cherchedouble.setString(6, fournisseur);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `brut` (nom,ref,matiere,stock,dimension,fournisseur) VALUES (?,?,?,?,?,?);"

            )){
                    pst.setString(1, nom);
                    pst.setString(2, ref);
                    pst.setString(3, matiere);
                    pst.setInt(4, stock);
                    pst.setString(5, dimension);
                    pst.setString(6, fournisseur);
                    pst.executeUpdate();
                    System.out.println("brut add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        }
}

public static void addoperateur(Connection connect,String identifiant, String motdepasse,String nom,String prenom,int idatelier,int statut, int tel, String mail)throws SQLException {
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from operateur where identifiant=? and motdepasse=? and nom=? and prenom=? and idatelier=? and satut=? and tel=? and mail=?")) {
            cherchedouble.setString(1, identifiant);
            cherchedouble.setString(2, motdepasse);
            cherchedouble.setString(3, nom);
            cherchedouble.setString(4, prenom);
            cherchedouble.setInt(5, idatelier);
            cherchedouble.setInt(6, statut);
            cherchedouble.setInt(7, tel);
            cherchedouble.setString(8, mail);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `operateur` (identifiant,motdepasse,nom,prenom,idatelier,satut,tel,mail) VALUES (?,?,?,?,?,?,?,?);"

            )){
                    pst.setString(1, nom);
                    pst.setString(1, identifiant);
                    pst.setString(2, motdepasse);
                    pst.setString(3, nom);
                    pst.setString(4, prenom);
                    pst.setInt(5, idatelier);
                    pst.setInt(6, statut);
                    pst.setInt(7, tel);
                    pst.setString(8, mail);
                    pst.executeUpdate();
                    System.out.println("operateur add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        }
}

public static void addoperation(Connection connect,int idtypeoperation, String nom,double duree,String outil)throws SQLException {
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from operation where idtypeoperation=? and nom=? and duree=? and outil=?")) {
            cherchedouble.setInt(1, idtypeoperation);
            cherchedouble.setString(2, nom);
            cherchedouble.setDouble(3, duree);
            cherchedouble.setString(4, outil);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `operation` (idtypeoperation,nom,duree,outil) VALUES (?,?,?,?);"

            )){
                    pst.setInt(1, idtypeoperation);
                    pst.setString(2, nom);
                    pst.setDouble(3, duree);
                    pst.setString(4, outil);
                    pst.executeUpdate();
                    System.out.println("operation add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        }
}

public static void addordre(Connection connect,int idopavant, int idopapres,int idproduit)throws SQLException {
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from ordre where idopavant=? and idopapres=? and idproduit=?")) {
            cherchedouble.setInt(1, idopavant);
            cherchedouble.setInt(2, idopapres);
            cherchedouble.setInt(3, idproduit);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `ordre` (idopavant,idopapres,idproduit) VALUES (?,?,?);"

            )){
                    pst.setInt(1, idopavant);
                    pst.setInt(2, idopapres);
                    pst.setInt(3, idproduit);
                    pst.executeUpdate();
                    System.out.println("ordre add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        }
}

public static void addpruduit(Connection connect,String ref,String des,int idbrut)throws SQLException{
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from produit where ref=? and des=? and idbrut=?")) {
            cherchedouble.setString(1, ref);
            cherchedouble.setString(2, des);
            cherchedouble.setInt(3, idbrut);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
                System.out.println("On ne peut pas l'ajouter");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `produit` (ref,des,idbrut) VALUES (?,?,?);"

            )){
                    pst.setString(1, ref);
                    pst.setString(2, des);
                    pst.setInt(3, idbrut);
                    pst.executeUpdate();
                    System.out.println("produit add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
}
}

public static void addrealiseoo(Connection connect,int idoperateur,int idoperation)throws SQLException{
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from realiseoo where idoperateur=? and idoperation=?")) {
            cherchedouble.setInt(1, idoperateur);
            cherchedouble.setInt(2, idoperateur);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
                System.out.println("On ne peut pas l'ajouter");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `realiseoo` (idoperateur,idoperation) VALUES (?,?);"

            )){
                    pst.setInt(1, idoperateur);
                    pst.setInt(2, idoperateur);
                    pst.executeUpdate();
                    System.out.println("realiseoo add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
}
}

public static void addtypeoperation(Connection connect,String nom)throws SQLException{
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from typeoperation where nom=?")) {
            cherchedouble.setString(1, nom);
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
                System.out.println("On ne peut pas l'ajouter");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `typeoperation` (nom) VALUES (?);"

            )){
                    pst.setString(1, nom);
                    pst.executeUpdate();
                    System.out.println("typeoperation add");

            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
}
}

public static int askidtype(Connection connect)throws SQLException{
    boolean test= false;
    int idtype=-1;
    while (test==false){
    try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from typeoperation where nom= ? ")) {
        System.out.println("type d'opération");
        String nomoperation= Lire.S();
            cherchedouble.setString(1, nomoperation);
            ResultSet rep = cherchedouble.executeQuery();
            test =rep.next();
            if (test!=false){
            idtype=rep.getInt(1);
            }
            
}
    }
    return idtype;
    }
public static int askidproduit(Connection connect)throws SQLException{
    boolean test= false;
    int idproduit=-1;
    while (test==false){
    try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from produit where nom= ? ")) {
        System.out.println("Produit:");
        String nomproduit= Lire.S();
            cherchedouble.setString(1, nomproduit);
            ResultSet rep = cherchedouble.executeQuery();
            test =rep.next();
            if(test!=false){
            idproduit=rep.getInt(1);
            }
            
}
    }
    return idproduit;
}
    //Affichage résultat
//           ResultSet r = st.executeQuery("select * from li_utilisateur where 1;");
//            while (r.next()) {
//                int id = r.getInt(1);
//                String nom = r.getString(2);
//                String numero = r.getString("numero");
//                System.out.println(nom + " : " + numero + " avec l'identifiant : "+ id);
//            }
    
    
    public static void main(String[] args) {
        System.out.print("Bonjour et bienvenue");
       Initialisation() ;
    }
}
