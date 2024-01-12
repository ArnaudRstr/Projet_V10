/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.moly.GestionBDD;

import fr.insa.moly.objet.Atelier;
import fr.insa.moly.objet.Brut;
import fr.insa.moly.objet.Gamme;
import fr.insa.moly.objet.Machine;
import fr.insa.moly.objet.Operateur;
import fr.insa.moly.objet.Operation;
import fr.insa.moly.objet.Produit;
import fr.insa.moly.objet.Typeoperation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


        

/**
 *
 * @author molys
 */

public class GestionBDD {

   
    
    public Connection conn ;//Attribut de la connexion
    //Consturcteur
    public GestionBDD() throws SQLException {
        this.conn = connectSurServeurM3();
    }
 
    
    
    public Connection getGestionBDD(){
        return this.conn;
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
            GestionBDD gestionnaire = new GestionBDD();
            gestionnaire.deleteBDDTest();
            gestionnaire.creatBDDTest();
            
            addtypeoperation(gestionnaire.conn,"fraisage");
            //addatelier(gestionnaire.conn,"production","rien",1743,2134);
            addmachine(gestionnaire.conn,"Presse",1,1,"presse hydraulique","NoName",3000,0,12,"Strasbourg",50,223);
            
            System.out.println("Voulez vous commencer avec les tests de base ?");
            System.out.println("oui=0, non=1");
            System.out.println();
            int r =Lire.i();
            if (r==0)
            { 
                System.out.println("selection = 0");
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
                         `outil` varchar(50) NOT NULL,
                         `idmachine` int NOT NULL
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
                         `idtypeoperation` int NOT NULL
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
                            ADD CONSTRAINT `fk_operation_idmachine` FOREIGN KEY (`idmachine`) REFERENCES `machine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
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
                                ADD CONSTRAINT `fk_realiseoo_idtypeoperation` FOREIGN KEY (`idtypeoperation`) REFERENCES `typeoperation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
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

public static boolean connectionidentifiant (Connection connect, String nomtest)throws SQLException {
   boolean existe=false;
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from operateur WHERE identifiant=?")) {
            affichetab.setString(1, nomtest);
            ResultSet tab = affichetab.executeQuery();
            if(tab.next()!= false){
               existe=true;
            }

            }
        catch (SQLException ex) { 
            System.out.print("rollback");
            throw ex;
        }
        
     
    return existe;
}

public static boolean connectionmp (Connection connect, String nomtest,String mp)throws SQLException {
   boolean existe=false;
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from operateur WHERE identifiant=? AND motdepasse=?")) {
            affichetab.setString(1, nomtest);
            affichetab.setString(2, mp);
            ResultSet tab = affichetab.executeQuery();
            if(tab.next()!= false){
               existe=true;
            }

            }
        catch (SQLException ex) { 
            System.out.print("rollback");
            throw ex;
        }
        
     
    return existe;
}

public static boolean connectionutilisateur (Connection connect, String nomtest,String mp)throws SQLException {
   boolean existe=false;
   
        if (connectionidentifiant(connect,nomtest)==true){
            existe=connectionmp(connect,nomtest,mp);
        }
        else{
            System.out.println("Identifiant faux");
        }
     
    return existe;
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
            System.out.println("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        }
}

public static void updateAtelier(Connection connect,int id, String nom, String des, int dimensionlargeur, int dimensionlongueur)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE atelier SET `nom`=?, `des`=?, `dimensionlargeur`=?,`dimensionlongueur`=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, nom);
            pst.setString(2, des);
            pst.setDouble(3, dimensionlargeur);
            pst.setDouble(4, dimensionlongueur);
            pst.setInt(5, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

public static void addmachine(Connection connect,String nom,int idatelier,int idtypeoperation,String des, String marque,double puissance,int statut,double couthoraire,String localisation,double dimensionlargeur,double dimensionlongueur)throws SQLException{
    
     connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from machine where nom=? and idatelier=? and idtypeoperation=? and des=? and marque=? and puissance=? and statut=? and couthoraire=? and localisation=? and dimensionlargeur=? and dimensionlongueur=?")) {
            cherchedouble.setString(1, nom);
            cherchedouble.setInt(2, idatelier);
            cherchedouble.setInt(3, idtypeoperation);
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
                System.out.println("Attention, la machine existe déjà");
            }
            else{
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `machine` (`nom`,`idatelier`,`idtypeoperation`,`des`,`marque`,`puissance`,`statut`,`couthoraire`,`localisation`,`dimensionlargeur`,`dimensionlongueur`) VALUES (?,?,?,?,?,?,?,?,?,?,?);"

            )){            

                    pst.setString(1, nom);
                    pst.setInt(2, idatelier);
                    pst.setInt(3, idtypeoperation);
                    pst.setString(4, des);
                    pst.setString(5, marque);
                    pst.setDouble(6, puissance);
                    pst.setInt(7, statut);
                    pst.setDouble(8, couthoraire);
                    pst.setString(9, localisation);
                    pst.setDouble(10, dimensionlargeur);
                    pst.setDouble(11, dimensionlongueur);
                    System.out.println("essai d'ajout de la machine");
                    
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

public static void updateMachine(Connection connect,int id,String nom,int idatelier,int idtypeoperation,String des, String marque,double puissance,int statut,double couthoraire,String localisation,double dimensionlargeur,double dimensionlongueur)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE machine SET `nom`=?,`idatelier`=?,`idtypeoperation`=?,`des`=?,`marque`=?,`puissance`=?,`statut`=?,`couthoraire`=?,`localisation`=?,`dimensionlargeur`=?,`dimensionlongueur`=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, nom);
                    pst.setInt(2, idatelier);
                    pst.setInt(3, idtypeoperation);
                    pst.setString(4, des);
                    pst.setString(5, marque);
                    pst.setDouble(6, puissance);
                    pst.setInt(7, statut);
                    pst.setDouble(8, couthoraire);
                    pst.setString(9, localisation);
                    pst.setDouble(10, dimensionlargeur);
                    pst.setDouble(11, dimensionlongueur);
            pst.setInt(12, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

public static void updateBrut(Connection connect,int id, String nom, String ref,String matiere,int stock,String dimension,String fournisseur)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE brut SET nom=?, ref=?, matiere=?, stock=?, dimension=?, fournisseur=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, nom);
            pst.setString(2, ref);
            pst.setString(3, matiere);
            pst.setInt(4, stock);
            pst.setString(5, dimension);
            pst.setString(6, fournisseur);
            pst.setInt(7, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

public static void addoperateur(Connection connect,String identifiant, String motdepasse,String nom,String prenom,int idatelier,int statut, int tel, String mail,ArrayList<Typeoperation> listtypeoperation)throws SQLException {
    System.out.print("1: " +listtypeoperation);
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select id from operateur where identifiant=? and motdepasse=? and nom=? and prenom=? and idatelier=? and statut=? and tel=? and mail=?")) {
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
                        "INSERT INTO `operateur` (identifiant,motdepasse,nom,prenom,idatelier,statut,tel,mail) VALUES (?,?,?,?,?,?,?,?);"

            ,Statement.RETURN_GENERATED_KEYS)){
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
                    
                    try ( ResultSet rid = pst.getGeneratedKeys()) {
                        System.out.println("id recupérer");
                        if(rid.next()){
                        int id = rid.getInt(1);

                        for(int i=0;i<listtypeoperation.size();i++){
                            System.out.println("realiseoo va etre appelé");
                            addrealiseoo(connect,id,(int)listtypeoperation.get(i).getId());
                        }
                        System.out.println("realiseoo add");
                        }
                        else{
                            System.out.println("addrealiseoo not read");
                        }
                    
                   
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("clé non optenu");
                ex.printStackTrace();
                System.out.println("Erreur SQL : " + ex.getMessage());
                }
            
            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.print("le refresh fonctionne operateur") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
            }
        }
}

public static void updateOperateur(Connection connect,int id,String identifiant, String motdepasse,String nom,String prenom,int idatelier,int statut, int tel, String mail,ArrayList<Typeoperation> listtypeoperation)throws SQLException {
     connect.setAutoCommit(false);
     try {
        

        String sql = "UPDATE operateur SET identifiant=?, motdepasse=?, nom=?, prenom=?, idatelier=?, statut=?, tel=?, mail=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, nom);
                    pst.setString(1, identifiant);
                    pst.setString(2, motdepasse);
                    pst.setString(3, nom);
                    pst.setString(4, prenom);
                    pst.setInt(5, idatelier);
                    pst.setInt(6, statut);
                    pst.setInt(7, tel);
                    pst.setString(8, mail);
            pst.setInt(9, id);
            pst.executeUpdate();
            
            
            deleteRealiseoo(connect,"idoperateur",id);
            if (!listtypeoperation.isEmpty()){
            for(int i=0;i<listtypeoperation.size();i++){
                System.out.println("addrealise execute");
                            addrealiseoo(connect,id,listtypeoperation.get(i).getId());
                        }
            }
            else {
                System.out.println("list vide");
            }
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

public static void addoperation(Connection connect, int idtypeoperation, String nom, double duree, String outil, int idmachine) throws SQLException {
    connect.setAutoCommit(false);

    try (PreparedStatement cherchedouble = connect.prepareStatement(
            "SELECT id FROM operation WHERE idtypeoperation=? AND nom=? AND duree=? AND outil=? AND idmachine=?")) {
        cherchedouble.setInt(1, idtypeoperation);
        cherchedouble.setString(2, nom);
        cherchedouble.setDouble(3, duree);
        cherchedouble.setString(4, outil);
        cherchedouble.setInt(5, idmachine);

        try (ResultSet test = cherchedouble.executeQuery()) {
            if (test.next()) {
                System.out.println("Attention, il existe déjà");
            } else {
                try (PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO operation (idtypeoperation, nom, duree, outil, idmachine) VALUES (?,?,?,?,?)")) {
                    pst.setInt(1, idtypeoperation);
                    pst.setString(2, nom);
                    pst.setDouble(3, duree);
                    pst.setString(4, outil);
                    pst.setInt(5, idmachine);

                    pst.executeUpdate();
                    System.out.println("operation add");
                } catch (SQLException ex) {
                    System.out.println("nothing");
                }
            }
        }
        try {
            connect.commit();
            System.out.print("le refresh fonctionne");
        } catch (SQLException ex) {
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);
        }
    }
}

public static void updateOperation(Connection connect,int id, int idtypeoperation, String nom,double duree,String outil,int idmachine)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE operation SET idtypeoperation=?, nom=?, duree=?, outil=?, idmachine=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, idtypeoperation);
                    pst.setString(2, nom);
                    pst.setDouble(3, duree);
                    pst.setString(4, outil);
                    pst.setInt(5, idmachine);
            pst.setInt(6, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

//Premet d'ajouter les ligne de la table et non une 'gamme' en soit
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

//ne pas utiliser
public static void updateOrdre(Connection connect,int id, int idopavant, int idopapres,int idproduit)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE ordre SET idopavant=?, idopapres=?, idproduit=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
           pst.setInt(1, idopavant);
            pst.setInt(2, idopapres);
            pst.setInt(3, idproduit);
            pst.setInt(4, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

//Attention cette fonction ne prend pas en compte l'ajoue de la gamme voir addordre
public static void addproduit(Connection connect,String ref,String des,int idbrut, ArrayList<Operation> listoperation)throws SQLException{
    
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
                        "INSERT INTO `produit` (ref,des,idbrut) VALUES (?,?,?);",Statement.RETURN_GENERATED_KEYS

            )){
                    pst.setString(1, ref);
                    pst.setString(2, des);
                    pst.setInt(3, idbrut);
                    pst.executeUpdate();
                    System.out.println("produit add");
                    try ( ResultSet rid = pst.getGeneratedKeys()) {
                        System.out.println("id recupérer");
                        if(rid.next()){
                        int id = rid.getInt(1);
                        
                        Gamme gam =new Gamme(listoperation,id);
                        addgamme(connect,gam);

                       
                        }
                        else{
                            System.out.println("addrealiseoo not read");
                        }
                    
                   
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
                System.out.println("clé non optenu");
                ex.printStackTrace();
                System.out.println("Erreur SQL : " + ex.getMessage());
                }

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

//Attention cette fonction ne prend pas en compte l'update de la gamme voir updateOrdre
public static void updateProduit(Connection connect,int id,String ref,String des,int idbrut,ArrayList<Operation> listoperation)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE produit SET ref=?, des=?, idbrut=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, ref);
            pst.setString(2, des);
            pst.setInt(3, idbrut);
            pst.setInt(4, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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
    System.out.println("updateProduit taille listoperation "+listoperation.size());
     Gamme gam = new Gamme(listoperation,id);
     updateGamme(connect,gam);
}

public static void addrealiseoo(Connection connect, int idoperateur, int idtypeoperation) throws SQLException {
    connect.setAutoCommit(false); // stoppe la mise à jour, elle sera faite à la fin si tout se passe bien
    
    try (PreparedStatement cherchedouble = connect.prepareStatement(
            "SELECT * FROM realiseoo WHERE idoperateur=? AND idtypeoperation=?")) {
        cherchedouble.setInt(1, idoperateur);
        cherchedouble.setInt(2, idtypeoperation);

        ResultSet test = cherchedouble.executeQuery();

        if (!test.next()) {
            System.out.println("existe pas");

            try (PreparedStatement pst = connect.prepareStatement(
                    "INSERT INTO realiseoo (idoperateur, idtypeoperation) VALUES (?, ?)")) {
                pst.setInt(1, idoperateur);
                pst.setInt(2, idtypeoperation);
                pst.executeUpdate();
                System.out.println("realiseoo add");
            } catch (SQLException ex) {
                // nothing to do: maybe the constraint was not created
                System.out.println("nothing");
            }
        } else {
            System.out.println("Attention, il existe déjà");
            System.out.println("On ne peut pas l'ajouter");
        }
    } finally {
        try {
            connect.commit(); // valide le refresh
            System.out.println("le refresh fonctionne realiseoo");
        } catch (SQLException ex) {
            connect.rollback();
            System.out.print("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true); // on remet le refresh automatique
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

public static void updateTypeOperation(Connection connect,int id, String nom)throws SQLException {
     try {
        connect.setAutoCommit(false);

        String sql = "UPDATE typeoperation SET `nom`=? WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, nom);
            pst.setInt(2, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le update a été exécuté avec succès.");
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

public static void addgamme(Connection connect,Gamme gamme) throws SQLException{
    
     connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement cherchedouble = connect.prepareStatement(
                "select * from ordre where idproduit=?")) {
            cherchedouble.setInt(1, gamme.getIdproduit());
            ResultSet test = cherchedouble.executeQuery();
            if (test.next()!= false){
                System.out.println("Attention, il existe déjà");
                System.out.println("On ne peut pas l'ajouter");
            }
            else{
System.out.print("list op taille"+ gamme.getList().size());
            for(int i=0;i<gamme.getList().size()-1;i++){
                System.out.println("add gamme 1 realisoo");
            try ( PreparedStatement pst = connect.prepareStatement(
                        "INSERT INTO `ordre` (idopavant,idopapres,idproduit) VALUES (?,?,?);"

            )){
                    pst.setInt(1, gamme.getList().get(i).getId());
                    pst.setInt(2, gamme.getList().get(i+1).getId());
                    pst.setInt(3, gamme.getIdproduit());
                    pst.executeUpdate();
      
            } catch (SQLException ex) {
            // nothing to do : maybe the constraint was not created
            System.out.println("nothing");
            }
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

public static void updateGamme(Connection connect,Gamme gamme)throws SQLException {
     deleteOrdre(connect,gamme.getIdproduit());
     addgamme(connect,gamme);
}

//ATTENTION ne fonctionne pas avec gamme(ordre) et realiseoo (ne peuvent pas être supprimés seulement modifiés)
public static void delete(Connection connect, String table, int id) throws SQLException {
    //try {
        connect.setAutoCommit(false);

        String sql = "DELETE FROM "+table+" WHERE id=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le DELETE a été exécuté avec succès.");
        } catch (SQLException ex) {
            connect.rollback();
            System.out.println("Rollback. Erreur : " + ex.getMessage());
            throw ex;
        
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


public static void deleteRealiseoo(Connection connect, String colonne, int idcolonne) throws SQLException {
    //try {
        connect.setAutoCommit(false);

        String sql = "DELETE FROM realiseoo WHERE " + colonne+ "=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, idcolonne);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le DELETE realiseoo a été exécuté avec succès.");
        } catch (SQLException ex) {
            connect.rollback();
            System.out.println("Rollback. Erreur : " + ex.getMessage());
            throw ex;
        
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

public static void deleteOrdre(Connection connect, int idproduit) throws SQLException {
    try {
        connect.setAutoCommit(false);

        String sql = "DELETE FROM ordre WHERE idproduit=?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, idproduit);
            pst.executeUpdate();
            connect.commit();
            System.out.println("Le DELETE ordre a été exécuté avec succès.");
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
 
//Renvoit la liste des ateliers de la base de donné en objet Atelier
public static ArrayList<Atelier> listAtelier (Connection connect)throws SQLException{
    ArrayList<Atelier> listatelier = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from atelier")) {
            
            ResultSet ateliers = affichetab.executeQuery();
            while (ateliers.next()!= false){
                Atelier at = new Atelier(ateliers.getInt("id"),ateliers.getString("nom"),ateliers.getString("des"));
                listatelier.add(at);
            }

            }
        try { // creation d'un requete 
            connect.commit(); // valide le refresh
            System.out.println("le refresh fonctionne") ;
        } catch (SQLException ex) { // en cas d'erreur on "rollback" on retourne avant 
            connect.rollback();
            System.out.println("rollback");
            throw ex;
        } finally {
            connect.setAutoCommit(true);// on remet le refresh automatique
        }
        
     
    return listatelier;
}  

//renvoit la list des bruts de la base de donnée en ArrayList<Brut> 
public static ArrayList<Brut> listBrut (Connection connect)throws SQLException{
    ArrayList<Brut> listbrut = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from brut")) {
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Brut at = new Brut(tab.getInt("id"),tab.getString("nom"),tab.getString("ref"),tab.getString("matiere"),tab.getInt("stock"),tab.getString("dimension"),tab.getString("fournisseur"));
                listbrut.add(at);
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
        
     
    return listbrut;
}   

//renvoit la liste de toutes les machines de la base de donnée en ArrayList<Machine>
public static ArrayList<Machine> listmachine (Connection connect)throws SQLException{
    ArrayList<Machine> listmachine = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from machine")) {
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Machine at = new Machine(tab.getInt("id"),tab.getString("nom"),tab.getInt("idatelier"),tab.getInt("idtypeoperation"),tab.getString("des"),tab.getString("marque"),tab.getDouble("puissance"),tab.getInt("statut"),tab.getDouble("couthoraire"),tab.getString("localisation"),tab.getDouble("dimensionlargeur"),tab.getDouble("dimensionlongueur"));
                listmachine.add(at);
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
        
     
    return listmachine;
}  

//renvoit la liste des machines d'un atelier particulier en ArrayList<Machine>
public static ArrayList<Machine> listMachineAtelier (Connection connect, int idAtelierRecherche)throws SQLException{
    ArrayList<Machine> listmachine = new ArrayList();
    
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
    String sql = "SELECT * FROM machine WHERE idatelier=?";
    
        try ( PreparedStatement affichetab = connect.prepareStatement(sql)) {
            affichetab.setInt(1,idAtelierRecherche);
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Machine at = new Machine(tab.getInt("id"),tab.getString("nom"),tab.getInt("idatelier"),tab.getInt("idtypeoperation"),tab.getString("des"),tab.getString("marque"),tab.getDouble("puissance"),tab.getInt("statut"),tab.getDouble("couthoraire"),tab.getString("localisation"),tab.getDouble("dimensionlargeur"),tab.getDouble("dimensionlongueur"));
                listmachine.add(at);
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
        
     
    return listmachine;
}  

//renvoit la liste de tous les operateurs de la base de donnée en ArrayList<Operateur>
public static ArrayList<Operateur> listoperateur (Connection connect)throws SQLException{
    ArrayList<Operateur> listoperateur = new ArrayList();
    ArrayList<Typeoperation> listoperationvide = new ArrayList();
   
    //System.out.println("arrivé dans listoperateur");
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from operateur")) {
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Operateur at = new Operateur(tab.getInt("id"),tab.getString("identifiant"),tab.getString("motdepasse"),tab.getString("nom"),tab.getString("prenom"),tab.getInt("idatelier"),tab.getInt("statut"),tab.getInt("tel"),tab.getString("mail"),listoperationvide);
                listoperateur.add(at);
                
            }
        }
        
        //System.out.println("arrivé etape 2");
        
    for (int i=0;i<listoperateur.size();i++){

                ArrayList<Typeoperation> listtypeoperation = new ArrayList();
        listtypeoperation=listRealiseooOperateur(connect,listoperateur.get(i).getId());
            listoperateur.get(i).setListtypeoperation(listtypeoperation);
    
            }
    //System.out.println("etape 2 finit");
        
        
     
    return listoperateur;
}  

//renvoit la liste de toutes les opérations de la base de donnée en ArrayList<Operation>
public static ArrayList<Operation> listoperation (Connection connect)throws SQLException{
    ArrayList<Operation> listoperation = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from operation")) {
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Operation at = new Operation(tab.getInt("id"),tab.getInt("idtypeoperation"),tab.getString("nom"),tab.getDouble("duree"),tab.getString("outil"),tab.getInt("idmachine"));
                listoperation.add(at);
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
        
     
    return listoperation;
}  

//renvoit la liste de tous les produits de la base de donnée en ArrayList<Produit>
public static ArrayList<Produit> listproduit (Connection connect)throws SQLException{
    ArrayList<Produit> listproduit = new ArrayList();
    ArrayList<Operation> listoperation =new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from produit")) {
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Produit at = new Produit(connect, tab.getInt("id"),tab.getString("ref"),tab.getString("des"),tab.getInt("idbrut"),listoperation);
                listproduit.add(at);
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
        
        for (int i=0;i<listproduit.size();i++){
            Gamme gam = new Gamme(connect,listproduit.get(i).getId());
            listproduit.get(i).setGamme(gam);
        }
     
    return listproduit;
}  

//renvoit la liste de tous les types d'opérations de la base de donnée en ArrayList<Typeoperation> 
public static ArrayList<Typeoperation> listtypeoperation (Connection connect)throws SQLException{
    ArrayList<Typeoperation> listtypeoperation = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from typeoperation")) {
            
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                Typeoperation at = new Typeoperation(tab.getInt("id"),tab.getString("nom"));
                listtypeoperation.add(at);
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
        
     
    return listtypeoperation;
}  

//renvoit la liste des types d'opération réalisable par un opérateur particulier en ArrayList<Typeoperation>
public static ArrayList<Typeoperation> listRealiseooOperateur (Connection connect,int idoperateur)throws SQLException{
    ArrayList<Typeoperation> listtypeoperation = new ArrayList();
    
    
        try ( PreparedStatement affichetab = connect.prepareStatement(
                "select idtypeoperation from realiseoo WHERE idoperateur=?")) {
            affichetab.setInt(1, idoperateur);
            ResultSet tab = affichetab.executeQuery();
            while (tab.next()!= false){
                System.out.println("typeoperation non vide");
                Typeoperation at = new Typeoperation(connect,tab.getInt("idtypeoperation"));
                listtypeoperation.add(at);
                
                //System.out.println("listrealiseoooperateur : Creation du typop: nom :  "+ at.getNom());
            }

            }
        System.out.println("try select done");
        
        
        
        
        
        //Permet juste d'afficher la listes des noms des types operations
        String typesOperations = listtypeoperation.stream()
            .map(Typeoperation::getNom) // Supposons que Typeoperation a une méthode getNom()
            .collect(Collectors.joining(", "));

    //System.out.println("Types d'opérations via listrealiseoooperateur: " + typesOperations);
        
     
    return listtypeoperation;
}  

//renvoit la liste des id des produits qui on dans leur gamme une opération particulière en ArrayList<Integer>
public static ArrayList<Integer> listgammeoperation(Connection connect,int idoperation) throws SQLException{
      ArrayList<Integer> listidproduit = new ArrayList();
  try ( PreparedStatement affichetab = connect.prepareStatement(
                "select idproduit from ordre where idopavant=? OR idopapres=?")) {
                affichetab.setInt(1, idoperation);
                affichetab.setInt(2, idoperation);
                ResultSet tab = affichetab.executeQuery();
            
            while (tab.next()!= false){
            listidproduit.add(tab.getInt("idproduit"));
            }
  }
  HashSet<Integer> listSansDouble = new HashSet<>(listidproduit);
  listidproduit= new ArrayList<>(listSansDouble);
  return listidproduit;
}

//renvoit la liste  des opérations dans l'ordre (gamme) d'un produit particulier en ArrayList<Integer>
public static ArrayList<Operation> listgammeproduit(Connection connect,int idproduit) throws SQLException{
      ArrayList<Integer> listidavant = new ArrayList();
      ArrayList<Integer> listidapres = new ArrayList();
      ArrayList<Integer> ordre = new ArrayList();
      ArrayList<Operation> listoperation =new ArrayList();
      int idfirst=0;
      boolean find=false;
      
  try ( PreparedStatement affichetab = connect.prepareStatement(
                "select * from ordre where idproduit=?")) {
                affichetab.setInt(1, idproduit);
                ResultSet tab = affichetab.executeQuery();
            
            while (tab.next()!= false){
            listidavant.add(tab.getInt("idopavant"));
            listidapres.add(tab.getInt("idopapres"));
            }
            if (!listidavant.isEmpty()){
  
//                for(int i=0;i<listidavant.size();i++){
//                    idfirst=listidavant.get(i);
//                    
//                    for(int j=0;j<listidapres.size();j++){
//                        if (idfirst==listidapres.get(j)){
//                            find=false;
//                        }
//                        else{
//                            find=true;
//                        }
//                    }
//                    if(find==true){
//                        break;
//                    }
//                }
                
//                ordre.add(idfirst);
                ordre.add(listidavant.get(0));

                for (int j=0;j<listidapres.size();j++){
//                     try ( PreparedStatement idapres = connect.prepareStatement(
//                    "select idopapres from ordre where idopavant=?")) {
//                         
//                         idapres.setInt(1, ordre.get(j));
//                         System.out.println("statement ready");
//                         ResultSet tabidopapres = idapres.executeQuery();
//                         System.out.println("resultat recup");
//                          while (tabidopapres.next()!= false){
//                              System.out.println("dans le while");
//                             ordre.add(tabidopapres.getInt("idopapres"));
//                             System.out.println("ordre add à sa list =" + ordre.get(j+1));
//                          }
//                     } catch(SQLException ex) {
//                          System.out.println("Rollback. Erreur : " + ex.getMessage());
//                     }
                        ordre.add(listidapres.get(j));
                }
            }
            else {
                ordre = new ArrayList();
            }
            }
  
  for (int i=0;i<ordre.size();i++){
      Operation op =new Operation(connect,ordre.get(i));
      listoperation.add(op);
  }
  return listoperation;
}

//renvoit la liste de toutes les gammes de la base de donnée en ArrayList<Gamme> 
public static ArrayList<Gamme> listgamme(Connection connect) throws SQLException{
    
      ArrayList<Gamme> listgamme = new ArrayList();
      ArrayList<Integer> listidproduit = new ArrayList();

      ArrayList<Operation> listoperation = new ArrayList();

    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement affichetabid = connect.prepareStatement(
                "select idproduit from ordre")) {
            
            ResultSet tabid = affichetabid.executeQuery();
            
            while (tabid.next()!= false){
                listidproduit.add(tabid.getInt("idproduit"));
            }
            //Suppression des doubles (listidproduit est maintenant sans double)
            Set<Integer> idProdSansDouble = new HashSet<>(listidproduit);
            listidproduit = new ArrayList<>(idProdSansDouble);
            
            for(int i=0;i<listidproduit.size();i++){
            listoperation = listgammeproduit(connect,listidproduit.get(i));
            listgamme.add(new Gamme(listoperation,listidproduit.get(i)));
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
        
     
    return listgamme;

}


//renvoit les id des enfants dans une table d'un elément particulier d'un table en ArrayList<Integer>
public static ArrayList<Integer> listchild(Connection connect,String tabparentname,int idparent,String tabchild) throws SQLException{
    ArrayList<Integer> listchild = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement idtab = connect.prepareStatement(
                "select id from ? where ?=?")) {
            idtab.setString(1, tabchild);
            idtab.setString(2,"id"+tabparentname);
            idtab.setInt(3, idparent);
            ResultSet tab = idtab.executeQuery();
            while (tab.next()!= false){
                listchild.add(tab.getInt("id"));
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
        
     
    return listchild;
}

//Donne la liste des id operateurs qui sont en lien avec un type opération en ArrayList<Integer>
public static ArrayList<Integer> listchildrealiseOperateur(Connection connect,int id) throws SQLException{
    ArrayList<Integer> listchild = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement idtab = connect.prepareStatement(
                "select idoperateur from realiseoo where idtypeoperation=?")) {
            idtab.setInt(1, id);
            ResultSet tab = idtab.executeQuery();
            while (tab.next()!= false){
                listchild.add(tab.getInt("idoperateur"));
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
          
    return listchild;
}

//renvoit les id des types opérateurs en lien avec un opérateur en ArrayList<Integer>
public static ArrayList<Integer> listChildRealiseOperation(Connection connect,int id) throws SQLException{
    ArrayList<Integer> listchild = new ArrayList();
    
    connect.setAutoCommit(false); //stope la mise à jour, elle sera fait à la fin si tout se passe bien
        try ( PreparedStatement idtab = connect.prepareStatement(
                "select idtypeoperation from realiseoo where idoperateur=?")) {
            idtab.setInt(1, id);
            ResultSet tab = idtab.executeQuery();
            while (tab.next()!= false){
                listchild.add(tab.getInt("idtypeoperation"));
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
          
    return listchild;
}

    public Connection getConn() {
        return conn;
    }


    public static void main(String[] args) {
        System.out.println("Bonjour et bienvenue");
       Initialisation() ;
    }
}
